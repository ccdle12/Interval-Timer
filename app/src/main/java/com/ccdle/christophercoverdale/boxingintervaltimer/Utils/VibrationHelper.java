package com.ccdle.christophercoverdale.boxingintervaltimer.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;

/**
 * Created by christophercoverdale on 26/08/2017.
 */

public class VibrationHelper
{
    Vibrator vibrator;

    public VibrationHelper(Activity activity)
    {
        this.vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void initShortVibrate()
    {
        this.vibrator.vibrate(500);
    }
}
