package es.jasolgar.posts.ui.base;

import android.app.Activity;
import android.app.Application;

import androidx.viewbinding.BuildConfig;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import es.jasolgar.posts.di.component.DaggerAppComponent;
import es.jasolgar.posts.utils.AppLogger;
import okhttp3.OkHttpClient;
import timber.log.Timber;

public class BaseApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Inject
    OkHttpClient okHttpClient;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        AppLogger.init();

        AndroidNetworking.initialize(getApplicationContext(), okHttpClient);

        if (BuildConfig.DEBUG)
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);

        Timber.plant(new Timber.DebugTree());

    }



}
