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

    /*Work and Rest Round Edit Texts Tests*/
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
    public void userChangesMinutes() {
        onView(withId(R.id.work_round_minutes)).perform(click());
        onView(withId(R.id.work_round_minutes)).perform(replaceText("01"));

        onView(withId(R.id.work_round_minutes)).check(matches(withText("01")));
    }

    @Test
    public void workIntervalSecondsIsDisplayed() {

        onView(withId(R.id.work_round_seconds)).check(matches(isDisplayed()));
    }

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

    /* User Changing the Round Tests*/
    @Test
    public void roundEdiTextCanBeChangedManually() {
        onView(withId(R.id.user_input_of_rounds)).perform(click());
        onView(withId(R.id.user_input_of_rounds)).perform(replaceText("01"));

        onView(withId(R.id.user_input_of_rounds)).check(matches(withText("01")));
    }


}