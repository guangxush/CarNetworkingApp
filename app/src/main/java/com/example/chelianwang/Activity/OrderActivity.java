package com.example.chelianwang.Activity;
import android.text.format.Time;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chelianwang.R;
import com.example.chelianwang.time.JudgeDate;
import com.example.chelianwang.time.ScreenInfo;
import com.example.chelianwang.time.WheelMain;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;




public class OrderActivity extends Activity   {
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private EditText tvjiayouzhan;
    private TextView tvjiayoucheliang;
    private EditText tvshijian;
    private EditText tvjiayouleixing;
    private EditText tvjiayoushuliang;
    private Button bt_1;
    String carid=null;
    private static String url_create_oilinfo = "http://"+ConstantApis.ip+"/carnetserver/create_oilinfo.php";
    private static final String TAG_SUCCESS = "success";

    WheelMain wheelMain;
    EditText txttime;
    String str;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:ss");
    //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private boolean hasTime = true;//true=date&time false=date
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        tvjiayouzhan=(EditText) findViewById(R.id.tvjiayouzhan);
        tvjiayouzhan.setText(getIntent().getStringExtra("name"));
        tvjiayoucheliang=(TextView) findViewById(R.id.tvjiayoucheliang);
        tvshijian=(EditText) findViewById(R.id.txttime);
        tvjiayouleixing=(EditText) findViewById(R.id.tvjiayouleixing);
        tvjiayoushuliang=(EditText) findViewById(R.id.tvjiayoushuliang);
        bt_1=(Button)findViewById(R.id.btnshengchengerweima);
        Map<String, String> carInfo = Utils.getCarInfo(this);

        txttime = (EditText) findViewById(R.id.txttime);



        Calendar calendar = Calendar.getInstance();
        txttime.setText(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + " "+calendar.get(calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE));
        str=txttime.getText().toString();
        Button btnselecttime = (Button) findViewById(R.id.button1);
        btnselecttime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                LayoutInflater inflater = LayoutInflater.from(OrderActivity.this);
                final View timepickerview = inflater.inflate(R.layout.timepicker, null);
                ScreenInfo screenInfo = new ScreenInfo(OrderActivity.this);
                wheelMain = new WheelMain(timepickerview, hasTime);
                wheelMain.screenheight = screenInfo.getHeight();
                String time = txttime.getText().toString();
                Calendar calendar = Calendar.getInstance();
                if (JudgeDate.isDate(time, "yyyy-MM-dd hh:ss")) {
                    try {
                        calendar.setTime(dateFormat.parse(time));
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);
                //wheelMain.initDateTimePicker(year,month,day,hour,min);
                //wheelMain.initDateTimePicker(year,month,day);只显示年月日

                if (hasTime)
                    wheelMain.initDateTimePicker(year, month, day, hour, min);
                else
                    wheelMain.initDateTimePicker(year, month, day);


                new AlertDialog.Builder(OrderActivity.this)
                        .setTitle("选择时间")
                        .setView(timepickerview)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {



                                java.text.DateFormat df=new java.text.SimpleDateFormat("yyyy-MM-dd hh:ss");
                                java.util.Calendar c1=java.util.Calendar.getInstance();
                                java.util.Calendar c2=java.util.Calendar.getInstance();
                                try
                                {
                                    c1.setTime(df.parse(str));
                                    c2.setTime(df.parse(  wheelMain.getTime().toString()));
                                }catch(java.text.ParseException e){

                                }
                                int result=c1.compareTo(c2);
                                if(result>=0)
                                {
                                   // Toast.makeText(OrderActivity.this,txttime.getText().toString(),Toast.LENGTH_SHORT).show();
                                    Toast.makeText(OrderActivity.this,"所选时间错误",Toast.LENGTH_SHORT).show();       }
                                else
                                {
                                    Toast.makeText(OrderActivity.this,wheelMain.getTime().toString(),Toast.LENGTH_SHORT).show();
                                    txttime.setText(wheelMain.getTime());
                                }

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
        if(carInfo != null) {
            carid=carInfo.get("carid");
            if(carid==null) {
                Toast.makeText(getApplicationContext(), "请先绑定车辆！",
                        Toast.LENGTH_SHORT).show();
                Intent in = new Intent(getApplicationContext(),
                        xinxiwanshan.class);
                startActivity(in);
            }
            else
            {
                tvjiayoucheliang.setText(carid);
            }
        }
        bt_1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String oiltime3=tvshijian.getText().toString();
                String oiltype3=tvjiayouleixing.getText().toString();
                String oilcount3=tvjiayoushuliang.getText().toString();
                String oilstation3=tvjiayouzhan.getText().toString();
                if(oiltime3.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "加油时间不能为空！",
                            Toast.LENGTH_SHORT).show();
                }
                else if(oiltime3.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "加油时间不能小于当前时间！",
                            Toast.LENGTH_SHORT).show();
                }
                else if(oilstation3.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "加油站不能为空！",
                            Toast.LENGTH_SHORT).show();
                }
                else if(oiltype3.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "加油类型不能为空！",
                            Toast.LENGTH_SHORT).show();
                }
                else if(oilcount3.equals("")){
                    Toast.makeText(getApplicationContext(), "加油数量不能为空！",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    new CreateNewProduct().execute();
                }
            }

        });


    }
    class CreateNewProduct extends AsyncTask<String, String, String> {
        String carid2=tvjiayoucheliang.getText().toString().trim();
        String carid1="\""+tvjiayoucheliang.getText().toString().trim()+"\"";
        String oiltime="\""+tvshijian.getText().toString()+"\"";
        String oiltype="\""+tvjiayouleixing.getText().toString()+"\"";
        String oilcount="\""+tvjiayoushuliang.getText().toString()+"\"";
        String oilstation="\""+tvjiayouzhan.getText().toString()+"\"";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OrderActivity.this);
            pDialog.setMessage("·正在添加·····");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("carid", carid1));
            params.add(new BasicNameValuePair("oiltime", oiltime));
            params.add(new BasicNameValuePair("oiltype", oiltype));
            params.add(new BasicNameValuePair("oilcount", oilcount));
            params.add(new BasicNameValuePair("oilstation",oilstation));
            System.out.println(params);
            JSONObject json = jsonParser.makeHttpRequest(url_create_oilinfo,
                    "POST", params);
            Log.d("加油：", json.toString());
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Intent intent=new Intent(OrderActivity.this,QrActivity.class);
                    intent.putExtra("jiayoucheliang",carid1);
                    intent.putExtra("shijian",oiltime);
                    intent.putExtra("jiayouzhan",oiltype);
                    intent.putExtra("leixing",oilcount);
                    intent.putExtra("shuliang", oilstation);
                    boolean isSaveSuccess = Utils.saveCarInfo(OrderActivity.this, carid2);
                    if(isSaveSuccess) {
                        System.out.print( "汽车ID保存成功！");
                    } else {
                        System.out.print( "汽车ID保存失败！");
                    }
                    startActivity(intent);
                    finish();
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
}

