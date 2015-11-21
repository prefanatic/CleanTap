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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import io.github.prefanatic.cleantap.ui.widget.BeerRatingView;
import io.github.prefanatic.cleantap.util.TextFormatUtil;

public class BeerInfoActivity extends BaseActivity<BeerInfoView, BeerInfoPresenter> implements BeerInfoView {
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.image) ImageView beerSplash;
    @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.recycler) RecyclerView recycler;

    private BeerStats stats;
    private BeerInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupAnimations();

        setContentView(R.layout.activity_beer_info);

        stats = (BeerStats) getIntent().getSerializableExtra("beer");
        presenter.getBeerInfo(stats.beer.bid);

        adapter = new BeerInfoAdapter(this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        adapter.addItem(stats);

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
        adapter.addItem(beer);

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
