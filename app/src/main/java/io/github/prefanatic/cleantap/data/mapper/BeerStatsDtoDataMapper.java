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

package io.github.prefanatic.cleantap.data.mapper;

import io.github.prefanatic.cleantap.data.dto.BeerStatsDto;
import io.github.prefanatic.cleantap.domain.Beer;

/**
 * Created by cody on 11/29/15.
 */
public class BeerStatsDtoDataMapper {
    public Beer transform(BeerStatsDto dto) {
        Beer beer = new Beer();
        beer.abv = dto.beer.beer_abv;
        beer.authRating = dto.beer.auth_rating;
        beer.breweryId = dto.brewery.brewery_id;
        beer.checkinCount = dto.checkin_count;
        beer.createdAt = dto.beer.created_at;
        beer.description = dto.beer.beer_description;
        beer.ibu = dto.beer.beer_ibu;
        beer.id = dto.beer.bid;
        beer.inProduction = dto.beer.in_production;
        beer.labelUrl = dto.beer.beer_label;
        beer.name = dto.beer.beer_name;
        beer.style = dto.beer.beer_style;
        beer.wishlist = dto.beer.wish_list;

        return beer;
    }
}
