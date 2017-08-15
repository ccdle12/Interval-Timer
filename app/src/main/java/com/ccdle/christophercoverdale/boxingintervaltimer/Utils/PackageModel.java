package com.ccdle.christophercoverdale.boxingintervaltimer.Utils;

import android.app.Activity;
import android.content.Context;

/**
 * Created by christophercoverdale on 08/08/2017.
 */

public class PackageModel
{
    /* Used to share context */
    private Activity activity;

    public PackageModel(Activity activity)
    {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

}
