package es.jasolgar.posts.di.module;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.jasolgar.posts.data.AppDataManager;
import es.jasolgar.posts.data.DataManager;
import es.jasolgar.posts.data.local.db.AppDatabase;
import es.jasolgar.posts.data.local.db.AppDbHelper;
import es.jasolgar.posts.data.local.db.DbHelper;
import es.jasolgar.posts.data.local.prefs.AppPreferencesHelper;
import es.jasolgar.posts.data.local.prefs.PreferencesHelper;
import es.jasolgar.posts.data.remote.ApiHelper;
import es.jasolgar.posts.data.remote.AppApiHelper;
import es.jasolgar.posts.di.DatabaseInfo;
import es.jasolgar.posts.di.PreferenceInfo;
import es.jasolgar.posts.utils.AppConstants;
import es.jasolgar.posts.utils.rx.AppSchedulerProvider;
import es.jasolgar.posts.utils.rx.SchedulerProvider;
import okhttp3.OkHttpClient;

@Module
public class AppModule {

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName)
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() { return AppConstants.DB_NAME;  }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREFERENCE_NAME;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    OkHttpClient provideOkHttpClient(){
        return new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

}
