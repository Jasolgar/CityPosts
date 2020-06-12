package es.jasolgar.cityposts.ui.splash;

import es.jasolgar.cityposts.data.DataManager;
import es.jasolgar.cityposts.ui.base.BaseViewModel;
import es.jasolgar.cityposts.utils.AppLogger;
import es.jasolgar.cityposts.utils.rx.SchedulerProvider;

public class SplashViewModel extends BaseViewModel<SplashNavigator> {

    public SplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onFetchDataStarted(){
        AppLogger.d("startPosts");
        getCompositeDisposable().add(
                getDataManager()
                .fetchPostDataByUsers()
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(aBoolean -> {
                    AppLogger.d("fetch Posts Info -> %s", (aBoolean));

                    getNavigator().openMainActivity();
                }, throwable ->   AppLogger.w(throwable.getMessage(), "startPosts: %s")));
    }
}
