package io.github.prefanatic.cleantap.data.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.hannesdorfmann.sqlbrite.dao.Dao;

import java.util.List;

import io.github.prefanatic.cleantap.data.dto.BeerStats;
import io.github.prefanatic.cleantap.data.dto.BeerStatsMapper;
import rx.Observable;

public class BeerStatsDao extends Dao {
    @Override
    public void createTable(SQLiteDatabase database) {
        CREATE_TABLE(BeerStats.TABLE_NAME,
                BeerStats.COL_ID + " INTEGER PRIMARY KEY NOT NULL",
                BeerStats.COL_CHECKIN_COUNT + " INTEGER",
                BeerStats.COL_FAVORITE + " BOOLEAN",
                BeerStats.COL_HAVE_HAD + " INTEGER",
                BeerStats.COL_TIME_LOOKED_AT + " LONG",
                BeerStats.COL_USER_RATING + " INTEGER",
                BeerStats.COL_BREWERY_ID + " INTEGER",
                BeerStats.COL_YOUR_COUNT + " INTEGER").execute(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Observable<List<BeerStats>> getStats() {
        return query(
                SELECT("*")
                        .FROM(BeerStats.TABLE_NAME)
        ).run().mapToList(BeerStatsMapper.MAPPER);
    }

    public Observable<Long> addBeerStats(BeerStats stats) {
        return insert(BeerStats.TABLE_NAME, mapValues(stats));
    }

    public Observable<Integer> updateBeerStats(BeerStats stats) {
        return update(BeerStats.TABLE_NAME, mapValues(stats), "id = ?", String.valueOf(stats.id));
    }

    public Observable<Integer> deleteBeerStats(BeerStats stats) {
        return delete(BeerStats.TABLE_NAME, "id = ?", String.valueOf(stats.id));
    }

    private ContentValues mapValues(BeerStats stats) {
        return BeerStatsMapper.contentValues()
                .id(stats.id)
                .checkin_count(stats.checkin_count)
                .favorite(stats.favorite)
                .have_had(stats.have_had)
                .timeLookedAt(stats.timeLookedAt)
                .userRating(stats.userRating)
                .your_count(stats.your_count)
                .breweryId(stats.breweryId)
                .build();
    }

}
