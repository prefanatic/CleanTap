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

package io.github.prefanatic.cleantap.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import io.github.prefanatic.cleantap.Application;
import io.github.prefanatic.cleantap.data.oauth.AuthDialog;
import io.github.prefanatic.cleantap.injection.module.AppContextModule;
import io.github.prefanatic.cleantap.mvp.BeerInfoPresenter;
import io.github.prefanatic.cleantap.mvp.BeerSearchPresenter;
import io.github.prefanatic.cleantap.ui.BeerSearchActivity;
import io.github.prefanatic.cleantap.ui.CheckinActivity;
import io.github.prefanatic.cleantap.ui.SearchFilterDialog;

@Singleton
@Component(modules = {AppContextModule.class})
public interface ApplicationComponent extends AppContextComponent {
    void inject(Application application);
    void inject(SearchFilterDialog dialog);
    void inject(BeerSearchPresenter presenter);
    void inject(AuthDialog dialog);
    void inject(BeerInfoPresenter presenter);
    void inject(CheckinActivity activity);
    void inject(BeerSearchActivity activity);
}
