package com.example.chelianwang.Activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cheshouye.api.client.WeizhangClient;
import com.cheshouye.api.client.json.CityInfoJson;
import com.example.chelianwang.R;
import com.example.chelianwang.adapter.ListAdapter;
import com.example.chelianwang.model.ListModel;


public class CityList extends Activity {
	private ListView lv_list;
	private ListAdapter mAdapter;
	private String provinceName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.csy_activity_citys);
		TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
		txtTitle.setText("选择查询地-城市");
		Button btnBack = (Button) findViewById(R.id.btnBack);
		btnBack.setVisibility(View.VISIBLE);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		Bundle bundle = getIntent().getExtras();
		provinceName = bundle.getString("province_name");
		final String provinceId = bundle.getString("province_id");
		lv_list = (ListView) findViewById(R.id.lv_1ist);
		mAdapter = new ListAdapter(this, getData(provinceId));
		lv_list.setAdapter(mAdapter);
		lv_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView txt_name = (TextView) view.findViewById(R.id.txt_name);
				Intent intent = new Intent();
				intent.putExtra("city_name", txt_name.getText());
				intent.putExtra("city_id",
						txt_name.getTag().toString());
				setResult(20, intent);
				finish();
			}
		});
	}


	private List<ListModel> getData(String provinceId) {
		List<ListModel> list = new ArrayList<ListModel>();

		List<CityInfoJson> cityList = WeizhangClient.getCitys(Integer
				.parseInt(provinceId));
		TextView txtListTip = (TextView) findViewById(R.id.list_tip);
		txtListTip.setText(provinceName + "已开通"+cityList.size()+"个城市, 其它城市将陆续开放");
		for (CityInfoJson cityInfoJson : cityList) {
			String cityName = cityInfoJson.getCity_name();
			int cityId = cityInfoJson.getCity_id();
			ListModel model = new ListModel();
			model.setNameId(cityId);
			model.setTextName(cityName);
			list.add(model);
		}

		return list;
	}
	
}
