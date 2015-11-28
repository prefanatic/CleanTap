package io.github.prefanatic.cleantap.data.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.hannesdorfmann.sqlbrite.dao.Dao;

import io.github.prefanatic.cleantap.data.dto.Beer;
import io.github.prefanatic.cleantap.data.dto.BeerMapper;
import io.github.prefanatic.cleantap.data.dto.BeerStats;
import io.github.prefanatic.cleantap.data.dto.Brewery;
import rx.Observable;

public class BeerDao extends Dao {
    @Override
    public void createTable(SQLiteDatabase database) {
        CREATE_TABLE(Beer.TABLE_NAME,
                Beer.COL_ID + " INTEGER PRIMARY KEY NOT NULL",
                Beer.COL_PRODUCTION + " INTEGER",
                Beer.COL_ABV + " FLOAT",
                Beer.COL_IBU + " FLOAT",
                Beer.COL_AUTH_RATING + " FLOAT",
                Beer.COL_NAME + " STRING",
                Beer.COL_LABEL + " STRING",
                Beer.COL_DESCRIPTION + " STRING",
                Beer.COL_CREATED_AT + " STRING",
                Beer.COL_STYLE + " STRING",
                Beer.COL_WISHLIST + " BOOLEAN").execute(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Observable<Beer> getBeer(int beerId) {
        return query(
                SELECT("*")
                        .FROM(Beer.TABLE_NAME)
                        .WHERE(Beer.COL_ID + " = ?")
        ).args(String.valueOf(beerId))
                .run()
                .mapToOne(BeerMapper.MAPPER);
    }

    public Observable saveBeer(Beer beer) {
        return getBeer(beer.bid)
                .firstOrDefault(null)
                .flatMap(b -> {
                    if (b == null) {
                        return addBeer(beer);
                    }

                    return updateBeer(beer);
                });
    }

    public Observable<Long> addBeer(Beer beer) {
        return insert(Beer.TABLE_NAME, mapValues(beer));
    }

    public Observable<Integer> updateBeer(Beer beer) {
        return update(Beer.TABLE_NAME, mapValues(beer), "id = ?", String.valueOf(beer.bid));
    }

    private ContentValues mapValues(Beer beer) {
        return BeerMapper.contentValues()
                .bid(beer.bid)
                .in_production(beer.in_production)
                .beer_abv(beer.beer_abv)
                .beer_ibu(beer.beer_ibu)
                .auth_rating(beer.auth_rating)
                .beer_name(beer.beer_name)
                .beer_label(beer.beer_label)
                .beer_description(beer.beer_description)
                .created_at(beer.created_at)
                .beer_style(beer.beer_style)
                .wish_list(beer.wish_list).build();
    }
}
