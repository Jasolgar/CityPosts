package es.jasolgar.posts.ui.posts;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;

import org.hamcrest.Matcher;

import java.util.Random;

import es.jasolgar.posts.R;
import es.jasolgar.posts.ui.utils.ViewShownIdlingResource;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

class BaseTest {

    void onRandomPositionRecyclerView(RecyclerView recyclerView) {
        int x = getRandomRecyclerPosition(recyclerView);

        onView(withId(R.id.recycler_posts))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(x, click()));
    }

    private int getRandomRecyclerPosition(RecyclerView recyclerView) {
        Random ran = new Random();

        //If the RecyclerView exists, get the item count from the adapter
        int n = (recyclerView == null)
                ? 1
                : recyclerView.getAdapter().getItemCount();

        //Return a random number from 0 (inclusive) to adapter.itemCount() (exclusive)
        return ran.nextInt(n);
    }

    void waitViewShown(Matcher<View> matcher) {
        IdlingResource idlingResource = new ViewShownIdlingResource(matcher);///
        try {
            IdlingRegistry.getInstance().register(idlingResource);
            onView(matcher).check(matches(isDisplayed()));
        } finally {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }

    void sleep(long timeInMills){
        try {
            Thread.sleep(timeInMills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




}
