package io.github.prefanatic.cleantap.ui;

import android.app.Activity;

import io.github.prefanatic.cleantap.common.adapter.TitleRecyclerListAdapter;
import io.github.prefanatic.cleantap.ui.delegate.BeerSearchDelegate;

public class BeerListAdapter extends TitleRecyclerListAdapter  {
    final int BEER_ENTRY = 0;

    public BeerListAdapter(Activity activity) {
        super();

        delegatesManager.addDelegate(new BeerSearchDelegate(activity, BEER_ENTRY, subject));
    }
}
