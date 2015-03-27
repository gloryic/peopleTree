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
import android.location.Location;
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
	long timeForUpdate = 1000*5;
	
	boolean isRun = false;
	Timer jobScheduler;
	public String dbg_str1;
	public String dbg_str2;
	int dbg_cnt = 0;
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

	
	
	public Location lastGpsVal(){
		return outsideLocationMeasurer.location;
	}
	
	public boolean isInsideValid(){
		return insideLocationMeasurer.isValidLocation();
	}
	public boolean isOutsideValid(){
		boolean ret = false;
		
		if(!insideLocationMeasurer.isValidLocation() 
				&& outsideLocationMeasurer.isValidLocation()){
			
			ret =true;
		}
		return ret;
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
					
					if(groupMemeberId== 0){
						return;
					}
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
					
					dbg_str1 = "";
					dbg_str2 = "";
					
					
					
					
					if(insideLocationMeasurer.isValidLocation() ){
			
						
						fpId = insideLocationMeasurer.curFpLocInfo.getLocationNumber();		
						latitude = insideLocationMeasurer.nearReferPoint.getX();
						longtitude = insideLocationMeasurer.nearReferPoint.getY();
						
						Log.i("log", "locTest - inside -notifyUpdate ["+latitude+"]["+longtitude+"]");
						dbg_str1+="[in ] "+insideLocationMeasurer.referPointDistance +"]";
						
						
						
					}else{ 
						if( !outsideLocationMeasurer.isValidLocation()){
							DeviceStatus.set(DeviceStatus.INVALID);
							statusCode = DeviceStatus.getStatus();
							dbg_str1+="[not] ";

							fpId = 1;
						}else{

							fpId = 0;
							dbg_str1+="[out] ";
							latitude = outsideLocationMeasurer.location.getLatitude();
							longtitude = outsideLocationMeasurer.location.getLongitude();


							Log.i("log", "locTest - outside -notifyUpdate ["+latitude+"]["+longtitude+"]");
							dbg_str1+="("+ String.format("%.2f",outsideLocationMeasurer.location.getAccuracy()) +")";
							
							long td = System.currentTimeMillis() - outsideLocationMeasurer.location.getTime();
							dbg_str1+="][td:"+td+"]";
							
						}

						

						
					}
					
					
					cmr = new CheckMemberRequest(groupMemeberId, parentGroupMemberId,
							parentManageMode, edgyType, statusCode, fpId, latitude,
							longtitude);
					NetworkManager.getInstance().request(cmr, onCheckMemberResponse,
							null);
					Log.i("log", "locTest -checkid  " + groupMemeberId + " st" +  statusCode);
					dbg_str1+="[id:"+groupMemeberId+"][pid:"+parentGroupMemberId+"]\n[st:"+statusCode+"][fpid:"+fpId+"]\ncnt:"+dbg_cnt++;
					dbg_str2 = "[lat:" +latitude +"]\n[lon:"+longtitude+"]";
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
