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

public class xiugaicheliangxinxi extends AppCompatActivity {
    private EditText etpinpai;
    JSONParser jParser = new JSONParser();
    private EditText etxinghao;
    private TextView etchepaihao;
    private EditText etfadongjihao;
    private EditText etlichengshu;
    private EditText etqiyouliang;
    private Button btnSave;
    private Button btnDelete;
    private ProgressDialog pDialog;
    private RadioGroup RGfaxingneng;
    private RadioButton RBfahao,RBfayichang;
    private RadioGroup RGbianxingneng;
    private RadioButton RBbianhao,RBbianyichang;
    private RadioGroup RGchexingneng;
    private RadioButton RBchehao,RBcheyichang;
    String motorperform="\""+"好"+"\"";
    String transperform="\""+"好"+"\"";
    String heanligthperform=""+"好"+"\"";
    JSONArray cars = null;
    String carid,userid;
    JSONParser jsonParser = new JSONParser();
    private static final String url_get_carinfo_detials = "http://"+ConstantApis.ip+"/carnetserver/get_carinfo_details.php";
    private static final String url_update_carinfo = "http://"+ConstantApis.ip+"/carnetserver/update_carinfo.php";
    private static final String url_delete_carinfo = "http://"+ConstantApis.ip+"/carnetserver/delete_carinfo.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_carinfos = "carinfos";
    private static final String TAG_carid = "carid";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        setContentView(R.layout.activity_xiugaicheliangxinxi);
        etpinpai=(EditText)findViewById(R.id.etpinpai);
        etxinghao=(EditText)findViewById(R.id.etxinghao);
        etchepaihao=(TextView)findViewById(R.id.etchepaihao);
        etfadongjihao=(EditText)findViewById(R.id.etfadongjihao);
        etlichengshu=(EditText)findViewById(R.id.etlichengshu);
        etqiyouliang=(EditText)findViewById(R.id.etqiyouliang);
        btnSave=(Button)findViewById(R.id.btnSave);
        btnDelete=(Button)findViewById(R.id.btnDelete);
        RGfaxingneng=(RadioGroup)this.findViewById(R.id.RGfaxingneng);
        RBfahao=(RadioButton)this.findViewById(R.id.RBfahao);
        RBfayichang=(RadioButton)this.findViewById(R.id.RBfayichang);
        RGbianxingneng=(RadioGroup)this.findViewById(R.id.RGbianxingneng);
        RBbianhao=(RadioButton)this.findViewById(R.id.RBbianhao);
        RBbianyichang=(RadioButton)this.findViewById(R.id.RBbianyichang);
        RGchexingneng=(RadioGroup)this.findViewById(R.id.RGchexingneng);
        RBchehao=(RadioButton)this.findViewById(R.id.RBchehao);
        RBcheyichang=(RadioButton)this.findViewById(R.id.RBcheyichang);
        Map<String, String> userInfo = Utils.getUserInfo(this);
        if(userInfo != null) {
            userid=userInfo.get("number");
        }
        Intent i = getIntent();
        carid = i.getStringExtra(TAG_carid);
        new GetProductDetails().execute();
        RGfaxingneng.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == RBfahao.getId()) {
                    motorperform = "\"" + "好" + "\"";
                } else {
                    motorperform = "\"" + "坏" + "\"";
                }
            }
        });
        RGbianxingneng.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == RBbianhao.getId()) {
                    transperform= "\"" + "好" + "\"";
                } else {
                    transperform = "\"" + "坏" + "\"";
                }
            }
        });
        RGchexingneng.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == RBchehao.getId()) {
                    heanligthperform = "\"" + "好" + "\"";
                } else {
                    heanligthperform = "\"" + "坏" + "\"";
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String fueleconomy2=etqiyouliang.getText().toString().trim();
                String mileage2=etlichengshu.getText().toString();
                if(fueleconomy2.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "汽油量不能为空！",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    double qcyl=Float.parseFloat(fueleconomy2);
                    if(qcyl>100||qcyl<0)
                    {
                        Toast.makeText(getApplicationContext(), "汽车油量必须在0-100之间！",
                                Toast.LENGTH_SHORT).show();
                        etqiyouliang.setText("");
                    }
                    else if(mileage2.equals(""))
                    {
                        Toast.makeText(getApplicationContext(), "里程数不能为空！",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if(Float.parseFloat(mileage2)<0||Float.parseFloat(mileage2)>60){
                        Toast.makeText(getApplicationContext(), "里程数不能超过60且非负值！",
                                Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        new SaveProductDetails().execute();
                    }

                }


//xiugaicheliangxinxi.this.finish();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Map<String, String> carInfo = Utils.getCarInfo(xiugaicheliangxinxi.this);
                if(carInfo != null) {
                    String carid2=carInfo.get("carid");
                    if(carid2.equals(carid)) {
                        boolean isSaveSuccess = Utils.saveCarInfo(xiugaicheliangxinxi.this, null);
                        if(isSaveSuccess) {
                            System.out.print( "汽车ID删除成功！");
                        } else {
                            System.out.print( "汽车ID删除失败！");
                        }
                    }
                }
                new DeleteProduct().execute();
              //  xiugaicheliangxinxi.this.finish();
            }
        });
    }
    class GetProductDetails extends AsyncTask<String, String, String> {
        String carbrand=null;
        String carlevel=null;
        String caridstr=null;
        String motortype=null;
        String mileage=null;
        String fueleconomy=null;
        String motorperform1=null;
        String transperform1=null;
        String heanligthperform1=null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(xiugaicheliangxinxi.this);
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
            Log.d("修改车辆信息：: ", json.toString());
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
                        motorperform1=carinfo.getString("motorperform");
                        transperform1=carinfo.getString("transperform");
                        heanligthperform1=carinfo.getString("heanligthperform");
                    }
                } else {
                    Intent i = new Intent(getApplicationContext(),
                            cheliangliebiao.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    xiugaicheliangxinxi.this.finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            etpinpai.setText(carbrand);
            etxinghao.setText(carlevel);
            etchepaihao.setText(carid);
            etfadongjihao.setText(motortype);
            etlichengshu.setText(mileage);
            etqiyouliang.setText(fueleconomy);
            if( motorperform1.equals("好"))
            {
                RBfahao.setChecked(true);
            }
            else
            {
                RBfayichang.setChecked(true);
            }
            if(transperform1.equals("好"))
            {
                RBbianhao.setChecked(true);
            }
            else
            {
                RBbianyichang.setChecked(true);
            }
            if(heanligthperform1.equals("好"))
            {
                RBchehao.setChecked(true);
            }
            else
            {
                RBcheyichang.setChecked(true);
            }
        }
    }
    class SaveProductDetails extends AsyncTask<String, String, String> {
        String carid="\""+etchepaihao.getText().toString()+"\"";
        String userid1="\""+userid+"\"";
        String carbrand="\""+etpinpai.getText().toString()+"\"";
        String carlogo="\""+etpinpai.getText().toString()+"\"";
        String carlevel="\""+etxinghao.getText().toString()+"\"";
        String cartype="\""+etxinghao.getText().toString()+"\"";
        String motortype="\""+etfadongjihao.getText().toString()+"\"";
        String mileage="\""+etlichengshu.getText().toString()+"\"";
        String fueleconomy="\""+etqiyouliang.getText().toString()+"\"";
        String carid3 = ((TextView)findViewById(R.id.etchepaihao)).getText()
                .toString();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(xiugaicheliangxinxi.this);
            pDialog.setMessage("");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("userid", userid1));
            params.add(new BasicNameValuePair("carid", carid));
            params.add(new BasicNameValuePair("carbrand", carbrand));
            params.add(new BasicNameValuePair("carlogo",carlogo));
            params.add(new BasicNameValuePair("carlevel",carlevel));
            params.add(new BasicNameValuePair("cartype", cartype));
            params.add(new BasicNameValuePair("motortype", motortype)) ;
            params.add(new BasicNameValuePair("mileage", mileage));
            params.add(new BasicNameValuePair("fueleconomy", fueleconomy));
            params.add(new BasicNameValuePair("motorperform",motorperform));
            params.add(new BasicNameValuePair("transperform", transperform));
            params.add(new BasicNameValuePair("heanligthperform", heanligthperform));
            JSONObject json = jsonParser.makeHttpRequest(url_update_carinfo,
                    "POST", params);
            Log.d("修改车辆信息更新车辆: ", json.toString());
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Intent in = new Intent(getApplicationContext(),
                            cheliangxinxi.class);
                    in.putExtra(TAG_carid, carid3);
                    startActivity(in);
                   xiugaicheliangxinxi.this.finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), xiugaicheliangxinxi.class);
                    startActivity(intent);
                    xiugaicheliangxinxi.this.finish();
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
    class DeleteProduct extends AsyncTask<String, String, String> {
         String carid4 = ((TextView)findViewById(R.id.etchepaihao)).getText()
        .toString();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(xiugaicheliangxinxi.this);
            pDialog.setMessage("正在删除车辆...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            String caridstr="\""+carid4+"\"";
            int success;
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("carid", caridstr));
                JSONObject json = jsonParser.makeHttpRequest(
                        url_delete_carinfo, "POST", params);
                Log.d("修改车辆信息删除车辆：", json.toString());
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Intent in = new Intent(getApplicationContext(),
                            cheliangliebiao.class);
                    startActivity(in);
                  xiugaicheliangxinxi.this.finish();
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
            Intent in = new Intent(xiugaicheliangxinxi.this,
                    cheliangliebiao.class);
            //in.putExtra(TAG_carid, carid);
            startActivity(in);
            xiugaicheliangxinxi.this.finish();
        }  return false;
    }
}
