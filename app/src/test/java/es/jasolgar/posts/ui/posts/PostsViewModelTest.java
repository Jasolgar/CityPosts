package es.jasolgar.posts.ui.posts;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import es.jasolgar.posts.data.DataManager;
import es.jasolgar.posts.data.model.others.PostInfo;
import es.jasolgar.posts.utils.rx.TestSchedulerProvider;
import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

@RunWith(MockitoJUnitRunner.class)
public class PostsViewModelTest {

    @Mock
    PostsNavigator mPostsNavigator;
    @Mock
    DataManager mMockDataManager;

    private TestScheduler mTestScheduler;

    private PostsViewModel mPostsViewModel;

    @BeforeClass
    public static void onlyOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mPostsViewModel = new PostsViewModel(mMockDataManager, testSchedulerProvider);
        mPostsViewModel.setNavigator(mPostsNavigator);
    }

    @After
    public void tearDown() throws Exception {
        mTestScheduler = null;
        mPostsViewModel = null;
        mPostsNavigator = null;
    }

    @Test
    public void testFetchRepo(){
        List<PostInfo> postInfoList = new ArrayList<>();

        Mockito.doReturn(Observable.just(postInfoList)).when(mMockDataManager).retrieveAllPostsInfo();

        mPostsViewModel.fetchData();
        mTestScheduler.triggerActions();
    }

}
