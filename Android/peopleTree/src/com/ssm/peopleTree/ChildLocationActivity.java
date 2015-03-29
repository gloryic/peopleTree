package com.ssm.peopleTree;

import com.ssm.peopleTree.map.ManageMode;
import com.ssm.peopleTree.map.MapManager;
import com.ssm.peopleTree.map.OnLoadFinishListener;

import net.daum.mf.map.api.MapView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ChildLocationActivity extends Activity implements OnClickListener {

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

		ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
		mapViewContainer.addView(mapView);
	
		////
		initializeMapManager();
	}
	
	private void initializeMapManager() {
		
		mapManager.loadSetting(mapView, new OnLoadFinishListener() {
			
			@Override
			public void onLoadFinish(ManageMode manageMode) {
				setBarText(manageMode);
				mapManager.showCurrentSetting(mapView);
				mapManager.showChild(mapView);
			}
		});
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
