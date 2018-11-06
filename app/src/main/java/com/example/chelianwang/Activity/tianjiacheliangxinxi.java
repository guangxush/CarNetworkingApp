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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class tianjiacheliangxinxi extends AppCompatActivity {
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private EditText etpinpai;
    private EditText etxinghao;
    private EditText etchepaihao;
    private EditText etfadongjihao;
    private EditText etlichengshu;
    private EditText etqiyouliang;
    private RadioGroup RGfaxingneng;
    private RadioButton RBfahao,RBfayichang;
    private RadioGroup RGbianxingneng;
    private RadioButton RBbianhao,RBbianyichang;
    private RadioGroup RGchexingneng;
    private RadioButton RBchehao,RBcheyichang;
    String rbbianstr="好";
    String rbfastr="好";
    String rbchestr="好";
    private Button btnqueren;
    String userid=null;
    private static String url_create_carinfo = "http://"+ConstantApis.ip+"/carnetserver/create_carinfo.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_userid = "userid";
    private int s=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        setContentView(R.layout.activity_tianjiacheliangxinxi);
        etpinpai=(EditText)findViewById(R.id.etpinpai);
        etxinghao=(EditText)findViewById(R.id.etxinghao);
        etchepaihao=(EditText)findViewById(R.id.etchepaihao);
        etfadongjihao=(EditText)findViewById(R.id.etfadongjihao);
        etlichengshu=(EditText)findViewById(R.id.etlichengshu);
        etqiyouliang=(EditText)findViewById(R.id.etqiyouliang);
        btnqueren=(Button)findViewById(R.id.btnqueren);
        RGfaxingneng=(RadioGroup)this.findViewById(R.id.RGfaxingneng);
        RBfahao=(RadioButton)this.findViewById(R.id.RBfahao);
        RBfayichang=(RadioButton)this.findViewById(R.id.RBfayichang);
        RGbianxingneng=(RadioGroup)this.findViewById(R.id.RGbianxingneng);
        RBbianhao=(RadioButton)this.findViewById(R.id.RBbianhao);
        RBbianyichang=(RadioButton)this.findViewById(R.id.RBbianyichang);
        RGchexingneng=(RadioGroup)this.findViewById(R.id.RGchexingneng);
        RBchehao=(RadioButton)this.findViewById(R.id.RBchehao);
        RBcheyichang=(RadioButton)this.findViewById(R.id.RBcheyichang);
        RGfaxingneng.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == RBfahao.getId()) {
                    rbfastr="好";
                } else {
                    rbfastr="坏";
                }
            }
        });
        RGbianxingneng.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == RBbianhao.getId()) {
                    rbbianstr="好";
                } else {
                    rbbianstr="坏";
                }
            }
        });
        RGchexingneng.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == RBchehao.getId()) {
                    rbchestr="好";
                } else {
                    rbchestr="坏";
                }
            }
        });
        Map<String, String> userInfo = Utils.getUserInfo(this);
        if(userInfo != null) {
            userid=userInfo.get("number");
        }
        Bundle bundle = this.getIntent().getExtras();

        s=bundle.getInt("s");
        if(s==1){String result = bundle.getString("result");
            String pinpai=result.substring(4,8);
            String xinghao=result.substring(9,17);
            String chepaihao=result.substring(18,26);
            String fadongjihao=result.substring(27,34);
            String lichengshu=result.substring(35,37);
            String youliang=result.substring(38,40);
            String faxinneng=result.substring(41,42);
            String bianxinneng=result.substring(43,44);
            String chexinneng=result.substring(45,46);
            etpinpai.setText(pinpai);
            etxinghao.setText(xinghao);
            etchepaihao.setText(chepaihao);
            etfadongjihao.setText(fadongjihao );
            etlichengshu.setText(lichengshu);
            etqiyouliang.setText(youliang);
            if( faxinneng.equals("好"))
            {
                RBfahao.setChecked(true);
            }
            else
            {
                RBfayichang.setChecked(true);
            }
            if(bianxinneng.equals("好"))
            {
                RBbianhao.setChecked(true);
            }
            else
            {
                RBbianyichang.setChecked(true);
            }
            if(chexinneng.equals("好"))
            {
                RBchehao.setChecked(true);
            }
            else
            {
                RBcheyichang.setChecked(true);
            }
        }

        btnqueren.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String Carid2=etchepaihao.getText().toString().trim();
                String fueleconomy2=etqiyouliang.getText().toString().trim();
                String mileage2=etlichengshu.getText().toString();
                if(fueleconomy2.equals(""))
                {

                    Toast.makeText(getApplicationContext(), "汽油量不能为空！",
                            Toast.LENGTH_SHORT).show();
                }

                else
                {
                    double qcyl = Float.parseFloat(fueleconomy2);
                    if (qcyl > 100 || qcyl < 0) {
                        Toast.makeText(getApplicationContext(), "汽车油量必须在0-100之间！",
                                Toast.LENGTH_SHORT).show();
                        etqiyouliang.setText("");
                    } else if (mileage2.equals("")) {
                        Toast.makeText(getApplicationContext(), "里程数不能为空！",
                                Toast.LENGTH_SHORT).show();
                    }else if(Carid2.equals(""))
                    {
                        Toast.makeText(getApplicationContext(), "车牌号不能为空！",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if (Float.parseFloat(mileage2)<0||Float.parseFloat(mileage2)>60){
                        Toast.makeText(getApplicationContext(), "里程数不能超过60且非负值！",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        new CreateNewProduct().execute();

                    }
                }
            }
        });

    }
    class CreateNewProduct extends AsyncTask<String, String, String> {
        String carid="\""+etchepaihao.getText().toString()+"\"";
        String Carid=etchepaihao.getText().toString().trim();
        String userid1="\""+userid+"\"";
        String carbrand="\""+etpinpai.getText().toString()+"\"";
        String carlogo="\""+etpinpai.getText().toString()+"\"";
        String carlevel="\""+etxinghao.getText().toString()+"\"";
        String cartype="\""+etxinghao.getText().toString()+"\"";
        String motortype="\""+etfadongjihao.getText().toString()+"\"";
        String mileage="\""+etlichengshu.getText().toString()+"\"";
        String fueleconomy="\""+etqiyouliang.getText().toString()+"\"";
        String motorperform="\""+rbfastr+"\"";
        String transperform="\""+rbbianstr+"\"";
        String heanligthperform="\""+rbchestr+"\"";
        int errors=0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(tianjiacheliangxinxi.this);
            pDialog.setMessage("·正在添加·····");
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
            System.out.println(params);
            JSONObject json = jsonParser.makeHttpRequest(url_create_carinfo,
                    "POST", params);
            Log.d("添加车辆信息：", json.toString());
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    errors=0;
                    boolean isSaveSuccess = Utils.saveCarInfo(tianjiacheliangxinxi.this, Carid);
                    if(isSaveSuccess) {
                        System.out.print( "汽车ID保存成功！");
                    } else {
                        System.out.print( "汽车ID保存失败！");
                    }
                    Intent i = new Intent(getApplicationContext(), cheliangliebiao.class);

                    startActivity(i);
                    finish();
                } else {
                    errors=1;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if(errors==1)
            {
                Toast.makeText(getApplicationContext(), "该车辆已经注册！",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "车联网祝您一路平安！",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent i = new Intent(tianjiacheliangxinxi.this, xinxiwanshan.class);
            i.putExtra(TAG_userid, userid);
            startActivity(i);
            tianjiacheliangxinxi.this.finish();
        }  return false;
    }
}
