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

import com.hannesdorfmann.sqlbrite.objectmapper.annotation.Column;
import com.hannesdorfmann.sqlbrite.objectmapper.annotation.ObjectMappable;

import java.io.Serializable;

@ObjectMappable
public class BeerDto implements Serializable {
    public static final String TABLE_NAME = "beer";
    public static final String COL_ID = "id";
    public static final String COL_PRODUCTION = "production";
    public static final String COL_ABV = "abv";
    public static final String COL_IBU = "ibu";
    public static final String COL_AUTH_RATING = "authRating";
    public static final String COL_NAME = "name";
    public static final String COL_LABEL = "label";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_CREATED_AT = "createdAt";
    public static final String COL_STYLE = "style";
    public static final String COL_WISHLIST = "wishlist";

    @Column(COL_ID) public int bid;
    @Column(COL_PRODUCTION) public int in_production;
    @Column(COL_ABV) public float beer_abv;
    @Column(COL_IBU) public float beer_ibu;
    @Column(COL_AUTH_RATING) public float auth_rating;

    @Column(COL_NAME) public String beer_name;
    @Column(COL_LABEL) public String beer_label;
    @Column(COL_DESCRIPTION) public String beer_description;
    @Column(COL_CREATED_AT) public String created_at;
    @Column(COL_STYLE) public String beer_style;

    @Column(COL_WISHLIST) public boolean wish_list;

    public BeerDto() {
    }

}
