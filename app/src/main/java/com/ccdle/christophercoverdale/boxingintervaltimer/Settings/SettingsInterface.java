package com.ccdle.christophercoverdale.boxingintervaltimer.Settings;

/**
 * Created by christophercoverdale on 26/08/2017.
 */

public interface SettingsInterface
{
    void userDisablesCountDownSound(boolean isChecked);
    void userDisablesEndOfRoundSounds(boolean isChecked);
    void userDisablesVibration(boolean isChecked);
    void userDisablesIntroRound(boolean isChecked);
    void backToDashboard();

    interface Callback
    {
        void setInterface(SettingsInterface settingsInterface);
    }
}
