package com.ccdle.christophercoverdale.boxingintervaltimer.FinishedScreen;

import android.app.Activity;

import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.Dashboard;
import com.ccdle.christophercoverdale.boxingintervaltimer.R;
import com.ccdle.christophercoverdale.boxingintervaltimer.TimerDisplay.FinishedScreenPresenterInterface;
import com.ccdle.christophercoverdale.boxingintervaltimer.TimerDisplay.TimerDisplay;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.PackageModel;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.RoundType;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.SoundFX;

import java.util.LinkedList;

import javax.inject.Inject;

/**
 * Created by christophercoverdale on 14/08/2017.
 */

public class FinishedScreenPresenter implements FinishedScreenPresenterInterface, FinishedScreenInterface
{
    private PackageModel packageModel;

    private FinishedScreen finishedScreen;
    private FinishedScreenInterface.FinishedScreenCallback finishedScreenInterface;
    private Dashboard dashboard;


    /* Dagger Injection */
    @Inject
    public FinishedScreenPresenter(FinishedScreen finishedScreen, Dashboard dashboard)
    {
        this.finishedScreen = finishedScreen;
        this.finishedScreenInterface = this.finishedScreen;
        this.dashboard = dashboard;

        this.finishedScreen.setInterface(this);
    }

    @Override
    public void viewCreated()
    {

    }

    @Override
    public void launchDashboard()
    {
        this.replaceFragmentInActivityWithDashboard();
    }

    @Override
    public void sendPackageModel(PackageModel packageModel)
    {
        this.packageModel = packageModel;
    }

    @Override
    public void launchFinishedScreen()
    {
        this.replaceFragmentWithFinishedScreen();
    }

    private void replaceFragmentWithFinishedScreen()
    {
        Activity activityRef = this.packageModel.getActivity();

        activityRef.getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_holder, this.finishedScreen)
                .commit();
    }


    private void replaceFragmentInActivityWithDashboard()
    {
        Activity activityRef = this.packageModel.getActivity();

        activityRef.getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_holder, this.dashboard)
                .commit();
    }


}
