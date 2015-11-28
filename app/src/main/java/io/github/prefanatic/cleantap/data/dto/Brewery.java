package io.github.prefanatic.cleantap.data.dto;

import com.hannesdorfmann.sqlbrite.objectmapper.annotation.ObjectMappable;

import java.io.Serializable;

@ObjectMappable
public class Brewery implements Serializable {
    public static final String TABLE_NAME = "brewery";
    public static final String COL_ID = "id";
    public static final String COL_ACTIVE = "active";
    public static final String COL_NAME = "name";
    public static final String COL_SLUG = "slug";
    public static final String COL_LABEL = "label";
    public static final String COL_COUNTRY = "countryName";


    public int brewery_id, brewery_active;
    public String brewery_name, brewery_slug, brewery_label, country_name;
    public Contact contact;
    public Location location;

    public Brewery() {
    }
}
