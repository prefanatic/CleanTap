/*
 * Copyright 2015 Cody Goldberg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.prefanatic.cleantap.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
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
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import io.github.prefanatic.cleantap.R;
import io.github.prefanatic.cleantap.common.BaseActivity;
import io.github.prefanatic.cleantap.common.ClickEvent;
import io.github.prefanatic.cleantap.common.PreferenceKeys;
import io.github.prefanatic.cleantap.common.RecyclerViewUpdateEvent;
import io.github.prefanatic.cleantap.data.dto.BeerStatsDto;
import io.github.prefanatic.cleantap.data.oauth.AuthDialog;
import io.github.prefanatic.cleantap.injection.Injector;
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

    @Inject SharedPreferences preferences;

    private BeerListAdapter beerAdapter;
    private StickyRecyclerHeadersDecoration beerDecor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.INSTANCE.getApplicationComponent().inject(this);
        setContentView(R.layout.activity_beer_search);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.getNavigationIcon().setTint(Color.BLACK);

        beerAdapter = new BeerListAdapter(this);
        beerDecor = new StickyRecyclerHeadersDecoration(beerAdapter);

        recyclerView.setAdapter(beerAdapter);
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(beerDecor);
        recyclerView.setNestedScrollingEnabled(false);

        beerAdapter.addHeader(0, "Favorite");
        beerAdapter.addHeader(1, "Recent");
        beerAdapter.addHeader(2, "Search Results");

        watch(RxTextView.textChangeEvents(searchView)
                .subscribe(this::searchViewTextEventLocal));
        watch(RxTextView.textChangeEvents(searchView)
                .debounce(700, TimeUnit.MILLISECONDS)
                .subscribe(this::searchViewTextEventNetwork));
        watch(RxTextView.editorActionEvents(searchView)
                .subscribe(this::searchViewEvent));
        watch(RxToolbar.navigationClicks(toolbar)
                .subscribe(v -> finish()));
        watch(beerAdapter.clickEvent()
                .subscribe(this::beerClicked));

        presenter.searchForLocalBeer("");

        if (preferences.getString(PreferenceKeys.AUTH_TOKEN, "").isEmpty()) {
            AuthDialog dialog = new AuthDialog();
            dialog.show(getFragmentManager(), "authDialog");
        }
    }

    @Override
    public void error(Throwable e) {
        Snackbar.make(recyclerView, e.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
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

    private void searchViewTextEventLocal(TextViewTextChangeEvent event) {
        presenter.searchForLocalBeer(event.text().toString());
    }

    private void searchViewTextEventNetwork(TextViewTextChangeEvent event) {
        presenter.searchForBeer(event.text().toString());
    }

    private void searchViewEvent(TextViewEditorActionEvent event) {
        if (event.actionId() == EditorInfo.IME_ACTION_SEARCH || (event.keyEvent().getKeyCode() == KeyEvent.KEYCODE_ENTER && event.keyEvent().getAction() == KeyEvent.ACTION_UP)) {
            recyclerView.setNestedScrollingEnabled(false);
            presenter.searchForBeer(searchView.getText().toString());

            AnimUtils.show(progress);
            AnimUtils.hide(helpText);
        }
    }

    @Override
    public void foundRecentBeer(RecyclerViewUpdateEvent<BeerStatsDto> event) {
        AnimUtils.hide(helpText);

        for (BeerStatsDto dto : event.getAdded())
            beerAdapter.addItemUnderHeader(1, 0, dto);
        for (BeerStatsDto dto : event.getRemoved())
            beerAdapter.removeItem(dto);
    }

    @Override
    public void foundFavoriteBeer(RecyclerViewUpdateEvent<BeerStatsDto> event) {
        AnimUtils.hide(helpText);

        for (BeerStatsDto dto : event.getAdded())
            beerAdapter.addItemUnderHeader(0, 0, dto);
        for (BeerStatsDto dto : event.getRemoved())
            beerAdapter.removeItem(dto);
    }

    @Override
    public void foundBeer(RecyclerViewUpdateEvent<BeerStatsDto> event) {
        AnimUtils.hide(progress);

        recyclerView.setNestedScrollingEnabled(true);
        for (BeerStatsDto dto : event.getAdded())
            beerAdapter.addItemUnderHeader(2, dto);
        for (BeerStatsDto dto : event.getRemoved())
            beerAdapter.removeItem(dto);

        beerDecor.invalidateHeaders();
        recyclerView.smoothScrollToPosition(0);
    }

    @NonNull
    @Override
    public BeerSearchPresenter createPresenter() {
        return new BeerSearchPresenter();
    }
}
