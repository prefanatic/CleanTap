package io.github.prefanatic.cleantap.mvp;

import com.hannesdorfmann.mosby.mvp.MvpView;

import io.github.prefanatic.cleantap.common.RecyclerViewUpdateEvent;
import io.github.prefanatic.cleantap.data.dto.BeerStatsDto;

public interface BeerSearchView extends MvpView {
    void foundFavoriteBeer(RecyclerViewUpdateEvent<BeerStatsDto> event);
    void foundRecentBeer(RecyclerViewUpdateEvent<BeerStatsDto> event);
    void foundBeer(RecyclerViewUpdateEvent<BeerStatsDto> event);
    void error(Throwable e);
}
