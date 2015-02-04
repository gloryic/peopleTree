package com.ssm.peopleTree.location;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.widget.TextView;

public class PeopleTreeLocationManager  {


	static private LocationMeasurer lm = null;

	public static LocationMeasurer initMeasurer(Context context){
		
	
		if(lm==null){
			lm = new OutsideLocationListener(context);
		}
		return lm;
	}
	
	
	public static LocationMeasurer getMeasurer(){
		return lm;
	}
}
