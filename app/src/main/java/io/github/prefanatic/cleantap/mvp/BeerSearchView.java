package io.github.prefanatic.cleantap.mvp;

import com.hannesdorfmann.mosby.mvp.MvpView;

import io.github.prefanatic.cleantap.data.dto.BeerStats;

public interface BeerSearchView extends MvpView {
    void foundBeer(BeerStats beer);
}
