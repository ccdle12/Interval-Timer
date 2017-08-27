package com.ccdle.christophercoverdale.boxingintervaltimer.Settings;

import android.app.Activity;

import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.Dashboard;
import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.SettingsPresenterInterface;
import com.ccdle.christophercoverdale.boxingintervaltimer.R;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.PackageModel;

/**
 * Created by christophercoverdale on 26/08/2017.
 */

public class SettingsPresenter implements SettingsPresenterInterface, SettingsInterface
{

    private PackageModel packageModel;
    private Settings settings;
    private Dashboard dashboard;

    private SettingsInterface.Callback settingsCallback;

    public SettingsPresenter(Settings settings, Dashboard dashboard)
    {
        this.settings = settings;
        this.settingsCallback = settings;
        this.dashboard = dashboard;

        this.settingsCallback.setInterface(this);
    }




    /* Dashboard interface */
    @Override
    public void sendPackageModel(PackageModel packageModel)
    {
        this.packageModel = packageModel;
    }

    @Override
    public void launchSettings()
    {
        this.replaceFragmentInActivityWithSettings();
    }

    private void replaceFragmentInActivityWithSettings()
    {
        Activity activityRef = this.packageModel.getActivity();

        activityRef.getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_holder, this.settings)
                .commit();
    }




    /* Settings interface */
    @Override
    public void userDisablesCountDownSound(boolean isChecked)
    {
        if (isChecked)
            SettingsSingleton.getInstance().setCountDownSound(true);
        else
            SettingsSingleton.getInstance().setCountDownSound(false);
    }

    @Override
    public void userDisablesEndOfRoundSounds(boolean isChecked)
    {
        if (isChecked)
            SettingsSingleton.getInstance().setEndOfRoundSound(true);
        else
            SettingsSingleton.getInstance().setEndOfRoundSound(false);
    }

    @Override
    public void userDisablesVibration(boolean isChecked)
    {
        if (isChecked)
            SettingsSingleton.getInstance().setVibrate(true);
        else
            SettingsSingleton.getInstance().setVibrate(false);
    }

    @Override
    public void userDisablesIntroRound(boolean isChecked)
    {
        if (isChecked)
            SettingsSingleton.getInstance().setIntroRound(true);
        else
            SettingsSingleton.getInstance().setIntroRound(false);
    }

    @Override
    public void backToDashboard()
    {
        this.replaceFragmentInActivityWithDashboard();
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
