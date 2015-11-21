package io.github.prefanatic.cleantap.ui;

import android.app.Activity;

import io.github.prefanatic.cleantap.common.adapter.RecyclerListAdapter;
import io.github.prefanatic.cleantap.data.dto.BeerStats;
import io.github.prefanatic.cleantap.ui.delegate.BeerGlanceDelegate;
import io.github.prefanatic.cleantap.ui.delegate.BeerRatingDelegate;

/**
 * Created by cody on 11/21/15.
 */
public class BeerInfoAdapter extends RecyclerListAdapter {
    final int BEER_GLANCE = 0;
    final int BEER_RATING = 1;

    public BeerInfoAdapter(Activity activity) {
        super();

        delegatesManager.addDelegate(new BeerGlanceDelegate(activity, BEER_GLANCE));
        delegatesManager.addDelegate(new BeerRatingDelegate(activity, BEER_RATING));
    }
}
