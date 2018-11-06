package com.example.chelianwang.player;

/**
 * Created by 霸气的史迪仔 on 2016/5/17.
 */


        import java.io.IOException;
        import java.util.List;

        import com.example.chelianwang.Activity.MainMusic;
        import com.example.chelianwang.Activity.MusicApplication;
        import com.example.chelianwang.model.Music;

        import android.app.Service;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.media.AudioManager;
        import android.media.MediaPlayer;
        import android.media.MediaPlayer.OnBufferingUpdateListener;
        import android.media.MediaPlayer.OnCompletionListener;
        import android.media.MediaPlayer.OnErrorListener;
        import android.media.MediaPlayer.OnInfoListener;
        import android.os.Handler;
        import android.os.IBinder;
        import android.os.Message;
        import android.telephony.TelephonyManager;
        import android.util.Log;

public class MusicPlayer extends Service implements OnBufferingUpdateListener,
        OnCompletionListener, MediaPlayer.OnPreparedListener, OnInfoListener,
        OnErrorListener {

    public static final String PLAY_STATE_previous = "previous";
    public static final String PLAY_STATE_next = "next";
    public static final String PLAY_STATE_play = "play";
    public static final String PLAY_STATE_pause = "pause";
    public static final String PLAY_STATE_function = "function";

    public MediaPlayer mediaPlayer;
     public List<Music> musicList;
    int Max;
    int curMusic = 0;
    int bufferingProgress;
    int curFunction = 0;

    MusicApplication mApp;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            mHandler.sendEmptyMessageDelayed(1, 500);
            switch(msg.what) {
                case 1:
                    if(mediaPlayer == null) return ;
                    int position = mediaPlayer.getCurrentPosition();
                    int duration = mediaPlayer.getDuration();
                    int percent = position * 100 / duration;

                    Intent intent = new Intent(MainMusic.BROADCAST_REFRESH_PROGRESS);
                    intent.putExtra("curMusic", curMusic);
                    intent.putExtra("curPercent", percent);
                    intent.putExtra("secondaryProgress", bufferingProgress);
                    sendBroadcast(intent);

                    break;
            }
        }

    };

    @Override
    public void onCreate() {
        mApp = (MusicApplication) getApplication();
        musicList = mApp.getMusicList();
        Max = musicList.size();
        super.onCreate();
    }

    private void sendMessage() {
        if(mHandler.hasMessages(1)) {
            mHandler.removeMessages(1);
        }
        mHandler.sendEmptyMessage(1);
    }
    private void removeMessage() {
        if(mHandler.hasMessages(1)) {
            mHandler.removeMessages(1);
        }
    }


    public void play() {
        releaseMediaPlay();
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);

            mediaPlayer.reset();

           mediaPlayer.setDataSource(musicList.get(curMusic).getUrl());


            mediaPlayer.prepare();

            sendChangeMusicBroadcast();

        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void sendChangeMusicBroadcast() {
        removeMessage();
        if(mediaPlayer == null) return ;
        int position = mediaPlayer.getCurrentPosition();
        int duration = mediaPlayer.getDuration();
        int percent = position * 100 / duration;

        Intent intent = new Intent(MainMusic.BROADCAST_CHANGE_MUSIC);
        intent.putExtra("curMusic", curMusic);
        intent.putExtra("curPercent", percent);
        intent.putExtra("secondaryProgress", bufferingProgress);
        sendBroadcast(intent);

        mHandler.sendEmptyMessageDelayed(1, 500);
    }


    public void pause() {
        if(mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            removeMessage();
        }
    }

    public void resumePlay() {
        if(mediaPlayer == null) return ;
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            sendMessage();
        } else {
            play();
        }


    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            removeMessage();
            mediaPlayer = null;
        }
    }

    public void previous() {
        if (curMusic == 0) {
            curMusic = Max - 1;
            play();
        } else {
            curMusic--;
            play();
        }
    }

    public void next() {
        if (curMusic + 1 == Max) {
            curMusic = 0;
            play();
        } else {
            curMusic++;
            play();
        }
    }

    /**
     * 顺序播放
     */
    public void slidShow() {
        curMusic++;
        if (curMusic == Max) {
            releaseMediaPlay();
            removeMessage();
        } else {
            play();
        }
    }

    /**
     * 循环播放
     */
    public void loop() {
        curMusic++;
        if (curMusic == Max) {
            curMusic = 0;
        }
        play();
    }

    /**
     * 随机播放
     */
    public void random() {
        curMusic = (int) (Math.random() * Max);
        play();
    }

    public void releaseMediaPlay() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.reset();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onPrepared(MediaPlayer arg0) {
        arg0.start();
        Log.e("mediaPlayer", "onPrepared");
    }

    @Override
    public void onCompletion(MediaPlayer arg0) {
        switch(curFunction) {
            case 0:
                slidShow();
                break;
            case 1:
                loop();
                break;
            case 2:
                play();
                break;
            case 3:
                random();
                break;
        }



    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        Log.i("yy", "onInfo what:" + what + ":" + extra);
        return false;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer arg0, int bufferingProgress) {
        this.bufferingProgress = bufferingProgress;
        Log.i("yy", bufferingProgress + "% buffer");
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        play();
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStart(Intent intent, int startId) {
        String state = intent.getStringExtra("state");
        int position = intent.getIntExtra("position", -1);
        int function = intent.getIntExtra("function", -1);
        if(position != -1) curMusic = position;
        if(function != -1) curFunction = function;
        if(state.trim().equals(PLAY_STATE_previous)) {
            previous();
        } else if(state.trim().equals(PLAY_STATE_next)) {
            next();
        } else if(state.trim().equals(PLAY_STATE_play)) {
            resumePlay();
            if(position == -1) return ;
        } else if(state.trim().equals(PLAY_STATE_pause)) {
            pause();
            return ;
        } else if(state.trim().equals(PLAY_STATE_function)) {
            return ;
        }
        play();
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        stop();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class inComingTelegram extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.trim().equals(TelephonyManager.EXTRA_STATE_RINGING)) {

            } else {

            }
        }

    }


}