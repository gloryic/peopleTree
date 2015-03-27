package com.ssm.peopleTree;

import com.ssm.peopleTree.dialog.ManageSelectDialog;
import com.ssm.peopleTree.dialog.SetRadiusDialog;
import com.ssm.peopleTree.dialog.SimpleAlertDialog;
import com.ssm.peopleTree.map.ManageMode;
import com.ssm.peopleTree.map.MapManager;
import com.ssm.peopleTree.map.OnFinishSettingListener;
import com.ssm.peopleTree.map.OnLoadFinishListener;
import com.ssm.peopleTree.map.OnStartSettingListener;

import net.daum.mf.map.api.MapView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChildActivity extends Activity implements OnClickListener {

	private MapManager mapManager;
	private TextView barText;

	private MapView mapView;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map3);
		
		mapManager = MapManager.getInstance();
		
		////
		barText = (TextView)findViewById(R.id.bar_text);
		((Button)findViewById(R.id.btn_close)).setOnClickListener(this);	
		
		////
		mapView = new MapView(this);
		mapView.setDaumMapApiKey(MapManager.getMapApiKey());
		mapView.setMapViewEventListener(mapManager);
		mapView.setPOIItemEventListener(mapManager);

		ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
		mapViewContainer.addView(mapView);
	
		////
		initializeMapManager(mapView);
	}
	
	private void initializeMapManager(MapView mapView) {
		
		mapManager.loadSetting(mapView, new OnLoadFinishListener() {
			
			@Override
			public void onLoadFinish(ManageMode manageMode) {
				setBarText(manageMode);
			}
		}, true);
		mapManager.loadChild(mapView);
	}
	
	private void setBarText(ManageMode manageMode) {
		barText.setText(manageMode.getStringId());
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_close:
			finish();
			break;
		}
	}
}
