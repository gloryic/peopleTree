package com.ssm.peopleTree;

import net.daum.mf.map.api.MapView;

import com.ssm.peopleTree.map.ManageMode;
import com.ssm.peopleTree.map.MapManager;
import com.ssm.peopleTree.map.OnCancelSettingListener;
import com.ssm.peopleTree.map.OnFinishSettingListener;
import com.ssm.peopleTree.map.OnLoadFinishListener;
import com.ssm.peopleTree.map.OnStartSettingListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ParentLocationActivity extends Activity implements OnClickListener {

	private MapManager mapManager;
	private TextView barText;
	
	private MapView mapView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_map3);
		
		mapManager = MapManager.getInstance();
		
		Button btn = (Button)findViewById(R.id.btn_close);
		btn.setOnClickListener(this);
		
		////
		barText = (TextView)findViewById(R.id.bar_text);
		((Button)findViewById(R.id.btn_close)).setOnClickListener(this);	
	
		////
		mapView = new MapView(this);
		mapView.setDaumMapApiKey(MapManager.getMapApiKey());

		ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
		mapViewContainer.addView(mapView);
		
		//
		initializeMapManager();
	}
	
	private void initializeMapManager() {
		
		mapManager.updateMyLocation();
		mapManager.loadParentSetting(mapView, new OnLoadFinishListener() {
			
			@Override
			public void onLoadFinish(ManageMode manageMode) {
				setBarText(manageMode);
				mapManager.showParentSetting(mapView);
			}
		});
	}
	
	private void setBarText(ManageMode manageMode) {
		barText.setText(manageMode.getStringId());
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_close:
			finish();
			break;

		default:
			break;
		}
	}
}