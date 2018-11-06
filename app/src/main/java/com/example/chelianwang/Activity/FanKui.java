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

public class FanKui extends AppCompatActivity {
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private EditText etzhuti;
    private EditText etzhengwen;
    private EditText etlianxifangshi;
    private Button btnfankui;
    private Button btntuichu;
    private Button btnfanhui;
    String userid;
    private static final String TAG_userid = "userid";
    private static String url_create_userfeedback = "http://"+ConstantApis.ip+"/carnetserver/create_userfeedback.php";
    private static final String TAG_SUCCESS = "success";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        setContentView(R.layout.activity_fan_kui);
        Intent i = getIntent();
        userid = i.getStringExtra(TAG_userid);
        etzhuti=(EditText)findViewById(R.id.etzhuti);
        etzhengwen=(EditText)findViewById(R.id.etzhengwen);
        etlianxifangshi=(EditText)findViewById(R.id.etlianxifangshi);
        btnfankui=(Button)findViewById(R.id.btnfankui);
        btntuichu=(Button)findViewById(R.id.btntuichu);
        btnfanhui=(Button)findViewById(R.id.btnfanhui);
        etlianxifangshi.setText(userid.toString());
        btnfankui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etzhuti1=etzhuti.getText().toString().trim();
                String etzhengwen1 = etzhengwen.getText().toString().trim();
                String etlianxifangshi1 =etlianxifangshi.getText().toString();
                if(etzhengwen1.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "正文必须填写！",
                            Toast.LENGTH_SHORT).show();
                }
                else if (etlianxifangshi1.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "联系方式必须填写！",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    new CreateNewProduct().execute();
                }
            }
        });
        btntuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),
                       denglu.class);
                startActivity(in);
                FanKui.this.finish();
            }
        });
        btnfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),
                        xianshigerenziliao.class);
                in.putExtra(TAG_userid, userid);
                startActivity(in);
               FanKui.this.finish();
            }
        });
    }
    class CreateNewProduct extends AsyncTask<String, String, String> {
        String feedbacktel ="\""+ etlianxifangshi.getText().toString()+"\"";
        String feedbackinfo ="\""+ etzhengwen.getText().toString()+"\"";
        String feedbacktheme ="\""+ etzhuti.getText().toString()+"\"";
        String userid1="\""+userid.toString()+"\"";
        int error=0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FanKui.this);
            pDialog.setMessage("正在提交·····");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("userid", userid1));
            params.add(new BasicNameValuePair("feedbackinfo",feedbackinfo));
            params.add(new BasicNameValuePair("feedbacktheme", feedbacktheme));
            params.add(new BasicNameValuePair("feedbacktel",feedbacktel));
            JSONObject json = jsonParser.makeHttpRequest(url_create_userfeedback,
                    "POST", params);
            Log.d("用户反馈信息：", json.toString());
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    error=0;
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
                Toast.makeText(getApplicationContext(), "提交反馈失败！",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "感谢您提出的宝贵意见！",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent in = new Intent(getApplicationContext(),
                    xianshigerenziliao.class);
            in.putExtra(TAG_userid, userid);
            startActivity(in);
            FanKui.this.finish();
        }  return false;
    }
}
