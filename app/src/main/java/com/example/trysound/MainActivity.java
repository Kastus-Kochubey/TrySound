package com.example.trysound;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Button butt_stop;
    Button butt_start;
    SeekBar seekBar;
    SeekBar progressSeekBar;
    AudioManager audioManager;
    MediaPlayer mediaPlayer;
    Timer timer = new Timer();
//    TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        butt_start = findViewById(R.id.butt_start);
        butt_stop = findViewById(R.id.butt_stop);
        seekBar = findViewById(R.id.seekBar);
        progressSeekBar = findViewById(R.id.seekBarProgress);


        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound);


        int maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int soundDuration = mediaPlayer.getDuration();
        Log.e("soundDuration", soundDuration + "");
        progressSeekBar.setMax(soundDuration);
        seekBar.setMax(maxVol);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) maxVol / 2, 0);
        seekBar.setProgress((int) maxVol / 2);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.e("run", mediaPlayer.getCurrentPosition() + "");
//                progressSeekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        };

        butt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Start();
            }
        });
        butt_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stop();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.e("onProgressChanged", progress + " ");
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
//                mediaPlayer.setVolume((float) progress, (float) progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        progressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mediaPlayer.seekTo((int) soundDuration * progress / 100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        timer.scheduleAtFixedRate(timerTask, 0, 300);
//        timer.

    }

    public void Start() {
        Log.e("qaz", "start");

        mediaPlayer.start();

    }

    public void Stop() {
        Log.e("qaz", "stop");
        mediaPlayer.pause();
    }
}