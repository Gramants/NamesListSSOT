package names.ste.mvpcleanarch.di.components;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import names.ste.mvpcleanarch.App;
import names.ste.mvpcleanarch.di.modules.AppModule;
import names.ste.mvpcleanarch.di.modules.DatabaseModule;
import names.ste.mvpcleanarch.di.modules.InteractorModule;
import names.ste.mvpcleanarch.di.modules.NetModule;
import names.ste.mvpcleanarch.di.modules.SharedPrefModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        DatabaseModule.class,
        SharedPrefModule.class,
        NetModule.class,
        InteractorModule.class,
        BuildersModule.class})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(App application);

        AppComponent build();
    }

    void inject(App app);
}