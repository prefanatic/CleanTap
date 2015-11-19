package io.github.prefanatic.cleantap.data.dto;

import java.io.Serializable;

public class Brewery implements Serializable {
    public int brewery_id, brewery_active;
    public String brewery_name, brewery_slug, brewery_label, country_name;
    public Contact contact;
    public Location location;
}
