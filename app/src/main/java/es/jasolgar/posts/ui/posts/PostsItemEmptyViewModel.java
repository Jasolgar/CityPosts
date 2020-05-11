package es.jasolgar.posts.ui.posts;

public class PostsItemEmptyViewModel {

    private PostItemEmptyViewModelListener mListener;

    public PostsItemEmptyViewModel(PostItemEmptyViewModelListener listener) {
        this.mListener = listener;
    }

    public void onRetryClick(){
        mListener.onRetryClick();
    }

    public interface  PostItemEmptyViewModelListener {
        void onRetryClick();
    }
}
