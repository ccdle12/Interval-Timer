package com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard;

/**
 * Created by USER on 5/14/2017.
 */

public interface DashboardInterface {
    void setDashboardCallback(DashboardCallback callback);
    void addToQueue(String workMins, String workSecs, String restMins, String restSecs, String numberOfRounds);
    void initializeTimer();
    void pauseAndResumeTimer();

    interface DashboardCallback {
        void updateTimerDisplay(String time);

    }
}
