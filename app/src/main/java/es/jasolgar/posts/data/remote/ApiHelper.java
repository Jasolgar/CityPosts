package es.jasolgar.posts.data.remote;

import java.util.List;

import es.jasolgar.posts.data.model.db.Comment;
import es.jasolgar.posts.data.model.db.Post;
import es.jasolgar.posts.data.model.db.User;
import io.reactivex.Single;

public interface ApiHelper {

    Single<List<Post>> doPostRequest();

    Single<List<Post>> doPostRequest(String userId);

    Single<List<User>> doUsersRequest();

    Single<List<Comment>> doCommentRequest(String postId);

    String provideImageRandomUrl();

    String provideAvatarRandomUrl();
}
