package es.jasolgar.cityposts.di.builder;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import es.jasolgar.cityposts.ui.details.DetailsActivity;
import es.jasolgar.cityposts.ui.details.DetailsActivityModule;
import es.jasolgar.cityposts.ui.main.MainActivity;
import es.jasolgar.cityposts.ui.posts.PostsFragmentProvider;
import es.jasolgar.cityposts.ui.splash.SplashActivity;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = {DetailsActivityModule.class})
    abstract DetailsActivity bindDetailsActivity();

    @ContributesAndroidInjector(modules = {
            PostsFragmentProvider.class})
    abstract MainActivity bindMainActivity();

}
