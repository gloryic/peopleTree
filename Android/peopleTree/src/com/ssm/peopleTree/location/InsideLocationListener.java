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
import android.util.Log;

class InsideLocationListener implements LocationMeasurer{
	private Context mContext;

	ArrayList<ApMeasureInfo> apMeasureInfos;	
	
	ArrayList<ApMeasureInfo> curApMeasureInfos;
	ArrayList<ApMeasureInfo> backup1ApMeasureInfos;
	ArrayList<ApMeasureInfo> backup2ApMeasureInfos;
	
	
	
	ArrayList<String> bssidInfos;
	
	static private WifiManager wifiManager = null;
	
	
	
	long timeInterval = 1000*4;
	boolean isLocationRequested = false;

	Timer jobScheduler;

	private BroadcastReceiver wifiReceiver;
	
	int x = -1;
	int y = -1;
	
	
	
	long lastLocGetTime = 0;
	FingerPrintLocationInfo curFpLocInfo= null;
	ReferencePoint nearReferPoint = null;
	double referPointDistance = 0;
	
	
	private static final double VALIDDISTANCE = 100.0;
	private static final long VALIDTIME = 1000*30;
	boolean isFpValid=false;
	boolean isWifiEnabled = false;
	boolean isGetLocation = false;
	InsideLocationListener(Context context) {
		this.mContext = context;
		apMeasureInfos = new ArrayList<ApMeasureInfo>();
		bssidInfos = new ArrayList<String>();
		wifiManager = (WifiManager) mContext
				.getSystemService(Context.WIFI_SERVICE);
		

		wifiReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				List<ScanResult> apList;
				if (intent.getAction().equals(
						WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
						&& wifiManager.getScanResults() != null) {
					apList = wifiManager.getScanResults();					
					backup2ApMeasureInfos = backup1ApMeasureInfos;
					backup1ApMeasureInfos = curApMeasureInfos;
					curApMeasureInfos = new ArrayList<ApMeasureInfo>();
					bssidInfos.clear();
					apMeasureInfos.clear();
					for (ScanResult iter1 : apList) {
						if (iter1.level >= ApMeasureInfo.MINLEVEL) {
							curApMeasureInfos.add(new ApMeasureInfo(iter1.BSSID, iter1.SSID,
										iter1.level) );
							apMeasureInfos.add(new ApMeasureInfo(iter1.BSSID, iter1.SSID,
									iter1.level) );
							bssidInfos.add(iter1.BSSID);
						}

					}
					if(backup1ApMeasureInfos !=null){
						mergeApMeasureInfos(backup1ApMeasureInfos,4);
					}
					if(backup2ApMeasureInfos != null){
						mergeApMeasureInfos(backup1ApMeasureInfos,6);
					}

					
					FingerPrintManager fpmanager = FingerPrintManager
							.getInstance();
					curFpLocInfo = fpmanager.validLocInfoFind(bssidInfos);
					if (curFpLocInfo != null) {

						lastLocGetTime = System.currentTimeMillis();
						findNearReferPoint(curFpLocInfo);

						isGetLocation = true;
						isFpValid = true;

					} else {
						isFpValid = false;
					}

				}
			}
			private void mergeApMeasureInfos(ArrayList<ApMeasureInfo> apinfo1,int weight){
				for(ApMeasureInfo iter1 : apinfo1){
					boolean flag = false;
				
					for(int i=0;i<apMeasureInfos.size();i++){
						ApMeasureInfo apmi = apMeasureInfos.get(i);
						
						if(apmi.getBssid().compareTo( iter1.getBssid())== 0){
							apmi.weightedReviseLevel(iter1.getlevel(),weight);
							flag = true;
							break;
						}
						
					}
					if(!flag && ( iter1.getlevel()-(2*weight) >= -97 ) ){
						apMeasureInfos.add(new ApMeasureInfo(iter1.getBssid(), iter1.getSsid(),
								iter1.getlevel()-(2*weight)));
						bssidInfos.add(iter1.getBssid());
					}
					
				}
			}

		};
	}
	
	private ReferencePoint findNearReferPoint(FingerPrintLocationInfo fpLocInfo){
		
		
		ArrayList<ReferencePoint> apminfos =  fpLocInfo.getReferencePoints();
		
		
		double min = 9999.99;
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

		if (isLocationRequested == false) {
			isLocationRequested = true;
			this.isGetLocation = false;

			mContext.registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
			
			
			jobScheduler = new Timer();
			jobScheduler.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					Log.i("log", "locTest inside timer run");
					isWifiEnabled = wifiManager.isWifiEnabled();
					Log.i("log", "isWifiEnabled :"+isWifiEnabled);
					if(isWifiEnabled){

						wifiManager.startScan();	
					}else{
						
					}
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
		this.isGetLocation = false;
	}

	@Override
	public boolean isValidLocation() {
		long curTime = System.currentTimeMillis();
		long timediff =  curTime - this.lastLocGetTime ;
	

		if(timediff >= VALIDTIME ||  this.referPointDistance >=VALIDDISTANCE || !isFpValid){
			return false;
		}
		return true;
	}


	@Override
	public boolean isLocReqPossible() {
		return true;
	}
	public boolean isFpValid() {
		return this.isFpValid;
	}
	
	
	
	public ArrayList<ApMeasureInfo> getApMeasureInfos(){
		return this.apMeasureInfos;
	}

	@Override
	public boolean isGetLcoation() {
		return this.isGetLocation;
	}

}



