package io.github.prefanatic.cleantap.data.dto;

import com.hannesdorfmann.sqlbrite.objectmapper.annotation.Column;
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
    public static final String COL_CONTACT = "contact";
    public static final String COL_LOCATION = "location";

    @Column(COL_ID) public int brewery_id;
    @Column(COL_ACTIVE) public int brewery_active;
    @Column(COL_NAME) public String brewery_name;
    @Column(COL_SLUG) public String brewery_slug;
    @Column(COL_LABEL) public String brewery_label;
    @Column(COL_COUNTRY) public String country_name;
    @Column(COL_CONTACT) public String contactJson;
    @Column(COL_LOCATION) public String locationJson;

    public Contact contact;
    public Location location;

    public Brewery() {
    }
}
