package com.ccdle.christophercoverdale.boxingintervaltimer.FinishedScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ccdle.christophercoverdale.boxingintervaltimer.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by christophercoverdale on 14/08/2017.
 */

public class FinishedScreen extends android.app.Fragment implements FinishedScreenInterface.FinishedScreenCallback
{
    private FinishedScreenInterface finishedScreenInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.finished_screen, container, false);
        ButterKnife.bind(this, rootView);

        this.finishedScreenInterface.viewCreated();


        return rootView;
    }

    @Override
    public void setInterface(FinishedScreenInterface finishedScreenInterface)
    {
        this.finishedScreenInterface = finishedScreenInterface;
    }

    @OnClick(R.id.back_to_dashboard) void backToDashboard()
    {
        this.finishedScreenInterface.launchDashboard();
    }
}
