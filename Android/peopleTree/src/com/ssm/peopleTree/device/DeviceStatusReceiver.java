package com.ssm.peopleTree.device;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;
 
public class DeviceStatusReceiver extends BroadcastReceiver {
	
	static final String TAG = "DeviceStatusReceiver";
    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        final String PACKAGE_NAME = "com.ssm.peopleTree";
        
        if(action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
        	// wifi
            WifiManager w = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            if (w.isWifiEnabled()) {
            	DeviceStatus.clear(DeviceStatus.WIFI_OFF);
            	Log.e("device", "wifi on");
            }
            else {
            	DeviceStatus.set(DeviceStatus.WIFI_OFF);
            	Log.e("device", "wifi off");
            }
        }
        else if (action.equals(LocationManager.PROVIDERS_CHANGED_ACTION)) {
        	//gps¸¦ Ä×´ÂÁö ¾ÈÄ×´ÂÁö
    		LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
    		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
    			DeviceStatus.clear(DeviceStatus.GPS_OFF);
    			Log.e("device", "gps on");
    		}
    		else {
    			DeviceStatus.set(DeviceStatus.GPS_OFF);
    			Log.e("device", "gps off");
    		}
        }
        else if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
        	int bLevel = intent.getIntExtra("level", 0);
        	if (bLevel < 100) {
        		DeviceStatus.set(DeviceStatus.BATTERY_LACK);
        		Log.e("device", "battery lack");
        	}
        	else {
        		DeviceStatus.clear(DeviceStatus.BATTERY_LACK);
        		Log.e("device", "battery normal");
        	}
        	
        }
        else if(Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())){
        	
        	/*
        	ComponentName topActivity = mActivityManager.getRunningTasks(1).get(0).topActivity;
        	String packageName = topActivity.getPackageName();
        	String className = topActivity.getClassName();
        	Log.v(TAG, "packageName" + packageName);
        	Log.v(TAG, "className" + className);
        	*/
        	
        
        	if(PACKAGE_NAME.equals(intent.getData().getSchemeSpecificPart())){
        		
        		Toast.makeText(context, "ACTION_PACKAGE_REMOVED", Toast.LENGTH_LONG).show();
        		Log.d("_PACKAGE_OBSERVER_", "!!!!!!!");
	            Log.d("_PACKAGE_OBSERVER_", "intent : "+"!!");
	    		Log.d("_PACKAGE_OBSERVER_", "  action = " + intent.getAction()+"!!");
	    		Log.d("_PACKAGE_OBSERVER_", "  data = " + intent.getData()+"!!");
	    		
        	}
        	
        }
    }
}