package io.github.prefanatic.cleantap;

import android.content.SharedPreferences;

import javax.inject.Inject;

import io.github.prefanatic.cleantap.common.PreferenceKeys;
import io.github.prefanatic.cleantap.data.RxUntappdApi;
import io.github.prefanatic.cleantap.injection.Injector;
import timber.log.Timber;

public class Application extends android.app.Application {
    @Inject RxUntappdApi api;
    @Inject SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        Injector.INSTANCE.initApplicationComponent(this);
        Injector.INSTANCE.getApplicationComponent().inject(this);

        String authToken = preferences.getString(PreferenceKeys.AUTH_TOKEN, "");
        if (!authToken.isEmpty()) {
            api.setAuthToken(authToken);
        }
    }
}
