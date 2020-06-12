package es.jasolgar.cityposts.ui.posts;

import androidx.databinding.ObservableField;

import es.jasolgar.cityposts.data.model.others.PostInfo;

public class PostsItemViewModel {

    public final ObservableField<String> imageUrl = new ObservableField<>();

    public final ObservableField<String> avatarUrl = new ObservableField<>();

    public final ObservableField<String> title = new ObservableField<>();

    public final ObservableField<String> author = new ObservableField<>();

    public final ObservableField<String> body = new ObservableField<>();

    private final String postId;

    private PostItemViewModelListener mListener;

    public PostsItemViewModel(PostInfo postInfo, PostItemViewModelListener listener) {
        this.postId = postInfo.getPostId();
        this.imageUrl.set(postInfo.getImageUrl());
        this.avatarUrl.set(postInfo.getAvatar());
        this.title.set(postInfo.getTitle());
        this.author.set(postInfo.getAuthorName());
        this.body.set(postInfo.getBody());
        mListener = listener;
    }

    public void onItemClick() {
        mListener.onItemClick(postId);
    }

    public interface PostItemViewModelListener {
        void onItemClick(String postId);
    }

}
