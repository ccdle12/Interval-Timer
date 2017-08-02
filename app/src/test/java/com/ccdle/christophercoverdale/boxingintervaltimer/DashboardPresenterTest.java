package com.ccdle.christophercoverdale.boxingintervaltimer;

import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.DashboardPresenter;
import com.ccdle.christophercoverdale.boxingintervaltimer.Timer.CountDownTimerInterface;

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
        this.dashboardPresenter = new DashboardPresenter(new LinkedList<>());

    }

    /*Unit Tests for formatting Minutes and Seconds*/
    @Test
    public void testFormatSeconds() {
        String seconds = this.dashboardPresenter.formatSeconds(5);
        Assert.assertEquals("05", seconds);
    }

    @Test
    public void testFormatSeconds2() {
        String seconds = this.dashboardPresenter.formatSeconds(30);
        Assert.assertEquals("30", seconds);
    }

    @Test
    public void testFormatSeconds3() {
        String seconds = this.dashboardPresenter.formatSeconds(0);
        Assert.assertEquals("00", seconds);
    }

    @Test
    public void testFormatSeconds4() {
        String seconds = this.dashboardPresenter.formatSeconds(61);
        Assert.assertEquals("00", seconds);
    }

    @Test
    public void testFormatSeconds5() {
        String seconds = this.dashboardPresenter.formatSeconds(59);
        Assert.assertEquals("59", seconds);
    }

    @Test
    public void testFormatMinutes() {
        String minutes = this.dashboardPresenter.formatMinutes(61);
        Assert.assertEquals("61", minutes);
    }

    @Test
    public void testFormatMinutes2() {
        String minutes = this.dashboardPresenter.formatMinutes(10);
        Assert.assertEquals("10", minutes);
    }

    @Test
    public void testFormatMinutes3() {
        String minutes = this.dashboardPresenter.formatMinutes(01);
        Assert.assertEquals("01", minutes);
    }

    @Test
    public void testFormatMinutes4() {
        String minutes = this.dashboardPresenter.formatMinutes(0);
        Assert.assertEquals("00", minutes);
    }

    /* Tests for the Queue functionality */
    @Test
    public void testTimeHasBeenAddedToQueue()
    {
        this.dashboardPresenter.addToQueue("00", "05", "00", "02", "1");

        Assert.assertEquals(Long.valueOf(5000), this.dashboardPresenter.pollQueue());
    }

    @Test
    public void testQueueIsFIFO1()
    {
        this.dashboardPresenter.addToQueue("00", "05", "00", "02", "1");

        this.dashboardPresenter.pollQueue();

        Assert.assertEquals(Long.valueOf(2000), this.dashboardPresenter.pollQueue());
    }

    @Test
    public void testQueueIsFIFO2()
    {
        this.dashboardPresenter.addToQueue("00", "05", "00", "02", "1");

        this.dashboardPresenter.pollQueue();
        this.dashboardPresenter.pollQueue();

        Assert.assertEquals(null, this.dashboardPresenter.pollQueue());
    }

    @Test
    public void testQueueIsNull()
    {
        this.dashboardPresenter.addToQueue("00", "05", "00", "02", "1");

        this.dashboardPresenter.clearQueue();

        Assert.assertEquals(null, this.dashboardPresenter.pollQueue());
    }

    @Test
    public void testQueueIsReturnSecondRound()
    {
        this.dashboardPresenter.addToQueue("00", "05", "00", "02", "2");

        this.dashboardPresenter.pollQueue();
        this.dashboardPresenter.pollQueue();

        Assert.assertEquals(Long.valueOf(5000), this.dashboardPresenter.pollQueue());
    }

    @Test
    public void testQueueIsReturnSecondRoundRest()
    {
        this.dashboardPresenter.addToQueue("00", "05", "00", "02", "2");

        this.dashboardPresenter.pollQueue();
        this.dashboardPresenter.pollQueue();
        this.dashboardPresenter.pollQueue();

        Assert.assertEquals(Long.valueOf(2000), this.dashboardPresenter.pollQueue());
    }

    @Test
    public void testQueueShouldReturnNull()
    {
        this.dashboardPresenter.addToQueue("00", "05", "00", "02", "2");

        this.dashboardPresenter.pollQueue();
        this.dashboardPresenter.pollQueue();
        this.dashboardPresenter.pollQueue();
        this.dashboardPresenter.pollQueue();

        Assert.assertEquals(null, this.dashboardPresenter.pollQueue());
    }
}