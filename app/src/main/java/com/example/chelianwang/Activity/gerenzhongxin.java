package com.example.chelianwang.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chelianwang.R;
import com.example.chelianwang.player.MusicPlayer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
public class gerenzhongxin extends AppCompatActivity {
    private Button yinyue;
    private Button cheliangxinxi;
    private Button btnyuyuexinxi,btnditu,btnchekuang;
    private Button btnnicheng;
    private Button btnweizhangchaxun;
    private ProgressDialog pDialog;
    private static final String TAG_userid = "userid";
    JSONArray users = null;
    JSONParser jParser = new JSONParser();
    String userid;
    private static final String url_get_userstylesinfo_detials = "http://"+ConstantApis.ip+"/carnetserver/get_userstyleinfo_details.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        setContentView(R.layout.activity_gerenzhongxin);
        yinyue=(Button)findViewById(R.id.btnyinyue);
        cheliangxinxi=(Button)findViewById(R.id.btncheliangxinxi);
        btnnicheng=(Button)findViewById(R.id.btnnicheng);
        btnyuyuexinxi=(Button)findViewById(R.id.btnyuyuexinxi);
        btnditu=(Button)findViewById(R.id.btnditu);
        btnchekuang=(Button)findViewById(R.id.btnchekuang);
        btnweizhangchaxun=(Button)findViewById(R.id.btnweizhangchaxun);

        Map<String, String> userInfo = Utils.getUserInfo(this);
        if(userInfo != null) {
            userid=userInfo.get("number");
        }
        new GetUserNickname().execute();
        cheliangxinxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), cheliangliebiao.class);
                in.putExtra(TAG_userid, userid);
                startActivity(in);
                gerenzhongxin.this.finish();
            }
        });
        yinyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ityinyue = new Intent(gerenzhongxin.this,MainMusic.class);//MainMusic.class);
                startActivity(ityinyue);
                //gerenzhongxin.this.finish();
            }
        });
        btnditu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ityinyue = new Intent(gerenzhongxin.this, ditu.class);//MainMusic.class);
                startActivity(ityinyue);
                gerenzhongxin.this.finish();
            }
        });
        btnchekuang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ityinyue = new Intent(gerenzhongxin.this, chekuang.class);//MainMusic.class);
                startActivity(ityinyue);
                gerenzhongxin.this.finish();
            }
        });
        btnnicheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), xianshigerenziliao.class);
                in.putExtra(TAG_userid, userid);
                startActivity(in);
                gerenzhongxin.this.finish();
            }
        });
        btnyuyuexinxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ityuyuexinxi = new Intent(gerenzhongxin.this, yuyuexinxi2.class);
                startActivity(ityuyuexinxi);
                gerenzhongxin.this.finish();
            }
        });
        btnweizhangchaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itweizhangchaxun = new Intent(gerenzhongxin.this,ChaxunActivity.class);
                startActivity(itweizhangchaxun);
                gerenzhongxin.this.finish();
            }
        });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {    exitBy2Click();
            //调用双击退出函数
        }  return false;
    } /**  * 双击退出函数  */
    private static Boolean isExit = false;
    private void exitBy2Click()
    {  Timer tExit = null;  if (isExit == false) {
        isExit = true; // 准备退出
        Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
        tExit = new Timer();
        tExit.schedule(new TimerTask() {
            @Override
            public void run() {
                isExit = false;
                // 取消退出
            }
        }, 2000);
        // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
    } else {
        Intent intent = new Intent(this, MusicPlayer.class);
        stopService(intent);
        finish();
        System.exit(0);  }
    }
class GetUserNickname extends AsyncTask<String, String, String> {
    String nickname=null;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(gerenzhongxin.this);
        pDialog.setMessage("正在加载信息,请稍后...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }
    protected String doInBackground(String... args) {
        String userid1="\""+userid+"\"";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("userid", userid1));
        JSONObject json = jParser.makeHttpRequest(url_get_userstylesinfo_detials, "POST", params);
        Log.d("个人中心:！ ", json.toString());
        try {
            int success = json.getInt("success");
            if (success == 1) {
                users = json.getJSONArray("userstyleinfos");
                for (int i = 0; i < users.length(); i++) {
                    JSONObject c = users.getJSONObject(i);
                     nickname = c.getString("usernick");
                }
            } else {
                Intent i = new Intent(getApplicationContext(),
                        denglu.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    protected void onPostExecute(String url) {
        pDialog.dismiss();
        btnnicheng.setText(nickname.toString());
    }
}
}