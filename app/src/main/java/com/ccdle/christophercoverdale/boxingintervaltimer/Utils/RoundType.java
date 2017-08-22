package com.ccdle.christophercoverdale.boxingintervaltimer.Utils;

/**
 * Created by christophercoverdale on 08/08/2017.
 */

public class RoundType
{
    private long roundTime;
    private String roundType;

    public RoundType(long roundTime, String roundType)
    {
        this.roundTime = roundTime;
        this.roundType = roundType;
    }

    public RoundType() {}

    public String getRoundType()
    {
        return roundType;
    }

    public long getRoundTime()
    {
        return roundTime;
    }

    public void setRoundTime(long roundTime)
    {
        this.roundTime = roundTime;
    }
}
