package es.jasolgar.posts.ui.splash;


import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import es.jasolgar.posts.BR;
import es.jasolgar.posts.R;
import es.jasolgar.posts.ViewModelProviderFactory;
import es.jasolgar.posts.databinding.ActivitySplashBinding;
import es.jasolgar.posts.ui.base.BaseActivity;
import es.jasolgar.posts.ui.main.MainActivity;

public class SplashActivity  extends BaseActivity<ActivitySplashBinding, SplashViewModel> implements SplashNavigator  {

    @Inject
    ViewModelProviderFactory factory;

    private SplashViewModel mSplashViewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public SplashViewModel getViewModel() {
        mSplashViewModel = new ViewModelProvider(this,factory).get(SplashViewModel.class);
        return mSplashViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSplashViewModel.setNavigator(this);
        mSplashViewModel.onFetchDataStarted();
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.newIntent(SplashActivity.this);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }
}
