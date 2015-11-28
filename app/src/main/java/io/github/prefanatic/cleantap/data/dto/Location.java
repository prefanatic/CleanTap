package io.github.prefanatic.cleantap.data.dto;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Location implements Serializable {
    public String brewery_city, brewery_state;
    public double lat, lng;
}
