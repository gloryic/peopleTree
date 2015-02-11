package com.ssm.peopleTree.location;

import java.util.ArrayList;

import org.json.JSONObject;

import android.util.Log;

import com.android.volley.Response.Listener;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.group.GroupManager;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.protocol.CheckMemberRequest;
import com.ssm.peopleTree.network.protocol.CheckMemberResponse;


class InsideLocationUpdateNotifier implements UpdateNotifier{
	InsideLocationListener parent;
	
	
	
	private Listener<JSONObject> onCheckMemberResponse;
	InsideLocationUpdateNotifier(){

		onCheckMemberResponse = new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {

				CheckMemberResponse res = new CheckMemberResponse(arg0);
				com.ssm.peopleTree.network.Status status = res.getStatus();
	
				if (res.getStatus() == com.ssm.peopleTree.network.Status.SUCCESS) {

				

				} else {


				}

	
			}
		};
	}
	
	@Override
	public void notifyUpdate(Object arg) {
		Log.i("log", "locTest - inside -notifyUpdate");
		parent = (InsideLocationListener)arg;
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
		
		int fpId;
		if(parent.curFpLocInfo ==null)
		{
			fpId = 1;
		}else{
			fpId = parent.curFpLocInfo.getLocationNumber();
		}
		double latitude;
		double longtitude;

		CheckMemberRequest cmr ; 
		PeopleTreeLocationManager pltm = PeopleTreeLocationManager.getInstance();		


		Status.clear();

		if(!parent.isWifiEnabled){
			Status.set(Status.WIFI_OFF);
		}


		
		
		long curTime = System.currentTimeMillis();
		if( (!parent.isValidLocation() && (curTime - pltm.getLastChangeTime() ) >  PeopleTreeLocationManager.MINTIMEINTERVAL)
				|| parent.nearReferPoint == null || !parent.isWifiEnabled) {
			Log.i("log", "locTest inside loc noty inval");

			latitude = 0;
			longtitude = 0;
			Status.set(Status.INVALID);
			statusCode = Status.getStatus();

			cmr = new CheckMemberRequest(groupMemeberId, parentGroupMemberId,
					parentManageMode, edgyType, statusCode, fpId, latitude,
					longtitude);
			NetworkManager.getInstance().request(cmr, onCheckMemberResponse,
					null);

			pltm.changeLocationMeasureMode();
		} else {
			Log.i("log", "locTest inside loc noty valid");

			latitude = parent.nearReferPoint.getX();
			longtitude = parent.nearReferPoint.getY();
			statusCode = Status.getStatus();

			cmr = new CheckMemberRequest(groupMemeberId, parentGroupMemberId,
					parentManageMode, edgyType, statusCode, fpId, latitude,
					longtitude);
			NetworkManager.getInstance().request(cmr, onCheckMemberResponse,
					null);

		}
		
	}

}

	
