package com.example.chelianwang.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chelianwang.R;
import com.example.chelianwang.player.MusicPlayer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
public class denglu extends AppCompatActivity {
    private Button btndenglu;
    private CheckBox cbRemember;
    private Button btnzhuce;
    private Button btnwangjimima;
    public EditText etyonghuming;
    public EditText etmima;
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> productsList;
    private static String url_users_details = "http://"+ConstantApis.ip+"/carnetserver/get_user_details.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_userid = "userid";
    public String userid;
    String number,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        setContentView(R.layout.activity_denglu);
        btndenglu = (Button) findViewById(R.id.btndenglu);
        btnzhuce = (Button) findViewById(R.id.btnzhuce);
        btnwangjimima = (Button) findViewById(R.id.btnwangjimima);
        etyonghuming=(EditText)findViewById(R.id.etyonghuming);
        etmima=(EditText)findViewById(R.id.etmima);
        cbRemember=(CheckBox)findViewById(R.id.cbRemember);
        Map<String, String> userInfo = Utils.getUserInfo(denglu.this);
        if(userInfo != null) {
            etyonghuming.setText(userInfo.get("number"));
            etmima.setText(userInfo.get("password"));
            if(userInfo.get("password")!=null&&userInfo.get("number")!=null) {
                cbRemember.setChecked(true);
            }
        }
        number = etyonghuming.getText().toString().trim();
        password = etmima.getText().toString();
        btndenglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = etyonghuming.getText().toString().trim();
                String password = etmima.getText().toString();
                if(TextUtils.isEmpty(number)) {
                    Toast.makeText(getApplicationContext(), "用户名不能为空！",
                        Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "密码不能为空！",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                // 登录成功
                // 如果正确, 判断是否勾选了记住密码
                if(cbRemember.isChecked()) {
                    Log.i("MainActivity", "记住密码: " + number + ", " + password);
                    // 保存用户信息
                    boolean isSaveSuccess2 = Utils.saveUserInfo(denglu.this, number, password);
                    if (isSaveSuccess2) {
                        System.out.print("保存用户名和密码成功！");
                    } else {
                        System.out.print("保存用户名和密码失败！");
                    }
                }
                else {
                    boolean isSaveSuccess = Utils.saveUserInfo(denglu.this, number, null);
                    if(isSaveSuccess) {
                        System.out.print( "保存用户名成功！");
                    } else {
                        System.out.print("保存用户名失败！");
                    }
                }
                new LoadUsers().execute();
          }
        });
        btnzhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(denglu.this, zhuce.class);
                startActivity(intent);
                denglu.this.finish();
            }
        });
        btnwangjimima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(denglu.this, xiugaimima2.class);
                startActivity(intent);
                denglu.this.finish();
            }
        });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
            //调用双击退出函数
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;
    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
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
            System.exit(0);
        }
    }

    class LoadUsers extends AsyncTask<String, String, String> {
        String  userid = "\""+etyonghuming.getText().toString()+"\"";
        String  userpwd = "\""+etmima.getText().toString().trim()+"\"";

        int  error=0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(denglu.this);
            pDialog.setMessage("正在登陆·····");
            pDialog.setIndeterminate(true);//false
            pDialog.setCancelable(true);//false
            pDialog.show();
        }
        protected String doInBackground(String... args) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("userid", userid));
                params.add(new BasicNameValuePair("userpwd", userpwd));
                JSONObject json = jParser.makeHttpRequest(url_users_details, "POST", params);
                Log.d("登录", json.toString());
                try {
                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 1)
                    {
                        error=0;
                        Intent in = new Intent(getApplicationContext(),chekuang.class);
                        in.putExtra(TAG_userid, userid);
                        startActivity(in);
                        denglu.this.finish();
                    }
                    else
                    {
                        error=1;
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            return null;
        }
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if(error==1)
            {
                Toast.makeText(getApplicationContext(), "用户名或密码错误！",
                        Toast.LENGTH_SHORT).show();
                etmima.setText("");
            }
        }
    }
}

