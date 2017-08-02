package com.ccdle.christophercoverdale.boxingintervaltimer.Timer;

/**
 * Created by USER on 5/30/2017.
 */

public interface CountDownTimerInterface {
    void setCallback(Callback callback);
    void startTheCountDownTimer();
    void stopTheCountDownTimer();
    boolean isRunning();

    interface Callback {
        void formatRemainingTime(long remainingTime);
        void timerHasFinished();
    }
}
