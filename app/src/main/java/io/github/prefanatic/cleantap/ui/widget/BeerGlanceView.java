package io.github.prefanatic.cleantap.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.prefanatic.cleantap.R;
import io.github.prefanatic.cleantap.data.dto.BeerStatsDto;

public class BeerGlanceView extends FrameLayout {
    @Bind(R.id.abv) TextView abvView;
    @Bind(R.id.ibu) TextView ibuView;
    @Bind(R.id.beer_name) TextView beerName;
    @Bind(R.id.brewery_name) TextView breweryName;
    @Bind(R.id.beer_style) TextView beerStyle;
    @Bind(R.id.beer_image) ImageView beerImage;
    @Bind(R.id.beer_description) TextView beerDescription;

    private BeerStatsDto stats;

    public BeerGlanceView(Context context) {
        this(context, null);
    }

    public BeerGlanceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BeerGlanceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.card_beer_glance, this, false);
        ButterKnife.bind(this, view);

        addView(view);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setValues(BeerStatsDto stats) {
        beerName.setText(stats.beer.beer_name);
        beerStyle.setText(stats.beer.beer_style);
        beerDescription.setText(stats.beer.beer_description);

        breweryName.setText(stats.brewery.brewery_name);

        abvView.setText(String.format("%.2f ABV", stats.beer.beer_abv));
        ibuView.setText(String.format("%.2f IBU", stats.beer.beer_ibu));
        Glide.with(getContext()).load(stats.beer.beer_label).into(beerImage);
    }
}
