package com.ssm.location;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;

class InsideLocationListener implements LocationMeasurer{
	private Context mContext;
	//미완성 더미 클래스
	
	static private WifiManager wifiManager = null;
	
	int x,y;
	long lastLocGetTime;
	long timeInterval = 1000*10;
	boolean isLocationRequested = false;
	Timer jobScheduler = new Timer();
	UpdateNotifier updateNotifier = null;
	private BroadcastReceiver wifiReceiver;

	InsideLocationListener() {
		wifiManager = (WifiManager) mContext
				.getSystemService(Context.WIFI_SERVICE);
		wifiManager.setWifiEnabled(true);
		updateNotifier = new InsideLocationUpdateNotifier();
		wifiReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				ScanResult scanResult;
				List apList;
				if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
					apList = wifiManager.getScanResults();
					if (wifiManager.getScanResults() != null) {
						int size = apList.size();
						for (int i = 0; i < size; i++) {
							scanResult = (ScanResult) apList.get(i);
						}
					}

				}
			}

		};

	}
	
	
	
	private void findWifiList(){
		
		
	}

	public void setLocTest(int x,int y) {
		this.x = x;
		this.y = y;
		
	}

	
	
	@Override
	public void startRequest(long distanceForUpdate, long timeForUpdate) {
		
		if (isLocationRequested == false) {
			isLocationRequested = true;
			jobScheduler.scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					
					
					
				}
				
			}, 1000, timeInterval);

		}

	}

	@Override
	public void stopRequest() {
		
		jobScheduler.cancel();
		boolean isLocationRequested = false;
	}

	@Override
	public boolean isValidLocation() {
	
		return true;
	}

	@Override
	public void setUpdateNotifier(UpdateNotifier u) {
		this.updateNotifier = u;
		
	}

	@Override
	public boolean isLocReqPossible() {
		
		return true;
	}

}
