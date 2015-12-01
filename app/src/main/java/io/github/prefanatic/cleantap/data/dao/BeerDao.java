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

package io.github.prefanatic.cleantap.data.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.hannesdorfmann.sqlbrite.dao.Dao;

import io.github.prefanatic.cleantap.data.dto.BeerDto;
import io.github.prefanatic.cleantap.data.dto.BeerDtoMapper;
import rx.Observable;
import timber.log.Timber;

public class BeerDao extends Dao {
    @Override
    public void createTable(SQLiteDatabase database) {
        CREATE_TABLE(BeerDto.TABLE_NAME,
                BeerDto.COL_ID + " INTEGER PRIMARY KEY NOT NULL",
                BeerDto.COL_PRODUCTION + " INTEGER",
                BeerDto.COL_ABV + " FLOAT",
                BeerDto.COL_IBU + " FLOAT",
                BeerDto.COL_AUTH_RATING + " FLOAT",
                BeerDto.COL_NAME + " STRING",
                BeerDto.COL_LABEL + " STRING",
                BeerDto.COL_DESCRIPTION + " STRING",
                BeerDto.COL_CREATED_AT + " STRING",
                BeerDto.COL_STYLE + " STRING",
                BeerDto.COL_WISHLIST + " BOOLEAN").execute(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Observable<BeerDto> getBeer(int beerId) {
        return query(
                SELECT("*")
                        .FROM(BeerDto.TABLE_NAME)
                        .WHERE(BeerDto.COL_ID + " = ?")
        ).args(String.valueOf(beerId))
                .run()
                .mapToOne(BeerDtoMapper.MAPPER);
    }

    public Observable<Long> addBeer(BeerDto beerDto) {
        return insert(BeerDto.TABLE_NAME, mapValues(beerDto), SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Observable<Integer> updateBeer(BeerDto beerDto) {
        return update(BeerDto.TABLE_NAME, mapValues(beerDto), "id = ?", String.valueOf(beerDto.bid));
    }

    private ContentValues mapValues(BeerDto beerDto) {
        return BeerDtoMapper.contentValues()
                .bid(beerDto.bid)
                .in_production(beerDto.in_production)
                .beer_abv(beerDto.beer_abv)
                .beer_ibu(beerDto.beer_ibu)
                .auth_rating(beerDto.auth_rating)
                .beer_name(beerDto.beer_name)
                .beer_label(beerDto.beer_label)
                .beer_description(beerDto.beer_description)
                .created_at(beerDto.created_at)
                .beer_style(beerDto.beer_style)
                .wish_list(beerDto.wish_list).build();
    }
}
