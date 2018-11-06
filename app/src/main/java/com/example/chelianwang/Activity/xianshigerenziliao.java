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
import android.widget.TextView;
import com.example.chelianwang.R;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
public class xianshigerenziliao extends AppCompatActivity {
    private TextView tvnicheng;
    private TextView tvxingming;
    private TextView tvxingbie;
    private TextView tvzhifu;
    private TextView tvchengshi;
    private Button btnxiugaiziliao,btnfanhui;
    private Button btnxiugaimima;
    private ProgressDialog pDialog;
    private Button btnfankui;
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
        setContentView(R.layout.activity_xianshigerenziliao);
        tvnicheng = (TextView) findViewById(R.id.tvnicheng);
        tvxingming = (TextView) findViewById(R.id.tvxingming);
        tvxingbie = (TextView) findViewById(R.id.tvxingbie);
        tvzhifu = (TextView) findViewById(R.id.tvzhifu);
        tvchengshi = (TextView) findViewById(R.id.tvchengshi);
        btnxiugaiziliao = (Button) findViewById(R.id.btnxiugaiziliao);
        btnxiugaimima = (Button) findViewById(R.id.btnxiugaimima);
        btnfanhui = (Button) findViewById(R.id.btnfanhui);
        btnfankui=(Button)findViewById(R.id.btnfankui);
        Intent i = getIntent();
        userid = i.getStringExtra(TAG_userid);
        new GetUserInfos().execute();
        btnxiugaiziliao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),
                        xiugaiziliao.class);
                in.putExtra(TAG_userid, userid);
                startActivity(in);
                xianshigerenziliao.this.finish();
            }
        });
        btnxiugaimima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),
                        xiugaimima.class);
                in.putExtra(TAG_userid, userid);
                startActivity(in);
               xianshigerenziliao.this.finish();
            }
        });
        btnfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),
                        gerenzhongxin.class);
                in.putExtra(TAG_userid, userid);
                startActivity(in);
                xianshigerenziliao.this.finish();
            }
        });
        btnfankui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),
                        FanKui.class);
                in.putExtra(TAG_userid, userid);
                startActivity(in);
               xianshigerenziliao.this.finish();
            }
        });
    }
    class GetUserInfos extends AsyncTask<String, String, String> {
            String nickname=null;
            String xingming=null;
            String usersex=null;
            String usercity=null;
            String userpay=null;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(xianshigerenziliao.this);
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
                Log.d("显示个人资料:！ ", json.toString());
                try {
                    int success = json.getInt("success");
                    if (success == 1) {
                        users = json.getJSONArray("userstyleinfos");
                        for (int i = 0; i < users.length(); i++) {
                            JSONObject c = users.getJSONObject(i);
                             nickname = c.getString("usernick");
                             xingming=c.getString("username");
                             usersex=c.getString("usersex");
                             usercity=c.getString("usercity");
                             userpay=c.getString("userpay");
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
            protected void onPostExecute(String file_url) {
                pDialog.dismiss();
                tvnicheng.setText(nickname.toString());
                tvxingming.setText(xingming.toString());
                tvxingbie.setText(usersex.toString());
                tvchengshi.setText(usercity.toString());
                tvzhifu.setText(userpay.toString());
            }
        }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent in = new Intent(getApplicationContext(),
                    gerenzhongxin.class);
            in.putExtra(TAG_userid, userid);
            startActivity(in);
          xianshigerenziliao.this.finish();
        }  return false;
    }
}

