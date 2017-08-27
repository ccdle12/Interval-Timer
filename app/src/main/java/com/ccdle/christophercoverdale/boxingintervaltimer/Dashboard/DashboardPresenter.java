package com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard;

import com.ccdle.christophercoverdale.boxingintervaltimer.CustomRounds.CustomRoundsPresenter;
import com.ccdle.christophercoverdale.boxingintervaltimer.Settings.SettingsPresenter;
import com.ccdle.christophercoverdale.boxingintervaltimer.TimerDisplay.TimerDisplayPresenter;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.PackageModel;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.RoundsModel;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.TimeValuesHelper;

import javax.inject.Inject;

/**
 * Created by USER on 5/14/2017.
 */

public class DashboardPresenter implements DashboardInterface
{

    private DashboardCallback dashboardCallback;

    private TimerDisplayPresenterInterface timerDisplayPresenterInterface;
    private CustomRoundsPresenterInterface customRoundsPresenterInterface;
    private SettingsPresenterInterface settingsPresenterInterface;

    private PackageModel packageModel;

    @Inject
    public DashboardPresenter(TimerDisplayPresenter timerDisplay, CustomRoundsPresenter customRoundsPresenter, SettingsPresenter settingsPresenter)
    {
        this.timerDisplayPresenterInterface = timerDisplay;
        this.customRoundsPresenterInterface = customRoundsPresenter;
        this.settingsPresenterInterface = settingsPresenter;
    }

    /* Dashboard Interface */
    @Override
    public void setDashboardCallback(DashboardInterface.DashboardCallback callback)
    {
        this.dashboardCallback = callback;
    }

    @Override
    public void sendPackageModel(PackageModel packageModel)
    {
        this.packageModel = packageModel;
    }


    /* Increment and Decrement Buttons */
    @Override
    public void incrementNumberOfRounds(String numOfRounds)
    {
        int roundsAsInt = TimeValuesHelper.incrementValue(numOfRounds);

        if (roundsAsInt > 100)
            roundsAsInt = 100;

        String roundsAsString = TimeValuesHelper.convertIntToString(roundsAsInt);

        this.dashboardCallback.updateRoundsDisplay(roundsAsString);
    }

    @Override
    public void decrementNumberOfRounds(String numOfRounds)
    {
        int roundsAsInt = TimeValuesHelper.decrementValue(numOfRounds);

        if (roundsAsInt < 1)
            roundsAsInt = 1;

        String roundsAsString = TimeValuesHelper.convertIntToString(roundsAsInt);

        this.dashboardCallback.updateRoundsDisplay(roundsAsString);
    }

    @Override
    public void incrementWorkRoundTime(String minutes, String seconds)
    {
        int workMinutes = TimeValuesHelper.convertStringToInt(minutes);
        int workSeconds = TimeValuesHelper.convertStringToInt(seconds);

        if (workMinutes < 60)
        {
            ++workSeconds;

            if (workSeconds >= 60) {
                workSeconds = 0;
                ++workMinutes;
            }

            if (workMinutes > 60) {
                workMinutes = 60;
                workSeconds = 0;
            }

        } else
        {
            workSeconds = 0;
        }

        String formattedMinutes = TimeValuesHelper.formatMinutesToString(workMinutes);
        String formattedSeconds = TimeValuesHelper.formatSecondsToString(workSeconds);

        this.dashboardCallback.updateWorkRoundDisplayFromIncrement(formattedMinutes, formattedSeconds);
    }

    @Override
    public void decrementWorkRoundTime(String minutes, String seconds)
    {
        int workMinutes = TimeValuesHelper.convertStringToInt(minutes);
        int workSeconds = TimeValuesHelper.convertStringToInt(seconds);

        --workSeconds;

        if (workSeconds < 0)
        {
            workSeconds = 59;
            --workMinutes;
        }

        if (workMinutes < 0)
        {
            workMinutes = 0;
            workSeconds = 0;
        }


        String formattedMinutes = TimeValuesHelper.formatMinutesToString(workMinutes);
        String formattedSeconds = TimeValuesHelper.formatSecondsToString(workSeconds);

        this.dashboardCallback.updateWorkRoundDisplayFromIncrement(formattedMinutes,formattedSeconds);
    }

    @Override
    public void incrementRestRoundTime(String minutes, String seconds) {
        int restMinutes = TimeValuesHelper.convertStringToInt(minutes);
        int restSeconds = TimeValuesHelper.convertStringToInt(seconds);

        if (restMinutes < 60)
        {
            ++restSeconds;

            if (restSeconds >= 60) {
                restSeconds = 0;
                ++restMinutes;
            }

            if (restMinutes > 60) {
                restMinutes = 60;
                restSeconds = 0;
            }

        } else
        {
            restSeconds = 0;
        }

        String formattedMinutes = TimeValuesHelper.formatMinutesToString(restMinutes);
        String formattedSeconds = TimeValuesHelper.formatSecondsToString(restSeconds);

        this.dashboardCallback.updateRestRoundDisplayFromIncrement(formattedMinutes, formattedSeconds);
    }

