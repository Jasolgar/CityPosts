package es.jasolgar.cityposts.ui.Splash;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import es.jasolgar.cityposts.data.DataManager;
import es.jasolgar.cityposts.ui.splash.SplashNavigator;
import es.jasolgar.cityposts.ui.splash.SplashViewModel;
import es.jasolgar.cityposts.utils.rx.TestSchedulerProvider;
import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

@RunWith(MockitoJUnitRunner.class)
public class SplashViewModelTest {

    @Mock
    SplashNavigator mSplashNavigator;
    @Mock
    DataManager mMockDataManager;

    private TestScheduler mTestScheduler;

    private SplashViewModel mSplashViewModel;

    @BeforeClass
    public static void onlyOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mSplashViewModel = new SplashViewModel(mMockDataManager, testSchedulerProvider);
        mSplashViewModel.setNavigator(mSplashNavigator);
    }

    @After
    public void tearDown() throws Exception {
        mTestScheduler = null;
        mSplashViewModel = null;
        mSplashNavigator = null;
    }

    @Test
    public void testOnFetchDataStarted(){
        Mockito.doReturn(Observable.just(true)).when(mMockDataManager).fetchPostDataByUsers();

        mSplashViewModel.onFetchDataStarted();
        mTestScheduler.triggerActions();

        Mockito.verify(mSplashNavigator).openMainActivity();
    }
}
