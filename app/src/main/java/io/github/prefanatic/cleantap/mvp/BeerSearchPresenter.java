package io.github.prefanatic.cleantap.mvp;

import android.content.SharedPreferences;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.github.prefanatic.cleantap.common.PreferenceKeys;
import io.github.prefanatic.cleantap.data.Database;
import io.github.prefanatic.cleantap.data.RxUntappdApi;
import io.github.prefanatic.cleantap.data.dto.BeerStats;
import io.github.prefanatic.cleantap.injection.Injector;

public class BeerSearchPresenter extends MvpBasePresenter<BeerSearchView> {
    @Inject RxUntappdApi api;
    @Inject SharedPreferences preferences;
    @Inject Database database;

    private List<BeerStats> persistBeers;

    public BeerSearchPresenter() {
        Injector.INSTANCE.getApplicationComponent().inject(this);
    }

    @Override
    public void attachView(BeerSearchView view) {
        super.attachView(view);

        // TODO: 11/25/2015 Do we move this into an observable?  Does this take execution time?
        persistBeers = database.getBeerStatsDao().getStats().toBlocking().first();
        sortPersisted();
    }

    private void sortPersisted() {
        Collections.sort(persistBeers, (lhs, rhs) -> lhs.timeLookedAt < rhs.timeLookedAt ? -1 : (lhs.timeLookedAt == rhs.timeLookedAt ? 0 : 1));
    }

    private int countRecentPersist() {
        int r = 0;

        for (BeerStats beer : persistBeers)
            if (!beer.favorite) r++;
        return r;
    }

    public boolean addBeerIfNotPeristed(BeerStats stats) {
        int i = persistBeers.indexOf(stats);
        if (i != -1) return false;

        handleRecentSizeStorage();
        persistBeers.add(stats);
        sortPersisted();

        database.getBeerStatsDao().addBeerStats(stats).subscribe();
        database.getBeerDao().saveBeer(stats.beer).subscribe();
        database.getBreweryDao().saveBrewery(stats.brewery).subscribe();

        return true;
    }

    public void persistBeer(BeerStats stats) {
        stats.timeLookedAt = System.currentTimeMillis();
        if (!addBeerIfNotPeristed(stats)) {
            database.getBeerStatsDao().updateBeerStats(stats);
            database.getBeerDao().saveBeer(stats.beer);
            database.getBreweryDao().saveBrewery(stats.brewery);
        }
    }

    private void handleRecentSizeStorage() {
        if (countRecentPersist() >= preferences.getInt(PreferenceKeys.RECENT_STORE_SIZES, 15)) {
            BeerStats toRemove = persistBeers.get(persistBeers.size() - 1);
            persistBeers.remove(toRemove);
            database.getBeerStatsDao().deleteBeerStats(toRemove);
        }
    }

    public void searchForLocalBeer(String query) {
        for (BeerStats stats : persistBeers) {
            stats.getBeer()
                    .filter(beer -> beer.beer_name.toLowerCase().contains(query.toLowerCase()))
                    .subscribe(beer -> {
                        if (stats.favorite)
                            getView().foundFavoriteBeer(stats);
                        else
                            getView().foundRecentBeer(stats);
                    });
        }
    }

    public void searchForBeer(String query) {
        api.searchForBeers(query)
                .subscribe(this::beersFound);
    }

    public void beersFound(BeerStats stats) {
        if (isViewAttached())
            getView().foundBeer(stats);
    }
}
