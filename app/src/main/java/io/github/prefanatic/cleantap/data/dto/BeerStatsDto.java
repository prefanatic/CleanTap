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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BeerStatsDto that = (BeerStatsDto) o;

        if (checkin_count != that.checkin_count) return false;
        if (have_had != that.have_had) return false;
        if (your_count != that.your_count) return false;
        if (id != that.id) return false;
        if (breweryId != that.breweryId) return false;
        if (timeLookedAt != that.timeLookedAt) return false;
        if (favorite != that.favorite) return false;
        if (userRating != that.userRating) return false;
        if (beer != null ? !beer.equals(that.beer) : that.beer != null) return false;
        return !(brewery != null ? !brewery.equals(that.brewery) : that.brewery != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (checkin_count ^ (checkin_count >>> 32));
        result = 31 * result + (have_had ? 1 : 0);
        result = 31 * result + your_count;
        result = 31 * result + (beer != null ? beer.hashCode() : 0);
        result = 31 * result + (brewery != null ? brewery.hashCode() : 0);
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (breweryId ^ (breweryId >>> 32));
        result = 31 * result + (int) (timeLookedAt ^ (timeLookedAt >>> 32));
        result = 31 * result + (favorite ? 1 : 0);
        result = 31 * result + userRating;
        return result;
    }
}
