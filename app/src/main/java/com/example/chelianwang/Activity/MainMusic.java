package com.example.chelianwang.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;

import com.example.chelianwang.R;
import com.example.chelianwang.adapter.MusicAdapter;
import com.example.chelianwang.model.Music;
import com.example.chelianwang.player.MusicPlayer;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class MainMusic extends Activity implements View.OnClickListener,
        AdapterView.OnItemClickListener {

    public static String TAG = "yy";
    public static String SDPath = Environment.getExternalStorageDirectory().getPath() + "/MyMusic";

    public static final String PLAY_STATE_previous = "previous";
    public static final String PLAY_STATE_next = "next";
    public static final String PLAY_STATE_play = "play";
    public static final String PLAY_STATE_pause = "pause";
    public static final String PLAY_STATE_function = "function";

    public static final String BROADCAST_REFRESH_PROGRESS = "com.music.refreshprogress";
    public static final String BROADCAST_CHANGE_MUSIC = "com.music.changemusic";

    ListView listView;
    MusicAdapter musicAdapter;
    MusicApplication mApp;
    Button btnPrevious, btnPlayOrPause, btnNext, btnStop, btnFunction,btnback;
    SeekBar seekBar;

    boolean isPause = false;
    int curMusic = 0, curPercent = 0, secondaryProgress = 0;
    int function = 0;    //0顺序播放，1循环播放，单曲循环，2随机播放
    String[] functionStr = {"顺序播放", "循环播放", "单曲循环", "随机播放"};

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    seekBar.setProgress(curPercent);
                    seekBar.setSecondaryProgress(secondaryProgress);
                    break;
                case 2:
                    musicAdapter.playView(curMusic);
                    break;
            }

        }

    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
private String start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_music);
        mApp = (MusicApplication) getApplication();
        initView();
        loadFileData();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        Intent intent3=getIntent();
        start = intent3.getStringExtra("str");
           if (TextUtils.equals(start,"firstcome"))
        {
            //Toast.makeText(this,"到达音乐界面",Toast.LENGTH_SHORT).show();
        isPause = true;
            //isPause = false;
            curMusic = 1;
            Intent intent = new Intent(this, MusicPlayer.class);
            intent.putExtra("state", PLAY_STATE_play);
            intent.putExtra("position", 0);
            intent.putExtra("function", -1);
            startService(intent);
            Intent intent1=new Intent(this,denglu.class);
            startActivity(intent1);
            this.finish();
        }


    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listv_music);
        musicAdapter = new MusicAdapter();
        btnback=(Button)findViewById(R.id.btnfanhui);
        btnPrevious = (Button) findViewById(R.id.btn_previous);
        btnPlayOrPause = (Button) findViewById(R.id.btn_pause_play);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnStop = (Button) findViewById(R.id.btn_stop);
        btnFunction = (Button) findViewById(R.id.btn_function);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
            listView.setAdapter(musicAdapter);
       listView.setOnItemClickListener(this);
        btnPrevious.setOnClickListener(this);
        btnPlayOrPause.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnback.setOnClickListener(this);
        btnFunction.setOnClickListener(this);

    }

    private void loadFileData() {
        ContentResolver resolver = this.getContentResolver();

        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        List<Music> musicList = new ArrayList<Music>();
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)); // 标题
                long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)); // 大小
                String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)); // 时长
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));

                if (duration >= 1000 && duration <= 900000) {
                    Music music = new Music();
                    music.setName(title);
                    music.setSize(size);
                    music.setUrl(url);
                    music.setDuration(duration);
                    musicList.add(music);
                }

            } while (cursor.moveToNext());
        }
        mApp.setMusicList(musicList);
        musicAdapter.update(musicList);

    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnfanhui)
        {
            Intent itback=new Intent(this,gerenzhongxin.class);
            startActivity(itback);
            this.finish();
        }
        else {
            Intent intent = new Intent(this, MusicPlayer.class);
            intent.putExtra("position", -1);
            if (v.getId() == R.id.btn_previous) {
                intent.putExtra("state", PLAY_STATE_previous);
                intent.putExtra("function", -1);
                startService(intent);
            } else if (v.getId() == R.id.btn_pause_play) {

                if (!isPause) {
                    isPause = true;
                                    btnPlayOrPause.setText("播放");
                                    intent.putExtra("state", PLAY_STATE_pause);
                } else {
                                     isPause = false;
                                    btnPlayOrPause.setText("暂停");
                                    intent.putExtra("state", PLAY_STATE_play);

                }
                intent.putExtra("function", -1);
                startService(intent);
            } else if (v.getId() == R.id.btn_next) {
                intent.putExtra("state", PLAY_STATE_next);
                intent.putExtra("function", -1);
                startService(intent);
            } else if (v.getId() == R.id.btn_stop) {
                stopService(intent);
            } else if (v.getId() == R.id.btn_function) {
                if (function + 1 < 4) {
                    function++;
                } else {
                    function = 0;
                }
                btnFunction.setText(functionStr[function]);
                intent.putExtra("state", PLAY_STATE_function);
                intent.putExtra("function", function);
                startService(intent);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                      long id) {
        isPause = true;
        Intent intent = new Intent(this, MusicPlayer.class);
        intent.putExtra("state", PLAY_STATE_play);
      intent.putExtra("position", position);
     intent.putExtra("function", -1);
        startService(intent);
    }

    MusicBroadcastReceiver recevier;

    @Override
    protected void onResume() {
        recevier = new MusicBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_REFRESH_PROGRESS);
        this.registerReceiver(recevier, intentFilter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        this.unregisterReceiver(recevier);
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.chelianwang/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.chelianwang/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public class MusicBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BROADCAST_REFRESH_PROGRESS)) {
                curMusic = intent.getIntExtra("curMusic", 0);
                curPercent = intent.getIntExtra("curPercent", 0);
                secondaryProgress = intent.getIntExtra("secondaryProgress", 0);
                mHandler.sendEmptyMessage(1);
            } else if (intent.getAction().equals(BROADCAST_CHANGE_MUSIC)) {
                curMusic = intent.getIntExtra("curMusic", 0);
                curPercent = intent.getIntExtra("curPercent", 0);
                secondaryProgress = intent.getIntExtra("secondaryProgress", 0);
                mHandler.sendEmptyMessage(2);
            }
        }

    }
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // TODO Auto-generated method stub
//
//        if(keyCode == KeyEvent.KEYCODE_BACK)
//        {    exitBy2Click();
//            //调用双击退出函数
//        }  return false;
//    } /**  * 双击退出函数  */
//    private static Boolean isExit = false;
//    private void exitBy2Click()
//    {  Timer tExit = null;  if (isExit == false) {
//        isExit = true; // 准备退出
//        Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//        tExit = new Timer();
//        tExit.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                isExit = false;
//                // 取消退出
//            }
//        }, 2000);
//        // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
//    } else {
//        Intent intent = new Intent(this, MusicPlayer.class);
//        stopService(intent);
//        finish();
//        System.exit(0);  }
//    }
}
