package io.github.prefanatic.cleantap.data.dto;

import java.io.Serializable;

public class Beer implements Serializable {
    public int bid, in_production;
    public float beer_abv, beer_ibu, auth_rating;
    public String beer_name, beer_label, beer_description, created_at, beer_style;
    public boolean wish_list;
}
