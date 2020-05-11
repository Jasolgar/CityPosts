package es.jasolgar.posts.data.remote;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.jasolgar.posts.data.model.db.Comment;
import es.jasolgar.posts.data.model.db.Post;
import es.jasolgar.posts.data.model.db.User;
import es.jasolgar.posts.utils.CommonUtils;
import io.reactivex.Single;

@Singleton
public class AppApiHelper implements ApiHelper {

    @Inject
    public AppApiHelper() {

    }

    @Override
    public Single<List<Post>> doPostRequest() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_POST)
                .build()
                .getObjectListSingle(Post.class);
    }

    @Override
    public Single<List<Post>> doPostRequest(String userId) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_POST)
                .addQueryParameter("userId",userId)
                .build()
                .getObjectListSingle(Post.class);
    }

    @Override
    public Single<List<User>> doUsersRequest() {
        return  Rx2AndroidNetworking
                .get(ApiEndPoint.ENDPOINT_USERS)
                .build()
                .getObjectListSingle(User.class);
    }

    @Override
    public Single<List<Comment>> doCommentRequest(String postId) {
        return Rx2AndroidNetworking
                .get(ApiEndPoint.ENDPOINT_COMMENTS)
                .addQueryParameter("postId",postId)
                .build()
                .getObjectListSingle(Comment.class);
    }

    @Override
    public String provideImageRandomUrl() {
        return String.format(ApiEndPoint.ENDPOINT_RANDOM_IMAGE,String.valueOf(new Random().nextInt(1000)));
    }

    @Override
    public String provideAvatarRandomUrl() {
        String eyes = String.valueOf(new Random().nextInt(10));
        String noise = String.valueOf(new Random().nextInt(10));
        String mouth = String.valueOf(new Random().nextInt(10));
        String color = CommonUtils.generateRandomColorHex();
        return String.format(ApiEndPoint.ENDPOINT_RANDOM_AVATAR,eyes,noise,mouth,color);
    }
}
