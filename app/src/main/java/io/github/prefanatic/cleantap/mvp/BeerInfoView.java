package io.github.prefanatic.cleantap.mvp;

import com.hannesdorfmann.mosby.mvp.MvpView;

import io.github.prefanatic.cleantap.data.dto.BeerExtended;

public interface BeerInfoView extends MvpView {
    void setBeerInfo(BeerExtended beer);
}
