package com.example.amyu.mediaplayersmaple;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends Activity{

    private SeekBar mSeekBar;

    private TextView mTimeText;

    private Handler mSeekHandler;

    private MusicService mMusicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(getApplicationContext(), MusicService.class));

        mSeekHandler = new Handler();

        mMusicService = MusicService.getInstance();

        initView();

        mMusicService.setSongUrl(Const.URL);

        startService(new Intent(MusicService.ACTION_PLAY));
    }

    private void initView() {
        mSeekBar = (SeekBar) findViewById(R.id.main_seek);
        mSeekBar.setOnSeekBarChangeListener(mOnSeekBarChangeListener);

        mTimeText = (TextView) findViewById(R.id.main_time_text);

        mSeekBar.setMax(mMusicService.getMusicDuration());
        mSeekHandler.post(mSeekRunnable);
    }


    private SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mMusicService.seekMusicTo(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private Runnable mSeekRunnable = new Runnable() {
        @Override
        public void run() {
            int position = mMusicService.getCurrentPosition();
            mTimeText.setText(position);
            mSeekBar.setProgress(position);
            mSeekHandler.postDelayed(this, 100);
        }
    };
}
