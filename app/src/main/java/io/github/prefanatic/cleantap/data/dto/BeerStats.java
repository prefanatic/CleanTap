package io.github.prefanatic.cleantap.data.dto;

import java.io.Serializable;

public class BeerStats implements Serializable {
    public long checkin_count;
    public boolean have_had;
    public int your_count;
    public Beer beer;
    public Brewery brewery;
}
