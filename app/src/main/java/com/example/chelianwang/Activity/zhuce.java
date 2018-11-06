package com.example.chelianwang.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chelianwang.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class zhuce extends Activity {
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    EditText inputuserid;
    EditText inputusertel;
    EditText inputyanzhengma;
    EditText inputuserpwd1;
    EditText inputuserpwd2;
    Button btnhuoqu;
    String yanzhengma;
    private static String url_create_user = "http://"+ConstantApis.ip+"/carnetserver/create_user.php";
    private static String url_create_userstyleinfo = "http://"+ConstantApis.ip+"/carnetserver/create_userstyleinfo.php";
    private static final String TAG_SUCCESS = "success";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        inputuserid = (EditText) findViewById(R.id.etshoujihao);
        inputusertel = (EditText) findViewById(R.id.etshoujihao);
        inputyanzhengma=(EditText)findViewById(R.id.etyanzhengma);
        inputuserpwd1 = (EditText) findViewById(R.id.etmima1);
        inputuserpwd2 = (EditText) findViewById(R.id.etmima2);
        btnhuoqu=(Button)findViewById(R.id.btnhuoqu);
        Button btnCreateuser = (Button) findViewById(R.id.btnqueren);
        btnhuoqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                int suijishu= (rand.nextInt(9)+1)*1000+(rand.nextInt(9)+0)*100+(rand.nextInt(9)+0)*10+(rand.nextInt(9)+0);
                yanzhengma=Integer.toString(suijishu);
                inputyanzhengma.setText(yanzhengma);
            }
        });
        btnCreateuser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String yonghuming=inputuserid.getText().toString().trim();
                String yanzhengma2 = inputyanzhengma.getText().toString().trim();
                String userpwd1 = inputuserpwd1.getText().toString();
                String userpwd2 = inputuserpwd2.getText().toString();
                if(yonghuming.length()!=11)
                {
                    Toast.makeText(getApplicationContext(), "手机号输入错误！",
                            Toast.LENGTH_SHORT).show();
                       inputuserid.setText("");
                }
                else  if(!yanzhengma2.equals(yanzhengma)){
                    Toast.makeText(getApplicationContext(), "验证码错误！",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!userpwd1.equals(userpwd2)) {
                    Toast.makeText(getApplicationContext(), "两次密码输入不一致，请重新输入！",
                            Toast.LENGTH_SHORT).show();
                    inputuserpwd1.setText("");
                    inputuserpwd2.setText("");
                }
                else if(userpwd1.equals("")||userpwd2.equals("")){
                    Toast.makeText(getApplicationContext(), "密码不能为空！",
                            Toast.LENGTH_SHORT).show();
                }
                else if(userpwd1.length()<6)
                {
                    Toast.makeText(getApplicationContext(), "密码长度不能少于六位！",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    new CreateNewProduct().execute();

                }
            }
        });
    }
    class CreateNewProduct extends AsyncTask<String, String, String> {
        String userid = inputuserid.getText().toString();
        String usertel = inputusertel.getText().toString();
        String userpwd ="\""+ inputuserpwd1.getText().toString()+"\"";
        int error=0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(zhuce.this);
            pDialog.setMessage("正在注册·····");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("userid", userid));
            params.add(new BasicNameValuePair("usertel", usertel));
            params.add(new BasicNameValuePair("userpwd", userpwd));
            JSONObject json = jsonParser.makeHttpRequest(url_create_user,
                    "POST", params);
            Log.d("用户表注册：", json.toString());
            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
            params2.add(new BasicNameValuePair("userid", userid));
            params2.add(new BasicNameValuePair("usersex", "\""+"男"+"\""));
            params2.add(new BasicNameValuePair("usernick", "\""+"点击修改昵称"+"\""));
            params2.add(new BasicNameValuePair("usercity","\""+"南京"+"\""));
            params2.add(new BasicNameValuePair("username","\""+"张三"+"\""));
            params2.add(new BasicNameValuePair("userpay", "\""+"12306"+"\""));
            JSONObject json2 = jsonParser.makeHttpRequest(url_create_userstyleinfo,
                    "POST", params2);
            Log.d("用户信息表注册：", json2.toString());
            try {
                int success = json.getInt(TAG_SUCCESS);
                int success2 = json2.getInt(TAG_SUCCESS);
                if (success == 1&& success2==1) {
                    Intent i = new Intent(getApplicationContext(), denglu.class);
                    startActivity(i);
                    finish();
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
            if(error==1) {
                Toast.makeText(getApplicationContext(), "此用户名已经注册！",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "注册成功！",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent i = new Intent(zhuce.this, denglu.class);
            startActivity(i);
            zhuce.this.finish();
        }  return false;
    }
}

