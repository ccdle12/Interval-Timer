package com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard;

/**
 * Created by USER on 5/14/2017.
 */

public interface DashboardInterface {

    //------------------------- Will move below to its own Presenter -----------------------------//
    void addToQueue(String workMins, String workSecs, String restMins, String restSecs, String numberOfRounds);
    void initializeTimer();
    void pauseAndResumeTimer();
    //------------------------- Will move above to its own Presenter -----------------------------//

    void setDashboardCallback(DashboardCallback callback);

    void incrementNumberOfRounds(String numOfRounds);
    void decrementNumberOfRounds(String numOfRounds);

    void incrementWorkRoundTime(String minutes, String seconds);
    void decrementWorkRoundTime(String minutes, String seconds);

    void incrementRestRoundTime(String minutes, String seconds);
    void decrementRestRoundTime(String minutes, String seconds);

    void checkRoundLimits(String rounds);
    void checkMinutesLimit(String minutes, int editTextID);
    void checkSecondsLimit(String minutes, String seconds, int editTextID);





    interface DashboardCallback {
        //------------------------- Will move below to its own Presenter -----------------------------//
        void updateTimerDisplay(String time);
        //------------------------- Will move above to its own Presenter -----------------------------//

        void updateRoundsDisplay(String rounds);
        void updateWorkRoundDisplayFromIncrement(String formattedMinutes, String formattedSeconds);
        void updateRestRoundDisplayFromIncrement(String formattedMinutes, String formattedSeconds);

        void updateWorkMinutesDisplay(String formattedMinutes);
        void updateWorkSecondsDisplay(String formattedSeconds);

        void updateRestMinutesDisplay(String formattedMinutes);
        void updateRestSecondsDisplay(String formattedSeconds);
    }
}
