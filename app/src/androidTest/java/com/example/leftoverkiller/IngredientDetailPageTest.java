package com.example.leftoverkiller;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
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

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class IngredientDetailPageTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void ingredientDetailPageTest() {
        ViewInteraction autoCompleteTextView = onView(
                allOf(withId(R.id.ingredients_search_auto_complete),
                        childAtPosition(
                                allOf(withId(R.id.ll_search_bar),
                                        childAtPosition(
                                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        autoCompleteTextView.perform(click());

        ViewInteraction autoCompleteTextView2 = onView(
                allOf(withId(R.id.ingredients_search_auto_complete),
                        childAtPosition(
                                allOf(withId(R.id.ll_search_bar),
                                        childAtPosition(
                                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        autoCompleteTextView2.perform(replaceText("pepper"), closeSoftKeyboard());

        onView(withText("black pepper"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

        onView(withId(R.id.ingredients_search_auto_complete)).perform(closeSoftKeyboard());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.ingredient_name), withText("black pepper"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredients_recycler_view),
                                        0),
                                1),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.recipe_name),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipes_recycler_view),
                                        0),
                                1),
                        isDisplayed()));
        appCompatTextView3.perform(click());
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        ViewInteraction textView = onView(
//                allOf(withId(R.id.recipe_name), withText("Charbroiled Salmon"),
//                        childAtPosition(
//                                allOf(withId(R.id.view_toolbar),
//                                        childAtPosition(
//                                                IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
//                                                1)),
//                                0),
//                        isDisplayed()));
//        textView.check(matches(withText("Charbroiled Salmon")));
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
