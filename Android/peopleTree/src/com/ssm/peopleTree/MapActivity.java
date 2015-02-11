package com.ssm.peopleTree;

import net.daum.mf.map.api.MapView;

import com.ssm.peopleTree.map.ManageMode;
import com.ssm.peopleTree.map.MapManager;
import com.ssm.peopleTree.map.view.AreaModeMapView;
import com.ssm.peopleTree.map.view.GeofenceModeMapView;
import com.ssm.peopleTree.map.view.GroupLocationMapView;
import com.ssm.peopleTree.map.view.RadiusSettable;
import com.ssm.peopleTree.map.view.TrackingModeMapView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MapActivity extends Activity implements OnClickListener {

	private MapManager mapManager;
	
	private AlertDialog noSaveAlert;
	private AlertDialog saveAlert;
	private AlertDialog failAlert;
	
	private MapView mapView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mapManager = MapManager.getInstance();
		mapManager.initSetting();
		ManageMode mode = mapManager.getTempManageMode();
		setContentView(mode.getLayout());
		
				
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("경고")
				.setMessage("설정이 저장되지 않았습니다.\n종료하시겠습니까?")
				.setCancelable(true)
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				})
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					// 확인 버튼 클릭시 설정
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
						finish();
					}

				});
		noSaveAlert = builder.create(); // 알림창 객체 생성
		
		builder = new AlertDialog.Builder(this);
		builder.setTitle("경고")
				.setMessage("설정을 저장하시겠습니까?")
				.setCancelable(true)
				// 뒤로 버튼 클릭시 취소 가능 설정
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				})
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					// 확인 버튼 클릭시 설정
					public void onClick(DialogInterface dialog, int whichButton) {
						boolean result = mapManager.finishAllSetting();
						if (result) {
							dialog.cancel();
							finish();
						}
						else {
							dialog.cancel();
							failAlert.show();
						}
					}

				});
		saveAlert = builder.create(); // 알림창 객체 생성
		
		builder = new AlertDialog.Builder(this);
		builder.setTitle("실패")
				.setMessage("설정을 저장할 수 없습니다.")
				.setCancelable(true)
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					// 확인 버튼 클릭시 설정
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
					}
				});
		failAlert = builder.create();
		
		showMapView(mode);
	}
	
	public void showMapView(ManageMode mode) {
		//ManageMode mode = mapManager.getMode();
		ViewGroup mapViewContainer = (ViewGroup)findViewById(R.id.map_view);
				
		Button btn = (Button)findViewById(R.id.btn_ok);
		if (btn != null) {
			btn.setOnClickListener(this);
		}
		
		btn = (Button)findViewById(R.id.btn_cancel);
		if (btn != null) {
			btn.setOnClickListener(this);
		}
		
		if (mode == ManageMode.TRAKING) {
			btn = (Button)findViewById(R.id.btn_setting);
			if (btn != null) {
				btn.setOnClickListener(this);
			}
			mapView = new TrackingModeMapView(this);
			mapViewContainer.addView(mapView);
		}
		else if (mode == ManageMode.AREA) {
			btn = (Button)findViewById(R.id.btn_setting);
			if (btn != null) {
				btn.setOnClickListener(this);
			}
			
			btn = (Button)findViewById(R.id.btn_center);
			if (btn != null) {
				btn.setOnClickListener(this);
			}
			mapManager.finishGeoPointSetting();
			mapView = new AreaModeMapView(this);
			mapViewContainer.addView(mapView);
		}
		else if (mode == ManageMode.GEOFENCE) {
			btn = (Button)findViewById(R.id.btn_start);
			if (btn != null) {
				btn.setOnClickListener(this);
			}
			
			btn = (Button)findViewById(R.id.btn_finish);
			if (btn != null) {
				btn.setOnClickListener(this);
			}
			mapManager.finishGeoPointSetting();
			mapView = new GeofenceModeMapView(this);
			mapViewContainer.addView(mapView);
		}
		else {
			mapView = new GroupLocationMapView(this);
			mapViewContainer.addView(mapView);
		}
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btn_setting:
			Toast.makeText(this, "Radius 설정됨", Toast.LENGTH_SHORT);
			setTempRadius();
			break;
		case R.id.btn_center:
			Toast.makeText(this, "시작", Toast.LENGTH_SHORT);
			mapManager.startGeoPointSetting();
			break;
		case R.id.btn_start:
			Toast.makeText(this, "시작", Toast.LENGTH_SHORT);
			if (mapView != null && mapView instanceof GeofenceModeMapView) {
				((GeofenceModeMapView)mapView).clearSetting();
				mapManager.startGeoPointSetting();
			}
			break;
		case R.id.btn_finish:
			Toast.makeText(this, "완료", Toast.LENGTH_SHORT);
			mapManager.finishGeoPointSetting();
			break;
		case R.id.btn_cancel:
			noSaveAlert.show();
			break;
		case R.id.btn_ok:
			saveAlert.show();
			break;
		default:
			break;
		}
	}

	public void setTempRadius() {
		EditText et = (EditText)findViewById(R.id.text_radius);
		if (et == null || et.getText().length() == 0) {
			return;
		}
		
		int radius = 0;
		try {
			radius = Integer.parseInt(et.getText().toString());
			mapManager.setTempRadius(radius);
		} catch(Exception e) {
		}
		
		radius = mapManager.getTempRadius();
		et.setText("" + radius);
		
		if (mapView == null) {
			return;
		}
		
		if (mapView instanceof RadiusSettable) {
			((RadiusSettable)mapView).setRadius(radius);
		}
	}
	
	@Override
	public void onBackPressed() {
		noSaveAlert.show();
	}
}
