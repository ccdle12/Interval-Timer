package com.ccdle.christophercoverdale.boxingintervaltimer.Utils;

/**
 * Created by christophercoverdale on 06/08/2017.
 */

public class TimeValuesHelper {

    private TimeValuesHelper() {}

    /* Formatting for incrementing number of rounds, work rounds and rest rounds*/
    public static int incrementValue(String dashboardValue)
    {
        int incrementedValue = convertStringToInt(dashboardValue);
        ++incrementedValue;

        return incrementedValue;
    }

    public static int decrementValue(String dashboardValue)
    {
        int decrementedValue = convertStringToInt(dashboardValue);
        --decrementedValue;

        return decrementedValue;
    }

    public static int convertStringToInt(String stringInput)
    {
        return Integer.parseInt(stringInput);
    }

    public static String convertIntToString(int intInput)
    {
        return String.valueOf(intInput);
    }



    /* Formatting and calculating time for display and the count down timer */
    public static long calculateTotalTimeInMillis(String minutes, String seconds)
    {
        long mins = Long.parseLong(minutes);
        long secs = Long.parseLong(seconds);

        long minsInMillis = (mins * 60) * 1000;
        long secsInMillis = secs * 1000;

        long totalTimeInMillis = minsInMillis + secsInMillis;

        return totalTimeInMillis;
    }


    public static int formatRemainingMinutes(long remainingTime)
    {
        remainingTime = remainingTime / 1000;
        int remainingMinutes = (int) remainingTime / 60;

        return remainingMinutes;
    }

    public static int formatRemainingSeconds(long remainingTime)
    {
        remainingTime = remainingTime / 1000;
        int remainingMinutes = (int) remainingTime / 60;
        int remainingSeconds = (int) remainingTime - (60 * remainingMinutes);

        return remainingSeconds;
    }

    public static String formatMinutesToString(int remainingMinutes)
    {
        return (remainingMinutes < 10) ? String.format("0%s", remainingMinutes) : String.valueOf(remainingMinutes);
    }

    public static String formatSecondsToString(int remainingSeconds)
    {
        String secondsFormatted;

        if (remainingSeconds < 10) {
            secondsFormatted = String.format("0%s", remainingSeconds);
        } else if (remainingSeconds > 59) {
            secondsFormatted = "00";
        } else {
            secondsFormatted = String.valueOf(remainingSeconds);
        }

        return secondsFormatted;
    }
}
