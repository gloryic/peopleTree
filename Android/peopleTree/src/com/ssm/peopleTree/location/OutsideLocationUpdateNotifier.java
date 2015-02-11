package com.ssm.peopleTree.location;

import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.ssm.peopleTree.C;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.protocol.CheckMemberRequest;
import com.ssm.peopleTree.network.protocol.CheckMemberResponse;

import android.util.Log;

class OutsideLocationUpdateNotifier implements UpdateNotifier, Response.Listener<JSONObject>, Response.ErrorListener {
	OutsideLocationListener parent;

	private Listener<JSONObject> onCheckMemberResponse;
	
	OutsideLocationUpdateNotifier(){

		onCheckMemberResponse = new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {

				CheckMemberResponse res = new CheckMemberResponse(arg0);
				com.ssm.peopleTree.network.Status status = res.getStatus();
				String str;
				if (res.getStatus() == com.ssm.peopleTree.network.Status.SUCCESS) {



				} else {
		
				}

	
			}
		};
	}

	@Override
	public void notifyUpdate(Object arg) {
		Log.i("log", "locTest - outside -notifyUpdate");
		
		parent = (OutsideLocationListener)arg;
		int groupMemeberId = MyManager.getInstance().getGroupMemberId();
		int parentGroupMemberId =  MyManager.getInstance().getParentGroupMemberId();
		int parentManageMode;
		
		if(MyManager.getInstance().getMyParentData() == null){
			parentManageMode = 0;
		}else{
			 parentManageMode = MyManager.getInstance().getMyParentData().manageMode;
		}
		

		int edgyType = MyManager.getInstance().getEdgeType();
		
		int statusCode;
		
		int fpId = 0;
		
		double latitude;
		double longtitude;
		
		CheckMemberRequest cmr ; 
		
		Status.clear();
		
		if(!parent.isGPSEnabled){
			Status.set(Status.GPS_OFF);	
		}
		if(parent.location == null){
			latitude = 0;
			longtitude = 0;
		}else{
			latitude = parent.location.getLatitude();
			longtitude = parent.location.getLongitude();
		}
		
		PeopleTreeLocationManager pltm = PeopleTreeLocationManager.getInstance();		
		long curTime = System.currentTimeMillis();
		if(!parent.isValidLocation() && (curTime - pltm.getLastChangeTime() ) >  PeopleTreeLocationManager.MINTIMEINTERVAL){
			Status.set(Status.INVALID);
			
			statusCode= Status.getStatus();
			cmr = new CheckMemberRequest(groupMemeberId, parentGroupMemberId, parentManageMode, edgyType, statusCode, fpId, latitude, longtitude);
			
			pltm.changeLocationMeasureMode();
			
		}else{
			Log.i("log", "locTest gps" +"lat"+latitude +" ,lon"+ longtitude);
			statusCode= Status.getStatus();
			cmr = new CheckMemberRequest(groupMemeberId, parentGroupMemberId, parentManageMode, edgyType, statusCode, fpId, latitude, longtitude);
		}
		
		
		
		
		


	}
	
	@Override
	public void onErrorResponse(VolleyError arg0) {

	}
	
	@Override
	public void onResponse(JSONObject arg0) {
		// TODO Auto-generated method stub

	}

}
