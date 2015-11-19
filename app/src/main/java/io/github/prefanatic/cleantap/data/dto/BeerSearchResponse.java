package io.github.prefanatic.cleantap.data.dto;

import java.util.List;

public class BeerSearchResponse {
    public String message, search_type, term, parsed_term;
    public boolean brewery_id;
    public int type_id, search_version, found, offset, limit;
    public BeerList beers;

    public static class BeerList {
        public int count;
        public List<BeerStats> items;
    }
}
