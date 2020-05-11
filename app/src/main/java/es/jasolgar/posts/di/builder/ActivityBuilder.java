package es.jasolgar.posts.di.builder;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import es.jasolgar.posts.ui.details.DetailsActivity;
import es.jasolgar.posts.ui.details.DetailsActivityModule;
import es.jasolgar.posts.ui.details.DetailsActivityProvider;
import es.jasolgar.posts.ui.main.MainActivity;
import es.jasolgar.posts.ui.posts.PostsFragmentProvider;
import es.jasolgar.posts.ui.splash.SplashActivity;

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
