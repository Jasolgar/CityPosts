package es.jasolgar.cityposts.data;

import java.util.List;

import es.jasolgar.cityposts.data.model.db.Comment;
import es.jasolgar.cityposts.data.model.db.Post;
import es.jasolgar.cityposts.data.model.db.User;
import es.jasolgar.cityposts.data.model.others.PostInfo;
import io.reactivex.Observable;

public interface DataManager {
    Observable<Boolean> fetchPostDataByUsers();

    Observable<List<PostInfo>> retrieveAllPostsInfo();

    Observable<Boolean> isEmptyCards();

    Observable<Post> retrievePostsById(String postId);

    Observable<User> retrieveUserById(String userId);

    Observable<List<Comment>> requestCommentsByPostId(String postId);

    Observable<Boolean> clearPostData();
}
