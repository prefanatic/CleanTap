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

package io.github.prefanatic.cleantap.injection.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.prefanatic.cleantap.Application;
import io.github.prefanatic.cleantap.data.Database;
import io.github.prefanatic.cleantap.data.RxUntappdApi;

@Module
public class AppContextModule {
    private final Application application;

    public AppContextModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application application() {
        return this.application;
    }

    @Provides
    @Singleton
    public Context applicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    public RxUntappdApi untappdApi() {
        return new RxUntappdApi();
    }

    @Provides
    @Singleton
    public Database database() {
        return new Database(application);
    }

    @Provides
    @Singleton
    public SharedPreferences preferences() {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }
}
