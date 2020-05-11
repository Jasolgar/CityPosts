package es.jasolgar.posts.ui.details;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import es.jasolgar.posts.data.DataManager;
import es.jasolgar.posts.data.model.db.Comment;
import es.jasolgar.posts.data.model.db.Post;
import es.jasolgar.posts.data.model.db.User;
import es.jasolgar.posts.data.model.others.Geo;
import es.jasolgar.posts.ui.base.BaseViewModel;
import es.jasolgar.posts.utils.AppLogger;
import es.jasolgar.posts.utils.rx.SchedulerProvider;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class DetailsViewModel extends BaseViewModel<DetailsNavigator> {

    public final ObservableField<String> imageUrl = new ObservableField<>();

    public final ObservableField<String> avatarUrl = new ObservableField<>();

    public final ObservableField<String> userFullName = new ObservableField<>();

    public final ObservableField<String> userMail = new ObservableField<>();

    public final ObservableField<String> userAddress = new ObservableField<>();

    public final ObservableField<String> userPhone = new ObservableField<>();

    public final ObservableField<String> userWeb = new ObservableField<>();

    public final ObservableField<String> userCompany = new ObservableField<>();

    public final ObservableField<String> commentsCount = new ObservableField<>();

    public final ObservableField<String> postsBody = new ObservableField<>();

    private  MutableLiveData<List<Comment>> commentListMutableLiveData = new MutableLiveData<>();

    private Geo mGeo;

    public DetailsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void notifyBundlePostId(String postId) {
        setIsLoading(true);
        getCompositeDisposable()
                .add(getDataManager().retrievePostsById(postId).flatMap((Function<Post, Observable<User>>) post -> {
                    imageUrl.set(post.getImageUrl());
                    postsBody.set(post.getBody());
                    return getDataManager().retrieveUserById(String.valueOf(post.getUserId()));
                })
                .observeOn(getSchedulerProvider().io())
                .flatMap((Function<User, Observable<List<Comment>>>) user -> {
                    mGeo = user.getAddress().getGeo();

                    avatarUrl.set(user.getAvatarUrl());

                    userFullName.set(user.getUsername().concat(" - ").concat(user.getName()));

                    userMail.set(user.getEmail());

                    userAddress.set(user.getAddress().toString());

                    userPhone.set(user.getPhone());

                    userWeb.set(user.getWebsite());

                    userCompany.set(user.getCompany().toString());

                    return getDataManager().requestCommentsByPostId(postId);
                })
                .observeOn(getSchedulerProvider().ui())
                .flatMap((Function<List<Comment>, Observable<Boolean>>) comments -> {
                    setCommentListMutableLiveData(comments);

                    setIsLoading(false);
                    return Observable.just(true);
                })
                .onErrorReturn(throwable -> {
                    AppLogger.w(throwable.getMessage(), "notifyBundlePostId: %s");

                     setCommentListMutableLiveData(new ArrayList<>());
                     setIsLoading(false);
                     return false;
                 })
                .subscribe(aBoolean -> {},throwable -> AppLogger.w(throwable.getMessage(), "notifyBundlePostId: %s") ));

    }

    MutableLiveData<List<Comment>> getCommentListMutableLiveData() {
        return commentListMutableLiveData;
    }

    private void setCommentListMutableLiveData(List<Comment> commentList){
        commentListMutableLiveData.setValue(commentList);
        commentsCount.set(String.valueOf(commentList.size()));
    }

    public void onMailClick(){ getNavigator().launchMail(userMail.get()); }

    public void onAddressClick(){   getNavigator().launchGeoMaps(mGeo.getLat(),mGeo.getLng());  }

    public void onPhoneClick(){  getNavigator().launchPhoneCall(userPhone.get());  }

    public void onWebClick(){  getNavigator().loadWebUrl(userWeb.get());  }
}
