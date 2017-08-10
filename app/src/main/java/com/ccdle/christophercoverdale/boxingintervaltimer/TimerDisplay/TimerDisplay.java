package com.ccdle.christophercoverdale.boxingintervaltimer.TimerDisplay;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.TimerDisplayPresenterInterface;
import com.ccdle.christophercoverdale.boxingintervaltimer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by christophercoverdale on 08/08/2017.
 */

public class TimerDisplay extends Fragment implements TimerDisplayInterface.TimerDisplayCallback
{
    private TimerDisplayInterface timerDisplayInterface;

    @BindView(R.id.remaining_rounds) TextView remainingRoundsTextView;
    @BindView(R.id.total_rounds) TextView totalRoundsTextView;
    @BindView(R.id.round_indicator) TextView roundIndicator;
    @BindView(R.id.count_down_timer) TextView countDownTimerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.timer_display, container, false);
        ButterKnife.bind(this, rootView);

        this.timerDisplayInterface.viewCreated();

        return rootView;
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }


    /* Callback from Timer Display Presenter */
    @Override
    public void setPresenterInterface(TimerDisplayInterface timerDisplayPresenter)
    {
        this.timerDisplayInterface = timerDisplayPresenter;
    }

    @Override
    public void updateRemainingRoundsDisplay(String remainingRounds)
    {
        this.remainingRoundsTextView.post(() -> this.remainingRoundsTextView.setText(remainingRounds));
    }

    @Override
    public void updateTotalRoundsDisplay(String totalRounds)
    {
        this.totalRoundsTextView.post(() -> this.totalRoundsTextView.setText(totalRounds));
    }

    @Override
    public void updateTimerDisplay(String time)
    {
        this.countDownTimerView.post(() -> countDownTimerView.setText(time));
    }

    @Override
    public void updateRoundIndicator(String roundIndicator)
    {
        this.roundIndicator.post(() -> this.roundIndicator.setText(roundIndicator));
    }

}
