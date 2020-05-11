package es.jasolgar.posts.ui.posts;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.jasolgar.posts.R;
import es.jasolgar.posts.ui.main.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class PostMainActivityTest extends BaseTest{

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void navigationDetails(){
        waitViewShown(withId(R.id.recycler_posts));

        onRandomPositionRecyclerView(mainActivityActivityTestRule.getActivity().findViewById(R.id.recyclew_comments));

        waitViewShown(withId(R.id.card_post_image));

        sleep(2500);

        Espresso.pressBack();
    }

    @Test
    public void deletePostsAndRetry(){

        waitViewShown(withId(R.id.recycler_posts));

        onView(withId(R.id.recycler_posts)).check(matches(isDisplayed()));

        onView(withId(R.id.floating_remove)).perform(click());

        onView(withText("OK"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());

        waitViewShown(withId(R.id.btn_retry));

        onView(withId(R.id.btn_retry))
                .check(matches(isDisplayed()))
                .perform(click());

        waitViewShown(withId(R.id.recycler_posts));
    }

}
