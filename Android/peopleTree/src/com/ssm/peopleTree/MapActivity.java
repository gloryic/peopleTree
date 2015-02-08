package com.ssm.peopleTree;

import com.ssm.peopleTree.map.MapManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MapActivity extends Activity implements OnClickListener {

	private MapManager mapManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_childpos);
	
		double lat = 37.53737528;
		double lng = 127.00557633;
		
		ViewGroup mapViewContainer = (ViewGroup)findViewById(R.id.map_view);
		mapManager = new MapManager(this);
		mapViewContainer.addView(mapManager.getMapView());
		
		// mapManager.setRadius(lat, lng, 100);
		mapManager.setMe(lat, lng);
		
		Button btn = (Button)this.findViewById(R.id.btn_close);
		btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		finish();
	}
}
