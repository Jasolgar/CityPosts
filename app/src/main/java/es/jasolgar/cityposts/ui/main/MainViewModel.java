package es.jasolgar.cityposts.ui.main;

import es.jasolgar.cityposts.data.DataManager;
import es.jasolgar.cityposts.ui.base.BaseViewModel;
import es.jasolgar.cityposts.utils.AppLogger;
import es.jasolgar.cityposts.utils.rx.SchedulerProvider;

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
