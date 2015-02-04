package com.ssm.peopleTree.location;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.ssm.peopleTree.location.fingerPrint.ApMeasureInfo;
import com.ssm.peopleTree.location.fingerPrint.FingerPrintLocationInfo;
import com.ssm.peopleTree.location.fingerPrint.FingerPrintManager;
import com.ssm.peopleTree.location.fingerPrint.ReferencePoint;

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
	ArrayList<ApMeasureInfo> apMeasureInfos;
	static private WifiManager wifiManager = null;
	
	
	
	long timeInterval = 1000*15;
	boolean isLocationRequested = false;

	Timer jobScheduler = new Timer();
	UpdateNotifier updateNotifier = null;
	private BroadcastReceiver wifiReceiver;
	
	int x = -1;
	int y = -1;
	
	long lastLocGetTime = 0;
	FingerPrintLocationInfo curFpLocInfo= null;
	ReferencePoint nearReferPoint = null;
	double referPointDistance;
	
	
	private static final double validDistance = 40.0;
	private static final long validTime = 1000*60*5;
	boolean isFpValid=false;
	InsideLocationListener(Context context) {
		this.mContext = context;
		apMeasureInfos = new ArrayList<ApMeasureInfo>();
		wifiManager = (WifiManager) mContext
				.getSystemService(Context.WIFI_SERVICE);
		
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
						apMeasureInfos.clear();
						ArrayList<String> bssidInfos = new ArrayList<String>();
						for (int i = 0; i < size; i++) {
							scanResult = (ScanResult) apList.get(i);
							if(scanResult.level >= ApMeasureInfo.MINLEVEL){
								apMeasureInfos.add(new ApMeasureInfo(scanResult.BSSID,scanResult.SSID,scanResult.level));
								bssidInfos.add(scanResult.BSSID);
							}

						}
						FingerPrintManager fpmanager = FingerPrintManager.getInstance();
						
						curFpLocInfo = fpmanager.validLocInfoFind(bssidInfos);
						if(curFpLocInfo  != null){
							
							lastLocGetTime = System.currentTimeMillis();
							findNearReferPoint(curFpLocInfo);
							
							
							isFpValid=true;
							
						}else{
							isFpValid=false;
						}
						if (updateNotifier != null) {

							updateNotifier.notifyUpdate(this);
						}

					}

				}
			}

		};
	}
	
	private ReferencePoint findNearReferPoint(FingerPrintLocationInfo fpLocInfo){
		
		
		ArrayList<ReferencePoint> apminfos =  fpLocInfo.getReferencePoints();
		
		
		double min = 0xffffffff;
		ReferencePoint minReferP = null;
		for(ReferencePoint iter : apminfos){
			double ret = iter.compreToApMeasureInfos(apMeasureInfos);
			
			if(ret<min){
				min = ret;
				minReferP = iter;
			}
			
		}
		this.referPointDistance = min;
		this.nearReferPoint = minReferP;
		
		
		return nearReferPoint;
	}
	
	
	@Override
	public boolean startRequest(long distanceForUpdate, long timeForUpdate) {
		boolean ret =true;
		if(!wifiManager.isWifiEnabled()){
			
			return false;
			//wifiManager.setWifiEnabled(true);
		}
		
		if (isLocationRequested == false) {
			isLocationRequested = true;
			jobScheduler.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					mContext.registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
					wifiManager.startScan();
					
					
				}
				
			}, 0, timeInterval);

		}
		
		return ret;

	}

	@Override
	public void stopRequest() {
		
		jobScheduler.cancel();
		mContext.unregisterReceiver(wifiReceiver);
		boolean isLocationRequested = false;
	}

	@Override
	public boolean isValidLocation() {
		long curTime = System.currentTimeMillis();
		long timediff = this.lastLocGetTime = curTime;
		if(timediff >= validTime || this.referPointDistance >=validDistance){
			return false;
		}
		return true;
	}

	@Override
	public void setUpdateNotifier(UpdateNotifier u) {
		this.updateNotifier = u;
		
	}

	@Override
	public boolean isLocReqPossible() {
		if(wifiManager.isWifiEnabled()){
			return true;
		}else{
			return false;
		}
	}
	public boolean isFpValid() {
		return this.isFpValid;
	}
	
	
	
	public ArrayList<ApMeasureInfo> getApMeasureInfos(){
		return this.apMeasureInfos;
	}

	@Override
	public boolean isGetLcoation() {
		// TODO Auto-generated method stub
		return false;
	}

}
