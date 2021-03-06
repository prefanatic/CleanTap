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

import com.google.gson.Gson;
import com.hannesdorfmann.sqlbrite.dao.Dao;

import io.github.prefanatic.cleantap.data.dto.Brewery;
import io.github.prefanatic.cleantap.data.dto.BreweryMapper;
import io.github.prefanatic.cleantap.data.dto.Contact;
import io.github.prefanatic.cleantap.data.dto.Location;
import rx.Observable;

public class BreweryDao extends Dao {
    Gson gson = new Gson();

    @Override
    public void createTable(SQLiteDatabase database) {
        CREATE_TABLE(Brewery.TABLE_NAME,
                Brewery.COL_ID + " INTEGER PRIMARY KEY NOT NULL",
                Brewery.COL_ACTIVE + " INTEGER",
                Brewery.COL_COUNTRY + " STRING",
                Brewery.COL_LABEL + " STRING",
                Brewery.COL_NAME + " STRING",
                Brewery.COL_LOCATION + " STRING",
                Brewery.COL_CONTACT + " STRING",
                Brewery.COL_SLUG + " STRING").execute(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Observable<Brewery> getBrewery(int id) {
        return query(
                SELECT("*")
                        .FROM(Brewery.TABLE_NAME)
                        .WHERE(Brewery.COL_ID + " = ?")
        ).args(String.valueOf(id))
                .run()
                .mapToOne(BreweryMapper.MAPPER)
                .doOnNext(this::loadContactAndLocation);
    }

    // I don't even care!
    private void loadContactAndLocation(Brewery brewery) {
        brewery.contact = gson.fromJson(brewery.contactJson, Contact.class);
        brewery.location = gson.fromJson(brewery.locationJson, Location.class);
    }

    private void serializeContactAndLocation(Brewery brewery) {
        if (brewery.contactJson == null) {
            brewery.contactJson = gson.toJson(brewery.contact);
        }
        if (brewery.locationJson == null) {
            brewery.locationJson = gson.toJson(brewery.location);
        }
    }

    public Observable<Long> addBrewery(Brewery brewery) {
        serializeContactAndLocation(brewery);
        return insert(Brewery.TABLE_NAME, mapValues(brewery), SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Observable<Integer> updateBrewery(Brewery brewery) {
        serializeContactAndLocation(brewery);
        return update(Brewery.TABLE_NAME, mapValues(brewery), "id = ?", String.valueOf(brewery.brewery_id));
    }

    public ContentValues mapValues(Brewery brewery) {
        return BreweryMapper.contentValues()
                .brewery_id(brewery.brewery_id)
                .brewery_active(brewery.brewery_active)
                .brewery_label(brewery.brewery_label)
                .brewery_slug(brewery.brewery_slug)
                .country_name(brewery.country_name)
                .brewery_name(brewery.brewery_name)
                .contactJson(brewery.contactJson)
                .locationJson(brewery.locationJson)
                .build();
    }
}
