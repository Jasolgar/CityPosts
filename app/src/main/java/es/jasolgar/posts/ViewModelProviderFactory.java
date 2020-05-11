package es.jasolgar.posts;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.jasolgar.posts.data.DataManager;
import es.jasolgar.posts.ui.details.DetailsViewModel;
import es.jasolgar.posts.ui.main.MainViewModel;
import es.jasolgar.posts.ui.posts.PostsViewModel;
import es.jasolgar.posts.ui.splash.SplashViewModel;
import es.jasolgar.posts.utils.rx.SchedulerProvider;

@Singleton
public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

    private final DataManager dataManager;
    private final SchedulerProvider schedulerProvider;

    @Inject
    public ViewModelProviderFactory(DataManager dataManager,
                                    SchedulerProvider schedulerProvider) {
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
         if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            //noinspection unchecked
            return (T) new SplashViewModel(dataManager,schedulerProvider);
         }else if (modelClass.isAssignableFrom(MainViewModel.class)) {
             //noinspection unchecked
             return (T) new MainViewModel(dataManager,schedulerProvider);
         }else if (modelClass.isAssignableFrom(PostsViewModel.class)) {
             //noinspection unchecked
             return (T) new PostsViewModel(dataManager,schedulerProvider);
         }else if (modelClass.isAssignableFrom(DetailsViewModel.class)) {
             //noinspection unchecked
             return (T) new DetailsViewModel(dataManager,schedulerProvider);
         }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}