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

import android.content.Context;

import com.hannesdorfmann.sqlbrite.dao.DaoManager;

import io.github.prefanatic.cleantap.data.dao.BeerDao;
import io.github.prefanatic.cleantap.data.dao.BeerStatsDao;
import io.github.prefanatic.cleantap.data.dao.BreweryDao;
import io.github.prefanatic.cleantap.data.dto.Brewery;

public class Database {
    public static final int VERSION = 1;
    private static Database INSTANCE;

    private BeerStatsDao beerStatsDao;
    private BeerDao beerDao;
    private BreweryDao breweryDao;

    public static Database get() {
        return INSTANCE;
    }

    public Database(Context context) {
        INSTANCE = this;

        beerStatsDao = new BeerStatsDao();
        beerDao = new BeerDao();
        breweryDao = new BreweryDao();

        DaoManager manager = new DaoManager(context, "cleantap.db", VERSION, beerStatsDao, beerDao, breweryDao);
        manager.setLogging(true);
    }

    public BeerStatsDao getBeerStatsDao() {
        return beerStatsDao;
    }

    public BeerDao getBeerDao() {
        return beerDao;
    }

    public BreweryDao getBreweryDao() {
        return breweryDao;
    }
}
