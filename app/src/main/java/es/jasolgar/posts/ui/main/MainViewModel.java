package es.jasolgar.posts.ui.main;

import es.jasolgar.posts.data.DataManager;
import es.jasolgar.posts.ui.base.BaseViewModel;
import es.jasolgar.posts.utils.AppLogger;
import es.jasolgar.posts.utils.rx.SchedulerProvider;

public class MainViewModel extends BaseViewModel<MainNavigator> {

    private static final String TAG = "MainViewModel";

    public MainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        loadPostCards();
    }

    private void loadPostCards() {
        getCompositeDisposable().add(getDataManager()
        .isEmptyCards()
        .subscribeOn(getSchedulerProvider().io())
        .observeOn(getSchedulerProvider().ui())
                .subscribe(isEmpty -> {
                     getNavigator().loadPostFragment();
                }, throwable -> AppLogger.w(throwable.getMessage(), "loadPostCards: %s")));
    }

}
