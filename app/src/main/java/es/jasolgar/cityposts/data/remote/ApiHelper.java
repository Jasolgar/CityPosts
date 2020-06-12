package es.jasolgar.cityposts.data.remote;

import java.util.List;

import es.jasolgar.cityposts.data.model.db.Comment;
import es.jasolgar.cityposts.data.model.db.Post;
import es.jasolgar.cityposts.data.model.db.User;
import io.reactivex.Single;

public interface ApiHelper {

    Single<List<Post>> doPostRequest();

    Single<List<Post>> doPostRequest(String userId);

    Single<List<User>> doUsersRequest();

    Single<List<Comment>> doCommentRequest(String postId);

    String provideImageRandomUrl();

    String provideAvatarRandomUrl();
}
