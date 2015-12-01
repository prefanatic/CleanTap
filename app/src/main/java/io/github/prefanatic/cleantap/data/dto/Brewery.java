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
