package com.ccdle.christophercoverdale.boxingintervaltimer.TimerDisplay;

import com.ccdle.christophercoverdale.boxingintervaltimer.CountDownTimer.CountDownTimerInterface;
import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.DashboardPresenter;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.RoundsModel;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created by christophercoverdale on 08/08/2017.
 */
public class TimerDisplayPresenterTest
{
    private TimerDisplayPresenter timerDisplayPresenter;
    private CountDownTimerInterface countDownTimer;

    private RoundsModel roundsModel1;
    private RoundsModel roundsModel2;

    @Before
    public void setUpObjects()
    {
        this.timerDisplayPresenter = new TimerDisplayPresenter(new LinkedList<>(), new TimerDisplay());
        this.roundsModel1 = new RoundsModel();
        this.roundsModel1.setWorkMins("00");
        this.roundsModel1.setWorkSecs("05");
        this.roundsModel1.setRestMins("00");
        this.roundsModel1.setRestSecs("02");
        this.roundsModel1.setRounds("1");

        this.roundsModel2 = new RoundsModel();
        this.roundsModel2.setWorkMins("00");
        this.roundsModel2.setWorkSecs("05");
        this.roundsModel2.setRestMins("00");
        this.roundsModel2.setRestSecs("02");
        this.roundsModel2.setRounds("2");
    }

    /* Tests for the Queue functionality */
    @Test
    public void testTimeHasBeenAddedToQueue()
    {
        this.timerDisplayPresenter.addToQueue(this.roundsModel1);

        Assert.assertEquals(Long.valueOf(5000), this.timerDisplayPresenter.pollQueue());
    }

    @Test
    public void testQueueIsFIFO1()
    {
        this.timerDisplayPresenter.addToQueue(this.roundsModel1);

        this.timerDisplayPresenter.pollQueue();

        Assert.assertEquals(Long.valueOf(2000), this.timerDisplayPresenter.pollQueue());
    }

    @Test
    public void testQueueIsFIFO2()
    {
        this.timerDisplayPresenter.addToQueue(this.roundsModel1);

        this.timerDisplayPresenter.pollQueue();
        this.timerDisplayPresenter.pollQueue();

        Assert.assertEquals(null, this.timerDisplayPresenter.pollQueue());
    }

    @Test
    public void testQueueIsNull()
    {
        this.timerDisplayPresenter.addToQueue(this.roundsModel1);

        this.timerDisplayPresenter.clearQueue();

        Assert.assertEquals(null, this.timerDisplayPresenter.pollQueue());
    }

    @Test
    public void testQueueIsReturnSecondRound()
    {
        this.timerDisplayPresenter.addToQueue(this.roundsModel2);

        this.timerDisplayPresenter.pollQueue();
        this.timerDisplayPresenter.pollQueue();

        Assert.assertEquals(Long.valueOf(5000), this.timerDisplayPresenter.pollQueue());
    }

    @Test
    public void testQueueIsReturnSecondRoundRest()
    {
        this.timerDisplayPresenter.addToQueue(this.roundsModel2);

        this.timerDisplayPresenter.pollQueue();
        this.timerDisplayPresenter.pollQueue();
        this.timerDisplayPresenter.pollQueue();

        Assert.assertEquals(Long.valueOf(2000), this.timerDisplayPresenter.pollQueue());
    }

    @Test
    public void testQueueShouldReturnNull()
    {
        this.timerDisplayPresenter.addToQueue(this.roundsModel2);

        this.timerDisplayPresenter.pollQueue();
        this.timerDisplayPresenter.pollQueue();
        this.timerDisplayPresenter.pollQueue();
        this.timerDisplayPresenter.pollQueue();

        Assert.assertEquals(null, this.timerDisplayPresenter.pollQueue());
    }
}