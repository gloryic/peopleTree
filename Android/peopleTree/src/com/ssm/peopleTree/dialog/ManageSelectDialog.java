package com.ssm.peopleTree.dialog;

import com.ssm.peopleTree.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

public class ManageSelectDialog extends Dialog {
	
	private Button closeBtn;
	private RelativeLayout nothingLayout;
	private RelativeLayout trackingLayout;
	private RelativeLayout areaLayout;
	private RelativeLayout geofenceLayout;
	
	public ManageSelectDialog(Context context) {
		super(context);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menu_manage_layout);
		
		closeBtn = (Button)findViewById(R.id.btn_close);
		nothingLayout = (RelativeLayout)findViewById(R.id.nothing_layout);
		trackingLayout = (RelativeLayout)findViewById(R.id.tracking_layout);
		areaLayout = (RelativeLayout)findViewById(R.id.area_layout);
		geofenceLayout = (RelativeLayout)findViewById(R.id.geofence_layout);
		
		/*
		((Button)findViewById(R.id.btn_close)).setOnClickListener(onClickListener);
		((RelativeLayout)findViewById(R.id.nothing_layout)).setOnClickListener(onClickListener);
		((RelativeLayout)findViewById(R.id.tracking_layout)).setOnClickListener(onClickListener);
		((RelativeLayout)findViewById(R.id.area_layout)).setOnClickListener(onClickListener);
		((RelativeLayout)findViewById(R.id.geofence_layout)).setOnClickListener(onClickListener);
		*/
	}
	
	public void setOnClickListener(View.OnClickListener onClickListener) {
		closeBtn.setOnClickListener(onClickListener);
		nothingLayout.setOnClickListener(onClickListener);
		trackingLayout.setOnClickListener(onClickListener);
		areaLayout.setOnClickListener(onClickListener);
		geofenceLayout.setOnClickListener(onClickListener);
	}
}
