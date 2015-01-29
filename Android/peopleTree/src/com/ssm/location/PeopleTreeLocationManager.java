package com.ssm.location;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.widget.TextView;

public class PeopleTreeLocationManager  {


	static private LocationMeasurer lm = null;
	static private WifiManager wifiManager = null;
	
	
	public static LocationMeasurer initMeasurer(Context context,Object wm){
		
		if(wifiManager == null ){
			wifiManager = (WifiManager)wm;
		}
		if(lm==null){
			lm = new OutsideLocationListener(context);
		}
		return lm;
	}
	
	
	public static LocationMeasurer getMeasurer(){
		return lm;
	}
	
	public static WifiManager getWifiManager(){	
		return wifiManager;
	}
}
