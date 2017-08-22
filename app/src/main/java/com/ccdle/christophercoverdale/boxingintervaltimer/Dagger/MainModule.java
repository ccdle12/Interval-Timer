package com.ccdle.christophercoverdale.boxingintervaltimer.Dagger;

import android.app.Application;

import com.ccdle.christophercoverdale.boxingintervaltimer.CustomRounds.CustomRounds;
import com.ccdle.christophercoverdale.boxingintervaltimer.CustomRounds.CustomRoundsPresenter;
import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.Dashboard;
import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.DashboardPresenter;
import com.ccdle.christophercoverdale.boxingintervaltimer.FinishedScreen.FinishedScreen;
import com.ccdle.christophercoverdale.boxingintervaltimer.FinishedScreen.FinishedScreenPresenter;
import com.ccdle.christophercoverdale.boxingintervaltimer.TimerDisplay.TimerDisplay;
import com.ccdle.christophercoverdale.boxingintervaltimer.TimerDisplay.TimerDisplayPresenter;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.RoundsModel;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.SoundFX;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by USER on 7/10/2017.
 */
@Module
public class MainModule {
    DaggerApplication app;

    public MainModule(DaggerApplication application) {
        this.app = application;
    }

    @Provides
    @Singleton
    protected Application provideApplication() {
        return this.app;
    }


    @Provides
    @Singleton
    public DashboardPresenter providesMainMenuPresenter() {
        return new DashboardPresenter(new TimerDisplayPresenter(new LinkedBlockingDeque<>(), new TimerDisplay(), new FinishedScreenPresenter(new FinishedScreen(), new Dashboard())), new CustomRoundsPresenter(new CustomRounds(), new Dashboard(), new TimerDisplayPresenter(new LinkedBlockingDeque<>(), new TimerDisplay(), new FinishedScreenPresenter(new FinishedScreen(), new Dashboard()))));
    }

    @Provides
    @Singleton
    public TimerDisplayPresenter providesTimerDisplayPresenter() {
        return new TimerDisplayPresenter(new LinkedBlockingDeque<>(), new TimerDisplay(), new FinishedScreenPresenter(new FinishedScreen(), new Dashboard()));
    }

    @Provides
    @Singleton
    public LinkedBlockingDeque<String> providesTimerQueue() {
        return new LinkedBlockingDeque<>();
    }

    @Provides
    @Singleton
    public RoundsModel provideRoundsModel() { return new RoundsModel(); }

    @Provides
    @Singleton
    public TimerDisplay providesTimerDisplay() { return new TimerDisplay(); }

    @Provides
    @Singleton
    public SoundFX providesSoundFX()
    {
        return new SoundFX();
    }

    @Provides
    @Singleton
    public Dashboard providesDashboard() { return new Dashboard(); }

    @Provides
    @Singleton
    public CustomRoundsPresenter providesCustomRoundsPresenter() { return new CustomRoundsPresenter(new CustomRounds(), new Dashboard(), new TimerDisplayPresenter(new LinkedBlockingDeque<>(), new TimerDisplay(), new FinishedScreenPresenter(new FinishedScreen(), new Dashboard()))); }

    @Provides
    @Singleton
    public CustomRounds providesCustomRounds() { return new CustomRounds(); }
}
