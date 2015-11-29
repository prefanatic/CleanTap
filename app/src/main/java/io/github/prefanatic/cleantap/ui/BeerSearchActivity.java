package io.github.prefanatic.cleantap.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.rxbinding.support.v7.widget.RxToolbar;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewEditorActionEvent;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import butterknife.Bind;
import io.github.prefanatic.cleantap.R;
import io.github.prefanatic.cleantap.common.BaseActivity;
import io.github.prefanatic.cleantap.common.ClickEvent;
import io.github.prefanatic.cleantap.data.dto.BeerStatsDto;
import io.github.prefanatic.cleantap.mvp.BeerSearchPresenter;
import io.github.prefanatic.cleantap.mvp.BeerSearchView;
import io.github.prefanatic.cleantap.ui.delegate.BeerSearchDelegate;
import io.github.prefanatic.cleantap.util.AnimUtils;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class BeerSearchActivity extends BaseActivity<BeerSearchView, BeerSearchPresenter> implements BeerSearchView {
    @Bind(R.id.recycler) RecyclerView recyclerView;
    @Bind(R.id.search) EditText searchView;
    @Bind(R.id.appbar_layout) AppBarLayout appbarLayout;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.help_search_info) TextView helpText;
    @Bind(R.id.progress) ProgressBar progress;

    private BeerListAdapter beerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_search);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.getNavigationIcon().setTint(Color.BLACK);

        beerAdapter = new BeerListAdapter(this);
        StickyRecyclerHeadersDecoration decor = new StickyRecyclerHeadersDecoration(beerAdapter);
        beerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                decor.invalidateHeaders();
            }
        });

        recyclerView.setAdapter(beerAdapter);
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(decor);
        recyclerView.setNestedScrollingEnabled(false);

        beerAdapter.addHeader(0, "Favorite");
        beerAdapter.addHeader(1, "Recent");
        beerAdapter.addHeader(2, "Search Results");

        watch(RxTextView.editorActionEvents(searchView)
                .subscribe(this::searchViewEvent));
        watch(RxToolbar.navigationClicks(toolbar)
                .subscribe(v -> finish()));
        watch(beerAdapter.clickEvent()
                .subscribe(this::beerClicked));

        presenter.searchForLocalBeer("");
    }

    private void beerClicked(ClickEvent event) {
        Intent intent = new Intent(this, BeerInfoActivity.class);
        intent.putExtra("beer", (BeerStatsDto) event.item);

        presenter.persistBeer(((BeerStatsDto) event.item));

        View beerImage = ((BeerSearchDelegate.ViewHolder) event.viewHolder).beerImage;
        ActivityOptionsCompat transitionOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, beerImage,
                ViewCompat.getTransitionName(beerImage));
        ActivityCompat.startActivity(this, intent, transitionOptions.toBundle());
    }

    private void searchViewEvent(TextViewEditorActionEvent event) {
        if (event.actionId() == EditorInfo.IME_ACTION_SEARCH || (event.keyEvent().getKeyCode() == KeyEvent.KEYCODE_ENTER && event.keyEvent().getAction() == KeyEvent.ACTION_UP)) {
            beerAdapter.clear();
            recyclerView.setNestedScrollingEnabled(false);
            presenter.searchForBeer(searchView.getText().toString());

            AnimUtils.show(progress);
            AnimUtils.hide(helpText);
        }
    }

    @Override
    public void foundRecentBeer(BeerStatsDto beer) {
        AnimUtils.hide(helpText);

        beerAdapter.addItemUnderHeader(1, beer);
    }

    @Override
    public void foundFavoriteBeer(BeerStatsDto beer) {
        AnimUtils.hide(helpText);

        beerAdapter.addItemUnderHeader(0, beer);
    }

    @Override
    public void foundBeer(BeerStatsDto beer) {
        AnimUtils.hide(progress);

        recyclerView.setNestedScrollingEnabled(true);
        beerAdapter.addItemUnderHeader(2, beer);
    }

    @NonNull
    @Override
    public BeerSearchPresenter createPresenter() {
        return new BeerSearchPresenter();
    }
}
