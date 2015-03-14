package com.ssm.peopleTree.location;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.group.GroupManager;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.protocol.CheckMemberRequest;
import com.ssm.peopleTree.network.protocol.CheckMemberResponse;
import com.ssm.peopleTree.device.DeviceStatus;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.TextView;

public class PeopleTreeLocationManager  {

	
	static private PeopleTreeLocationManager instance = null;

	public static PeopleTreeLocationManager getInstance() {
		if (null == instance) {
			instance = new PeopleTreeLocationManager();
		}
		
		return instance;
	}
	
	
	private InsideLocationListener insideLocationMeasurer = null;
	private OutsideLocationListener outsideLocationMeasurer = null;
	Context context;
	public void initialize(Context _context) {
    	if (context == null) {
    		context = _context;
    		
    		insideLocationMeasurer = new InsideLocationListener(_context);
    		outsideLocationMeasurer = new OutsideLocationListener(_context);
    	
    		
    	}
    }

	
	
	long distanceForUpdate = 0;
	long timeForUpdate = 1000*10;
	
	boolean isRun = false;
	Timer jobScheduler;
	private Listener<JSONObject> onCheckMemberResponse;
	private PeopleTreeLocationManager(){

		onCheckMemberResponse = new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {

				CheckMemberResponse res = new CheckMemberResponse(arg0);
				com.ssm.peopleTree.network.Status status = res.getStatus();
	
				if (res.getStatus() == com.ssm.peopleTree.network.Status.SUCCESS) {
					Log.i("log", "locTest - onCheckMemberResponse -success");
				

				} else {
					Log.i("log", "locTest - onCheckMemberResponse -fail");

				}

	
			}
		};

		
	}

	


	public synchronized void startLocationMeasure(){
		if(!isRun){
			isRun = true;
			this.insideLocationMeasurer.startRequest(distanceForUpdate, timeForUpdate);
			this.outsideLocationMeasurer.startRequest(distanceForUpdate, timeForUpdate);
			jobScheduler = new Timer();
			jobScheduler.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					long curTime  = System.currentTimeMillis();
					int groupMemeberId = MyManager.getInstance().getGroupMemberId();
					int edgyType = MyManager.getInstance().getEdgeType();
					int parentGroupMemberId =  MyManager.getInstance().getParentGroupMemberId();
					int parentManageMode;
					if(MyManager.getInstance().getMyParentData() == null){
						parentManageMode = 0;
					}else{
						 parentManageMode = MyManager.getInstance().getMyParentData().manageMode;
					}
					
					int statusCode;
					DeviceStatus.clear(DeviceStatus.INVALID);
					statusCode= DeviceStatus.getStatus();
					
					int fpId = 0;
					double latitude = 0 ;
					double longtitude = 0 ;
					CheckMemberRequest cmr ; 
					if(insideLocationMeasurer.isValidLocation()){
			
						
						fpId = insideLocationMeasurer.curFpLocInfo.getLocationNumber();		
						latitude = insideLocationMeasurer.nearReferPoint.getX();
						longtitude = insideLocationMeasurer.nearReferPoint.getY();
						
						Log.i("log", "locTest - inside -notifyUpdate ["+latitude+"]["+longtitude+"]");
						
						
						
						
					}else if( outsideLocationMeasurer.isValidLocation()){

						fpId = 0;

						latitude = outsideLocationMeasurer.location.getLatitude();
						longtitude = outsideLocationMeasurer.location.getLongitude();


						Log.i("log", "locTest - outside -notifyUpdate ["+latitude+"]["+longtitude+"]");
						
						
					}else{
						Log.i("log", "locTest - nothing -notifyUpdate");
						DeviceStatus.set(DeviceStatus.INVALID);
						statusCode = DeviceStatus.getStatus();
					}
					
					cmr = new CheckMemberRequest(groupMemeberId, parentGroupMemberId,
							parentManageMode, edgyType, statusCode, fpId, latitude,
							longtitude);
					NetworkManager.getInstance().request(cmr, onCheckMemberResponse,
							null);
					Log.i("log", "locTest -checkid  " + groupMemeberId + " st" +  statusCode);
					
				}
				
			}, 0, timeForUpdate);

			
		}
		
	}
	public synchronized void stopLocationMeasure(){
		this.insideLocationMeasurer.stopRequest();
		this.outsideLocationMeasurer.stopRequest();
		jobScheduler.cancel();
		isRun = false;
	}

}
