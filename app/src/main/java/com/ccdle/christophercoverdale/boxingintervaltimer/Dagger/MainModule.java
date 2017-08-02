package com.ccdle.christophercoverdale.boxingintervaltimer.Dagger;

import android.app.Application;

import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.DashboardPresenter;

import java.util.LinkedList;

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
        return new DashboardPresenter(new LinkedList<Long>());
    }

    @Provides
    @Singleton
    public LinkedList<String> providesTimerQueue() {
        return new LinkedList<>();
    }
}
