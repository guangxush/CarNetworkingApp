package com.example.chelianwang.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.chelianwang.R;

public class searchActivity extends Activity implements View.OnClickListener,
        OnGetGeoCoderResultListener {
    private Button btdaohang;
    private EditText etqishi;
    private EditText etmudi;
    private Button btnqiehuan;
    private Button btnloc;
    private  LatLng locLatLng;
    GeoCoder mSearch = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        btdaohang=(Button)findViewById(R.id.btdaohang);
        etqishi=(EditText)findViewById(R.id.etqishi);
        etmudi=(EditText)findViewById(R.id.etmudi);
        btnloc=(Button)findViewById(R.id.btnloc);
        btnloc.setOnClickListener(this);
        btnqiehuan=(Button)findViewById(R.id.btnqiehuan);
        btnqiehuan.setOnClickListener(this);
        btdaohang.setOnClickListener(this);
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);

        Intent in = getIntent();
        locLatLng= new LatLng(in.getDoubleExtra("locLat", 0),
                in.getDoubleExtra("locLon",0));
        mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                .location(locLatLng));


    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.btdaohang:
                Intent intent=new Intent(this,RouteActivity.class);
                intent.putExtra("s",2);
                intent.putExtra("qishi",etqishi.getText().toString());
                intent.putExtra("mudi", etmudi.getText().toString());
                startActivity(intent);
                break;
            case R.id.btnqiehuan:
                String temp=etqishi.getText().toString();
                etqishi.setText(etmudi.getText().toString());
                etmudi.setText(temp);
                break;
            case R.id.btnloc:
                Intent in = getIntent();
                locLatLng= new LatLng(in.getDoubleExtra("locLat", 0),
                in.getDoubleExtra("locLon",0));
                mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                        .location(locLatLng));

//Toast.makeText(searchActivity.this,mSearch.toString(),Toast.LENGTH_SHORT).show();
            default:
                break;
        }


    }
     @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
         etqishi.setText(result.getAddress());
        Toast.makeText(this, result.getAddress(),
                Toast.LENGTH_LONG).show();

    }
    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {

    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mSearch.destroy();
        super.onDestroy();
    }
}
