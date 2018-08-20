package names.ste.mvpcleanarch.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import names.ste.mvpcleanarch.data.database.ProjectDb;
import names.ste.mvpcleanarch.data.database.UsersDao;
import names.ste.mvpcleanarch.util.Config;

@Module
public class DatabaseModule {


    @Provides
    @Singleton
    ProjectDb provideProjectDb(Context context) {
        return Room.databaseBuilder(context, ProjectDb.class, Config.DATABASE_NAME).build();
    }

    @Provides
    @Singleton
    UsersDao provideRepoDao(ProjectDb projectDb) {
        return projectDb.namesDao();
    }


}