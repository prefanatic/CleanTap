package io.github.prefanatic.cleantap.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;

import butterknife.Bind;
import io.github.prefanatic.cleantap.R;
import io.github.prefanatic.cleantap.common.ButterKnifeViewHolder;
import io.github.prefanatic.cleantap.common.ClickEvent;
import io.github.prefanatic.cleantap.common.adapter.RecyclerListAdapter;
import io.github.prefanatic.cleantap.data.dto.BeerStats;
import io.github.prefanatic.cleantap.ui.delegate.BeerSearchDelegate;
import rx.Observable;
import rx.subjects.PublishSubject;

public class BeerListAdapter extends RecyclerListAdapter {
    final int BEER_ENTRY = 0;

    public BeerListAdapter(Activity activity) {
        super();

        delegatesManager.addDelegate(new BeerSearchDelegate(activity, BEER_ENTRY, subject));
    }

}
