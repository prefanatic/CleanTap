package io.github.prefanatic.cleantap.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.jakewharton.rxbinding.support.v7.widget.RxToolbar;

import butterknife.Bind;
import butterknife.OnClick;
import io.github.prefanatic.cleantap.R;
import io.github.prefanatic.cleantap.common.BaseActivity;
import io.github.prefanatic.cleantap.data.dto.BeerExtended;
import io.github.prefanatic.cleantap.data.dto.BeerStats;
import io.github.prefanatic.cleantap.mvp.BeerInfoPresenter;
import io.github.prefanatic.cleantap.mvp.BeerInfoView;
import io.github.prefanatic.cleantap.util.TextFormatUtil;

public class BeerInfoActivity extends BaseActivity<BeerInfoView, BeerInfoPresenter> implements BeerInfoView {
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.image) ImageView beerSplash;
    @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.beer_name) TextView beerName;
    @Bind(R.id.brewery_name) TextView breweryName;
    @Bind(R.id.beer_style) TextView beerStyle;
    @Bind(R.id.beer_image) ImageView beerImage;
    @Bind(R.id.beer_description) TextView beerDescription;
    @Bind(R.id.scrollView) NestedScrollView scrollView;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.rating) TextView rating;
    @Bind(R.id.count) TextView ratingCount;
    @Bind(R.id.abv) TextView abvView;
    @Bind(R.id.ibu) TextView ibuView;

    private BeerStats stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupAnimations();

        setContentView(R.layout.activity_beer_info);

        stats = (BeerStats) getIntent().getSerializableExtra("beer");
        presenter.getBeerInfo(stats.beer.bid);

        beerName.setText(stats.beer.beer_name);
        beerStyle.setText(stats.beer.beer_style);
        breweryName.setText(stats.brewery.brewery_name);
        beerDescription.setText(stats.beer.beer_description);
        abvView.setText(String.format("%.2f ABV", stats.beer.beer_abv));
        ibuView.setText(String.format("%.2f IBU", stats.beer.beer_ibu));
        Glide.with(this).load(stats.beer.beer_label).into(beerImage);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbarLayout.setTitle(stats.beer.beer_name);

        watch(RxToolbar.navigationClicks(toolbar).subscribe(v -> onBackPressed()));
    }

    @OnClick(R.id.fab)
    public void performCheckIn() {
        Intent intent = new Intent(this, CheckinActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, fab, fab.getTransitionName());
        startActivity(intent, options.toBundle());
    }

    private void setupAnimations() {
        Interpolator interpolator = PathInterpolatorCompat.create(0.5f, 0.5f);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getSharedElementEnterTransition().setInterpolator(interpolator);
            getWindow().getSharedElementExitTransition().setInterpolator(interpolator);
        }
    }

    @Override
    public void setBeerInfo(BeerExtended beer) {
        rating.setText(String.format("%.2f", beer.rating_score));
        ratingCount.setText(TextFormatUtil.LongToFormattedString(beer.stats.total_count));
        Glide.with(this).load(beer.media.items.get(0).photo.photo_img_lg).into(new GlideDrawableImageViewTarget(beerSplash) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);

                //scrollView.smoothScrollTo(0, 500);
            }
        });
    }

    @NonNull
    @Override
    public BeerInfoPresenter createPresenter() {
        return new BeerInfoPresenter();
    }
}
