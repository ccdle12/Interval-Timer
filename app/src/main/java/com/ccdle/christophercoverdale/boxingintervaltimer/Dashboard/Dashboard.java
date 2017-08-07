package com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

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

    @BindView(R.id.number_of_rounds) EditText editTextRounds;

    @BindView(R.id.increment_rest_time) ImageView incrementRoundTimeBtn;
    @BindView(R.id.work_round_minutes) EditText workIntervalMinutes;
    @BindView(R.id.work_round_seconds) EditText workIntervalSeconds;

    @BindView(R.id.rest_round_minutes) EditText restIntervalMinutes;
    @BindView(R.id.rest_round_seconds) EditText restIntervalSeconds;


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
        this.initializeUIListeners();
    }

    /* Dagger Injection Methods */
    @Inject
    public void injectDashboardPresenter(DashboardPresenter dashboardPresenter)
    {
        this.dashBoardPresenterInterface = dashboardPresenter;
    }

    private void injectObjects()
    {
        DaggerApplication.component().inject(this);
    }

    private void setDashboardPresenterCallback()
    {
        this.dashBoardPresenterInterface.setDashboardCallback(this);
    }

    private void initializeUIListeners()
    {
        this.roundsTextChanged();

        this.workMinutesFocusChanged();
        this.workSecondsFocusChanged();

        this.restMinutesFocusChanged();
        this.restSecondsFocusChanged();
    }


    /* UI Click Listeners */
    @OnClick(R.id.increment_number_of_rounds) void incrementNumberOfRounds()
    {
        String rounds = this.editTextRounds.getText().toString();
        this.dashBoardPresenterInterface.incrementNumberOfRounds(rounds);
    }

    @OnClick(R.id.decrement_number_of_rounds) void decrementNumberOfRounds()
    {
        String rounds = this.editTextRounds.getText().toString();
        this.dashBoardPresenterInterface.decrementNumberOfRounds(rounds);
    }


    @OnClick(R.id.increment_round_time) void incrementWorkRoundTime()
    {
        String workMinutes = this.workIntervalMinutes.getText().toString();
        String workSeconds = this.workIntervalSeconds.getText().toString();
        this.dashBoardPresenterInterface.incrementWorkRoundTime(workMinutes, workSeconds);
    }

    @OnClick(R.id.decrement_round_time) void decrementWorkRoundTime()
    {
        String workMinutes = this.workIntervalMinutes.getText().toString();
        String workSeconds = this.workIntervalSeconds.getText().toString();

        this.dashBoardPresenterInterface.decrementWorkRoundTime(workMinutes, workSeconds);
    }


    @OnClick(R.id.increment_rest_time) void incrementRestTime()
    {
        String restMinutes = this.restIntervalMinutes.getText().toString();
        String restSeconds = this.restIntervalSeconds.getText().toString();

        this.dashBoardPresenterInterface.incrementRestRoundTime(restMinutes, restSeconds);
    }

    @OnClick(R.id.decrement_rest_time) void decrementRestTime()
    {
        String restMinutes = this.restIntervalMinutes.getText().toString();
        String restSeconds = this.restIntervalSeconds.getText().toString();

        this.dashBoardPresenterInterface.decrementRestRoundTime(restMinutes, restSeconds);
    }


    @OnClick(R.id.number_of_rounds) void userClicksOnRounds()
    {
        this.editTextRounds.setCursorVisible(true);
    }

    @OnClick(R.id.work_round_minutes) void userClicksOnWorkMinutes()
    {
        this.workIntervalMinutes.setCursorVisible(true);
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


    /* Unfocus UI listeners */
    private void roundsTextChanged()
    {
        this.editTextRounds.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused)
            {
             if (!isFocused)
             {
                 String rounds = editTextRounds.getText().toString();
                 dashBoardPresenterInterface.checkRoundLimits(rounds);
             }
            }
        });
    }

    private void workMinutesFocusChanged()
    {
        this.workIntervalMinutes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused)
            {
                if (!isFocused)
                {
                    String workMinutes = workIntervalMinutes.getText().toString();
                    dashBoardPresenterInterface.checkMinutesLimit(workMinutes, 0);
                }
            }
        });
    }

    private void workSecondsFocusChanged()
    {
        this.workIntervalSeconds.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused)
            {
                if (!isFocused)
                {
                    String workMinutes = workIntervalMinutes.getText().toString();
                    String workSeconds = workIntervalSeconds.getText().toString();
                    dashBoardPresenterInterface.checkSecondsLimit(workMinutes, workSeconds, 0);
                }
            }
        });
    }

    private void restMinutesFocusChanged()
    {
        this.restIntervalMinutes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (!isFocused)
                {
                    String restMinutes = restIntervalMinutes.getText().toString();
                    dashBoardPresenterInterface.checkMinutesLimit(restMinutes, 1);
                }
            }
        });
    }

    private void restSecondsFocusChanged()
    {
        this.restIntervalSeconds.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused)
            {
                if (!isFocused)
                {
                    String restMinutes = restIntervalMinutes.getText().toString();
                    String restSeconds = restIntervalSeconds.getText().toString();
                    dashBoardPresenterInterface.checkSecondsLimit(restMinutes, restSeconds, 1);
                }
            }
        });
    }



    /* Presenter Callback */
    @Override
    public void updateRoundsDisplay(String rounds)
    {
        this.editTextRounds.post(() -> this.editTextRounds.setText(rounds));
    }

    @Override
    public void updateWorkRoundDisplayFromIncrement(String formattedMinutes, String formattedSeconds)
    {
        this.workIntervalMinutes.post(() -> this.workIntervalMinutes.setText(formattedMinutes));
        this.workIntervalSeconds.post(() -> this.workIntervalSeconds.setText(formattedSeconds));
    }

    @Override
    public void updateRestRoundDisplayFromIncrement(String formattedMinutes, String formattedSeconds)
    {
        this.restIntervalMinutes.post(() -> this.restIntervalMinutes.setText(formattedMinutes));
        this.restIntervalSeconds.post(() -> this.restIntervalSeconds.setText(formattedSeconds));
    }

    @Override
    public void updateWorkMinutesDisplay(String formattedMinutes)
    {
        this.workIntervalMinutes.post(() -> this.workIntervalMinutes.setText(formattedMinutes));
    }

    @Override
    public void updateWorkSecondsDisplay(String formattedSeconds)
    {
        this.workIntervalSeconds.post(() -> this.workIntervalSeconds.setText(formattedSeconds));
    }


    @Override
    public void updateRestMinutesDisplay(String formattedMinutes)
    {
        this.restIntervalMinutes.post(() -> this.restIntervalMinutes.setText(formattedMinutes));
    }

    @Override
    public void updateRestSecondsDisplay(String formattedSeconds)
    {
        this.restIntervalSeconds.post(() -> this.restIntervalSeconds.setText(formattedSeconds));
    }

    //------------------------- Will move below to its own Presenter -----------------------------//
    //@BindView(R.id.count_down_timer) TextView countDownTimerView;

    @Override
    public void updateTimerDisplay(String time)
    {
        //this.countDownTimerView.post(() -> countDownTimerView.setText(time));
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
    //------------------------- Will move above to its own Presenter -----------------------------//
}
