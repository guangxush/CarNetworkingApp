package com.example.chelianwang.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.chelianwang.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cheliangliebiao extends ListActivity {
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> CarinfoList;
    private static String url_all_cars = "http://"+ConstantApis.ip+"/carnetserver/get_user_car.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_carinfos = "carinfos";
    private static final String TAG_userid = "userid";
    private static final String TAG_carid = "carid";
    private TextView tvmoren;
    private Button btnjia,btnfanhui;
    JSONArray cars = null;
    String userid=null;
    String carid=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheliangliebiao);
        btnjia=(Button)findViewById(R.id.btnjia);
        btnfanhui=(Button)findViewById(R.id.btnfanhui);
        tvmoren=(TextView)findViewById(R.id.tvmoren);
        CarinfoList = new ArrayList<HashMap<String, String>>();
        new LoadAllCars().execute();
        ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String carid = ((TextView) view.findViewById(R.id.carid)).getText()
                        .toString();
               Intent in = new Intent(getApplicationContext(),
                       cheliangxinxi.class);
                        in.putExtra(TAG_carid, carid);
                startActivityForResult(in, 100);
                cheliangliebiao.this.finish();
            }
        });
        Map<String, String> userInfo = Utils.getUserInfo(cheliangliebiao.this);
        if(userInfo != null) {
            userid=userInfo.get("number");
        }
        Map<String, String> carInfo = Utils.getCarInfo(cheliangliebiao.this);
        if(carInfo != null) {
            String carid1=carInfo.get("carid");
            if(carid1==null) {
                tvmoren.setText("请绑定车辆！");
            }
            else
            {
                tvmoren.setText(carid1.toString());
            }
        }
        btnjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),
                        xinxiwanshan.class);
                in.putExtra(TAG_userid, userid);
                startActivity(in);
                cheliangliebiao.this.finish();
            }
        });
        btnfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(cheliangliebiao.this,
                        gerenzhongxin.class);
                startActivity(in);
                cheliangliebiao.this.finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            new LoadAllCars().execute();
        }
    }
    class LoadAllCars extends AsyncTask<String, String, String> {
        int errors=0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(cheliangliebiao.this);
            pDialog.setMessage("加载车辆信息，请稍后...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            String userid1="\""+userid+"\"";
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("userid", userid1));
            JSONObject json = jParser.makeHttpRequest(url_all_cars, "POST", params);
            Log.d("车辆信息列表: ", json.toString());
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    cars = json.getJSONArray(TAG_carinfos);
                    for (int i = 0; i < cars.length(); i++) {
                        JSONObject c = cars.getJSONObject(i);
                        String userid = c.getString(TAG_userid);
                        String carid = c.getString(TAG_carid);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(TAG_userid, userid);
                        map.put(TAG_carid, carid);
                        CarinfoList.add(map);
                    }
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
                Intent in = new Intent(getApplicationContext(),
                        xinxiwanshan.class);
                in.putExtra(TAG_userid, userid);
                startActivity(in);
                finish();
            }
            runOnUiThread(new Runnable() {
                public void run() {
                    ListAdapter adapter = new SimpleAdapter(
                            cheliangliebiao.this, CarinfoList,
                            R.layout.list_item, new String[] { TAG_carid,
                            TAG_userid},
                            new int[] { R.id.carid, R.id.userid });
                    setListAdapter(adapter);
                }
            });
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent i = new Intent(cheliangliebiao.this, gerenzhongxin.class);
            startActivity(i);
            cheliangliebiao.this.finish();
        }  return false;
    }
}
