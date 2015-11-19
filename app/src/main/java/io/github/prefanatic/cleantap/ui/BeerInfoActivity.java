package io.github.prefanatic.cleantap.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import io.github.prefanatic.cleantap.R;
import io.github.prefanatic.cleantap.common.BaseActivity;
import io.github.prefanatic.cleantap.data.dto.BeerExtended;
import io.github.prefanatic.cleantap.data.dto.BeerStats;
import io.github.prefanatic.cleantap.mvp.BeerInfoPresenter;
import io.github.prefanatic.cleantap.mvp.BeerInfoView;

public class BeerInfoActivity extends BaseActivity<BeerInfoView, BeerInfoPresenter> implements BeerInfoView {
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.image) ImageView imageView;
    @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.beer_name) TextView beerName;
    @Bind(R.id.brewery_name) TextView breweryName;
    @Bind(R.id.beer_style) TextView beerStyle;
    @Bind(R.id.beer_image) ImageView beerImage;
    @Bind(R.id.beer_description) TextView beerDescription;

    private BeerStats stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_info);

        stats = (BeerStats) getIntent().getSerializableExtra("beer");
        presenter.getBeerInfo(stats.beer.bid);

        beerName.setText(stats.beer.beer_name);
        beerStyle.setText(stats.beer.beer_style);
        breweryName.setText(stats.brewery.brewery_name);
        beerDescription.setText(stats.beer.beer_description);
        Glide.with(this).load(stats.beer.beer_label).into(beerImage);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbarLayout.setTitle(stats.beer.beer_name);
    }

    @Override
    public void setBeerInfo(BeerExtended beer) {
        Glide.with(this).load(beer.media.items.get(0).photo.photo_img_og).into(imageView);
    }

    @NonNull
    @Override
    public BeerInfoPresenter createPresenter() {
        return new BeerInfoPresenter();
    }
}
