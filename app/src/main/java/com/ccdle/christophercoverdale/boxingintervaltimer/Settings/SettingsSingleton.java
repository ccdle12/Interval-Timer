package com.ccdle.christophercoverdale.boxingintervaltimer.Settings;

import android.media.audiofx.BassBoost;

/**
 * Created by christophercoverdale on 26/08/2017.
 */

public class SettingsSingleton
{
    private static SettingsSingleton singleton = null;
    private boolean vibrate = true;
    private boolean countDownSound = true;
    private boolean endOfRoundSound = true;
    private boolean introRound = true;

    private SettingsSingleton() {}

    public static SettingsSingleton getInstance()
    {
        if (singleton == null)
            return singleton = new SettingsSingleton();
        else
            return singleton;
    }

    public boolean isVibrate()
    {
        return vibrate;
    }

    public void setVibrate(boolean vibrate)
    {
        this.vibrate = vibrate;
    }

    public boolean isCountDownSound()
    {
        return countDownSound;
    }

    public void setCountDownSound(boolean countDownSound)
    {
        this.countDownSound = countDownSound;
    }

    public boolean isEndOfRoundSound()
    {
        return endOfRoundSound;
    }

    public void setEndOfRoundSound(boolean endOfRoundSound)
    {
        this.endOfRoundSound = endOfRoundSound;
    }

    public boolean isIntroRound()
    {
        return introRound;
    }

    public void setIntroRound(boolean introRound)
    {
        this.introRound = introRound;
    }
}
