package com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard;

import android.util.Log;

import com.ccdle.christophercoverdale.boxingintervaltimer.Timer.CountDownTimer;
import com.ccdle.christophercoverdale.boxingintervaltimer.Timer.CountDownTimerInterface;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.TimeValuesHelper;

import java.util.LinkedList;
import java.util.Queue;

import javax.inject.Inject;

import static android.content.ContentValues.TAG;

/**
 * Created by USER on 5/14/2017.
 */

public class DashboardPresenter implements DashboardInterface, CountDownTimerInterface.Callback {

    /* Queue for each round*/
    private Queue timerQueue;

    private DashboardCallback dashboardCallback;
    private CountDownTimerInterface countDownTimer;


    @Inject
    public DashboardPresenter(LinkedList<Long> timerQueue)
    {
        this.timerQueue = timerQueue;
    }


    /* Dashboard Interface */
    @Override
    public void setDashboardCallback(DashboardCallback callback)
    {
        this.dashboardCallback = callback;
    }


    @Override
    public void addToQueue(String workMins, String workSecs, String restMins, String restSecs, String numOfRounds)
    {
        long workInMillis = TimeValuesHelper.calculateTotalTimeInMillis(workMins, workSecs);
        long restInMillis = TimeValuesHelper.calculateTotalTimeInMillis(restMins, restSecs);
        int numberOfRounds = TimeValuesHelper.convertStringToInt(numOfRounds);

        this.sortAddToQueue(workInMillis, restInMillis, numberOfRounds);
    }

    private void sortAddToQueue(long workInMillis, long restInMillis, int numOfRounds)
    {
        for (int i = 0; i < numOfRounds; i++)
        {
            this.timerQueue.add(workInMillis);
            this.timerQueue.add(restInMillis);
        }
    }

    public Object pollQueue()
    {
        return this.timerQueue.poll();
    }

    public void clearQueue()
    {
        this.timerQueue.clear();
    }


    @Override
    public void initializeTimer()
    {
        this.initializeNextRound();

        this.countDownTimer.startTheCountDownTimer();
    }


    private void initializeNextRound()
    {
        long nextRound = (Long) this.pollQueue();
        this.initializeCountDownTimer(nextRound);
        this.setCountDownTimerCallback();
    }

    private void initializeCountDownTimer(long nextRound)
    {
        this.countDownTimer = new CountDownTimer(nextRound);
    }

    private void setCountDownTimerCallback()
    {
        this.countDownTimer.setCallback(this);
    }



    @Override
    public void incrementNumberOfRounds(String numOfRounds)
    {
        int roundsAsInt = TimeValuesHelper.incrementValue(numOfRounds);

        String roundsAsString = TimeValuesHelper.convertIntToString(roundsAsInt);

        this.dashboardCallback.updateRoundsDisplay(roundsAsString);
    }

    @Override
    public void decrementNumberOfRounds(String numOfRounds) {

    }


    @Override
    public void pauseAndResumeTimer()
    {
        if (this.countDownTimer.isRunning()) {
            this.countDownTimer.stopTheCountDownTimer();
        } else {
            this.countDownTimer.startTheCountDownTimer();
        }
    }



    /* Count Down Timer Callback */
    @Override
    public void formatRemainingTime(long remainingTime)
    {
        int remainingMinutes = TimeValuesHelper.formatRemainingMinutes(remainingTime);
        int remainingSeconds = TimeValuesHelper.formatRemainingSeconds(remainingTime);

        String minutesForDisplay = TimeValuesHelper.formatMinutesToString(remainingMinutes);
        String secondsForDisplay = TimeValuesHelper.formatSecondsToString(remainingSeconds);

        String formattedDisplay = String.format("%s:%s", minutesForDisplay, secondsForDisplay);

        this.dashboardCallback.updateTimerDisplay(formattedDisplay);
    }

    @Override
    public void timerHasFinished()
    {
        if (this.queueHasNext()) {
            this.initializeNextRound();
            this.countDownTimer.startTheCountDownTimer();
        } else {
            this.dashboardCallback.updateTimerDisplay("Finished!");
        }
    }

    private Boolean queueHasNext()
    {
        return !this.timerQueue.isEmpty() ? true : false;
    }

}
