package com.ssm.peopleTree.wifi;

import java.util.List;

import com.ssm.peopleTree.C;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiInfoReceiver  extends BroadcastReceiver{

	
	ScanResult scanResult;
	WifiManager wm;
	List apList;
	public WifiInfoReceiver(WifiManager wm){
		this.wm = wm;
		
		Log.i("Log","WifiInfoReceiver - WifiInfoReceiver");
	}
	
	public void startScan(){
		this.wm.startScan();
	}
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
			Log.i("Log","WifiInfoReceiver - onReceive");
			searchWifi();
		}
	}
	
	public void searchWifi() {
		apList = wm.getScanResults();
		if (wm.getScanResults() != null) {
			String str = "";
			int size = apList.size();
			for (int i = 0; i < size; i++) {
				scanResult = (ScanResult) apList.get(i);
				str	+=  "" + scanResult.SSID + " ," +scanResult.level + "\n";
				
			}
			C.wifiTextView1.setText(str);
			
			
		}
	}

}
