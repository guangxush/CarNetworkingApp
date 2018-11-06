package com.example.chelianwang.Activity;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import com.example.chelianwang.R;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.TYPE_STATUS_BAR, WindowManager.LayoutParams.TYPE_STATUS_BAR);
        setContentView(R.layout.activity_main);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Intent intent3 = new Intent(MainActivity.this, MainMusic.class);//
                intent3.putExtra("str","firstcome");
                startActivity(intent3);
                MainActivity.this.finish();
            }
        };
        timer.schedule(task, 2000);


    }
}
