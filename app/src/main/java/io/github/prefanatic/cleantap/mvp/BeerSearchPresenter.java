package io.github.prefanatic.cleantap.mvp;

import android.content.SharedPreferences;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.github.prefanatic.cleantap.common.PreferenceKeys;
import io.github.prefanatic.cleantap.data.Database;
import io.github.prefanatic.cleantap.data.RxUntappdApi;
import io.github.prefanatic.cleantap.data.dto.BeerStatsDto;
import io.github.prefanatic.cleantap.injection.Injector;

public class BeerSearchPresenter extends MvpBasePresenter<BeerSearchView> {
    @Inject RxUntappdApi api;
    @Inject SharedPreferences preferences;
    @Inject Database database;

    private List<BeerStatsDto> persistBeers;

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
        Collections.sort(persistBeers, (rhs, lhs) -> lhs.timeLookedAt < rhs.timeLookedAt ? -1 : (lhs.timeLookedAt == rhs.timeLookedAt ? 0 : 1));
    }

    private int countRecentPersist() {
        int r = 0;

        for (BeerStatsDto beer : persistBeers)
            if (!beer.favorite) r++;
        return r;
    }

    public boolean addBeerIfNotPeristed(BeerStatsDto stats) {
        int i = persistBeers.indexOf(stats);
        if (i != -1) return false;

        handleRecentSizeStorage();
        persistBeers.add(stats);
        sortPersisted();

        database.getBeerStatsDao().addBeerStats(stats);
        database.getBeerDao().addBeer(stats.beer);
        database.getBreweryDao().addBrewery(stats.brewery);

        return true;
    }

    public void persistBeer(BeerStatsDto stats) {
        stats.timeLookedAt = System.currentTimeMillis();
        if (!addBeerIfNotPeristed(stats)) {
            database.getBeerStatsDao().addBeerStats(stats);
            database.getBeerDao().addBeer(stats.beer);
            database.getBreweryDao().addBrewery(stats.brewery);
        }
    }

    private void handleRecentSizeStorage() {
        if (countRecentPersist() >= preferences.getInt(PreferenceKeys.RECENT_STORE_SIZES, 15)) {
            BeerStatsDto toRemove = persistBeers.get(persistBeers.size() - 1);
            persistBeers.remove(toRemove);
            database.getBeerStatsDao().deleteBeerStats(toRemove);
        }
    }

    public void searchForLocalBeer(String query) {
        for (BeerStatsDto stats : persistBeers) {
            stats.getBeerDto()
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

    public void beersFound(BeerStatsDto stats) {
        if (isViewAttached())
            getView().foundBeer(stats);
    }
}
