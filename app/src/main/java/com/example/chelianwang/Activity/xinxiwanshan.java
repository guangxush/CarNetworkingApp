package com.example.chelianwang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.example.chelianwang.R;

import java.util.Map;

public class xinxiwanshan extends AppCompatActivity {
    private Button btnsaoma;
   // public static final int SCANNIN_GREQUEST_CODE = 1;
    private  Button btnshoudong,btnfanhui;
    String userid=null;
    private static final String TAG_userid = "userid";
   // private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        setContentView(R.layout.activity_xinxiwanshan);
        btnsaoma=(Button)findViewById(R.id.btnsaoma);
        btnshoudong=(Button)findViewById(R.id.btnshoudong);
        btnfanhui=(Button)findViewById(R.id.btnfanhui);
        Map<String, String> userInfo = Utils.getUserInfo(this);
        if(userInfo != null) {
            userid=userInfo.get("number");
        }
        btnsaoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(xinxiwanshan.this,
                        MipcaActivityCapture.class);
               // in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                xinxiwanshan.this.finish();
            }
        });
        btnshoudong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(xinxiwanshan.this,
                        tianjiacheliangxinxi.class);
                Bundle bundle = new Bundle();
                bundle.putInt("s",2);
                in.putExtras(bundle);
                //in.putExtra(TAG_userid, userid);
                startActivity(in);
                xinxiwanshan.this.finish();
               // xinxiwanshan.this.finish();
            }
        });
        btnfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(xinxiwanshan.this,
                        gerenzhongxin.class);
                in.putExtra(TAG_userid, userid);
                startActivity(in);
                xinxiwanshan.this.finish();
            }
        });


    }

   /* @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    //显示扫描到的内容
                    mTextView=(TextView)findViewById(R.id.mTextView);
                    mTextView.setText(bundle.getString("result"));

                }
                break;
        }
    }*/

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent i = new Intent(xinxiwanshan.this, gerenzhongxin.class);
            i.putExtra(TAG_userid, userid);
            startActivity(i);
            xinxiwanshan.this.finish();
        }  return false;
    }
}
