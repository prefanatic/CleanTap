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

package io.github.prefanatic.cleantap.ui;

import android.app.Activity;

import io.github.prefanatic.cleantap.common.adapter.RecyclerListAdapter;
import io.github.prefanatic.cleantap.ui.delegate.BeerGlanceDelegate;
import io.github.prefanatic.cleantap.ui.delegate.BeerRatingDelegate;
import io.github.prefanatic.cleantap.ui.delegate.BreweryGlanceDelegate;

/**
 * Created by cody on 11/21/15.
 */
public class BeerInfoAdapter extends RecyclerListAdapter {
    final int BEER_GLANCE = 0;
    final int BEER_RATING = 1;
    final int BREWERY_GLANCE = 2;

    public BeerInfoAdapter(Activity activity) {
        super();

        delegatesManager.addDelegate(new BeerGlanceDelegate(activity, BEER_GLANCE));
        delegatesManager.addDelegate(new BeerRatingDelegate(activity, BEER_RATING));
        delegatesManager.addDelegate(new BreweryGlanceDelegate(activity, BREWERY_GLANCE));
    }
}
