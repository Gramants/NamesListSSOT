package names.ste.mvpcleanarch.di.modules;

import dagger.Module;
import dagger.Provides;
import names.ste.mvpcleanarch.interfaces.Interactor;
import names.ste.mvpcleanarch.interfaces.MainActivityView;
import names.ste.mvpcleanarch.interfaces.PersistentStorageProxy;
import names.ste.mvpcleanarch.presenter.MainActivityPresenterImpl;

@Module
public class PresenterModule {

    @Provides
    MainActivityPresenterImpl providePresenter(MainActivityView view,
                                               Interactor interactor,
                                               PersistentStorageProxy persistence) {
        return new MainActivityPresenterImpl(view, interactor, persistence);
    }


}