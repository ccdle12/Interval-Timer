package com.ccdle.christophercoverdale.boxingintervaltimer.TimerDisplay;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.provider.MediaStore;
import android.util.Log;

import com.ccdle.christophercoverdale.boxingintervaltimer.CountDownTimer.CountDownTimer;
import com.ccdle.christophercoverdale.boxingintervaltimer.CountDownTimer.CountDownTimerInterface;
import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.Dashboard;
import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.TimerDisplayPresenterInterface;
import com.ccdle.christophercoverdale.boxingintervaltimer.FinishedScreen.FinishedScreenPresenter;
import com.ccdle.christophercoverdale.boxingintervaltimer.R;
import com.ccdle.christophercoverdale.boxingintervaltimer.Settings.SettingsSingleton;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.AudioManagerHelper;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.CustomRoundType;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.PackageModel;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.RoundType;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.RoundsModel;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.SoundFX;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.TimeValuesHelper;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.VibrationHelper;

import java.util.concurrent.LinkedBlockingDeque;

import javax.inject.Inject;


/**
 * Created by christophercoverdale on 08/08/2017.
 */

public class TimerDisplayPresenter implements TimerDisplayInterface, TimerDisplayPresenterInterface, CountDownTimerInterface.Callback  {

    private PackageModel packageModel;
    private RoundsModel roundsModel;
    private TimerDisplay timerDisplayForLaunchingFragment;
    private TimerDisplayCallback timerDisplayCallback;

    private LinkedBlockingDeque timerQueue;
    private SoundFX countDownBeep;
    private SoundFX roundEndBell;

    private CountDownTimerInterface countDownTimer;

    private int currentRoundNumber;
    private String currentRoundType;

    private FinishedScreenPresenterInterface finishedScreen;

    private boolean isCustomList;
    private boolean isIntro;

    private AudioManagerHelper audioManagerHelper;
    private VibrationHelper vibrationHelper;


    /* Dagger Injection */
    @Inject
    public TimerDisplayPresenter(LinkedBlockingDeque<RoundType> queue, TimerDisplay timerDisplay, FinishedScreenPresenter finishedScreen)
    {
        this.timerQueue = queue;
        this.timerDisplayForLaunchingFragment = timerDisplay;
        this.timerDisplayCallback = this.timerDisplayForLaunchingFragment;

        this.countDownBeep = new SoundFX();
        this.roundEndBell = new SoundFX();

        this.finishedScreen = finishedScreen;

        this.timerDisplayCallback.setPresenterInterface(this);
    }






    /* Callbacks from Dashboard Presenter */
    @Override
    public void sendPackageModel(PackageModel packageModel)
    {
        this.packageModel = packageModel;
        this.initSoundFX();
    }

    private void initSoundFX()
    {
        this.countDownBeep.createCountDownSound(this.packageModel.getActivity());
        this.roundEndBell.createBoxingBellSound(this.packageModel.getActivity());
    }

    @Override
    public void sendRoundsModel(RoundsModel roundsModel)
    {
        this.isCustomList = false;
        this.roundsModel = roundsModel;
    }

    @Override
    public void sendQueue(LinkedBlockingDeque queue)
    {
        this.isCustomList = true;
        this.timerQueue = queue;
    }

    @Override
    public void launchTimerDisplay()
    {
        this.replaceFragmentInActivity();
    }

