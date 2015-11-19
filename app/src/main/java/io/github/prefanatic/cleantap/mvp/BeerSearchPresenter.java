package io.github.prefanatic.cleantap.mvp;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.github.prefanatic.cleantap.data.RxUntappdApi;
import io.github.prefanatic.cleantap.data.dto.BeerStats;
import io.github.prefanatic.cleantap.injection.Injector;

public class BeerSearchPresenter extends MvpBasePresenter<BeerSearchView> {
    @Inject RxUntappdApi api;

    public BeerSearchPresenter() {
        Injector.INSTANCE.getApplicationComponent().inject(this);
    }

    public void searchForBeer(String query) {
        api.searchForBeers(query)
                .subscribe(this::beersFound);
    }

    public void beersFound(BeerStats stats) {
        if (isViewAttached())
            getView().foundBeer(stats);
    }
}
