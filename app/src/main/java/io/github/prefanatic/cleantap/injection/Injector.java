package io.github.prefanatic.cleantap.injection;

import io.github.prefanatic.cleantap.Application;
import io.github.prefanatic.cleantap.injection.component.ApplicationComponent;
import io.github.prefanatic.cleantap.injection.component.DaggerApplicationComponent;
import io.github.prefanatic.cleantap.injection.module.AppContextModule;

public enum Injector {
    INSTANCE;

    private ApplicationComponent component;

    private Injector() {

    }

    public void initApplicationComponent(Application application) {
        this.component = DaggerApplicationComponent.builder()
                .appContextModule(new AppContextModule(application))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return component;
    }
}
