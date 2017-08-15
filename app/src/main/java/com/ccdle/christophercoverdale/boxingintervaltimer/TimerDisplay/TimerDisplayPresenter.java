package com.ccdle.christophercoverdale.boxingintervaltimer.TimerDisplay;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.ccdle.christophercoverdale.boxingintervaltimer.CountDownTimer.CountDownTimer;
import com.ccdle.christophercoverdale.boxingintervaltimer.CountDownTimer.CountDownTimerInterface;
import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.Dashboard;
import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.TimerDisplayPresenterInterface;
import com.ccdle.christophercoverdale.boxingintervaltimer.FinishedScreen.FinishedScreen;
import com.ccdle.christophercoverdale.boxingintervaltimer.FinishedScreen.FinishedScreenPresenter;
import com.ccdle.christophercoverdale.boxingintervaltimer.R;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.PackageModel;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.RoundType;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.RoundsModel;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.SoundFX;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.TimeValuesHelper;

import java.util.LinkedList;
import java.util.Queue;

import javax.inject.Inject;

import static android.content.ContentValues.TAG;


/**
 * Created by christophercoverdale on 08/08/2017.
 */

public class TimerDisplayPresenter implements TimerDisplayInterface, TimerDisplayPresenterInterface, CountDownTimerInterface.Callback {

    private PackageModel packageModel;
    private RoundsModel roundsModel;
    private TimerDisplay timerDisplayForLaunchingFragment;
    private TimerDisplayCallback timerDisplayCallback;

    private Queue timerQueue;
    private SoundFX countDownBeep;
    private SoundFX roundEndBell;

    private CountDownTimerInterface countDownTimer;

    private int currentRoundNumber;
    private String currentRoundType;

    private FinishedScreenPresenterInterface finishedScreen;


    /* Dagger Injection */
    @Inject
    public TimerDisplayPresenter(LinkedList<RoundType> queue, TimerDisplay timerDisplay, FinishedScreenPresenter finishedScreen)
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
        this.roundsModel = roundsModel;
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
        this.addFiveSecondIntro();
        this.launchCountDownTimer();

        this.updateTotalRoundsDisplay(this.roundsModel.getRounds());
        this.timerDisplayCallback.updateRemainingRoundsDisplay(String.valueOf(this.currentRoundNumber));

        this.updateTimerDisplayBackground();
    }

    public void addFiveSecondIntro()
    {
        RoundType fiveSecondIntro = new RoundType(5000, "intro");
        this.timerQueue.add(fiveSecondIntro);
    }

    private void launchCountDownTimer()
    {
        this.addToQueue(this.roundsModel);
        this.initializeTimer();
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
            RoundType eachRound = (RoundType) item;

            if (eachRound.getRoundType().equals("work"))
            {
                totalWorkRound++;
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
        remainingSeconds++;

        if (this.currentRoundType.equals("intro")) {

            this.timerDisplayCallback.hideUIElements();
            this.timerDisplayCallback.showRoundIndicatorAsIntroIndicator("Get Ready");

            this.timerDisplayCallback.updateTimerDisplay(String.valueOf(remainingSeconds));

        }
        else {

            int remainingMinutes = TimeValuesHelper.formatRemainingMinutes(remainingTime);

            String minutesForDisplay = TimeValuesHelper.formatMinutesToString(remainingMinutes);
            String secondsForDisplay = TimeValuesHelper.formatSecondsToString(remainingSeconds);

            String formattedDisplay = String.format("%s:%s", minutesForDisplay, secondsForDisplay);

            this.timerDisplayCallback.showUIElements();
            this.timerDisplayCallback.updateTimerDisplay(formattedDisplay);
        }

        if (remainingSeconds <= 5 && remainingSeconds != 1)
            this.countDownBeep.startSoundFX();
    }

    @Override
    public void timerHasFinished()
    {

        if (this.queueHasNext()) {
            this.roundEndBell.startSoundFX();
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
