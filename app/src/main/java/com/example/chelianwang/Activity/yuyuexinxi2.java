package com.example.chelianwang.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chelianwang.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class yuyuexinxi2 extends AppCompatActivity {
    private Button btnshengchengerweima,btnfanhui;
    private TextView tvjiayoucheliang;
    private TextView tvjiayoushijian;
    private TextView tvjiayouzhan;
    private TextView tvjiayouleixing;
    private TextView tvjiayoushuliang;
    private ProgressDialog pDialog;
    private static final String TAG_SUCCESS = "success";
    JSONArray oils = null;
    JSONParser jParser = new JSONParser();
    String userid,carid;
    JSONParser jsonParser = new JSONParser();
    private static final String url_get_oilinfo_detials = "http://"+ConstantApis.ip+"/carnetserver/get_oilinfo_details.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        setContentView(R.layout.activity_yuyuexinxi2);
        tvjiayoucheliang=(TextView)findViewById(R.id.tvjiayoucheliang);
        tvjiayoushijian=(TextView)findViewById(R.id.tvshijian);
        tvjiayouzhan=(TextView)findViewById(R.id.tvjiayouzhan);
        tvjiayouleixing=(TextView)findViewById(R.id.tvjiayouleixing);
        tvjiayoushuliang=(TextView)findViewById(R.id.tvjiayoushuliang);
        btnshengchengerweima=(Button)findViewById(R.id.btnshengchengerweima);
        btnfanhui=(Button)findViewById(R.id.btnfanhui);
        Map<String, String> carInfo = Utils.getCarInfo(this);
        if(carInfo != null) {
            carid=carInfo.get("carid");
        }
        new GetOilInfos().execute();
        btnshengchengerweima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(yuyuexinxi2.this, QrActivity.class);
                intent.putExtra("jiayoucheliang", tvjiayoucheliang.getText().toString());
                intent.putExtra("shijian", tvjiayoushijian.getText().toString());
                intent.putExtra("jiayouzhan", tvjiayouzhan.getText().toString());
                intent.putExtra("leixing", tvjiayouleixing.getText().toString());
                intent.putExtra("shuliang", tvjiayoushuliang.getText().toString());
                startActivity(intent);
                //finish();
            }
        });
        btnfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(yuyuexinxi2.this, gerenzhongxin.class);
                startActivity(i);
                finish();
            }
        });
    }
    class GetOilInfos extends AsyncTask<String, String, String> {
        String oiltime=null;
        String oiltype=null;
        String oilcount=null;
        String oilstation=null;
        int error=0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(yuyuexinxi2.this);
            pDialog.setMessage("正在加载信息,请稍后...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            String carid1="\""+carid+"\"";
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("carid", carid1));
            JSONObject json = jParser.makeHttpRequest(url_get_oilinfo_detials, "POST", params);
            Log.d("预约信息2！ ", json.toString());
            try {
                int success = json.getInt("success");
                if (success == 1) {
                    oils = json.getJSONArray("oilinfos");
                    error=0;
                    for (int i = 0; i < oils.length(); i++) {
                        JSONObject c = oils.getJSONObject(i);
                        carid= c.getString("carid");
                        oiltime=c.getString("oiltime");
                        oiltype=c.getString("oiltype");
                        oilcount=c.getString("oilcount");
                        oilstation=c.getString("oilstation");
                    }
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
            if(error==0) {
                tvjiayoucheliang.setText(carid.toString());
                tvjiayoushijian.setText(oiltime.toString());
                tvjiayouleixing.setText(oiltype.toString());
                tvjiayoushuliang.setText(oilcount.toString());
                tvjiayouzhan.setText(oilstation.toString());
            }
            else
            {
                Intent i = new Intent(getApplicationContext(),
                        gerenzhongxin.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                Toast.makeText(getApplicationContext(), "未加油！",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent i = new Intent(yuyuexinxi2.this, gerenzhongxin.class);
            startActivity(i);
            yuyuexinxi2.this.finish();
        }  return false;
    }
}
