package es.jasolgar.cityposts.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import es.jasolgar.cityposts.BR;
import es.jasolgar.cityposts.R;
import es.jasolgar.cityposts.ViewModelProviderFactory;
import es.jasolgar.cityposts.databinding.ActivityMainBinding;
import es.jasolgar.cityposts.ui.base.BaseActivity;
import es.jasolgar.cityposts.ui.posts.PostsFragment;

public class MainActivity  extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, HasSupportFragmentInjector {


    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ViewModelProviderFactory factory;

    private MainViewModel mMainViewModel;
    private ActivityMainBinding mActivityMainBinding;
    private Toolbar mToolbar;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    public int getBindingVariable()  {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        mMainViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        return mMainViewModel;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        mMainViewModel.setNavigator(this);

        mToolbar = mActivityMainBinding.toolbar;

        setSupportActionBar(mToolbar);

        setUpViewBinding();
    }

    private void setUpViewBinding() {
    }

    @Override
    public void loadPostFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .add(R.id.frame_main_container, PostsFragment.newInstance(), PostsFragment.TAG)
                .commit();
    }

    @Override
    public void handleError(Throwable throwable) {

    }


    @Override
    public void onBackPressed() {
        FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() >= 2) {
            fragmentManager.popBackStackImmediate();
            fts.commit();
        } else {
            super.onBackPressed();
        }
    }


}
