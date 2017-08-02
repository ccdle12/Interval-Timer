package com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard;

import android.util.Log;

import com.ccdle.christophercoverdale.boxingintervaltimer.Timer.CountDownTimer;
import com.ccdle.christophercoverdale.boxingintervaltimer.Timer.CountDownTimerInterface;

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
        long workInMillis = this.calculateTotalTimeInMillis(workMins, workSecs);
        long restInMillis = this.calculateTotalTimeInMillis(restMins, restSecs);
        int numberOfRounds = Integer.valueOf(numOfRounds);

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

    private long calculateTotalTimeInMillis(String minutes, String seconds)
    {
        long mins = Long.parseLong(minutes);
        long secs = Long.parseLong(seconds);

        long minsInMillis = (mins * 60) * 1000;
        long secsInMillis = secs * 1000;

        long totalTimeInMillis = minsInMillis + secsInMillis;

        return totalTimeInMillis;
    }


    @Override
    public void initializeTimer()
    {
        this.initializeNextRound();

        this.countDownTimer.startTheCountDownTimer();
        Log.d(TAG, "Count Down Timer Starting");
    }

    private void initializeNextRound()
    {
        long nextRound = (Long) this.pollQueue();
        this.countDownTimer = new CountDownTimer(nextRound);
        this.setCountDownTimerCallback();
    }

    private void setCountDownTimerCallback()
    {
        this.countDownTimer.setCallback(this);
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
        remainingTime = remainingTime / 1000;
        int remainingMinutes = (int) remainingTime / 60;
        int remainingSeconds = (int) remainingTime - (60 * remainingMinutes);

        String minutesForDisplay = formatMinutes(remainingMinutes);
        String secondsForDisplay = formatSeconds(remainingSeconds);

        String formattedDisplay = String.format("%s:%s", minutesForDisplay, secondsForDisplay);

        this.dashboardCallback.updateTimerDisplay(formattedDisplay);
    }

    public String formatMinutes(int remainingMinutes)
    {
        return (remainingMinutes < 10) ? String.format("0%s", remainingMinutes) : String.valueOf(remainingMinutes);
    }

    public String formatSeconds(int remainingSeconds)
    {
        String secondsFormatted;

        if (remainingSeconds < 10) {
            secondsFormatted = String.format("0%s", remainingSeconds);
        } else if (remainingSeconds > 59) {
            secondsFormatted = "00";
        } else {
            secondsFormatted = String.valueOf(remainingSeconds);
        }

        return secondsFormatted;
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