    @Override
    public void decrementRestRoundTime(String minutes, String seconds) {
        int restMinutes = TimeValuesHelper.convertStringToInt(minutes);
        int restSeconds = TimeValuesHelper.convertStringToInt(seconds);

        --restSeconds;

        if (restSeconds < 0)
        {
            restSeconds = 59;
            --restMinutes;
        }

        if (restMinutes < 0)
        {
            restMinutes = 0;
            restSeconds = 0;
        }


        String formattedMinutes = TimeValuesHelper.formatMinutesToString(restMinutes);
        String formattedSeconds = TimeValuesHelper.formatSecondsToString(restSeconds);

        this.dashboardCallback.updateRestRoundDisplayFromIncrement(formattedMinutes,formattedSeconds);
    }



    /* Checks for user manually changing time values */
    @Override
    public void checkRoundLimits(String rounds)
    {
        if (rounds.length() > 0)
        {
            int numberOfRounds = TimeValuesHelper.convertStringToInt(rounds);

            if (numberOfRounds > 100)
            {
                numberOfRounds = 100;
                this.sendUpdateToRoundsDisplay(numberOfRounds);
            }

            if (numberOfRounds < 1)
            {
                numberOfRounds = 1;
                this.sendUpdateToRoundsDisplay(numberOfRounds);
            }

        }
    }

    private void sendUpdateToRoundsDisplay(int numberOfRounds)
    {
        String formattedRounds = TimeValuesHelper.convertIntToString(numberOfRounds);
        this.dashboardCallback.updateRoundsDisplay(formattedRounds);
    }


    @Override
    public void checkMinutesLimit(String minutes, int editTextID) {
        if (minutes.length() > 0)
        {
            int minutesAsInt = TimeValuesHelper.convertStringToInt(minutes);

            if (minutesAsInt >= 60)
            {
                minutesAsInt = 60;
                this.sendUpdateSecondsDisplay(0, editTextID);
            }

            this.sendUpdateMinutesToDisplay(minutesAsInt, editTextID);

        } else
        {
            this.sendUpdateMinutesToDisplay(1, editTextID);
        }
    }

    private void sendUpdateMinutesToDisplay(int minutes, int editTextID)
    {
        String formattedMinutes = TimeValuesHelper.formatMinutesToString(minutes);

        switch(editTextID)
        {
            case 0:
                this.dashboardCallback.updateWorkMinutesDisplay(formattedMinutes);
                break;

            case 1:
                this.dashboardCallback.updateRestMinutesDisplay(formattedMinutes);
                break;
        }
    }



    @Override
    public void checkSecondsLimit(String minutes, String seconds, int editTextID)
    {
        if (seconds.length() > 0)
        {
            int minutesAsInt = TimeValuesHelper.convertStringToInt(minutes);
            int secondsAsInt = TimeValuesHelper.convertStringToInt(seconds);

            if (secondsAsInt > 59)
                secondsAsInt = 0;

            if (minutesAsInt >= 60)
                secondsAsInt = 0;


            this.sendUpdateSecondsDisplay(secondsAsInt, editTextID);

        } else
        {
            this.sendUpdateSecondsDisplay(0, editTextID);
        }

    }

    private void sendUpdateSecondsDisplay(int seconds, int editTextID)
    {
        String formattedSeconds = TimeValuesHelper.formatSecondsToString(seconds);

        switch(editTextID)
        {
            case 0:
                this.dashboardCallback.updateWorkSecondsDisplay(formattedSeconds);
                break;

            case 1:
                this.dashboardCallback.updateRestSecondsDisplay(formattedSeconds);
                break;
        }
    }


    @Override
    public void startTheTimer(RoundsModel roundsModel)
    {
        this.sendPackageModelToTimerDisplayPresenter();
        this.sendRoundsModel(roundsModel);
        this.launchTimerDisplay();

    }

    @Override
    public void launchCustomRounds()
    {
        this.customRoundsPresenterInterface.sendPackageModel(this.packageModel);
        this.customRoundsPresenterInterface.launchCustomRounds();
    }

    @Override
    public void launchSettings()
    {
        this.settingsPresenterInterface.sendPackageModel(this.packageModel);
        this.settingsPresenterInterface.launchSettings();
    }

    private void launchTimerDisplay()
    {
        this.timerDisplayPresenterInterface.launchTimerDisplay();
    }

    private void sendPackageModelToTimerDisplayPresenter()
    {
        this.timerDisplayPresenterInterface.sendPackageModel(this.packageModel);
    }

    private void sendRoundsModel(RoundsModel roundsModel)
    {
        this.timerDisplayPresenterInterface.sendRoundsModel(roundsModel);
    }

}
