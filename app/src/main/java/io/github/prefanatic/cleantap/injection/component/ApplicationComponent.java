package io.github.prefanatic.cleantap.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import io.github.prefanatic.cleantap.Application;
import io.github.prefanatic.cleantap.data.oauth.AuthDialog;
import io.github.prefanatic.cleantap.injection.module.AppContextModule;
import io.github.prefanatic.cleantap.mvp.BeerInfoPresenter;
import io.github.prefanatic.cleantap.mvp.BeerSearchPresenter;

@Singleton
@Component(modules = {AppContextModule.class})
public interface ApplicationComponent extends AppContextComponent {
    void inject(Application application);
    void inject(BeerSearchPresenter presenter);
    void inject(AuthDialog dialog);
    void inject(BeerInfoPresenter presenter);
}
