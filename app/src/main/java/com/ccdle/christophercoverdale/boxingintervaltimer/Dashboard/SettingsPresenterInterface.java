package com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard;

import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.PackageModel;

/**
 * Created by christophercoverdale on 27/08/2017.
 */

public interface SettingsPresenterInterface
{
    void sendPackageModel(PackageModel packageModel);
    void launchSettings();

    interface Callback
    {

    }
}
