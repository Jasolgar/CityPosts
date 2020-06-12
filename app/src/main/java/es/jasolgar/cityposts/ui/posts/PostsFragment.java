package es.jasolgar.cityposts.ui.posts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import javax.inject.Inject;

import es.jasolgar.cityposts.BR;
import es.jasolgar.cityposts.R;
import es.jasolgar.cityposts.ViewModelProviderFactory;
import es.jasolgar.cityposts.databinding.FragmentPostsBinding;
import es.jasolgar.cityposts.ui.base.BaseFragment;

public class PostsFragment extends BaseFragment<FragmentPostsBinding, PostsViewModel> implements PostsNavigator, PostsItemEmptyViewModel.PostItemEmptyViewModelListener {

    public static final String TAG = PostsFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    PostsAdapter mPostAdapter;

    private PostsViewModel mPostViewModel;

    private FragmentPostsBinding mFragmentPostSourceBinding;

    public static PostsFragment newInstance() {
        Bundle args = new Bundle();
        PostsFragment fragment = new PostsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_posts;
    }

    @Override
    public PostsViewModel getViewModel() {
        mPostViewModel =   ViewModelProviders.of(this, factory).get(PostsViewModel.class);
        return mPostViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBaseActivity().supportPostponeEnterTransition();

        mPostViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFragmentPostSourceBinding = getViewDataBinding();

        setUp();
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentPostSourceBinding.recyclerPosts.setLayoutManager(mLayoutManager);
        mFragmentPostSourceBinding.recyclerPosts.setItemAnimator(new DefaultItemAnimator());
        mFragmentPostSourceBinding.recyclerPosts.setAdapter(mPostAdapter);

        mPostAdapter.setListener(this);

        mPostViewModel.seedData();
    }

    @Override
    public void onRetryClick() {
        mPostViewModel.requestRepoAndFetchData();
    }

    @Override
    public void showRemoveDataDialog() {
        AlertDialog dialog = new AlertDialog.Builder(getBaseActivity()).setMessage(R.string.remove_data_message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPostViewModel.removeData();
                        // Do stuff if user accepts
                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // Do stuff when user neglects.
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                        // Do stuff when cancelled
                    }
                }).create();
        dialog.show();
    }

}
