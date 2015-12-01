/*
 * Copyright 2015 Cody Goldberg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
