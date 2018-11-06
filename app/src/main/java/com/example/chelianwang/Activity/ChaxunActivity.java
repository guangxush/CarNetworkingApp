package com.example.chelianwang.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cheshouye.api.client.WeizhangClient;
import com.cheshouye.api.client.WeizhangIntentService;
import com.cheshouye.api.client.json.CarInfo;
import com.cheshouye.api.client.json.CityInfoJson;
import com.cheshouye.api.client.json.InputConfigJson;
import com.example.chelianwang.R;

public class ChaxunActivity extends Activity {

    private String defaultChepai = "粤"; // 粤=广东

    private TextView short_name;
    private TextView query_city;
    private View btn_cpsz;
    private Button btn_query;
    private Button btnBack;

    private EditText chepai_number;
    private EditText chejia_number;
    private EditText engine_number;


    // 行驶证图示
    private View popXSZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chaxun);
        TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText("车辆违章查询");

        // ********************************************************
        Log.d("初始化服务代码","");
        Intent weizhangIntent = new Intent(this, WeizhangIntentService.class);
        weizhangIntent.putExtra("appId",1823);// 您的appId
        weizhangIntent.putExtra("appKey", "e83783012d77fb5bf1796345a9e30605");// 您的appKey
        startService(weizhangIntent);
        // ********************************************************

        // 选择省份缩写
        query_city = (TextView) findViewById(R.id.cx_city);
        chepai_number = (EditText) findViewById(R.id.chepai_number);
        chejia_number = (EditText) findViewById(R.id.chejia_number);
        engine_number = (EditText) findViewById(R.id.engine_number);
        short_name = (TextView) findViewById(R.id.chepai_sz);
        btnBack=(Button)findViewById(R.id.btnBack);

        // ----------------------------------------------

        btn_cpsz = (View) findViewById(R.id.btn_cpsz);
        btn_query = (Button) findViewById(R.id.btn_query);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ChaxunActivity.this, gerenzhongxin.class);
                startActivity(intent);
                ChaxunActivity.this.finish();
            }
        });

        btn_cpsz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ChaxunActivity.this, ShortNameList.class);
                intent.putExtra("select_short_name", short_name.getText());
                startActivityForResult(intent, 0);
            }
        });

        query_city.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ChaxunActivity.this, ProvinceList.class);
                startActivityForResult(intent, 1);
            }
        });

        btn_query.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 获取违章信息
                CarInfo car = new CarInfo();
                String quertCityStr = null;
                String quertCityIdStr = null;

                final String shortNameStr = short_name.getText().toString()
                        .trim();
                final String chepaiNumberStr = chepai_number.getText()
                        .toString().trim();
                if (query_city.getText() != null
                        && !query_city.getText().equals("")) {
                    quertCityStr = query_city.getText().toString().trim();

                }

                if (query_city.getTag() != null
                        && !query_city.getTag().equals("")) {
                    quertCityIdStr = query_city.getTag().toString().trim();
                    car.setCity_id(Integer.parseInt(quertCityIdStr));
                }
                final String chejiaNumberStr = chejia_number.getText()
                        .toString().trim();
                final String engineNumberStr = engine_number.getText()
                        .toString().trim();

                Intent intent = new Intent();

                car.setChejia_no(chejiaNumberStr);
                car.setChepai_no(shortNameStr + chepaiNumberStr);

                car.setEngine_no(engineNumberStr);

                Bundle bundle = new Bundle();
                bundle.putSerializable("carInfo", car);
                intent.putExtras(bundle);

                boolean result = checkQueryItem(car);

                if (result) {
                    intent.setClass(ChaxunActivity.this, WeizhangResult.class);
                    startActivity(intent);

                }
            }
        });

        // 根据默认查询地城市id, 初始化查询项目
        // setQueryItem(defaultCityId, defaultCityName);
        short_name.setText(defaultChepai);

        // 显示隐藏行驶证图示
        popXSZ = (View) findViewById(R.id.popXSZ);
        popXSZ.setOnTouchListener(new popOnTouchListener());
        hideShowXSZ();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;

        switch (requestCode) {
            case 0:
                Bundle bundle = data.getExtras();
                String ShortName = bundle.getString("short_name");
                short_name.setText(ShortName);
                break;
            case 1:
                Bundle bundle1 = data.getExtras();
                String cityId = bundle1.getString("city_id");
                setQueryItem(Integer.parseInt(cityId));

                break;
        }
    }

    // 根据城市的配置设置查询项目
    private void setQueryItem(int cityId) {

        InputConfigJson cityConfig = WeizhangClient.getInputConfig(cityId);

        // 没有初始化完成的时候;
        if (cityConfig != null) {
            CityInfoJson city = WeizhangClient.getCity(cityId);

            query_city.setText(city.getCity_name());
            query_city.setTag(cityId);

            int len_chejia = cityConfig.getClassno();
            int len_engine = cityConfig.getEngineno();

            View row_chejia = (View) findViewById(R.id.row_chejia);
            View row_engine = (View) findViewById(R.id.row_engine);

            // 车架号
            if (len_chejia == 0) {
                row_chejia.setVisibility(View.GONE);
            } else {
                row_chejia.setVisibility(View.VISIBLE);
                setMaxlength(chejia_number, len_chejia);
                if (len_chejia == -1) {
                    chejia_number.setHint("请输入完整车架号");
                } else if (len_chejia > 0) {
                    chejia_number.setHint("请输入车架号后" + len_chejia + "位");
                }
            }

            // 发动机号
            if (len_engine == 0) {
                row_engine.setVisibility(View.GONE);
            } else {
                row_engine.setVisibility(View.VISIBLE);
                setMaxlength(engine_number, len_engine);
                if (len_engine == -1) {
                    engine_number.setHint("请输入完整车发动机号");
                } else if (len_engine > 0) {
                    engine_number.setHint("请输入发动机后" + len_engine + "位");
                }
            }
        }
    }

    // 提交表单检测
    private boolean checkQueryItem(CarInfo car) {
        if (car.getCity_id() == 0) {
            Toast.makeText(ChaxunActivity.this, "请选择查询地", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (car.getChepai_no().length() != 7) {
            Toast.makeText(ChaxunActivity.this, "您输入的车牌号有误", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (car.getCity_id() > 0) {
            InputConfigJson inputConfig = WeizhangClient.getInputConfig(car
                    .getCity_id());
            int engineno = inputConfig.getEngineno();
            int registno = inputConfig.getRegistno();
            int classno = inputConfig.getClassno();

            // 车架号
            if (classno > 0) {
                if (car.getChejia_no().equals("")) {
                    Toast.makeText(ChaxunActivity.this, "输入车架号不为空", Toast.LENGTH_SHORT).show();
                    return false;
                }

                if (car.getChejia_no().length() != classno) {
                    Toast.makeText(ChaxunActivity.this, "输入车架号后" + classno + "位",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else if (classno < 0) {
                if (car.getChejia_no().length() == 0) {
                    Toast.makeText(ChaxunActivity.this, "输入全部车架号",Toast.LENGTH_SHORT).show();
                    return false;
                }
            }

            //发动机
            if (engineno > 0) {
                if (car.getEngine_no().equals("")) {
                    Toast.makeText(ChaxunActivity.this, "输入发动机号不为空",Toast.LENGTH_SHORT).show();
                    return false;
                }

                if (car.getEngine_no().length() != engineno) {
                    Toast.makeText(ChaxunActivity.this,
                            "输入发动机号后" + engineno + "位", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else if (engineno < 0) {
                if (car.getEngine_no().length() == 0) {
                    Toast.makeText(ChaxunActivity.this, "输入全部发动机号",Toast.LENGTH_SHORT).show();
                    return false;
                }
            }

            // 注册证书编号
            if (registno > 0) {
                if (car.getRegister_no().equals("")) {
                    Toast.makeText(ChaxunActivity.this, "输入证书编号不为空",Toast.LENGTH_SHORT).show();
                    return false;
                }

                if (car.getRegister_no().length() != registno) {
                    Toast.makeText(ChaxunActivity.this,
                            "输入证书编号后" + registno + "位",Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else if (registno < 0) {
                if (car.getRegister_no().length() == 0) {
                    Toast.makeText(ChaxunActivity.this, "输入全部证书编号",Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            return true;
        }
        return false;

    }

    // 设置/取消最大长度限制
    private void setMaxlength(EditText et, int maxLength) {
        if (maxLength > 0) {
            et.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
                    maxLength) });
        } else { // 不限制
            et.setFilters(new InputFilter[] {});
        }
    }

    // 显示隐藏行驶证图示
    private void hideShowXSZ() {
        View btn_help1 = (View) findViewById(R.id.ico_chejia);
        View btn_help2 = (View) findViewById(R.id.ico_engine);
        Button btn_closeXSZ = (Button) findViewById(R.id.btn_closeXSZ);

        btn_help1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popXSZ.setVisibility(View.VISIBLE);
            }
        });
        btn_help2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popXSZ.setVisibility(View.VISIBLE);
            }
        });
        btn_closeXSZ.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popXSZ.setVisibility(View.GONE);
            }
        });
    }

    // 避免穿透导致表单元素取得焦点
    private class popOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View arg0, MotionEvent arg1) {
            popXSZ.setVisibility(View.GONE);
            return true;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent in = new Intent(ChaxunActivity.this,
                    gerenzhongxin.class);
            //in.putExtra(TAG_carid, carid);
            startActivity(in);
            ChaxunActivity.this.finish();
        }  return false;
    }
}
