package com.ccdle.christophercoverdale.boxingintervaltimer;

import android.app.Activity;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.RelativeLayout;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isFocusable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by USER on 5/14/2017.
 */

@RunWith(AndroidJUnit4.class)
public class DashboardTest {

    Activity mainActivity;
    RelativeLayout rootLayout;


    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class, true, true);


    @Before
    public void setUpActivityReference() {
        this.mainActivity = activityTestRule.getActivity();
    }


    @Test
    public void testRootLayoutInFragmentIsVisible() {
        activityTestRule.launchActivity(new Intent());
        onView(withId(R.id.dashboard_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void testIfStartTimerButtonIsVisible() {
        onView(withId(R.id.start_timer_button)).check(matches(isDisplayed()));
    }

    @Test
    public void testIfStartTimerHasText() {
        onView(withId(R.id.start_timer_button)).check(matches(withText("Start")));
    }

    @Test
    public void workIntervalSecondsIsDisplayed() {

        onView(withId(R.id.work_round_seconds)).check(matches(isDisplayed()));
    }




    /* Cursor visibility on click */
    @Test
    public void cursorIsVisibleOnUserClickRestSeconds() {
        onView(withId(R.id.rest_round_minutes)).perform(click());
        onView(withId(R.id.rest_round_minutes)).perform(replaceText("00"));

        onView(withId(R.id.rest_round_minutes)).check(matches(isFocusable()));
    }

    @Test
    public void cursorIsVisibleOnUserClickRestMinutes() {
        onView(withId(R.id.rest_round_minutes)).perform(click());
        onView(withId(R.id.rest_round_minutes)).perform(replaceText("30"));

        onView(withId(R.id.rest_round_minutes)).check(matches(isFocusable()));
    }




    /* User Changing the Round Tests manually */
    @Test
    public void roundEditTextCanBeChangedManually() {
        onView(withId(R.id.number_of_rounds)).perform(click());
        onView(withId(R.id.number_of_rounds)).perform(replaceText("1"));

        onView(withId(R.id.number_of_rounds)).check(matches(withText("1")));
    }




    /* User Changing Number of Rounds using Increment and Decrement */
    @Test
    public void incrementNumberOfRoundsTo1() {
        onView(withId(R.id.increment_number_of_rounds)).perform(click());
        onView(withId(R.id.number_of_rounds)).check(matches(withText("1")));
    }

    @Test
    public void incrementNumberOfRounds11() {
        for (int i = 0; i <= 10; i++)
            onView(withId(R.id.increment_number_of_rounds)).perform(click());

        onView(withId(R.id.number_of_rounds)).check(matches(withText("11")));
    }

    @Test
    public void roundEditTextDecrement() {
        onView(withId(R.id.increment_number_of_rounds)).perform(click());
        onView(withId(R.id.increment_number_of_rounds)).perform(click());

        onView(withId(R.id.decrement_number_of_rounds)).perform(click());
        onView(withId(R.id.number_of_rounds)).check(matches(withText("1")));
    }

    @Test
    public void roundEditTextDecrementShouldRemainZero() {
        onView(withId(R.id.decrement_number_of_rounds)).perform(click());
        onView(withId(R.id.number_of_rounds)).check(matches(withText("0")));
    }




    /* User Changing Number of Work Rounds manually */
    @Test
    public void userChangesSeconds() {
        onView(withId(R.id.work_round_seconds)).perform(click());
        onView(withId(R.id.work_round_seconds)).perform(replaceText("30"));

        onView(withId(R.id.work_round_seconds)).check(matches(withText("30")));
    }

    @Test
    public void cursorIsVisibleOnUserClickWorkMinutes() {
        onView(withId(R.id.work_round_minutes)).perform(click());
        onView(withId(R.id.work_round_minutes)).perform(replaceText("03"));

        onView(withId(R.id.work_round_minutes)).check(matches(isFocusable()));
    }

    @Test
    public void cursorIsVisibleOnUserClickWorkSeconds() {
        onView(withId(R.id.work_round_seconds)).perform(click());
        onView(withId(R.id.work_round_seconds)).perform(replaceText("30"));

        onView(withId(R.id.work_round_seconds)).check(matches(isFocusable()));
    }

    @Test
    public void userChangesMinutes() {
        onView(withId(R.id.work_round_minutes)).perform(click());
        onView(withId(R.id.work_round_minutes)).perform(replaceText("1"));

        onView(withId(R.id.work_round_minutes)).check(matches(withText("1")));
    }


    /* User Changing Work Rounds via Incrementing and Decrementing Values*/
    @Test
    public void incrementWorkRound() {
        onView(withId(R.id.increment_round_time)).perform(click());
        onView(withId(R.id.work_round_seconds)).check(matches(withText("01")));
    }

    @Test
    public void incrementWorkRoundTo59() {
        onView(withId(R.id.work_round_seconds)).perform(replaceText("58"));
        onView(withId(R.id.increment_round_time)).perform(click());

        onView(withId(R.id.work_round_seconds)).check(matches(withText("59")));
    }

    @Test
    public void incrementWorkRoundShouldBeOneMinute() {
        onView(withId(R.id.work_round_seconds)).perform(replaceText("59"));

        onView(withId(R.id.increment_round_time)).perform(click());
        onView(withId(R.id.work_round_seconds)).check(matches(withText("00")));
        onView(withId(R.id.work_round_minutes)).check(matches(withText("01")));
    }

    @Test
    public void decrementWorkRound() {
        onView(withId(R.id.work_round_seconds)).perform(replaceText("01"));

        onView(withId(R.id.decrement_round_time)).perform(click());
        onView(withId(R.id.work_round_seconds)).check(matches(withText("00")));
    }

    @Test
    public void decrementToPreviousMinutes() {
        onView(withId(R.id.work_round_minutes)).perform(replaceText("01"));

        onView(withId(R.id.decrement_round_time)).perform(click());
        onView(withId(R.id.work_round_minutes)).check(matches(withText("00")));
        onView(withId(R.id.work_round_seconds)).check(matches(withText("59")));
    }

    @Test
    public void decrementInSameMinutes() {
        onView(withId(R.id.work_round_minutes)).perform(replaceText("01"));
        onView(withId(R.id.work_round_seconds)).perform(replaceText("02"));

        onView(withId(R.id.decrement_round_time)).perform(click());
        onView(withId(R.id.work_round_minutes)).check(matches(withText("01")));
        onView(withId(R.id.work_round_seconds)).check(matches(withText("01")));
    }

    @Test
    public void decrementInDefault() {
        onView(withId(R.id.decrement_round_time)).perform(click());
        onView(withId(R.id.work_round_minutes)).check(matches(withText("00")));
        onView(withId(R.id.work_round_seconds)).check(matches(withText("00")));
    }









    /* User Changing Rest rounds via Incrementing and Decrementing Values*/
    @Test
    public void incrementRestRound() {
        onView(withId(R.id.increment_rest_time)).perform(click());
        onView(withId(R.id.rest_round_seconds)).check(matches(withText("01")));
    }

    @Test
    public void incrementRestRoundTo59() {
        onView(withId(R.id.rest_round_seconds)).perform(replaceText("58"));
        onView(withId(R.id.increment_rest_time)).perform(click());

        onView(withId(R.id.rest_round_seconds)).check(matches(withText("59")));
    }

    @Test
    public void incrementRestRoundShouldBeOneMinute() {
        onView(withId(R.id.rest_round_seconds)).perform(replaceText("59"));

        onView(withId(R.id.increment_rest_time)).perform(click());
        onView(withId(R.id.rest_round_seconds)).check(matches(withText("00")));
        onView(withId(R.id.rest_round_minutes)).check(matches(withText("01")));
    }

    @Test
    public void decrementRestRound() {
        onView(withId(R.id.rest_round_seconds)).perform(replaceText("01"));

        onView(withId(R.id.decrement_rest_time)).perform(click());
        onView(withId(R.id.rest_round_seconds)).check(matches(withText("00")));
    }

    @Test
    public void decrementToPreviousRestMinutes() {
        onView(withId(R.id.rest_round_minutes)).perform(replaceText("01"));

        onView(withId(R.id.decrement_rest_time)).perform(click());
        onView(withId(R.id.rest_round_minutes)).check(matches(withText("00")));
        onView(withId(R.id.rest_round_seconds)).check(matches(withText("59")));
    }

    @Test
    public void decrementInSameRestMinutes() {
        onView(withId(R.id.rest_round_minutes)).perform(replaceText("01"));
        onView(withId(R.id.rest_round_seconds)).perform(replaceText("02"));

        onView(withId(R.id.decrement_rest_time)).perform(click());
        onView(withId(R.id.rest_round_minutes)).check(matches(withText("01")));
        onView(withId(R.id.rest_round_seconds)).check(matches(withText("01")));
    }

    @Test
    public void decrementInDefaultRestRounds() {
        onView(withId(R.id.decrement_rest_time)).perform(click());
        onView(withId(R.id.rest_round_minutes)).check(matches(withText("00")));
        onView(withId(R.id.rest_round_seconds)).check(matches(withText("00")));
    }


    /* User manually inputs values over the limits set on each edit text*/
    @Test
    public void addOver100Rounds() {
        onView(withId(R.id.number_of_rounds)).perform(replaceText("101"));
        onView(withId(R.id.number_of_rounds)).check(matches(withText("100")));
    }
}