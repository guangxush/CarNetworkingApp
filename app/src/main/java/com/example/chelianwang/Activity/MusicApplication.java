package com.example.chelianwang.Activity;

/**
 * Created by 霸气的史迪仔 on 2016/5/17.
 */


        import java.util.List;

        import com.baidu.mapapi.SDKInitializer;
        import com.example.chelianwang.model.Music;
        import android.app.Application;

public class MusicApplication extends Application {


    List<Music> musicList;
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        com.thinkland.sdk.android.SDKInitializer.initialize(getApplicationContext());

    }

    public List<Music> getMusicList() {
        return musicList;
    }
    public void setMusicList(List<Music> musicList) {
        this.musicList = musicList;
    }
}
