package com.ccdle.christophercoverdale.boxingintervaltimer.Dagger;

import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.Dashboard;
import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.DashboardPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by USER on 7/10/2017.
 */
@Component (modules= {MainModule.class})
@Singleton
public interface DaggerGraphComponent {
    void inject(Dashboard dashboard);
    void inject(DashboardPresenter dashboardPresenter);

    final class Initializer {
        private Initializer() {
        }

        public static DaggerGraphComponent init(DaggerApplication app) {
            return DaggerDaggerGraphComponent.builder()
                    .mainModule(new MainModule(app))
                    .build();
        }

    }

}
