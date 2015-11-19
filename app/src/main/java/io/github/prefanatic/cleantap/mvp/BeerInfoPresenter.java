package io.github.prefanatic.cleantap.mvp;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.github.prefanatic.cleantap.data.RxUntappdApi;
import io.github.prefanatic.cleantap.data.dto.BeerExtended;
import io.github.prefanatic.cleantap.injection.Injector;

public class BeerInfoPresenter extends MvpBasePresenter<BeerInfoView> {
    @Inject RxUntappdApi api;

    public BeerInfoPresenter() {
        Injector.INSTANCE.getApplicationComponent().inject(this);
    }

    public void getBeerInfo(long beerId) {
        api.getBeerInfo(beerId)
                .subscribe(this::beerReceived);
    }

    private void beerReceived(BeerExtended beer) {
        if (isViewAttached()) {
            getView().setBeerInfo(beer);
        }
    }
}
