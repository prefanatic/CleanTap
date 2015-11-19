package io.github.prefanatic.cleantap.ui;

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
import io.github.prefanatic.cleantap.common.RecyclerListAdapter;
import io.github.prefanatic.cleantap.data.dto.BeerStats;
import rx.Observable;
import rx.subjects.PublishSubject;

public class BeerListAdapter extends RecyclerListAdapter<BeerStats, BeerListAdapter.ViewHolder> {
    private final Context context;
    private PublishSubject<ClickEvent<BeerStats>> subject;

    public BeerListAdapter(Context context) {
        this.context = context;
        subject = PublishSubject.create();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BeerStats stats = data.get(position);

        holder.beerName.setText(stats.beer.beer_name);
        holder.breweryName.setText(stats.brewery.brewery_name);
        Glide.with(context).load(stats.beer.beer_label).into(holder.beerImage);

        holder.itemView.setTag(stats);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_beer, parent, false));
    }

    public Observable<ClickEvent<BeerStats>> selection() {
        return subject.asObservable();
    }

    public class ViewHolder extends ButterKnifeViewHolder {
        @Bind(R.id.beer_name) TextView beerName;
        @Bind(R.id.brewery_name) TextView breweryName;
        @Bind(R.id.beer_image) ImageView beerImage;

        public ViewHolder(View itemView) {
            super(itemView);
            RxView.clicks(itemView).subscribe(v -> {
                subject.onNext(new ClickEvent<>(itemView, (BeerStats) itemView.getTag()));
            });
        }
    }
}
