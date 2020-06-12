package es.jasolgar.cityposts.di.module;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.jasolgar.cityposts.data.AppDataManager;
import es.jasolgar.cityposts.data.DataManager;
import es.jasolgar.cityposts.data.local.db.AppDatabase;
import es.jasolgar.cityposts.data.local.db.AppDbHelper;
import es.jasolgar.cityposts.data.local.db.DbHelper;
import es.jasolgar.cityposts.data.local.prefs.AppPreferencesHelper;
import es.jasolgar.cityposts.data.local.prefs.PreferencesHelper;
import es.jasolgar.cityposts.data.remote.ApiHelper;
import es.jasolgar.cityposts.data.remote.AppApiHelper;
import es.jasolgar.cityposts.di.DatabaseInfo;
import es.jasolgar.cityposts.di.PreferenceInfo;
import es.jasolgar.cityposts.utils.AppConstants;
import es.jasolgar.cityposts.utils.rx.AppSchedulerProvider;
import es.jasolgar.cityposts.utils.rx.SchedulerProvider;
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
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

}
