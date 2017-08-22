package com.ccdle.christophercoverdale.boxingintervaltimer.CountDownTimer;

import android.util.Log;

/**
 * Created by USER on 5/23/2017.
 */

public class CountDownTimer extends android.os.CountDownTimer implements CountDownTimerInterface
{
    private final String TAG = "CountDownTimer";

    Callback countDownTimerCallback;

    private long remainingTime;
    private boolean hasPaused = false;
    private boolean isRunning = false;

    public CountDownTimer(long time)
    {
        super(time, 10);
    }

    @Override
    public void onTick(long millisUntilFinished)
    {
        if (this.hasPaused) {
            if (remainingTime <= 0) {
                this.terminateTimer();
            } else {
                remainingTime = remainingTime - 10;
                this.countDownTimerCallback.formatRemainingTime(remainingTime);
            }

        } else {
            remainingTime = millisUntilFinished;

            this.countDownTimerCallback.formatRemainingTime(millisUntilFinished);

        }
    }

    private void terminateTimer() {
        this.onFinish();
        this.cancel();
    }

    @Override
    public void onFinish() {
        hasPaused = false;
        this.countDownTimerCallback.timerHasFinished();
    }

    /* Count Down Timer Interface*/
    @Override
    public void setCallback(Callback callback) {
        this.countDownTimerCallback = callback;
    }


    @Override
    public void startTheCountDownTimer() {
        this.isRunning = true;
        this.start();
    }

    @Override
    public void stopTheCountDownTimer() {
        this.isRunning = false;
        hasPaused = true;
        this.cancel();
    }

    @Override
    public boolean isRunning() {
        return this.isRunning;
    }

}
