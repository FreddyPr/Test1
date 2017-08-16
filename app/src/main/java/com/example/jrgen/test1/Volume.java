package com.example.jrgen.test1;

import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class Volume extends BaseActivity implements View.OnClickListener {

    int currVolume = values.getVolume();
    private AudioManager audioManager = null;
    static MediaPlayer mp = null;

    @Override
    public void onRestart() {
        super.onRestart();
        init();
    }

    @Override
    public void onPause() {
        releaseMediaPlayer();
        super.onStop();
    }

    public void onConfigurationChanged(Configuration configuration) {
        // Device turned from landscape into normal, or the other way around.
        initToolbar(getResources().getString(R.string.volumeTitle));
        initButton(getResources().getString(R.string.pContinue), 1);
        initButton(getResources().getString(R.string.volumeDown), 2);
        initButton(getResources().getString(R.string.volumeUp), 3);
        super.onConfigurationChanged(configuration);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume);

        init();

        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currVolume, 0);
        displayCurrentVolume();
    }

    private void init() {
        initToolbar(getResources().getString(R.string.volumeTitle));
        initButton(getResources().getString(R.string.pContinue), 1);
        initButton(getResources().getString(R.string.volumeDown), 2);
        initButton(getResources().getString(R.string.volumeUp), 3);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mp = MediaPlayer.create(Volume.this, R.raw.happyplace);
        currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        mp.start();
        displayCurrentVolume();
    }

    private void releaseMediaPlayer() {
        // Release mediaPlayer
        if(mp != null) {
            mp.release();
        }
    }

    @Override
    public boolean onKeyUp(final int keyCode, final KeyEvent event) {
        // Override method from BaseActivity, so that VolumeButtons do not throw any warning.
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                    displayCurrentVolume();
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                    displayCurrentVolume();
                return true;
            default:
                return super.onKeyUp(keyCode,event);
        }
    }

    @Override
    public void onClick(View v) {
        currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        switch (v.getId()) {
            case R.id.button1:                  // Next Activity
                values.setVolume(currVolume);
                releaseMediaPlayer();
                break;

            case R.id.button2:                  // Lower Volume
                if(currVolume > 0) {
                    currVolume--;
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                }
                break;

            case R.id.button3:                  // Raise Volume
                if(currVolume < 15) {
                    currVolume++;
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                }
                break;
        }
        displayCurrentVolume();
    }

    private void displayCurrentVolume() {
        // Display the current volume in the according TextView
        int resID = getResources().getIdentifier("textView1", "id", getPackageName());
        TextView textView = (TextView) findViewById(resID);
        textView.setTextSize(40);

        currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if(currVolume == 0) {
            textView.setText(getResources().getString(R.string.minimum));
        } else {
            if(currVolume == 15) {
                textView.setText(getResources().getString(R.string.maximum));
            } else {
                textView.setText(String.valueOf(currVolume));
            }
        }
    }
}
