package io.github.prefanatic.cleantap.injection.component;

import android.content.Context;
import android.content.SharedPreferences;

import io.github.prefanatic.cleantap.Application;
import io.github.prefanatic.cleantap.data.Database;
import io.github.prefanatic.cleantap.data.RxUntappdApi;

public interface AppContextComponent {
    Application application();
    Context applicationContext();
    RxUntappdApi untappdApi();
    Database database();
    SharedPreferences preferences();
}
