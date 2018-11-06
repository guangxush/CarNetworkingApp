package com.example.chelianwang.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.chelianwang.R;
import com.example.chelianwang.adapter.PriceListAdapter;
import com.example.chelianwang.bean.Station;

/**
 * Created by 12847 on 2016/5/12.
 */
public class StationInfoActivity extends Activity implements View.OnClickListener {

    private Context mContext;
    private TextView tv_title_right, tv_name, tv_distance, tv_area, tv_addr;
    private ImageView iv_back;

    private ScrollView sv;
    private ListView lv_gast_price, lv_price;
    private Station s;
    private Button t123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        mContext = this;
        initView();
        setText();
    }

    private void initView() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_distance = (TextView) findViewById(R.id.tv_distance);
        tv_area = (TextView) findViewById(R.id.tv_area);
        tv_addr = (TextView) findViewById(R.id.tv_addr);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title_right = (TextView) findViewById(R.id.tv_title_button);
        tv_title_right.setText("导航 >");
        tv_title_right.setOnClickListener(this);
        tv_title_right.setVisibility(View.VISIBLE);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(this);
        lv_gast_price = (ListView) findViewById(R.id.lv_gast_price);

        lv_price = (ListView) findViewById(R.id.lv_price);
        sv = (ScrollView) findViewById(R.id.sv);
        t123=(Button)findViewById(R.id.t123);
        t123.setOnClickListener(this);
    }


    private void setText(){
        s = getIntent().getParcelableExtra("s");
        tv_name.setText(s.getName()+" - "+s.getBrand());
        tv_addr.setText(s.getAddr());
        tv_distance.setText(s.getDistance()+"m");
        tv_area.setText(s.getArea());
        PriceListAdapter gastPriceAdapter = new PriceListAdapter(mContext, s.getGastPriceList());
        lv_gast_price.setAdapter(gastPriceAdapter);
        PriceListAdapter priceAdapter = new PriceListAdapter(mContext, s.getPriceList());
        lv_price.setAdapter(priceAdapter);

        sv.smoothScrollTo(0, 0);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_title_button:
                Intent intent = new Intent(mContext,RouteActivity.class);
                intent.putExtra("s",1);
                intent.putExtra("lat", s.getLat());
                intent.putExtra("lon", s.getLon());
                intent.putExtra("locLat", getIntent().getDoubleExtra("locLat", 0));
                intent.putExtra("locLon", getIntent().getDoubleExtra("locLon", 0));
                startActivity(intent);
                break;
            case R.id.t123:
                Intent in = new Intent(StationInfoActivity.this,OrderActivity.class);
                in.putExtra("name",tv_name.getText());
                startActivity(in);
            default:
                break;
        }
    }

}