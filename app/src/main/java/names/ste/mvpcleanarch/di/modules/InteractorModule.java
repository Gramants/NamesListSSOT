package names.ste.mvpcleanarch.di.modules;

import dagger.Module;
import dagger.Provides;
import names.ste.mvpcleanarch.data.database.ProjectDb;
import names.ste.mvpcleanarch.data.database.UsersDao;
import names.ste.mvpcleanarch.domain.InteractorImpl;
import names.ste.mvpcleanarch.interfaces.Api;
import names.ste.mvpcleanarch.interfaces.Interactor;

@Module
public class InteractorModule {

    @Provides
    Interactor provideInteractor(
            Api api,
            ProjectDb db,
            UsersDao dao) {

        return new InteractorImpl(api, db, dao);
    }

}