    private void replaceFragmentInActivity()
    {
        Activity activityRef = this.packageModel.getActivity();

        activityRef.getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_holder, timerDisplayForLaunchingFragment)
                .commit();
    }






    /* Interface to Timer Display */
    @Override
    public void setTimerDisplayCallback(TimerDisplayCallback timerDisplayCallback)
    {
        this.timerDisplayCallback = timerDisplayCallback;
    }

    @Override
    public void viewCreated()
    {
        this.addIntroIfChecked();
        this.launchCountDownTimer();

        if (!this.isCustomList)
            this.updateTotalRoundsDisplay(this.roundsModel.getRounds());
        else
        {
            int totalRounds = (this.timerQueue.size()/2) + 1;
            this.updateTotalRoundsDisplay(String.valueOf(totalRounds));
        }

        this.timerDisplayCallback.updateRemainingRoundsDisplay(String.valueOf(this.currentRoundNumber));

        this.updateTimerDisplayBackground();

        this.initHelperVariables();
    }

    private void addIntroIfChecked()
    {
        if (SettingsSingleton.getInstance().isIntroRound())
            this.addFiveSecondIntro();
    }

    public void addFiveSecondIntro()
    {
        RoundType fiveSecondIntro = new RoundType(5000, "intro");
        this.isIntro = true;
        this.timerQueue.addFirst(fiveSecondIntro);
    }

    private void launchCountDownTimer()
    {
        if (!this.isCustomList)
            this.addToQueue(this.roundsModel);

        this.initializeTimer();
    }

    private void initHelperVariables()
    {
        this.initAudioManagerHelper();
        this.initVibrationHelper();
    }

    private void initAudioManagerHelper()
    {
        this.audioManagerHelper = new AudioManagerHelper(this.packageModel.getActivity());
    }

    private void initVibrationHelper()
    {
        this.vibrationHelper = new VibrationHelper(this.packageModel.getActivity());
    }

    private void updateTotalRoundsDisplay(String totalRounds)
    {
        this.timerDisplayCallback.updateTotalRoundsDisplay(totalRounds);
    }

    private void updateTimerDisplayBackground()
    {
        Activity activity = this.packageModel.getActivity();

        if (this.currentRoundType.equals("intro"))
            this.timerDisplayCallback.updateBackgroundColor(activity.getResources().getColor(R.color.accent));

        if (this.currentRoundType.equals("work"))
            this.timerDisplayCallback.updateBackgroundColor(activity.getResources().getColor(R.color.work_color));

        if (this.currentRoundType.equals("rest"))
            this.timerDisplayCallback.updateBackgroundColor(activity.getResources().getColor(R.color.rest_color));

    }







    /* Queue methods for Count Down Timer */
    public void addToQueue(RoundsModel roundsModel)
    {
        String workMins = roundsModel.getWorkMins();
        String workSecs = roundsModel.getWorkSecs();
        String restMins = roundsModel.getRestMins();
        String restSecs = roundsModel.getRestSecs();
        String numOfRounds = roundsModel.getRounds();

        long workInMillis = TimeValuesHelper.calculateTotalTimeInMillis(workMins, workSecs);
        long restInMillis = TimeValuesHelper.calculateTotalTimeInMillis(restMins, restSecs);
        int numberOfRounds = TimeValuesHelper.convertStringToInt(numOfRounds);

        RoundType roundTypeWork = new RoundType(workInMillis, "work");
        RoundType roundTypeRest = new RoundType(restInMillis, "rest");

        this.sortAddToQueue(roundTypeWork, roundTypeRest, numberOfRounds);
    }

    private void sortAddToQueue(RoundType roundTypeWork, RoundType roundTypeRest, int numOfRounds)
    {
        for (int i = 0; i < numOfRounds; i++) {
            this.timerQueue.add(roundTypeWork);
            this.timerQueue.add(roundTypeRest);
        }
    }

    public Object pollQueue() {
        return this.timerQueue.poll();
    }

    public void clearQueue() {
        this.timerQueue.clear();
    }


    public void initializeTimer()
    {
        this.initializeNextRound();
        this.countDownTimer.startTheCountDownTimer();
    }

    private void initializeNextRound()
    {
        RoundType roundType = (RoundType) this.pollQueue();

        this.currentRoundType = roundType.getRoundType();

        if (this.timerQueue.size() <= 1)
            this.currentRoundNumber = this.timerQueue.size();
        else
            this.currentRoundNumber = this.getCurrentRoundNumber() + 1;

        long nextRound = roundType.getRoundTime();
        this.initializeCountDownTimer(nextRound);


        this.updateTimerDisplayBackground();
        this.setCountDownTimerCallback();


    }

    private int getCurrentRoundNumber()
    {
        int totalWorkRound = 0;
        for (Object item : this.timerQueue)
        {
            if (this.isCustomList)
            {
                CustomRoundType eachRound = (CustomRoundType) item;

                if (eachRound.getRoundType().equals("work"))
                {
                    totalWorkRound++;
                }

            } else {

                RoundType eachRound = (RoundType) item;

                if (eachRound.getRoundType().equals("work"))
                {
                    totalWorkRound++;
                }
            }
        }

        return totalWorkRound;
    }

    private void initializeCountDownTimer(long nextRound)
    {
        this.countDownTimer = new CountDownTimer(nextRound);
    }

    private void setCountDownTimerCallback() {
        this.countDownTimer.setCallback(this);
    }








    /* Count Down Timer Callback */
    @Override
    public void formatRemainingTime(long remainingTime)
    {
        int remainingSeconds = TimeValuesHelper.formatRemainingSeconds(remainingTime);
        int remainingMinutes = TimeValuesHelper.formatRemainingMinutes(remainingTime);

        remainingSeconds++;

        if (this.currentRoundType.equals("intro")) {

            this.timerDisplayCallback.hideUIElements();
            this.timerDisplayCallback.showRoundIndicatorAsIntroIndicator("Get Ready");

            this.timerDisplayCallback.updateTimerDisplay(String.valueOf(remainingSeconds));

        }
        else {
            String minutesForDisplay = TimeValuesHelper.formatMinutesToString(remainingMinutes);
            String secondsForDisplay = TimeValuesHelper.formatSecondsToString(remainingSeconds);

            String formattedDisplay = String.format("%s:%s", minutesForDisplay, secondsForDisplay);

            this.timerDisplayCallback.showUIElements();
            this.timerDisplayCallback.updateTimerDisplay(formattedDisplay);
        }

        if (SettingsSingleton.getInstance().isCountDownSound() && remainingSeconds <= 5 && remainingSeconds != 1 && remainingMinutes == 0 && this.audioManagerHelper.requesetAudioFoucs())
        {
            this.countDownBeep.startSoundFX();
        }
    }

    @Override
    public void timerHasFinished()
    {

        if (this.queueHasNext()) {

            this.launchRoundEndSound();
            this.launchVibration();

            this.initializeNextRound();

            this.updateRemainingRoundsDisplay();
            this.updateRoundIndicator();
            this.updateTimerDisplayBackground();

            this.countDownTimer.startTheCountDownTimer();
        } else {

            this.countDownBeep.releaseMediaPlayer();
            this.roundEndBell.releaseMediaPlayer();

            this.finishedScreen.sendPackageModel(this.packageModel);
            this.finishedScreen.launchFinishedScreen();
        }

        this.isIntro = false;
    }

    private void launchRoundEndSound()
    {
        if (this.audioManagerHelper.requesetAudioFoucs())
        {
            if (SettingsSingleton.getInstance().isEndOfRoundSound())
                this.roundEndBell.startSoundFX();

            this.audioManagerHelper.releaseAudioFocus();
        }
    }

    private void launchVibration()
    {
        if (SettingsSingleton.getInstance().isVibrate())
            this.vibrationHelper.initShortVibrate();
    }

    private void updateRemainingRoundsDisplay()
    {
        if (this.currentRoundType.equals("work"))
        {
            String remainingRounds = String.valueOf(this.currentRoundNumber);
            this.timerDisplayCallback.updateRemainingRoundsDisplay(remainingRounds);
        }

    }

    private void updateRoundIndicator()
    {
        String currentRoundType;

        if (this.currentRoundType.equals("work"))
            currentRoundType = "Work";
        else if (this.currentRoundType.equals("rest"))
            currentRoundType = "Rest";
        else
            currentRoundType = " ";


        this.timerDisplayCallback.updateRoundIndicator(currentRoundType);
    }


    private Boolean queueHasNext() {
        return !this.timerQueue.isEmpty() ? true : false;
    }

    @Override
    public void pauseAndResumeTimer()
    {
        if (this.countDownTimer.isRunning()) {
            this.timerDisplayCallback.updatePauseButtonGo();
            this.countDownTimer.stopTheCountDownTimer();
        }
        else {
            this.timerDisplayCallback.updatePauseButtonStop();
            this.countDownTimer.startTheCountDownTimer();
        }

    }


    @Override
    public void resetTimer()
    {
        if (!this.countDownTimer.isRunning())
        {
            this.clearQueue();
            this.launchDashboardFragment();
        }
        else
        {
            this.timerDisplayCallback.showSnackbarPressPause();
        }

    }

    private void launchDashboardFragment()
    {
        Activity activityRef = this.packageModel.getActivity();

        activityRef.getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_holder, new Dashboard())
                .commit();
    }

}
