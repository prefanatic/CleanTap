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

package io.github.prefanatic.cleantap.domain;

/**
 * Created by cody on 11/29/15.
 */
public class Beer {
    public long id;
    public long breweryId;
    public long checkinCount;
    public long timeLookedAt;

    public String name;
    public String labelUrl;
    public String description;
    public String style;
    public String createdAt;

    public boolean wishlist;
    public boolean favorite;

    public int inProduction;
    public int userRating;

    public float abv;
    public float ibu;
    public float authRating;
}
