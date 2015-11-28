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
