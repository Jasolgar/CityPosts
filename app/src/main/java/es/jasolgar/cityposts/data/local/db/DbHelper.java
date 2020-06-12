package es.jasolgar.cityposts.data.local.db;


import java.util.List;

import es.jasolgar.cityposts.data.model.db.Comment;
import es.jasolgar.cityposts.data.model.db.Post;
import es.jasolgar.cityposts.data.model.db.User;
import io.reactivex.Observable;

public interface DbHelper {

    Observable<Boolean> insertUsers(List<User> users);

    Observable<Boolean> insertUser(User users);

    Observable<Boolean> insertComments(List<Comment> comments);

    Observable<Boolean> insertPosts(List<Post> posts);

    Observable<List<Post>> getPostData();

    Observable<Post> getPostsById(String postId);

    Observable<User> getUserById(String userId);

    Observable<List<Comment>> getCommentsByPostId(String postId);

    Observable<Boolean> removePostData();
}
