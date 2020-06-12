package es.jasolgar.cityposts.ui.details;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DetailsActivityProvider {
    @ContributesAndroidInjector(modules = DetailsActivityModule.class)
    abstract DetailsActivity provideDetailsActivityFactory();
}
