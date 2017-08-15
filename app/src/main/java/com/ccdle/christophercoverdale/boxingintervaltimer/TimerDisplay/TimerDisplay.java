package com.ccdle.christophercoverdale.boxingintervaltimer.TimerDisplay;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.TimerDisplayPresenterInterface;
import com.ccdle.christophercoverdale.boxingintervaltimer.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by christophercoverdale on 08/08/2017.
 */

public class TimerDisplay extends Fragment implements TimerDisplayInterface.TimerDisplayCallback
{
    private TimerDisplayInterface timerDisplayInterface;
    View rootView;

    @BindView(R.id.remaining_rounds) TextView remainingRoundsTextView;
    @BindView(R.id.total_rounds) TextView totalRoundsTextView;
    @BindView(R.id.round_display) TextView roundsDisplayTextView;

    @BindView(R.id.back_slash_rounds) TextView backslashRounds;
    @BindView(R.id.round_indicator) TextView roundIndicator;
    @BindView(R.id.count_down_timer) TextView countDownTimerView;

    @BindView(R.id.pause_and_go) Button pauseAndGoBtn;
    @BindView(R.id.Reset) Button resetBtn;

    @BindView(R.id.timer_display) RelativeLayout timerDisplay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        this.rootView = inflater.inflate(R.layout.timer_display, container, false);
        ButterKnife.bind(this, rootView);

        this.timerDisplayInterface.viewCreated();

        return this.rootView;
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


    @OnClick(R.id.pause_and_go) void pauseAndStartTimer()
    {
        this.timerDisplayInterface.pauseAndResumeTimer();
    }

    @OnClick(R.id.Reset) void reset()
    {
        this.timerDisplayInterface.resetTimer();
    }

    @Override
    public void showSnackbarPressPause()
    {
        Snackbar.make(this.rootView, "Please pause the timer first", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void updateBackgroundColor(int color)
    {
        this.timerDisplay.post(() -> this.timerDisplay.setBackgroundColor(color));
    }

    @Override
    public void updatePauseButtonStop()
    {
        this.pauseAndGoBtn.post(() -> this.pauseAndGoBtn.setText("Pause"));
    }

    @Override
    public void updatePauseButtonGo()
    {
        this.pauseAndGoBtn.post(() -> this.pauseAndGoBtn.setText("Go"));
    }

    @Override
    public void hideUIElements()
    {
        this.remainingRoundsTextView.setVisibility(View.INVISIBLE);
        this.totalRoundsTextView.setVisibility(View.INVISIBLE);
        this.backslashRounds.setVisibility(View.INVISIBLE);
        this.pauseAndGoBtn.setVisibility(View.INVISIBLE);
        this.resetBtn.setVisibility(View.INVISIBLE);
        this.roundsDisplayTextView.setVisibility(View.INVISIBLE);

    }

    @Override
    public void showUIElements()
    {
        this.remainingRoundsTextView.setVisibility(View.VISIBLE);
        this.totalRoundsTextView.setVisibility(View.VISIBLE);
        this.backslashRounds.setVisibility(View.VISIBLE);
        this.pauseAndGoBtn.setVisibility(View.VISIBLE);
        this.resetBtn.setVisibility(View.VISIBLE);
        this.roundsDisplayTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRoundIndicatorAsIntroIndicator(String getReady)
    {
        this.roundIndicator.post(() -> this.roundIndicator.setText(getReady));
    }

}
