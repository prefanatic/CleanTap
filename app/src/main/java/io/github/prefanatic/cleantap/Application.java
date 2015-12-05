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

package io.github.prefanatic.cleantap;

import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.orm.SugarApp;

import javax.inject.Inject;

import io.fabric.sdk.android.Fabric;
import io.github.prefanatic.cleantap.common.CrashlyticsTree;
import io.github.prefanatic.cleantap.common.PreferenceKeys;
import io.github.prefanatic.cleantap.data.RxUntappdApi;
import io.github.prefanatic.cleantap.injection.Injector;
import timber.log.Timber;

public class Application extends SugarApp {
    @Inject RxUntappdApi api;
    @Inject SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();

        CrashlyticsCore core = new CrashlyticsCore.Builder()
                .disabled(BuildConfig.DEBUG)
                .build();
        Fabric.with(this, new Crashlytics.Builder().core(core).build());

        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
        Timber.plant(new CrashlyticsTree());

        Injector.INSTANCE.initApplicationComponent(this);
        Injector.INSTANCE.getApplicationComponent().inject(this);

        String authToken = preferences.getString(PreferenceKeys.AUTH_TOKEN, "");
        if (!authToken.isEmpty()) {
            api.setAuthToken(authToken);
        }
    }
}
