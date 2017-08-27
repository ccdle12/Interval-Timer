package com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard;

import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.PackageModel;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.RoundsModel;

/**
 * Created by USER on 5/14/2017.
 */

public interface DashboardInterface
{

    void setDashboardCallback(DashboardCallback callback);
    void sendPackageModel(PackageModel packageModel);

    void incrementNumberOfRounds(String numOfRounds);
    void decrementNumberOfRounds(String numOfRounds);

    void incrementWorkRoundTime(String minutes, String seconds);
    void decrementWorkRoundTime(String minutes, String seconds);

    void incrementRestRoundTime(String minutes, String seconds);
    void decrementRestRoundTime(String minutes, String seconds);

    void checkRoundLimits(String rounds);
    void checkMinutesLimit(String minutes, int editTextID);
    void checkSecondsLimit(String minutes, String seconds, int editTextID);

    void startTheTimer(RoundsModel roundsModel);
    void launchCustomRounds();
    void launchSettings();

    interface DashboardCallback
    {
        void updateRoundsDisplay(String rounds);
        void updateWorkRoundDisplayFromIncrement(String formattedMinutes, String formattedSeconds);
        void updateRestRoundDisplayFromIncrement(String formattedMinutes, String formattedSeconds);

        void updateWorkMinutesDisplay(String formattedMinutes);
        void updateWorkSecondsDisplay(String formattedSeconds);

        void updateRestMinutesDisplay(String formattedMinutes);
        void updateRestSecondsDisplay(String formattedSeconds);
    }
}
