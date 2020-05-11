package es.jasolgar.posts.ui.posts;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import es.jasolgar.posts.data.DataManager;
import es.jasolgar.posts.data.model.others.PostInfo;
import es.jasolgar.posts.ui.base.BaseViewModel;
import es.jasolgar.posts.utils.AppLogger;
import es.jasolgar.posts.utils.rx.SchedulerProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class PostsViewModel extends BaseViewModel<PostsNavigator> {

    private final MutableLiveData<List<PostInfo>> postItemsLiveData;

    private final ObservableBoolean mIsEmptyList = new ObservableBoolean();

    public PostsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        postItemsLiveData = new MutableLiveData<>();
    }

    public LiveData<List<PostInfo>> getPostItemsLiveData() { return postItemsLiveData;  }

    void requestRepoAndFetchData() {
       getCompositeDisposable().add(
               requestIsLoading()
                .flatMap((Function<Boolean, Observable<Boolean>>) aBoolean ->
                        aBoolean ? Observable.just(false) : notifyIsLoading(true).concatMap(aBoolean1 -> getDataManager().fetchPostDataByUsers()))
                .flatMap((Function<Boolean, Observable<Boolean>>)
                        aBoolean -> fetchData())
                .flatMap((Function<Boolean, Observable<Boolean>>)
                        aBoolean -> notifyIsLoading(false))
               .subscribeOn(getSchedulerProvider().io())
               .subscribe(aBoolean -> { },throwable ->   AppLogger.w(throwable.getMessage(), "removeData: %s")));
    }

    Observable<Boolean> fetchData(){
        return getDataManager().retrieveAllPostsInfo()
                .observeOn(getSchedulerProvider().ui())
                .flatMap((Function<List<PostInfo>, Observable<Boolean>>)
                    postInfoList -> Observable.fromCallable(() -> {
                        postItemsLiveData.setValue(postInfoList);
                        setIsEmptyList(postInfoList.isEmpty());
                        return true;
                    }));
    }

    public void onRemoveDataClick() {
        getNavigator().showRemoveDataDialog();
    }

    void removeData() {
        getCompositeDisposable().add(
                getDataManager().clearPostData()
                        .observeOn(getSchedulerProvider().ui())
                        .concatMap((Function<Boolean, ObservableSource<?>>) aBoolean -> Observable.fromCallable(() -> {
                            mIsEmptyList.set(true);
                            postItemsLiveData.setValue(new ArrayList<>());
                            return true;
                        }))
                        .concatMap((Function<Object, Observable<Boolean>>) o -> notifyIsLoading(false))
                        .subscribeOn(getSchedulerProvider().io())
                        .subscribe(aBoolean -> {
                        },throwable ->   AppLogger.w(throwable.getMessage(), "removeData: %s")));
    }

    public ObservableBoolean getIsEmptyList() { return mIsEmptyList; }

    private void setIsEmptyList(boolean isLoading) {
        mIsEmptyList.set(isLoading);
    }

    void seedData() {
        getCompositeDisposable().add(
                fetchData().subscribe());
    }
}
