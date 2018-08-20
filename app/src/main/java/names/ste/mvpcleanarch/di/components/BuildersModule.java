package names.ste.mvpcleanarch.di.components;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import names.ste.mvpcleanarch.di.modules.PresenterModule;
import names.ste.mvpcleanarch.di.modules.PresenterViewModule;
import names.ste.mvpcleanarch.view.MainActivity;


@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector(modules = {PresenterViewModule.class, PresenterModule.class})
    abstract MainActivity bindActivity();

}