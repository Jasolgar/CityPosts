package es.jasolgar.posts.utils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

import es.jasolgar.posts.data.model.others.PostInfo;
import es.jasolgar.posts.ui.base.BaseActivity;
import es.jasolgar.posts.ui.posts.PostsAdapter;

public class BindingUtils {

    @BindingAdapter({"postsAdapter"})
    public static void addPostItems(RecyclerView recyclerView, List<PostInfo> postItemViewModels) {
        PostsAdapter adapter = (PostsAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(postItemViewModels);
        }
    }

    @BindingAdapter({"avatar"})
    public static void loadAvatar(ImageView imageView, String avatarUrl) {
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions().circleCrop())
                .asDrawable()
                .load(avatarUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(new CustomViewTarget<ImageView,Drawable>(imageView) {
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {

                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        imageView.setImageDrawable(resource);
                    }

                    @Override
                    protected void onResourceCleared(@Nullable Drawable placeholder) {
                        imageView.setImageDrawable(null);
                    }
                });
    }

    @BindingAdapter({"image"})
    public static void loadImage(ImageView intoView, String imageUrl) {
        Glide.with(intoView.getContext())
                .asDrawable()
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .fitCenter()
                .into(new CustomViewTarget<ImageView,Drawable>(intoView) {
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {

                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        intoView.setImageDrawable(resource);
                        ((BaseActivity)intoView.getContext()).supportStartPostponedEnterTransition();
                    }

                    @Override
                    protected void onResourceCleared(@Nullable Drawable placeholder) {
                        intoView.setImageDrawable(null);
                    }
                }) ;
    }

}
