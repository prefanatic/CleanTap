package io.github.prefanatic.cleantap.mvp;

import com.hannesdorfmann.mosby.mvp.MvpView;

import io.github.prefanatic.cleantap.data.dto.BeerStatsDto;

public interface BeerSearchView extends MvpView {
    void foundFavoriteBeer(BeerStatsDto beer);
    void foundRecentBeer(BeerStatsDto beer);
    void foundBeer(BeerStatsDto beer);
}
