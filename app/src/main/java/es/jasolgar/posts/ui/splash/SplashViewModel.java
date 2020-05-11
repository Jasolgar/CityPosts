package es.jasolgar.posts.ui.splash;

import es.jasolgar.posts.data.DataManager;
import es.jasolgar.posts.ui.base.BaseViewModel;
import es.jasolgar.posts.utils.AppLogger;
import es.jasolgar.posts.utils.rx.SchedulerProvider;

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
