package io.github.prefanatic.cleantap.data.dto;

public class BeerExtended {
    public int bid, is_in_production, is_homebrew;
    public String beer_name, beer_label, beer_description, beer_style, beer_slug, created_at;
    public double beer_abv, beer_ibu, rating_score, auth_rating;
    public long rating_count;
    public Stats stats;
    public Brewery brewery;
    public MediaList media;
}
