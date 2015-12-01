/*
 * Copyright 2015 Cody Goldberg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.prefanatic.cleantap.mvp;

import android.content.SharedPreferences;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.github.prefanatic.cleantap.common.PreferenceKeys;
import io.github.prefanatic.cleantap.common.RecyclerViewUpdateEvent;
import io.github.prefanatic.cleantap.data.Database;
import io.github.prefanatic.cleantap.data.RxUntappdApi;
import io.github.prefanatic.cleantap.data.dto.BeerStatsDto;
import io.github.prefanatic.cleantap.injection.Injector;
import rx.Subscription;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class BeerSearchPresenter extends MvpBasePresenter<BeerSearchView> {
    @Inject RxUntappdApi api;
    @Inject SharedPreferences preferences;
    @Inject Database database;

    private List<BeerStatsDto> persistBeers;
    private Subscription searchSub;

    private RecyclerViewUpdateEvent<BeerStatsDto> favoriteSet, recentSet, networkSet;

    public BeerSearchPresenter() {
        Injector.INSTANCE.getApplicationComponent().inject(this);

        favoriteSet = new RecyclerViewUpdateEvent<>();
        recentSet = new RecyclerViewUpdateEvent<>();
        networkSet = new RecyclerViewUpdateEvent<>();
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
            if (stats.beer.beer_name.toLowerCase().contains(query.toLowerCase())) {
                Timber.d("Found %s from %s", stats.beer.beer_name, query);
                if (stats.favorite)
                    favoriteSet.addToCache(stats);
                else
                    recentSet.addToCache(stats);
            }
        }

        favoriteSet.updateFromCache();
        recentSet.updateFromCache();

        Timber.d("%s", recentSet.toString());

        if (isViewAttached()) {
            getView().foundFavoriteBeer(favoriteSet);
            getView().foundRecentBeer(recentSet);
        }
    }

    public void searchForBeer(String query) {
        if (query.isEmpty())
            return;

        if (searchSub != null && !searchSub.isUnsubscribed())
            searchSub.unsubscribe();

        searchSub = api.searchForBeers(query)
                .unsubscribeOn(Schedulers.io()) // Fixes https://github.com/square/okhttp/issues/1592
                .subscribe(networkSet::addToCache, err -> {
                    if (isViewAttached())
                        getView().error(err);
                }, () -> {
                    networkSet.updateFromCache();
                    if (isViewAttached())
                        getView().foundBeer(networkSet);
                });
    }
}
