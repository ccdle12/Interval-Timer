package com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ccdle.christophercoverdale.boxingintervaltimer.Dagger.DaggerApplication;
import com.ccdle.christophercoverdale.boxingintervaltimer.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by USER on 5/14/2017.
 */

public class Dashboard extends Fragment implements DashboardInterface.DashboardCallback {

    private final String TAG = "Dashboard";
    private DashboardInterface dashBoardPresenterInterface;

//    @BindView(R.id.count_down_timer) TextView countDownTimerView;
    @BindView(R.id.work_round_minutes) EditText workIntervalMinutes;
    @BindView(R.id.work_round_seconds) EditText workIntervalSeconds;
    @BindView(R.id.rest_round_minutes) EditText restIntervalMinutes;
    @BindView(R.id.rest_round_seconds) EditText restIntervalSeconds;

    /*Dagger Injection Methods*/
    @Inject
    public void injectDashboardPresenter(DashboardPresenter dashboardPresenter)
    {
        this.dashBoardPresenterInterface = dashboardPresenter;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dashboard, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }



    @Override
    public void onStart()
    {
        super.onStart();
        this.injectObjects();

        this.setDashboardPresenterCallback();
    }

    private void injectObjects()
    {
        DaggerApplication.component().inject(this);
    }

    private void setDashboardPresenterCallback()
    {
        this.dashBoardPresenterInterface.setDashboardCallback(this);
    }




    @OnClick(R.id.start_timer_button) void startTimer()
    {
        String workIntervalMins = this.workIntervalMinutes.getText().toString();
        String workIntervalSecs = this.workIntervalSeconds.getText().toString();
        String restIntervalMins = this.restIntervalMinutes.getText().toString();
        String restIntervalSecs = this.restIntervalSeconds.getText().toString();

        this.dashBoardPresenterInterface.addToQueue(workIntervalMins, workIntervalSecs, restIntervalMins, restIntervalSecs, "2");
        this.dashBoardPresenterInterface.initializeTimer();
    }

    @OnClick(R.id.work_round_minutes) void userClicksOnWorkMinutes()
    {
        this.workIntervalMinutes.setCursorVisible(true);
        this.workIntervalMinutes.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    @OnClick(R.id.work_round_seconds) void userClicksOnWorkSeconds()
    {
        this.workIntervalSeconds.setCursorVisible(true);
    }

    @OnClick(R.id.rest_round_minutes) void userClicksOnRestMinutes()
    {
        this.restIntervalMinutes.setCursorVisible(true);
    }

    @OnClick(R.id.rest_round_seconds) void userClicksOnRestSeconds()
    {
        this.restIntervalSeconds.setCursorVisible(true);
    }



    /* Presenter Callback*/
    @Override
    public void updateTimerDisplay(String time)
    {
//        this.countDownTimerView.post(() -> countDownTimerView.setText(time));
    }
}
