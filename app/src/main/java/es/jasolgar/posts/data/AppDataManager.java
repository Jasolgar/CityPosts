package es.jasolgar.posts.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.jasolgar.posts.data.local.db.DbHelper;
import es.jasolgar.posts.data.local.prefs.PreferencesHelper;
import es.jasolgar.posts.data.model.db.Comment;
import es.jasolgar.posts.data.model.db.Post;
import es.jasolgar.posts.data.model.db.User;
import es.jasolgar.posts.data.model.others.MailEmoji;
import es.jasolgar.posts.data.model.others.PostInfo;
import es.jasolgar.posts.data.remote.ApiHelper;
import es.jasolgar.posts.utils.AppConstants;
import es.jasolgar.posts.utils.AppLogger;
import es.jasolgar.posts.utils.CommonUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

@Singleton
public class AppDataManager implements DataManager {

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;
    private final Gson mGson;

    @Inject
    public AppDataManager(Context context, DbHelper dbHelper, PreferencesHelper preferencesHelper, ApiHelper apiHelper, Gson gson) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
        mGson = gson;
    }

    public Observable<Boolean> fetchPostDataByUsers(){
       return requestUsers()
                .flatMap((Function<List<User>, Observable<User>>) Observable::fromIterable)
                .flatMap(user -> mDbHelper.insertUser(user)
                        .flatMap(aBoolean -> requestPosts(String.valueOf(user.getId())))
                .flatMap((Function<List<Post>, Observable<Boolean>>) mDbHelper::insertPosts))
                .toList().toObservable()
                .flatMap((Function<List<Boolean>, Observable<Boolean>>) booleans -> {
                   for (Boolean aBoolean : booleans)
                       if(aBoolean)
                           return Observable.just(true);
                   return Observable.just(false);
                })
                .onErrorReturn(throwable -> false);
    }

    private Observable<List<Post>> requestPosts(String userId){
       return mApiHelper
               .doPostRequest(userId)
               .map(postList -> {
                   for (Post post : postList)
                       post.setImageUrl(mApiHelper.provideImageRandomUrl());
                   return postList;
               }).toObservable();
    }

    private Observable<List<User>> requestUsers(){
        return  mApiHelper
                .doUsersRequest()
                .map(users -> {
                    for (User user : users)
                        user.setAvatarUrl(mApiHelper.provideAvatarRandomUrl());
                    return users;
                }).toObservable() ;
    }

    @Override
    public Observable<List<Comment>> requestCommentsByPostId(String postId) {
        return mApiHelper
                .doCommentRequest(postId)
                .toObservable()
                .flatMap((Function<List<Comment>, Observable<List<Comment>>>) comments -> {
                    for (Comment comment : comments)
                        comment.setEmail(buildEmailWithEmoji(comment.getEmail()));

                    return mDbHelper.insertComments(comments).map(aBoolean -> comments);
                })
                .onErrorReturn(throwable -> {
                    AppLogger.w(throwable.getMessage(), "getCommentsByPostId: %s") ;
                   return   mDbHelper.getCommentsByPostId(postId).blockingFirst();
                });
    }

    private String buildEmailWithEmoji(String email) {
        try {
            Type type = new TypeToken<List<MailEmoji>>() {  }.getType();
            List<MailEmoji> emojiList = mGson.fromJson(CommonUtils.loadJSONFromAsset(mContext, AppConstants.JSON_MAIL_EMOJI_OPTIONS), type);

            for (MailEmoji mailEmoji : emojiList)
                if(email.toLowerCase().endsWith(mailEmoji.getEndsWith().toLowerCase()))
                    return email.concat(" ").concat(mailEmoji.getEmojiByUnicode());
        }catch (IOException e) {
            e.printStackTrace();
        }

        return email;
    }

    @Override
    public Observable<List<PostInfo>> retrieveAllPostsInfo() {
       return mDbHelper.getPostData()
               .flatMap((Function<List<Post>, Observable<Post>>) Observable::fromIterable)
               .flatMap((Function<Post, Observable<PostInfo>>) post ->
                       Observable.zip(Observable.just(post),
                               mDbHelper.getUserById(String.valueOf(post.getUserId())), (BiFunction<Post, User, PostInfo>) (post1, user) -> {
                                   PostInfo postInfo = new PostInfo(post1);
                                   postInfo.setAvatar(user.getAvatarUrl());
                                   postInfo.setAuthorName(user.getUsername());
                                   return postInfo;
                               }))
               .toList()
               .onErrorReturn(throwable -> {
                   AppLogger.w(throwable.getMessage(), "getAllPostsInfo: %s") ;
                   return new ArrayList<>();
               })
               .toObservable();
    }

    @Override
    public Observable<Boolean> isEmptyCards() {
        return mDbHelper.getPostData()
                .flatMap((Function<List<Post>, Observable<Boolean>>)
                        postList -> Observable.just(postList.isEmpty()))
                .onErrorReturn(throwable -> {
                    AppLogger.w(throwable.getMessage(), "isEmptyCards: %s") ;
                    return true;
                });
    }

    @Override
    public Observable<Post> retrievePostsById(String postId) {
        return mDbHelper.getPostsById(postId);
    }

    @Override
    public Observable<User> retrieveUserById(String userId) {
        return mDbHelper.getUserById(userId);
    }

    @Override
    public Observable<Boolean> clearPostData() {
        return mDbHelper.removePostData();
    }


}
