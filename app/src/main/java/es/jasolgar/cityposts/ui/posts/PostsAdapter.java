package es.jasolgar.cityposts.ui.posts;

import android.app.ActivityOptions;
import android.os.Build;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import es.jasolgar.cityposts.data.model.others.PostInfo;
import es.jasolgar.cityposts.databinding.ItemPostBinding;
import es.jasolgar.cityposts.databinding.ItemPostEmptyViewBinding;
import es.jasolgar.cityposts.ui.base.BaseViewHolder;
import es.jasolgar.cityposts.ui.details.DetailsActivity;
import es.jasolgar.cityposts.ui.main.MainActivity;
import es.jasolgar.cityposts.utils.AppConstants;
import es.jasolgar.cityposts.utils.CommonUtils;

public class PostsAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    MainActivity mContext;

    private List<PostInfo> postInfoList;

    private PostsItemEmptyViewModel.PostItemEmptyViewModelListener mListener;

    @Inject
    public PostsAdapter(MainActivity context){
        mContext = context;
        postInfoList = new ArrayList<>();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                ItemPostBinding itemPostBinding = ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new PostItemViewHolder(itemPostBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ItemPostEmptyViewBinding emptyViewBinding = ItemPostEmptyViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new EmptyViewHolder(emptyViewBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    public void setListener(PostsItemEmptyViewModel.PostItemEmptyViewModelListener listener) {
        this.mListener = listener;
    }

    @Override
    public int getItemCount() {
        if (postInfoList != null && postInfoList.size() > 0) {
            return postInfoList.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (postInfoList != null && !postInfoList.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    public void clearItems() {
        postInfoList.clear();
    }

    public void addItems(List<PostInfo> repoList) {
        postInfoList.addAll(repoList);
        notifyDataSetChanged();
    }

    public class PostItemViewHolder extends BaseViewHolder implements PostsItemViewModel.PostItemViewModelListener {

        private final ItemPostBinding mBinding;

        public PostItemViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            PostInfo postInfo = postInfoList.get(position);
            PostsItemViewModel mOpenSourceItemViewModel = new PostsItemViewModel(postInfo,this);
            mBinding.setViewModel(mOpenSourceItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mBinding.cardPostImage.setTransitionName("cardPostImage".concat(" ").concat(postInfo.getPostId()));
                mBinding.cardAvatarImage.setTransitionName("cardAvatarImage".concat(" ").concat(postInfo.getPostId()));
            }
        }

        @Override
        public void onItemClick(String postId) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mContext,
                        Pair.create(mBinding.cardPostImage, mBinding.cardPostImage.getTransitionName()),
                        Pair.create(mBinding.cardAvatarImage, mBinding.cardAvatarImage.getTransitionName()));

                mContext.startActivity(DetailsActivity.newIntent(mContext,postId),options.toBundle());
            }else
                mContext.startActivity(DetailsActivity.newIntent(mContext,postId));
        }
    }

    public class EmptyViewHolder extends BaseViewHolder implements PostsItemEmptyViewModel.PostItemEmptyViewModelListener {

        private ItemPostEmptyViewBinding mBinding;

        public EmptyViewHolder(ItemPostEmptyViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            PostsItemEmptyViewModel emptyItemViewModel = new PostsItemEmptyViewModel(this);
            mBinding.setViewModel(emptyItemViewModel);

            try {
                mBinding.imvEmpty.setImageBitmap(CommonUtils.loadFileFromAsset(mContext, AppConstants.ASSETS_EMPTY_IMAGE));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onRetryClick() {
            mListener.onRetryClick();
        }
    }
}
