package com.ssm.peopleTree;

import com.ssm.peopleTree.map.MapManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MapActivity extends Activity implements OnClickListener {

	private MapManager mapManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_childpos);
	
		mapManager = MapManager.getInstance();
		mapManager.showGroupPosition(this);
		
		Button btn = (Button)this.findViewById(R.id.btn_close);
		btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		finish();
	}
}
