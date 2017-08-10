package com.ccdle.christophercoverdale.boxingintervaltimer.TimerDisplay;

import android.app.Activity;
import android.util.Log;

import com.ccdle.christophercoverdale.boxingintervaltimer.CountDownTimer.CountDownTimer;
import com.ccdle.christophercoverdale.boxingintervaltimer.CountDownTimer.CountDownTimerInterface;
import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.TimerDisplayPresenterInterface;
import com.ccdle.christophercoverdale.boxingintervaltimer.R;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.PackageModel;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.RoundType;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.RoundsModel;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.TimeValuesHelper;

import java.util.LinkedList;
import java.util.Queue;

import javax.inject.Inject;

import static android.content.ContentValues.TAG;


/**
 * Created by christophercoverdale on 08/08/2017.
 */

public class TimerDisplayPresenter implements TimerDisplayInterface, TimerDisplayPresenterInterface, CountDownTimerInterface.Callback
{
    private PackageModel packageModel;
    private RoundsModel roundsModel;
    private TimerDisplay timerDisplayForLaunchingFragment;
    private TimerDisplayCallback timerDisplayCallback;

    private Queue timerQueue;
    private CountDownTimerInterface countDownTimer;

    private int currentRoundNumber;
    private String currentRoundType;







    /* Dagger Injection */
    @Inject
    public TimerDisplayPresenter(LinkedList<RoundType> queue, TimerDisplay timerDisplay)
    {
        this.timerQueue = queue;
        this.timerDisplayForLaunchingFragment = timerDisplay;
        this.timerDisplayCallback = this.timerDisplayForLaunchingFragment;
        this.timerDisplayCallback.setPresenterInterface(this);
    }







    /* Callbacks from Dashboard Presenter */
    @Override
    public void sendPackageModel(PackageModel packageModel)
    {
        this.packageModel = packageModel;
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
        this.launchCountDownTimer();

        this.updateTotalRoundsDisplay(this.roundsModel.getRounds());
        this.timerDisplayCallback.updateRemainingRoundsDisplay(String.valueOf(this.currentRoundNumber));

        this.updateRoundIndicator();
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

    private void updateRoundIndicator()
    {
        this.timerDisplayCallback.updateRoundIndicator(this.currentRoundType);
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
        for (int i = 0; i < numOfRounds; i++)
        {
            this.timerQueue.add(roundTypeWork);
            this.timerQueue.add(roundTypeRest);
        }
    }

    public Object pollQueue()
    {
        return this.timerQueue.poll();
    }

    public void clearQueue()
    {
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
            this.currentRoundNumber = this.timerQueue.size() - 1;

        long nextRound = roundType.getRoundTime();
        this.initializeCountDownTimer(nextRound);
        this.setCountDownTimerCallback();
    }

    private void initializeCountDownTimer(long nextRound)
    {
        this.countDownTimer = new CountDownTimer(nextRound);
    }

    private void setCountDownTimerCallback()
    {
        this.countDownTimer.setCallback(this);
    }







    /* Count Down Timer Callback */
    @Override
    public void formatRemainingTime(long remainingTime)
    {
        int remainingMinutes = TimeValuesHelper.formatRemainingMinutes(remainingTime);
        int remainingSeconds = TimeValuesHelper.formatRemainingSeconds(remainingTime);

        remainingSeconds++;

        String minutesForDisplay = TimeValuesHelper.formatMinutesToString(remainingMinutes);
        String secondsForDisplay = TimeValuesHelper.formatSecondsToString(remainingSeconds);

        String formattedDisplay = String.format("%s:%s", minutesForDisplay, secondsForDisplay);



        this.timerDisplayCallback.updateTimerDisplay(formattedDisplay);
    }

    @Override
    public void timerHasFinished()
    {
        if (this.queueHasNext()) {
            this.initializeNextRound();
            this.updateRemainingRoundsDisplay();
            this.updateRoundIndicator();
            this.countDownTimer.startTheCountDownTimer();
        } else {
            this.timerDisplayCallback.updateRemainingRoundsDisplay("0");
            this.timerDisplayCallback.updateTimerDisplay("Finished!");
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


    private Boolean queueHasNext()
    {
        return !this.timerQueue.isEmpty() ? true : false;
    }

    @Override
    public void pauseAndResumeTimer()
    {
        if (this.countDownTimer.isRunning())
            this.countDownTimer.stopTheCountDownTimer();
        else
            this.countDownTimer.startTheCountDownTimer();

    }
}
