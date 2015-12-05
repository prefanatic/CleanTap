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

package io.github.prefanatic.cleantap.data;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import io.github.prefanatic.cleantap.data.dto.BeerExtended;
import io.github.prefanatic.cleantap.data.dto.BeerStatsDto;
import io.github.prefanatic.cleantap.data.dto.CheckInResponse;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class RxUntappdApi {
    private UntappdInterface api;
    private Map<String, String> authOptions = new HashMap<>();

    public RxUntappdApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.untappd.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        authOptions.put("client_id", "EFED9A1C92001BD10DEE9BA371181CBA0B0D831A");
        authOptions.put("client_secret", "EBEFFF7DE85FAFB4F7994A3059277A1859396818");

        api = retrofit.create(UntappdInterface.class);
    }

    public void setAuthToken(String authToken) {
        authOptions.clear();
        authOptions.put("access_token", authToken);
    }

    public Observable<BeerStatsDto> searchForBeers(String beers) {
       return api.searchBeers(authOptions, beers, 0, 25, "checkin")
               .flatMapIterable(response -> response.response.beers.items)
               .doOnNext(stats -> {
                   stats.id = stats.beer.bid;
                   stats.breweryId = stats.brewery.brewery_id;
               })
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .doOnError(err -> Timber.e("Search failed"));
    }

    public Observable<BeerExtended> getBeerInfo(long beerId) {
        return api.beerInfo(beerId, authOptions, "false")
                .map(response -> response.response.beer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(err -> Timber.e("Beer Info failed"));
    }

    public Observable<CheckInResponse> checkinBeer(long beerId, String shout, float rating, Double geolat, Double geolng) {
        TimeZone zone = TimeZone.getDefault();

        return api.checkInBeer(authOptions, String.valueOf(zone.getRawOffset() / 3600000), zone.getID(), beerId, null, geolat, geolng, shout, String.format("%.2f", rating), null, null, null)
                .map(response -> response.response)
                .doOnError(err -> Timber.e(err, "Check-in failed"));
    }


}
