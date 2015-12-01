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

package io.github.prefanatic.cleantap.data.dto;

import com.hannesdorfmann.sqlbrite.objectmapper.annotation.Column;
import com.hannesdorfmann.sqlbrite.objectmapper.annotation.ObjectMappable;

import java.io.Serializable;

import io.github.prefanatic.cleantap.data.Database;
import rx.Observable;

@ObjectMappable
public class BeerStatsDto implements Serializable {
    public static final String TABLE_NAME = "beer_stats";
    public static final String COL_CHECKIN_COUNT = "checkinCount";
    public static final String COL_YOUR_COUNT = "yourCount";
    public static final String COL_HAVE_HAD = "haveHad";
    public static final String COL_TIME_LOOKED_AT = "timeLookedAt";
    public static final String COL_FAVORITE = "favorite";
    public static final String COL_USER_RATING = "userRating";
    public static final String COL_ID = "id";
    public static final String COL_BREWERY_ID = "breweryId";

    // DTO Fields
    @Column(COL_CHECKIN_COUNT) public long checkin_count;
    @Column(COL_HAVE_HAD) public boolean have_had;
    @Column(COL_YOUR_COUNT) public int your_count;

    // These may or may not be null, depending on if this is loaded from disk or through retrofit.
    public BeerDto beer;
    public Brewery brewery;

    // Persistence Fields
    @Column(COL_ID) public long id;
    @Column(COL_BREWERY_ID) public long breweryId;
    @Column(COL_TIME_LOOKED_AT) public long timeLookedAt;
    @Column(COL_FAVORITE) public boolean favorite;
    @Column(COL_USER_RATING) public int userRating;

    public BeerStatsDto() {
    }

    public Observable<BeerDto> getBeerDto() {
        if (beer != null)
            return Observable.just(beer);

        return Database.get().getBeerDao().getBeer((int) id)
                .doOnNext(b -> beer = b);
    }

    public Observable<Brewery> getBrewery() {
        if (brewery != null)
            return Observable.just(brewery);

        return Database.get().getBreweryDao().getBrewery((int) breweryId)
                .doOnNext(b -> brewery = b);
    }
}
