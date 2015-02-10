package com.ssm.peopleTree;

import com.ssm.peopleTree.map.MapManager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MapActivity extends Activity implements OnClickListener {

	private MapManager mapManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mapManager = MapManager.getInstance();
		setContentView(mapManager.getMode().getLayout());
	
		mapManager = MapManager.getInstance();
		mapManager.initSetting();
		mapManager.show(this);
		
		Button btn = (Button)findViewById(R.id.btn_cancel);
		btn.setOnClickListener(this);
		
		btn = (Button)findViewById(R.id.btn_ok);
		btn.setOnClickListener(this);
		
		btn = (Button)findViewById(R.id.btn_setting);
		if (btn != null) {
			btn.setOnClickListener(this);
		}
		
		btn = (Button)findViewById(R.id.btn_center);
		if (btn != null) {
			btn.setOnClickListener(this);
		}
		
		btn = (Button)findViewById(R.id.btn_start);
		if (btn != null) {
			btn.setOnClickListener(this);
		}
		
		btn = (Button)findViewById(R.id.btn_finish);
		if (btn != null) {
			btn.setOnClickListener(this);
		}
		
		btn = (Button)findViewById(R.id.btn_cancel);
		if (btn != null) {
			btn.setOnClickListener(this);
		}
		
		btn = (Button)findViewById(R.id.btn_ok);
		if (btn != null) {
			btn.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btn_setting:
			setTempRadius();
			break;
		case R.id.btn_center:
			mapManager.startGeoPointSetting();
			break;
		case R.id.btn_start:
			mapManager.startGeoPointSetting();
			break;
		case R.id.btn_finish:
			mapManager.finishGeoPointSetting();
			break;
		case R.id.btn_cancel:
			finish();
			break;
		case R.id.btn_ok:
			mapManager.finishAllSetting();
			finish();
			break;
		default:
			break;
		}
	}

	public void setTempRadius() {
		EditText et = (EditText)findViewById(R.id.text_radius);
		if (et == null) {
			return;
		}
		
		try {
			int radius = Integer.parseInt(et.getText().toString());
			mapManager.setTempRadius(radius);
		} catch(Exception e) {
		}
		
		et.setText("" + mapManager.getTempRadius());
	}
	
	@Override
	public void onBackPressed() {
		if (mapManager.isGeoPointSettingStarted()) {
			mapManager.finishGeoPointSetting();
			// TODO
		}
		else {
			super.onBackPressed();
			
		}
	}
}
