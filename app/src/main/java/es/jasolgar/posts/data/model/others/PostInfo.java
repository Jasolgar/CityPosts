package es.jasolgar.posts.data.model.others;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;

import es.jasolgar.posts.data.model.db.Post;

public class PostInfo {

    private final long id;
    private final long userId;


    private final String title;
    private final String imageUrl;

    private String body = "";
    private String authorName = "";

    private String avatar = "";
    private String postId;

    public PostInfo(Post post) {
        this.id = post.getId();
        this.userId = post.getUserId();
        this.imageUrl = post.getImageUrl();
        this.title = post.getTitle();
        this.body = post.getBody();
    }

    public String getPostId() { return String.valueOf(id);  }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


}
