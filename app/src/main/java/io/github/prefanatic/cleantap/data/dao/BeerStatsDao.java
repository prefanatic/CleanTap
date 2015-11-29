package io.github.prefanatic.cleantap.data.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.hannesdorfmann.sqlbrite.dao.Dao;

import java.util.List;

import io.github.prefanatic.cleantap.data.Database;
import io.github.prefanatic.cleantap.data.dto.BeerStatsDto;
import io.github.prefanatic.cleantap.data.dto.BeerStatsDtoMapper;
import rx.Observable;

public class BeerStatsDao extends Dao {
    @Override
    public void createTable(SQLiteDatabase database) {
        CREATE_TABLE(BeerStatsDto.TABLE_NAME,
                BeerStatsDto.COL_ID + " INTEGER PRIMARY KEY NOT NULL",
                BeerStatsDto.COL_CHECKIN_COUNT + " INTEGER",
                BeerStatsDto.COL_FAVORITE + " BOOLEAN",
                BeerStatsDto.COL_HAVE_HAD + " INTEGER",
                BeerStatsDto.COL_TIME_LOOKED_AT + " LONG",
                BeerStatsDto.COL_USER_RATING + " INTEGER",
                BeerStatsDto.COL_BREWERY_ID + " INTEGER",
                BeerStatsDto.COL_YOUR_COUNT + " INTEGER").execute(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Observable<List<BeerStatsDto>> getStats() {
        return query(
                SELECT("*")
                        .FROM(BeerStatsDto.TABLE_NAME)
        ).run()
                .mapToList(BeerStatsDtoMapper.MAPPER)
                .doOnNext(list -> {
                    for (BeerStatsDto dto : list) {
                        mergeBeerAndBrewery(dto);
                    }
                });
    }

    private void mergeBeerAndBrewery(BeerStatsDto dto) {
        dto.brewery = Database.get().getBreweryDao().getBrewery((int) dto.breweryId).toBlocking().first();
        dto.beer = Database.get().getBeerDao().getBeer((int) dto.id).toBlocking().first();
    }

    public Observable<Long> addBeerStats(BeerStatsDto stats) {
        return insert(BeerStatsDto.TABLE_NAME, mapValues(stats), SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Observable<Integer> updateBeerStats(BeerStatsDto stats) {
        return update(BeerStatsDto.TABLE_NAME, mapValues(stats), "id = ?", String.valueOf(stats.id));
    }

    public Observable<Integer> deleteBeerStats(BeerStatsDto stats) {
        return delete(BeerStatsDto.TABLE_NAME, "id = ?", String.valueOf(stats.id));
    }

    private ContentValues mapValues(BeerStatsDto stats) {
        return BeerStatsDtoMapper.contentValues()
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
