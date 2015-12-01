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
