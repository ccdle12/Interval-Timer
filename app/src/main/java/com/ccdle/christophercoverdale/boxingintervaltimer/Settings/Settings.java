package com.ccdle.christophercoverdale.boxingintervaltimer.Settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.ccdle.christophercoverdale.boxingintervaltimer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by christophercoverdale on 26/08/2017.
 */

public class Settings extends android.app.Fragment implements SettingsInterface.Callback {
    private SettingsInterface settingsInterface;

    @BindView(R.id.settings_count_down_toggle) CheckBox settingsCountDownToggle;
    @BindView(R.id.settings_end_of_round_toggle) CheckBox settingsEndOfRoundToggle;
    @BindView(R.id.settings_vibration_toggle) CheckBox settingsVibrationToggle;
    @BindView(R.id.settings_intro_round_toggle) CheckBox settingsIntroRoundToggle;

    @BindView(R.id.back_to_dashboard_fab) FloatingActionButton backToDashboard;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_layout, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }



    /* Interface from Presenter */
    @Override
    public void setInterface(SettingsInterface settingsInterface)
    {
        this.settingsInterface = settingsInterface;
    }


    /* Check box listeners */
    @OnCheckedChanged(R.id.settings_count_down_toggle) void userDisablesCountDownSound()
    {
        this.settingsInterface.userDisablesCountDownSound(this.settingsCountDownToggle.isChecked());
    }

    @OnCheckedChanged(R.id.settings_end_of_round_toggle) void userDisabesEndOfRoundSound()
    {
        this.settingsInterface.userDisablesEndOfRoundSounds(this.settingsEndOfRoundToggle.isChecked());
    }

    @OnCheckedChanged(R.id.settings_vibration_toggle) void userDisablesVibration()
    {
        this.settingsInterface.userDisablesVibration(this.settingsVibrationToggle.isChecked());
    }

    @OnCheckedChanged(R.id.settings_intro_round_toggle) void userDisablesIntroRound()
    {
        this.settingsInterface.userDisablesIntroRound(this.settingsIntroRoundToggle.isChecked());
    }

    @OnClick(R.id.back_to_dashboard_fab) void backToDashboard()
    {
        this.settingsInterface.backToDashboard();
    }


}
