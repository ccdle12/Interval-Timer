package com.ccdle.christophercoverdale.boxingintervaltimer.TimerDisplay;

import com.google.android.gms.ads.AdRequest;

/**
 * Created by christophercoverdale on 08/08/2017.
 */

public interface TimerDisplayInterface
{
    void setTimerDisplayCallback(TimerDisplayCallback timerDisplayCallback);
    void viewCreated();
    void loadAdToView();

    void initializeTimer();
    void pauseAndResumeTimer();

    void resetTimer();

    interface TimerDisplayCallback
    {
        void setPresenterInterface(TimerDisplayInterface timerDisplayPresenter);

        void updateTotalRoundsDisplay(String totalRounds);
        void updateRemainingRoundsDisplay(String remainingRounds);
        void updateTimerDisplay(String time);
        void updateRoundIndicator(String roundIndicator);

        void hideUIElements();
        void showUIElements();
        void showRoundIndicatorAsIntroIndicator(String getReady);

        void showSnackbarPressPause();

        void updateBackgroundColor(int color);

        void updatePauseButtonStop();
        void updatePauseButtonGo();

        void updateAdView(AdRequest adRequest);
    }
}
