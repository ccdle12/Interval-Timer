package com.ccdle.christophercoverdale.boxingintervaltimer.Dagger;

import android.app.Application;

/**
 * Created by USER on 7/10/2017.
 */

public class DaggerApplication extends Application {
    private static DaggerGraphComponent graph;
    private static DaggerApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        buildComponentGraph();
    }

    public static DaggerGraphComponent component() { return graph; }

    public static void buildComponentGraph() {
        graph = DaggerGraphComponent.Initializer.init(instance);
    }
}
