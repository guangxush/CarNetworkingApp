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

import com.example.chelianwang.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class cheliangxinxi extends AppCompatActivity {
    private TextView tvpinpai;
    JSONParser jParser = new JSONParser();
    private TextView tvxinghao;
    private TextView tvchepaihao;
    private TextView tvfadongjihao;
    private TextView tvlichengshu;
    private TextView tvqiyouliang;
    private TextView tvfaxingneng;
    private TextView tvbianxingneng;
    private TextView tvdengxingneng;
    private Button btnqueren;
    private Button btnxiugai,btnfanhui;
    private ProgressDialog pDialog;
    JSONArray cars = null;
    String carid;
    JSONParser jsonParser = new JSONParser();
    private static final String url_get_carinfo_detials = "http://"+ConstantApis.ip+"/carnetserver/get_carinfo_details.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_carinfos = "carinfos";
    private static final String TAG_carid = "carid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        setContentView(R.layout.activity_cheliangxinxi);
        btnxiugai=(Button)findViewById(R.id.btnxiugai);
        btnqueren=(Button)findViewById(R.id.btnqueren);
        btnfanhui=(Button)findViewById(R.id.btnfanhui);
        tvchepaihao=(TextView)findViewById(R.id.tvchepaihao);
        Intent i = getIntent();
        carid = i.getStringExtra(TAG_carid);
        System.out.print("修改车辆信息页面的车辆信息是：" + carid);
        new GetCarDetails().execute();
        btnxiugai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),
                        xiugaicheliangxinxi.class);
                in.putExtra(TAG_carid, carid);
                startActivity(in);
                cheliangxinxi.this.finish();
            }
        });
        btnfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(cheliangxinxi.this,
                        cheliangliebiao.class);
                //in.putExtra(TAG_carid, carid);
                startActivity(in);
                cheliangxinxi.this.finish();
            }
        });
        btnqueren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSaveSuccess = Utils.saveCarInfo(cheliangxinxi.this, carid);
                if(isSaveSuccess) {
                    System.out.print( "汽车ID保存成功！");
                } else {
                    System.out.print( "汽车ID保存失败！");
                }
                Intent in = new Intent(getApplicationContext(),
                        cheliangliebiao.class);
                startActivity(in);
                cheliangxinxi.this.finish();
            }
        });
    }
    class GetCarDetails extends AsyncTask<String, String, String> {
        String carbrand=null;
        String carlevel=null;
        String caridstr=null;
        String motortype=null;
        String mileage=null;
        String fueleconomy=null;
        String motorperform=null;
        String transperform=null;
        String heanligthperform=null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(cheliangxinxi.this);
            pDialog.setMessage("正在加载信息,请稍后...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            String caridstr="\""+carid+"\"";
            System.out.print("修改车辆信息页面的车辆信息是：" + carid);
            tvpinpai=(TextView)findViewById(R.id.tvpinpai);
            tvxinghao=(TextView)findViewById(R.id.tvxinghao);
            tvchepaihao=(TextView)findViewById(R.id.tvchepaihao);
            tvfadongjihao=(TextView)findViewById(R.id.tvfadongjihao);
            tvlichengshu=(TextView)findViewById(R.id.tvlichengshu);
            tvqiyouliang=(TextView)findViewById(R.id.tvqiyouliang);
            tvfaxingneng=(TextView)findViewById(R.id.tvfaxingneng);
            tvbianxingneng=(TextView)findViewById(R.id.tvbianxingneng);
            tvdengxingneng=(TextView)findViewById(R.id.tvdengxingneng);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("carid", caridstr));
            JSONObject json = jParser.makeHttpRequest(url_get_carinfo_detials, "POST", params);
            Log.d("显示车辆信息: ", json.toString());
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    cars = json.getJSONArray(TAG_carinfos);
                    for (int j = 0; j < cars.length(); j++) {
                        JSONObject carinfo = cars.getJSONObject(j);
                      carbrand=carinfo.getString("carbrand");
                      carlevel=carinfo.getString("carlevel");
                      carid=carinfo.getString("carid");
                      motortype=carinfo.getString("motortype");
                      mileage=carinfo.getString("mileage");
                        fueleconomy=carinfo.getString("fueleconomy");
                        motorperform=carinfo.getString("motorperform");
                       transperform=carinfo.getString("transperform");
                        heanligthperform=carinfo.getString("heanligthperform");
                    }
                } else {

                    Intent i = new Intent(getApplicationContext(),
                            cheliangliebiao.class);
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
            tvpinpai.setText(carbrand);
            tvxinghao.setText(carlevel);
            tvchepaihao.setText(carid);
            tvfadongjihao.setText(motortype);
            tvlichengshu.setText(mileage);
            tvqiyouliang.setText(fueleconomy);
            tvfaxingneng.setText(motorperform);
            tvbianxingneng.setText(transperform);
            tvdengxingneng.setText(heanligthperform);
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent in = new Intent(cheliangxinxi.this,
                    cheliangliebiao.class);
            //in.putExtra(TAG_carid, carid);
            startActivity(in);
            cheliangxinxi.this.finish();
        }  return false;
    }
}
