package com.ccdle.christophercoverdale.boxingintervaltimer.FinishedScreen;

/**
 * Created by christophercoverdale on 14/08/2017.
 */

public interface FinishedScreenInterface
{
    void viewCreated();
    void launchDashboard();

    interface FinishedScreenCallback
    {
        void setInterface(FinishedScreenInterface finishedScreenInterface);
    }
}
