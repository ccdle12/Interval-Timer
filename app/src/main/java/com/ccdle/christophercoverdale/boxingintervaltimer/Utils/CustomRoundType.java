package com.ccdle.christophercoverdale.boxingintervaltimer.Utils;

import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by christophercoverdale on 16/08/2017.
 */

public class CustomRoundType extends RoundType
{
    private String customMinutes;
    private String customSeconds;
    private String roundType;
    private long roundTime;
    private int position;

    public CustomRoundType(String customMinutes, String customSeconds, String roundType, int position)
    {
        this.customMinutes = customMinutes;
        this.customSeconds = customSeconds;
        this.roundType = roundType;
        this.position = position;
    }

    public void setCustomMinutes(String customMinutes)
    {
        this.customMinutes = customMinutes;
    }

    public String getCustomMinutes()
    {
        return customMinutes;
    }

    public void setCustomSeconds(String customSeconds)
    {
        this.customSeconds = customSeconds;
    }

    public String getCustomSeconds()
    {
        return customSeconds;
    }

    public void setRoundType(String roundType)
    {
        this.roundType = roundType;
    }

    public String getRoundType()
    {
        return roundType;
    }

    public void setTime()
    {
        long time = TimeValuesHelper.calculateTotalTimeInMillis(customMinutes, customSeconds);

        this.roundTime = time;
    }

    public long getRoundTime()
    {
        return this.roundTime;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }
}
