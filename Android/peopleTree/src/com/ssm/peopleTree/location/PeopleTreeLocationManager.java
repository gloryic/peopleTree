package com.ssm.peopleTree.location;

import com.android.volley.toolbox.Volley;
import com.ssm.peopleTree.group.GroupManager;
import com.ssm.peopleTree.network.NetworkManager;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.widget.TextView;

public class PeopleTreeLocationManager  {


	private LocationMeasurer insideLocationMeasurer = null;
	private LocationMeasurer outsideLocationMeasurer = null;
	private LocationMeasurer currentLocationMeasurer = null;
	
	
	static private PeopleTreeLocationManager instance = null;

	
	static public final int NOT_INITIALIZED = 0;	
	static public final int INSIDE_MODE = 1;
	static public final int OUTSIZE_MODE = 2;
	static public final int NOT_MEASURABLE= 4;
	
	long distanceForUpdate = 0;
	long timeForUpdate = 1000*15;
	
	
	long lastChangeTime =0;
	int dCount;
	
	private int locationMeasureMode = NOT_INITIALIZED;
	
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
    		outsideLocationMeasurer = new OutsideLocationListener(_context);
    		locationMeasureMode = OUTSIZE_MODE;
    		currentLocationMeasurer = outsideLocationMeasurer;
    		
    	}
    }
	
	public int getLocationMeasureMode(){
		
		return locationMeasureMode;
	}
	
	public void startLocationMeasure(){
		
		if(currentLocationMeasurer != null){
			this.currentLocationMeasurer.startRequest(distanceForUpdate, timeForUpdate);
		}
	}
	public void stopLocationMeasure(){
		
		if(currentLocationMeasurer != null){
			this.currentLocationMeasurer.stopRequest();
			
		}
	}
	public void changeLocationMeasureMode(){
		
		if(currentLocationMeasurer != null){
			lastChangeTime = System.currentTimeMillis();
			this.currentLocationMeasurer.stopRequest();
			switch(this.locationMeasureMode){
			case INSIDE_MODE:
				this.currentLocationMeasurer = outsideLocationMeasurer;
				currentLocationMeasurer.startRequest(distanceForUpdate, timeForUpdate);

				this.locationMeasureMode = OUTSIZE_MODE;
				break;
			case OUTSIZE_MODE:
				this.currentLocationMeasurer = insideLocationMeasurer;
				currentLocationMeasurer.startRequest(distanceForUpdate, timeForUpdate);
				this.locationMeasureMode = INSIDE_MODE;
				break;
			default:
				break;
			
			}
		}
	}
	public void resetChangeTime(){			
		lastChangeTime = System.currentTimeMillis();

	}
	
	
	public long getLastChangeTime(){
		
		return lastChangeTime;
	}
	public static final long MINTIMEINTERVAL = 1000*15;
	

}
