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
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

public class ManageSelectDialog extends Dialog implements View.OnClickListener {

	private MapManager mapManager;
	private Context context;
	
	private AlertDialog alertDialog;
	
	public ManageSelectDialog(Context context) {
		super(context);

		this.context = context;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("경고")
			.setMessage("위치 관리를 해제하시겠습니까?")
			.setCancelable(true)
			.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			})
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					MapManager mapManager = MapManager.getInstance();
					mapManager.initSetting();
					mapManager.setTempManageMode(ManageMode.NOTHING);
					mapManager.finishAllSetting();
					dialog.cancel();
				}
			});
		alertDialog = builder.create();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.menu_manage_layout);

		mapManager = MapManager.getInstance();

		Button btn = (Button) findViewById(R.id.btn_close);
		if (btn != null) {
			btn.setOnClickListener(this);
		}

		RelativeLayout layout = (RelativeLayout)findViewById(R.id.nothing_layout);
		layout.setOnClickListener(this);
		layout = (RelativeLayout)findViewById(R.id.tracking_layout);
		layout.setOnClickListener(this);
		layout = (RelativeLayout)findViewById(R.id.area_layout);
		layout.setOnClickListener(this);
		layout = (RelativeLayout)findViewById(R.id.geofence_layout);
		layout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_close:
			dismiss();
			break;
		case R.id.nothing_layout:
			alertDialog.show();
			//goMapActivity(ManageMode.NOTHING);
			break;
		case R.id.tracking_layout:

			if (((TestActivity) context).chkGpsService())
				goMapActivity(ManageMode.TRACKING);
			break;
		case R.id.area_layout:
			goMapActivity(ManageMode.AREA);
			break;
		case R.id.geofence_layout:
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
