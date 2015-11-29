package io.github.prefanatic.cleantap.domain;

/**
 * Created by cody on 11/29/15.
 */
public class Beer {
    public long id;
    public long breweryId;
    public long checkinCount;
    public long timeLookedAt;

    public String name;
    public String labelUrl;
    public String description;
    public String style;
    public String createdAt;

    public boolean wishlist;
    public boolean favorite;

    public int inProduction;
    public int userRating;

    public float abv;
    public float ibu;
    public float authRating;
}
