package io.github.prefanatic.cleantap.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.jakewharton.rxbinding.support.v7.widget.RxToolbar;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewEditorActionEvent;

import butterknife.Bind;
import io.github.prefanatic.cleantap.R;
import io.github.prefanatic.cleantap.common.BaseActivity;
import io.github.prefanatic.cleantap.common.ClickEvent;
import io.github.prefanatic.cleantap.data.dto.BeerStats;
import io.github.prefanatic.cleantap.mvp.BeerSearchPresenter;
import io.github.prefanatic.cleantap.mvp.BeerSearchView;
import io.github.prefanatic.cleantap.ui.delegate.BeerSearchDelegate;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class BeerSearchActivity extends BaseActivity<BeerSearchView, BeerSearchPresenter> implements BeerSearchView {
    @Bind(R.id.recycler) RecyclerView recyclerView;
    @Bind(R.id.search) EditText searchView;
    @Bind(R.id.toolbar) Toolbar toolbar;

    private BeerListAdapter beerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_search);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.getNavigationIcon().setTint(Color.BLACK);

        beerAdapter = new BeerListAdapter(this);
        recyclerView.setAdapter(beerAdapter);
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        watch(RxTextView.editorActionEvents(searchView)
                .subscribe(this::searchViewEvent));
        watch(RxToolbar.navigationClicks(toolbar)
                .subscribe(v -> finish()));
        watch(beerAdapter.clickEvent()
                .map(event -> (ClickEvent<BeerSearchDelegate.ViewHolder, BeerStats>) event)
                .subscribe(this::beerClicked));
    }


    private void beerClicked(ClickEvent<BeerSearchDelegate.ViewHolder, BeerStats> event) {
        Intent intent = new Intent(this, BeerInfoActivity.class);
        intent.putExtra("beer", event.item);

        ActivityOptionsCompat transitionOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, event.viewHolder.beerImage,
                ViewCompat.getTransitionName(event.viewHolder.beerImage));
        ActivityCompat.startActivity(this, intent, transitionOptions.toBundle());
    }

    private void searchViewEvent(TextViewEditorActionEvent event) {
        if (event.actionId() == EditorInfo.IME_ACTION_SEARCH || (event.keyEvent().getKeyCode() == KeyEvent.KEYCODE_ENTER && event.keyEvent().getAction() == KeyEvent.ACTION_UP)) {
            beerAdapter.clear();
            presenter.searchForBeer(searchView.getText().toString());
        }
    }

    @Override
    public void foundBeer(BeerStats beer) {
        beerAdapter.addItem(beer);
    }

    @NonNull
    @Override
    public BeerSearchPresenter createPresenter() {
        return new BeerSearchPresenter();
    }
}
