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
import android.widget.Toast;

import com.example.chelianwang.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class xiugaimima extends AppCompatActivity {
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    EditText inputuserid;
    EditText inputusertel;
    EditText inputyanzhengma;
    EditText inputuserpwd1;
    EditText inputuserpwd2;
    Button btnUpdateuser;
    Button btnhuoqu;
    String yanzhengma;
    String userid=null;
    private static String url_update_user = "http://"+ConstantApis.ip+"/carnetserver/update_user.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_userid = "userid";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        setContentView(R.layout.activity_xiugaimima);
        inputuserid = (EditText) findViewById(R.id.etshoujihao);
        inputusertel = (EditText) findViewById(R.id.etshoujihao);
        inputyanzhengma=(EditText)findViewById(R.id.etyanzhengma);
        btnhuoqu=(Button)findViewById(R.id.btnhuoqu);
        inputuserpwd1 = (EditText) findViewById(R.id.etmima1);
        inputuserpwd2 = (EditText) findViewById(R.id.etmima2);
        btnUpdateuser = (Button) findViewById(R.id.btnqueren);
        Map<String, String> userInfo = Utils.getUserInfo(this);
        if(userInfo != null) {
            userid=userInfo.get("number");
        }
        //Intent i = getIntent();
       // userid = i.getStringExtra(TAG_userid);
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
        btnUpdateuser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String yanzhengma2 = inputyanzhengma.getText().toString().trim();
                String userpwd1 = inputuserpwd1.getText().toString();
                String userpwd2 = inputuserpwd2.getText().toString();
                if(!yanzhengma2.equals(yanzhengma)){
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
                    Toast.makeText(getApplicationContext(), "修改密码成功！",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    class CreateNewProduct extends AsyncTask<String, String, String> {
        String userid =  "\""+inputuserid.getText().toString().trim()+"\"";
        String usertel =  "\""+inputusertel.getText().toString().trim()+"\"";
        String userpwd =  "\""+inputuserpwd1.getText().toString().trim()+"\"";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(xiugaimima.this);
            pDialog.setMessage("正在修改·····");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("userid", userid));
            params.add(new BasicNameValuePair("usertel", usertel));
            params.add(new BasicNameValuePair("userpwd", userpwd));
            JSONObject json = jsonParser.makeHttpRequest(url_update_user,
                    "POST", params);
            Log.d("修改密码：", json.toString());
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Intent i = new Intent(getApplicationContext(), denglu.class);
                    startActivity(i);
                    xiugaimima.this.finish();
                } else {
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
        }

    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent i = new Intent(xiugaimima.this, xianshigerenziliao.class);
            i.putExtra(TAG_userid, userid);
            startActivity(i);
            xiugaimima.this.finish();
        }  return false;
    }
}
