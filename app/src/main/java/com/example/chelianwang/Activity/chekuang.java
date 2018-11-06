package com.example.chelianwang.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

public class chekuang extends AppCompatActivity {
    private ProgressDialog pDialog;
    private Button btnditu;
    private Button btngeren;
    private ImageView ivxingshilicheng,ivxingshishijian,ivqicheyouliang;
    private TextView chepaihao;
    String mileage=null;
    String fueleconomy=null;
    JSONArray cars = null;
    JSONParser jsonParser = new JSONParser();
    private float xslc,xssj,qcyl;
    String carid=null;
    JSONParser jParser = new JSONParser();
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
        setContentView(R.layout.activity_chekuang);
        btngeren = (Button) findViewById(R.id.btngeren);
        btnditu = (Button) findViewById(R.id.btnditu);
        ivxingshilicheng=(ImageView)findViewById(R.id.ivxingshilicheng);
        ivxingshishijian=(ImageView)findViewById(R.id.ivxingshishijian);
        ivqicheyouliang=(ImageView)findViewById(R.id.ivqicheyouliang);
        chepaihao=(TextView)findViewById(R.id.chepaihao);
        Map<String, String> carInfo = Utils.getCarInfo(this);
        if(carInfo != null) {
            carid=carInfo.get("carid");
            if(carid==null) {
                Toast.makeText(getApplicationContext(), "请先绑定车辆！",
                        Toast.LENGTH_SHORT).show();
                Intent in = new Intent(getApplicationContext(),
                        cheliangliebiao.class);
                startActivity(in);
                finish();
            }
            else
            {
                chepaihao.setText(carid.toString());
                new GetCarDetails().execute();
            }
        }
        btngeren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(chekuang.this,gerenzhongxin.class);
                startActivity(intent);
                chekuang.this.finish();
            }
        });
        btnditu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(chekuang.this,ditu.class);
                startActivity(intent);
                chekuang.this.finish();
            }
        });
    }

    class GetCarDetails extends AsyncTask<String, String, String> {
        String motorperform=null;
        String transperform=null;
        String heanligthperform=null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(chekuang.this);
            pDialog.setMessage("正在加载信息,请稍后...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            String caridstr="\""+carid+"\"";
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
                        mileage=carinfo.getString("mileage");
                        fueleconomy=carinfo.getString("fueleconomy");
                        motorperform=carinfo.getString("motorperform");
                        transperform=carinfo.getString("transperform");
                        heanligthperform=carinfo.getString("heanligthperform");
                        xslc=Float.parseFloat(mileage);//10f;
                        xssj=30f;
                        qcyl=Float.parseFloat(fueleconomy);//50f;
                        System.out.print("当前的车速和油量1："+xslc+","+qcyl);
                    }
                } else {
                    Intent i = new Intent(getApplicationContext(),
                            gerenzhongxin.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if(mileage==null||fueleconomy==null)
            {
                Toast.makeText(getApplicationContext(), "参数错误，请完善车辆信息",
                        Toast.LENGTH_SHORT).show();
                Intent in = new Intent(getApplicationContext(),
                        cheliangliebiao.class);
                startActivity(in);
                finish();
            }
            else
            {
                double xslc2=xslc;

                for(int i=1;i<41;i++)
                {
                    if(xslc2*10==15*i)
                    {
                        Toast.makeText(getApplicationContext(), "行驶里程超过15000公里，请及时维护！",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            if(qcyl<20)
            {
                Toast.makeText(getApplicationContext(), "油量低于20%,请及时加油！",
                        Toast.LENGTH_SHORT).show();
            }

            if(!motorperform.equals("好"))
            {
                Toast.makeText(getApplicationContext(), "发动机故障，请及时维护！",
                        Toast.LENGTH_SHORT).show();
            }
            if(! transperform.equals("好"))
            {
                Toast.makeText(getApplicationContext(), "变速器故障，请及时维护！",
                        Toast.LENGTH_SHORT).show();
            }
            if(!heanligthperform.equals("好"))
            {
                Toast.makeText(getApplicationContext(), "车灯故障，请及时维护！",
                        Toast.LENGTH_SHORT).show();
            }
            xslc=xslc*(245-3)/60+3;
            xssj=xssj*(245-3)/60+3;
            qcyl=qcyl*(245-3)/60+3;
            RotateAnimation a = new RotateAnimation(3f,xslc,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            a.setDuration(1000+5 * (long)xslc);
            a.setFillAfter(true);
            a.setRepeatCount(0);
            ivxingshilicheng.setAnimation(a);
            a.startNow();
            RotateAnimation b = new RotateAnimation(3f,xssj,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            b.setDuration(1000+5 * (long)xssj);
            b.setFillAfter(true);
            b.setRepeatCount(0);
            ivxingshishijian.setAnimation(b);
            b.startNow();
            RotateAnimation c = new RotateAnimation(3f,6*qcyl/10,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            c.setDuration(1000+3* (long)qcyl);
            c.setFillAfter(true);
            c.setRepeatCount(0);
            ivqicheyouliang.setAnimation(c);
            c.startNow();
            }
        }
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
}
