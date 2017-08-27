package com.ccdle.christophercoverdale.boxingintervaltimer.Utils;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;

/**
 * Created by christophercoverdale on 26/08/2017.
 */

public class AudioManagerHelper
{
    AudioManager audioManager;

    public AudioManagerHelper(Activity activity)
    {
        this.audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
    }

    public boolean requesetAudioFoucs()
    {
        int audioFocus = this.audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE);

        return audioFocus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

    public void releaseAudioFocus()
    {
        this.audioManager.abandonAudioFocus(null);

    }
}
