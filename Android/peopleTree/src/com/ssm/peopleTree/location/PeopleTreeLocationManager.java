package com.ssm.peopleTree.location;

import com.android.volley.toolbox.Volley;
import com.ssm.peopleTree.group.GroupManager;
import com.ssm.peopleTree.network.NetworkManager;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.TextView;

public class PeopleTreeLocationManager  {


	private LocationMeasurer insideLocationMeasurer = null;
	private LocationMeasurer outsideLocationMeasurer = null;
	static private PeopleTreeLocationManager instance = null;


	long distanceForUpdate = 0;
	long timeForUpdate = 1000*10;
	
	
	long lastChangeTime =0;
	int dCount;
	
	
	Context context;
	private PeopleTreeLocationManager(){
		

		
	}
	public static PeopleTreeLocationManager getInstance() {
		if (null == instance) {
			instance = new PeopleTreeLocationManager();
		}
		
		return instance;
	}
	
	public void initialize(Context _context) {
    	if (context == null) {
    		context = _context;
    		
    		insideLocationMeasurer = new InsideLocationListener(_context);
    		//outsideLocationMeasurer = new OutsideLocationListener(_context);
    	
    		
    	}
    }

	public void startLocationMeasure(){
		this.insideLocationMeasurer.startRequest(distanceForUpdate, timeForUpdate);
	
	}
	public void stopLocationMeasure(){
		this.insideLocationMeasurer.stopRequest();
	}
	public void changeLocationMeasureMode(){
	
	}
	public void resetChangeTime(){			
		lastChangeTime = System.currentTimeMillis();

	}
	
	
	public long getLastChangeTime(){
		
		return lastChangeTime;
	}
	public static final long MINTIMEINTERVAL = 1000*15;
	

}
