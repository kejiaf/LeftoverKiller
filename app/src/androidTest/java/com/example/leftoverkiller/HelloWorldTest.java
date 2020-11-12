package com.example.leftoverkiller;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.Espresso.onView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HelloWorldTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addItem() {

        onView(withId(R.id.ingredients_search_auto_complete)).check(matches(isDisplayed()));
        onView(withId(R.id.ingredients_search_auto_complete)).perform(click());
        onView(withId(R.id.ingredients_search_auto_complete)).perform(ViewActions.typeText("salt"));
        onView(withText("salt"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());
        onView(withId(R.id.ingredients_search_auto_complete)).perform(closeSoftKeyboard());
//        onView(withId(R.id.ingredients_search_auto_complete)).perform(click());
        onView(withId(R.id.ingredients_search_auto_complete)).perform(ViewActions.typeText("new york strip steaks"));
        onView(withText("new york strip steaks"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());
        onView(withId(R.id.ingredients_search_auto_complete)).perform(closeSoftKeyboard());
        onView(withId(R.id.ingredients_search_auto_complete)).perform(ViewActions.typeText("rosemary"));
        onView(withText("rosemary"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());
        onView(withId(R.id.ingredients_search_auto_complete)).perform(closeSoftKeyboard());
        onView(withId(R.id.ingredients_search_auto_complete)).perform(ViewActions.typeText("red wine"));
        onView(withText("red wine"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());
        onView(withId(R.id.ingredients_search_auto_complete)).perform(closeSoftKeyboard());
        onView(withId(R.id.fab_search_recipes)).perform(click());


        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView = onView(
                allOf(withId(R.id.recipe_name), withText("rosemary steak"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.matching_recipes_recycler_view),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("rosemary steak")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

}