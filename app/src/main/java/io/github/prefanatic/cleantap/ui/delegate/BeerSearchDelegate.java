package io.github.prefanatic.cleantap.ui.delegate;

import android.app.Activity;
import android.support.annotation.NonNull;
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
import io.github.prefanatic.cleantap.common.adapter.delegation.AdapterDelegationPassthrough;
import io.github.prefanatic.cleantap.data.dto.BeerStatsDto;
import rx.subjects.PublishSubject;

/**
 * Created by cody on 11/21/15.
 */
public class BeerSearchDelegate extends AdapterDelegationPassthrough<BeerStatsDto, BeerSearchDelegate.ViewHolder> {
    private PublishSubject<ClickEvent<ViewHolder, BeerStatsDto>> subject;

    public BeerSearchDelegate(Activity activity, int viewType, PublishSubject subject) {
        super(activity, viewType);
        this.subject = subject;
    }

    @Override
    public void onBindViewHolder(BeerStatsDto stats, ViewHolder holder) {

        holder.beerName.setText(stats.beer.beer_name);
        holder.breweryName.setText(stats.brewery.brewery_name);
        Glide.with(holder.itemView.getContext()).load(stats.beer.beer_label).into(holder.beerImage);

        holder.itemView.setTag(stats);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.entry_beer, viewGroup, false));

    }

    @Override
    public boolean isForViewType(Object item) {
        return item instanceof BeerStatsDto;
    }

    public class ViewHolder extends ButterKnifeViewHolder {
        @Bind(R.id.beer_name) public TextView beerName;
        @Bind(R.id.brewery_name) public TextView breweryName;
        @Bind(R.id.beer_image) public ImageView beerImage;

        public ViewHolder(View itemView) {
            super(itemView);
            RxView.clicks(itemView).subscribe(v -> {
                subject.onNext(new ClickEvent<>(this, (BeerStatsDto) itemView.getTag()));
            });
        }
    }
}
