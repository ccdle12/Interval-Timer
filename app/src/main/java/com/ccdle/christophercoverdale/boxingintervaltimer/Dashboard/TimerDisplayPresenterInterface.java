package com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard;

import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.PackageModel;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.RoundsModel;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by christophercoverdale on 08/08/2017.
 */

public interface TimerDisplayPresenterInterface
{

    void launchTimerDisplay();
    void sendPackageModel(PackageModel packageModel);
    void sendRoundsModel(RoundsModel roundsModel);
    void sendQueue(LinkedBlockingDeque queue);
}
