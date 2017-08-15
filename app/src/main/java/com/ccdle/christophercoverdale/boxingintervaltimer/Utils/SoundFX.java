package com.ccdle.christophercoverdale.boxingintervaltimer.Utils;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;

import com.ccdle.christophercoverdale.boxingintervaltimer.R;

/**
 * Created by christophercoverdale on 12/08/2017.
 */

public class SoundFX
{
    private MediaPlayer mediaPlayer;

    public SoundFX()
    {
        this.mediaPlayer = new MediaPlayer();
    }

    public void createCountDownSound(Activity activity)
    {

        this.mediaPlayer = this.mediaPlayer.create(activity, R.raw.beep2);
    }

    public void createBoxingBellSound(Activity activity)
    {
        this.mediaPlayer = this.mediaPlayer.create(activity, R.raw.boxing_bell);
    }

    public void startSoundFX()
    {
        this.mediaPlayer.start();
    }

    public void releaseMediaPlayer()
    {
        this.mediaPlayer.release();
    }



}
