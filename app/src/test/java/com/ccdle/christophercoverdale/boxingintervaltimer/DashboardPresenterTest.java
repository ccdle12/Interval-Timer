package com.ccdle.christophercoverdale.boxingintervaltimer;

import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.DashboardPresenter;
import com.ccdle.christophercoverdale.boxingintervaltimer.CountDownTimer.CountDownTimerInterface;
import com.ccdle.christophercoverdale.boxingintervaltimer.TimerDisplay.TimerDisplay;
import com.ccdle.christophercoverdale.boxingintervaltimer.TimerDisplay.TimerDisplayPresenter;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.SoundFX;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.TimeValuesHelper;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

/**
 * Created by USER on 5/23/2017.
 */
public class DashboardPresenterTest {

    private DashboardPresenter dashboardPresenter;
    private CountDownTimerInterface countDownTimer;

    @Before
    public void setUpObjects() {
        this.dashboardPresenter = new DashboardPresenter(new TimerDisplayPresenter(new LinkedList<>(), new TimerDisplay(), new SoundFX()));

    }

    /* Unit Tests for formatting Minutes and Seconds */
    @Test
    public void testFormatSeconds() {
        String seconds = TimeValuesHelper.formatSecondsToString(5);
        Assert.assertEquals("05", seconds);
    }

    @Test
    public void testFormatSeconds2() {
        String seconds = TimeValuesHelper.formatSecondsToString(30);
        Assert.assertEquals("30", seconds);
    }

    @Test
    public void testFormatSeconds3() {
        String seconds = TimeValuesHelper.formatSecondsToString(0);
        Assert.assertEquals("00", seconds);
    }

    @Test
    public void testFormatSeconds4() {
        String seconds = TimeValuesHelper.formatSecondsToString(61);
        Assert.assertEquals("00", seconds);
    }

    @Test
    public void testFormatSeconds5() {
        String seconds = TimeValuesHelper.formatSecondsToString(59);
        Assert.assertEquals("59", seconds);
    }

    @Test
    public void testFormatMinutes() {
        String minutes = TimeValuesHelper.formatMinutesToString(61);
        Assert.assertEquals("61", minutes);
    }

    @Test
    public void testFormatMinutes2() {
        String minutes = TimeValuesHelper.formatMinutesToString(10);
        Assert.assertEquals("10", minutes);
    }

    @Test
    public void testFormatMinutes3() {
        String minutes = TimeValuesHelper.formatMinutesToString(01);
        Assert.assertEquals("01", minutes);
    }

    @Test
    public void testFormatMinutes4() {
        String minutes = TimeValuesHelper.formatMinutesToString(0);
        Assert.assertEquals("00", minutes);
    }

    /* Tests for increment and decrement rounds, work rounds and rest rounds */
    @Test
    public void decrementNumberOfRounds() {
        int decrementedNumberOfRounds = TimeValuesHelper.decrementValue("1");
        Assert.assertEquals(0, decrementedNumberOfRounds);
    }


}