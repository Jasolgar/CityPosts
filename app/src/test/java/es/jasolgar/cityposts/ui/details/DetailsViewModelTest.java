package es.jasolgar.cityposts.ui.details;

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

import es.jasolgar.cityposts.data.DataManager;
import es.jasolgar.cityposts.data.model.db.Comment;
import es.jasolgar.cityposts.data.model.db.Post;
import es.jasolgar.cityposts.data.model.db.User;
import es.jasolgar.cityposts.utils.rx.TestSchedulerProvider;
import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

@RunWith(MockitoJUnitRunner.Silent.class)
public class DetailsViewModelTest {

    @Mock
    DetailsNavigator mDetailsNavigator;
    @Mock
    DataManager mMockDataManager;

    private TestScheduler mTestScheduler;

    private DetailsViewModel mDetailsViewModel;

    @BeforeClass
    public static void onlyOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mDetailsViewModel = new DetailsViewModel(mMockDataManager, testSchedulerProvider);
        mDetailsViewModel.setNavigator(mDetailsNavigator);
    }

    @After
    public void tearDown() throws Exception {
        mTestScheduler = null;
        mDetailsViewModel = null;
        mDetailsNavigator = null;
    }

    @Test
    public void testFetchRepo(){
        List<Comment> commentList = new ArrayList<>();

        Mockito.doReturn(Observable.just(new User())).when(mMockDataManager).retrieveUserById("1");
        Mockito.doReturn(Observable.just(new Post())).when(mMockDataManager).retrievePostsById("1");
        Mockito.doReturn(Observable.just(commentList)).when(mMockDataManager).requestCommentsByPostId("1");

        mDetailsViewModel.notifyBundlePostId("1");
        mTestScheduler.triggerActions();
    }

}
