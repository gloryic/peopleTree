package com.ssm.peopleTree;

import com.ssm.peopleTree.dialog.ManageSelectDialog;
import com.ssm.peopleTree.dialog.SetRadiusDialog;
import com.ssm.peopleTree.dialog.SimpleAlertDialog;
import com.ssm.peopleTree.group.GroupManager;
import com.ssm.peopleTree.map.ManageMode;
import com.ssm.peopleTree.map.MapManager;
import com.ssm.peopleTree.map.OnCancelSettingListener;
import com.ssm.peopleTree.map.OnFinishSettingListener;
import com.ssm.peopleTree.map.OnLoadFinishListener;
import com.ssm.peopleTree.map.OnStartSettingListener;

import net.daum.mf.map.api.MapView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MapActivity extends Activity implements OnClickListener {

	private MapManager mapManager;
	private TextView barText;
	
	private ManageSelectDialog manageSelectDialog; 
	private AlertDialog saveAlertDialog;
	private AlertDialog loadAlertDialog;
	private AlertDialog locAlertDialog;
	private AlertDialog gpsAlertDialog;
	
	private SetRadiusDialog radiusDialog;
	private SimpleAlertDialog pointDialog;
	private AlertDialog cancelDialog;
	private AlertDialog nothingDialog;
	private AlertDialog areaDialog;
	private AlertDialog geofenceDialog;
	
	private LinearLayout btnLayout;
	
	private MapView mapView;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map2);
		
		mapManager = MapManager.getInstance();
		
		////
		barText = (TextView)findViewById(R.id.bar_text);
		((Button)findViewById(R.id.btn_close)).setOnClickListener(this);	
		((ImageButton)findViewById(R.id.btn_more)).setOnClickListener(this);
		
		btnLayout = (LinearLayout)findViewById(R.id.btn_container);
	
		////
		mapView = new MapView(this);
		mapView.setDaumMapApiKey(MapManager.getMapApiKey());
		mapView.setMapViewEventListener(mapManager);
		mapView.setPOIItemEventListener(mapManager);

		ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
		mapViewContainer.addView(mapView);
	
		////
		initializeDialog();
		initializeMapManager(mapView);
	}
	
	private void initializeMapManager(MapView mapView) {
		
		mapManager.loadSetting(mapView, new OnLoadFinishListener() {
			
			@Override
			public void onLoadFinish(ManageMode manageMode) {
				setBarText(manageMode);
				
				if (manageMode == ManageMode.TRACKING) {
					if (!isOnGPS()) {
						gpsAlertDialog.show();
					}
					else if (!mapManager.isAvailableMyLocation()) {
						loadAlertDialog.show();
					}
				}
			}
		}, true);
		mapManager.setStartSettingListener(new OnStartSettingListener() {
			
			@Override
			public void onStartSetting(ManageMode manageMode) {
				setBarText(manageMode);
				
				if (manageMode == ManageMode.NOTHING || manageMode == ManageMode.INDOOR) {
					return;
				}
				else if (manageMode == ManageMode.TRACKING) {
					radiusDialog.show();
					return;
				}
				
				btnLayout.removeAllViews();
				LinearLayout.inflate(MapActivity.this, R.layout.btn_prev_next, btnLayout);
				Button btnPrev = ((Button)btnLayout.findViewById(R.id.btn_prev));
				Button btnNext = ((Button)btnLayout.findViewById(R.id.btn_next));
				final ManageMode mode = manageMode;
				btnNext.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if (mapManager.isSettingPointsValid()) {
							saveAlertDialog.show();
						}
						else if (mode == ManageMode.AREA){
							areaDialog.show();
						}
						else if (mode == ManageMode.GEOFENCE) {
							geofenceDialog.show();
						}
					}
				});
				
				switch (manageMode) {
				case AREA:
					radiusDialog.show();
					btnPrev.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// radius dialog
							radiusDialog.show();
						}
					});
					break;
				case GEOFENCE:
					pointDialog.show();
					btnPrev.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// cancel dialog
							cancelDialog.show();
						}
					});
					break;
				default:
					break;
				}
			}
		});
		mapManager.setFinishSettingLisetner(new OnFinishSettingListener() {
			
			@Override
			public void onFinishSetting() {
				btnLayout.removeAllViews();
				RelativeLayout.inflate(MapActivity.this, R.layout.btn_close, btnLayout);
				((Button)btnLayout.findViewById(R.id.btn_close)).setOnClickListener(MapActivity.this);
				GroupManager.getInstance().updateSelf();
			}
		});
		mapManager.setCancelSettingListener(new OnCancelSettingListener() {
			
			@Override
			public void onCancelSetting() {
				setBarText(mapManager.getManageMode());
				btnLayout.removeAllViews();
				RelativeLayout.inflate(MapActivity.this, R.layout.btn_close, btnLayout);
				((Button)btnLayout.findViewById(R.id.btn_close)).setOnClickListener(MapActivity.this);
			}
		});
	}
	
	private void initializeDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("확인")
			.setMessage("현재 설정한 내용을 저장하시겠습니까?")
			.setCancelable(true)
			.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			})
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					mapManager.finishSetting(mapView);
					dialog.cancel();
				}

			});
		saveAlertDialog = builder.create();
		
		builder = new AlertDialog.Builder(this);
		builder.setTitle("경고")
			.setMessage("GPS가 꺼져있어 사용자의 현재 위치를 표시할 수 없습니다. GPS를 켜주시기 바랍니다.")
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    				intent.addCategory(Intent.CATEGORY_DEFAULT);
    				startActivity(intent);
    				
					dialog.cancel();
				}

			});
		gpsAlertDialog = builder.create();
		
		builder = new AlertDialog.Builder(this);
		builder.setTitle("경고")
			.setMessage("사용자의 현재 위치 정보를 확인할 수 없어 기존 설정을 불러올 수 없습니다. 잠시 후 다시 시도해주십시오.")
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
				}

			});
		loadAlertDialog = builder.create();
		
		builder = new AlertDialog.Builder(this);
		builder.setTitle("경고")
			.setMessage("사용자의 현재 위치 정보를 확인할 수 없어 해당 모드는 사용할 수 없습니다. 잠시 후 다시 시도해주십시오.")
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
				}

			});
		locAlertDialog = builder.create();
		
		builder = new AlertDialog.Builder(this);
		builder.setTitle("경고")
			.setMessage("설정이 완료되지 않았습니다. 중심 지점을 지정해주십시오.")
			.setCancelable(true)
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
				}

			});
		areaDialog = builder.create();
		
		builder = new AlertDialog.Builder(this);
		builder.setTitle("경고")
			.setMessage("설정이 완료되지 않았습니다. 지오펜스 모드에는 최소 3개의 지점이 필요합니다.")
			.setCancelable(true)
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
				}

			});
		geofenceDialog = builder.create();
		
		manageSelectDialog = new ManageSelectDialog(this);
		manageSelectDialog.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.btn_close:
					
					
					manageSelectDialog.dismiss();
					break;
				case R.id.nothing_layout:
					nothingDialog.show();
					break;
				case R.id.tracking_layout:
					if (!isOnGPS()) {
						gpsAlertDialog.show();
					}
					else if (!mapManager.isAvailableMyLocation()) {
						locAlertDialog.show();
					}
					else {
						mapManager.startSetting(ManageMode.TRACKING, mapView);
						manageSelectDialog.dismiss();
					}
					break;
				case R.id.area_layout:
					mapManager.startSetting(ManageMode.AREA, mapView);
					manageSelectDialog.dismiss();
					break;
				case R.id.geofence_layout:
					mapManager.startSetting(ManageMode.GEOFENCE, mapView);
					manageSelectDialog.dismiss();
					break;
				case R.id.indoor_layout:
					mapManager.startSetting(ManageMode.INDOOR, mapView);
					mapManager.finishSetting(mapView);
					manageSelectDialog.dismiss();
					break;
				default:
					break;
				}
			}
		});
		
		pointDialog = new SimpleAlertDialog(this);
		pointDialog.setTitle("알림");
		pointDialog.setMessage("지도를 터치하여 지점을 설정하십시오.");
		
		builder = new AlertDialog.Builder(this);
		builder.setTitle("취소")
			.setMessage("설정을 취소하시겠습니까?")
			.setCancelable(true)
			.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			})
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mapManager.cancelSetting(mapView);
					if (radiusDialog.isShowing()) {
						radiusDialog.dismiss();
					}
					dialog.cancel();				
				}
			});
		cancelDialog = builder.create();
		
		builder = new AlertDialog.Builder(this);
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
					mapManager.startSetting(ManageMode.NOTHING, mapView);
					mapManager.finishSetting(mapView);
					dialog.cancel();
				}
			});
		nothingDialog = builder.create();
		
		radiusDialog = new SetRadiusDialog(this);
		radiusDialog.setCancelable(false);
		radiusDialog.setOnClickLisener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch(v.getId()) {
				case R.id.btn_cancel:
					// cancel
					cancelDialog.show();
					break;
				case R.id.btn_next:
					mapManager.setRadius(radiusDialog.getRadiusSetting());
					radiusDialog.dismiss();
					
					if (!mapManager.isSettingRadiusValid()) {
						radiusDialog.show();
						return;
					}
					
					switch(mapManager.getNewManageMode()) {
					case TRACKING:
						mapManager.finishSetting(mapView);
						mapManager.showCurrentSetting(mapView);
						break;
					case AREA:
						pointDialog.show();
						break;
					default:
						break;
					}
					break;
				}
			}
		});
	}
	
	public boolean isOnGPS() {
    	String gs = android.provider.Settings.Secure.getString(getContentResolver(),
    	android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
    	return gs.indexOf("gps", 0) >= 0;
    }
	
	private void setBarText(ManageMode manageMode) {
		barText.setText(manageMode.getStringId());
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_more:
			manageSelectDialog.show();
			break;
		case R.id.btn_close:
			finish();
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		if (!mapManager.isSettingMode()) {
			super.onBackPressed();
		}
	}
}
