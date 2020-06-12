package es.jasolgar.cityposts.ui.details;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import es.jasolgar.cityposts.data.model.db.Comment;
import es.jasolgar.cityposts.databinding.ItemCommentBinding;
import es.jasolgar.cityposts.ui.base.BaseViewHolder;

public class CommentsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final ArrayList<Comment> commentsList = new ArrayList<>();

    @Inject
    public CommentsAdapter(){

    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommentBinding  itemCommentBinding = ItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CommentItemViewHolder(itemCommentBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public void clearItems() {
        commentsList.clear();
    }

    public void addItems(List<Comment> commentList) {
        commentsList.addAll(commentList);
        notifyDataSetChanged();
    }

    private class CommentItemViewHolder extends BaseViewHolder {
        private final ItemCommentBinding mBinding;

        public CommentItemViewHolder(ItemCommentBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            Comment comment = commentsList.get(position);
            CommentItemViewModel mOpenSourceItemViewModel = new CommentItemViewModel(comment);
            mBinding.setViewModel(mOpenSourceItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();

        }
    }
}
