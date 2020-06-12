package es.jasolgar.cityposts;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.jasolgar.cityposts.data.DataManager;
import es.jasolgar.cityposts.ui.details.DetailsViewModel;
import es.jasolgar.cityposts.ui.main.MainViewModel;
import es.jasolgar.cityposts.ui.posts.PostsViewModel;
import es.jasolgar.cityposts.ui.splash.SplashViewModel;
import es.jasolgar.cityposts.utils.rx.SchedulerProvider;

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