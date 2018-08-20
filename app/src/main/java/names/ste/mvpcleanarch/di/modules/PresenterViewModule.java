package names.ste.mvpcleanarch.di.modules;

import dagger.Binds;
import dagger.Module;
import names.ste.mvpcleanarch.interfaces.MainActivityView;
import names.ste.mvpcleanarch.view.MainActivity;

@Module
public abstract class PresenterViewModule {

    @Binds
    abstract MainActivityView provideView(MainActivity activity);

}