package com.ssm.peopleTree.dialog;

import com.ssm.peopleTree.Progressable;
import com.ssm.peopleTree.R;
import com.ssm.peopleTree.TestActivity;
import com.ssm.peopleTree.map.ManageMode;
import com.ssm.peopleTree.map.MapManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManageSelectDialog extends Dialog implements View.OnClickListener {

	private MapManager mapManager;
	private Context context;

	public ManageSelectDialog(Context context) {
		super(context);

		this.context = context;

		setCancelable(true);
		setTitle(R.string.manage_mode_setting);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_map_select);

		mapManager = MapManager.getInstance();

		Button btn = (Button) findViewById(R.id.btn_cancel);
		if (btn != null) {
			btn.setOnClickListener(this);
		}

		btn = (Button) findViewById(R.id.btn_nothing);
		if (btn != null) {
			btn.setOnClickListener(this);
		}
		btn = (Button) findViewById(R.id.btn_tracking);
		if (btn != null) {
			btn.setOnClickListener(this);
		}
		btn = (Button) findViewById(R.id.btn_area);
		if (btn != null) {
			btn.setOnClickListener(this);
		}
		btn = (Button) findViewById(R.id.btn_geofence);
		if (btn != null) {
			btn.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_cancel:
			dismiss();
			break;
		case R.id.btn_nothing:
			goMapActivity(ManageMode.NOTHING);
			break;
		case R.id.btn_tracking:

			if (((TestActivity) context).chkGpsService())
				goMapActivity(ManageMode.TRAKING);
			break;
		case R.id.btn_area:
			goMapActivity(ManageMode.AREA);
			break;
		case R.id.btn_geofence:
			goMapActivity(ManageMode.GEOFENCE);
			break;
		default:
			break;
		}
	}

	public void goMapActivity(ManageMode mode) {
		mapManager.setTempManageMode(mode);
		Progressable p = (Progressable) context;
		if (p != null) {
			p.progress();
		} else {

		}
	}
}
