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
