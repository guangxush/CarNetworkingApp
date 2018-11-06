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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.chelianwang.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class xiugaiziliao extends AppCompatActivity {
    private EditText etnicheng;
    private EditText etxingming;
    private EditText etxingbie;
    private EditText etzhifu;
    private EditText etchengshi;
    private  Button btnfanhui;
    private RadioGroup RG;
    private RadioButton RBnan,RBnv;
    JSONArray users = null;
    String userid;
    JSONParser jsonParser = new JSONParser();
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> CarinfoList;
    private static final String url_update_userstyleinfo = "http://"+ConstantApis.ip+"/carnetserver/update_userstyleinfo.php";
    private static final String url_get_userstylesinfo_detials = "http://"+ConstantApis.ip+"/carnetserver/get_userstyleinfo_details.php";
    private static final String TAG_SUCCESS = "success";



    private static final String TAG_userid = "userid";
    private Button btnquerenxiugai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        setContentView(R.layout.activity_xiugaiziliao);
        etnicheng=(EditText)findViewById(R.id.etnicheng);
        etxingming=(EditText)findViewById(R.id.etxingming);
       etxingbie=(EditText)findViewById(R.id.etxingbie);


        RG=(RadioGroup)this.findViewById(R.id.RG);
        RBnan=(RadioButton)this.findViewById(R.id.RBnan);
        RBnv=(RadioButton)this.findViewById(R.id.RBnv);
        etzhifu=(EditText)findViewById(R.id.etzhifu);
        etchengshi=(EditText)findViewById(R.id.etchengshi);

        btnquerenxiugai=(Button)findViewById(R.id.btnquerenxiugai);
        btnfanhui=(Button)findViewById(R.id.btnfanhui);
        Intent i = getIntent();
        userid = i.getStringExtra(TAG_userid);
        new GetUserInfos().execute();

        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == RBnan.getId()) {
                    etxingbie.setText("男");
                    //motorperform = "\"" + "好" + "\"";
                } else {
                    etxingbie.setText("女");
                   // motorperform = "\"" + "坏" + "\"";
                }
            }
        });
        btnquerenxiugai.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String usernick1 = etnicheng.getText().toString();
                String usercity1 = etchengshi.getText().toString();
                String username1 = etxingming.getText().toString();
                String userpay1 = etzhifu.getText().toString();
                if(usernick1.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "昵称不能为空！",
                            Toast.LENGTH_SHORT).show();
                }
                else if(username1.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "姓名不能为空！",
                            Toast.LENGTH_SHORT).show();
                }
                else if(userpay1.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "支付宝不能为空！",
                            Toast.LENGTH_SHORT).show();
                }
                else if(usercity1.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "城市不能为空！",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    new CreateNewProduct().execute();
                    Toast.makeText(getApplicationContext(), "修改个人信息成功！！",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), xianshigerenziliao.class);

                i.putExtra(TAG_userid, userid);
                startActivity(i);
                xiugaiziliao.this.finish();
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
            pDialog = new ProgressDialog(xiugaiziliao.this);
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
            Log.d("All User:！ ", json.toString());
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
                    xiugaiziliao.this.finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            etnicheng.setText(nickname.toString());
            etxingming.setText(xingming.toString());
            etxingbie.setText(usersex.toString());
            if(usersex.toString().equals("男"))
            {
                RBnan.setChecked(true);
            }
            else
            {
                RBnv.setChecked(true);
            }
            etchengshi.setText(usercity.toString());
            etzhifu.setText(userpay.toString());
        }
    }
    class CreateNewProduct extends AsyncTask<String, String, String> {
        String usernick = "\""+etnicheng.getText().toString()+"\"";
        String userid2 ="\""+userid+"\"";
        String usersex = "\""+etxingbie.getText().toString()+"\"";
        String usercity = "\""+etchengshi.getText().toString()+"\"";
        String username = "\""+etxingming.getText().toString()+"\"";
        String userpay = "\""+etzhifu.getText().toString()+"\"";
        int error=0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(xiugaiziliao.this);
            pDialog.setMessage("正在修改·····");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("userid", userid2));
            params.add(new BasicNameValuePair("usernick", usernick));
            params.add(new BasicNameValuePair("usersex", usersex));
            params.add(new BasicNameValuePair("usercity", usercity));
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("userpay", userpay));
            JSONObject json = jsonParser.makeHttpRequest(url_update_userstyleinfo,
                    "POST", params);
            Log.d("修改资料：", json.toString());
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    error=0;
                    Intent in = new Intent(getApplicationContext(),
                            xianshigerenziliao.class);
                    in.putExtra(TAG_userid, userid);
                    startActivity(in);
                    xiugaiziliao.this.finish();
                } else {
                   error=1;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if(error==1)
            {
                Toast.makeText(getApplicationContext(), "修改个人信息失败！！",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent i = new Intent(getApplicationContext(), xianshigerenziliao.class);
            i.putExtra(TAG_userid, userid);
            startActivity(i);
            xiugaiziliao.this.finish();
        }  return false;
    }
}
