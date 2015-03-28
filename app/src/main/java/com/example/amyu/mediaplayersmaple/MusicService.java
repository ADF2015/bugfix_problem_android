package com.example.amyu.mediaplayersmaple;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.PowerManager;

import java.io.IOException;

/**
 * Created by amyu on 15/03/19.
 */
public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnSeekCompleteListener {

    public static final String ACTION_PLAY = "PLAY";
    public static final String ACTION_PAUSE = "PAUSE";

    private static final int ERROR = -1;

    private static MusicService sInstance;

    private MediaPlayer mMediaPlayer;

    enum State {
        Retrieving,
        Stopped,
        Preparing,
        Playing,
        Paused
    }

    private static State sState = State.Retrieving;

    @Override
    public void onCreate() {
        initMediaPlayer();
        sInstance = this;
    }

    @Override
    public void onDestroy() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
        sState = State.Retrieving;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return START_STICKY;
        }

        String action = intent.getAction();
        if (action == null) {
            return START_STICKY;
        }
        if (action.equals(ACTION_PLAY)) {
            startMusic();
        } else if (action.equals(ACTION_PAUSE)) {
            pauseMusic();
        }

        return START_STICKY;
    }

    private void initMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnBufferingUpdateListener(this);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnSeekCompleteListener(this);
        mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
    }

    public void setSongUrl(String info) {
        if (info.equals("")) {
            sState = State.Retrieving;
            return;
        }

        try {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(info);
            mMediaPlayer.prepare();

            sState = State.Preparing;
        } catch (IllegalArgumentException | IllegalStateException | IOException ignore) {
            sState = State.Retrieving;
        }

    }

    public void pauseMusic() {
        if (sState == State.Playing) {
            mMediaPlayer.pause();
            sState = State.Paused;
        }
    }


    public void startMusic() {
        if (!sState.equals(State.Preparing) && !sState.equals(State.Retrieving)) {
            mMediaPlayer.start();
            sState = State.Playing;
        }
    }

    public boolean isPlaying() {
        return sState == State.Playing;
    }

    public int getMusicDuration() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getDuration();
        }
        return ERROR;
    }

    public int getCurrentPosition() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getCurrentPosition();
        }
        return ERROR;
    }

    public void seekMusicTo(int pos) {
        if (mMediaPlayer == null) {
            return;
        }
        mMediaPlayer.seekTo(pos);
    }

    public static MusicService getInstance() {
        return sInstance;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }
}
