package com.ssm.peopleTree;

import java.util.Observable;
import java.util.Observer;

import com.ssm.peopleTree.location.PeopleTreeLocationManager;
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

public class ChildLocationActivity extends Activity implements OnClickListener, Observer {

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
		
		PeopleTreeLocationManager.getInstance().addObserver(this);
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
		
	@Override
	public void finish() {
		PeopleTreeLocationManager.getInstance().deleteObserver(this);
		super.finish();
	}

	@Override
	public void update(Observable observable, Object data) {
		mapManager.updateMyLocation();
		mapManager.showCurrentSetting(mapView);
		mapManager.showChild(mapView);
	}
}
