package es.jasolgar.cityposts.data.local.db;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.jasolgar.cityposts.data.model.db.Comment;
import es.jasolgar.cityposts.data.model.db.Post;
import es.jasolgar.cityposts.data.model.db.User;
import io.reactivex.Observable;

@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Observable<Boolean> insertUsers(List<User> users) {
        return Observable.fromCallable(() -> {
            mAppDatabase.userDao().insertAll(users);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertUser(User  user) {
        return Observable.fromCallable(() -> {
            mAppDatabase.userDao().insert(user);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertComments(List<Comment> comments) {
        return Observable.fromCallable(() -> {
            mAppDatabase.commentDao().insertAll(comments);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertPosts(List<Post> posts) {
        return Observable.fromCallable(() -> {
            mAppDatabase.postDao().insertAll(posts);
            return true;
        });
    }

    @Override
    public Observable<List<Post>> getPostData() {
        return mAppDatabase.postDao()
                .loadAll()
                .toObservable();
    }

    @Override
    public Observable<Post> getPostsById(String postId) {
        return mAppDatabase.postDao()
                .getPostById(postId)
                .toObservable();
    }

    @Override
    public Observable<User> getUserById(String userId) {
        return mAppDatabase.userDao()
                .getUserById(userId)
                .toObservable();
    }

    @Override
    public Observable<List<Comment>> getCommentsByPostId(String postId) {
        return mAppDatabase.commentDao()
                .getCommentsByPostId(postId)
                .toObservable();
    }

    @Override
    public Observable<Boolean> removePostData() {
        return Observable.fromCallable(() -> {
            mAppDatabase.userDao().deleteAll();
            return true;
        });
    }


}
