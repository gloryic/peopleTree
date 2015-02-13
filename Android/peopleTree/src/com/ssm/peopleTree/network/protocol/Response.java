package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import android.util.Log;

import com.ssm.peopleTree.C;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.network.Status;

public abstract class Response extends Protocol {
	
	protected static final String STATUS_KEY = "status";	
	protected static final String RESPONSE_DATA_KEY = "responseData";
	protected static final String ERROR_DESC_KEY = "errorDesc";
	
	protected static final String UNKNOWN_ERROR_DESC = "Unknown Error";
	
	private Status status;
	
	public Response(JSONObject jsonObj) {
		Object result = null;
		OnInit();
		try {		
			status = Status.getStatus(jsonObj.getInt(STATUS_KEY));
			if (status == Status.SUCCESS) {
				result = jsonObj.get(RESPONSE_DATA_KEY);
				OnSuccess(result);
			}
			else {
				result = jsonObj.get(ERROR_DESC_KEY);
				OnFail(status, result);
			}
			
			if (C.networkLogging) {
				Log.e("test-network", "response: " + result.toString());
			}
		} catch (Exception e) {
			status = Status.UNKNOWN_ERROR;
			OnFail(status, UNKNOWN_ERROR_DESC);
		}
	}
	
	protected void OnInit() {
	}
	
	protected void OnSuccess(Object responseData) {	
	}
	
	protected void OnFail(Status status, Object errorDesc) {
	}
	
	public final Status getStatus() {
		return status;
	}

	protected static MemberData parseInfo(JSONObject jsonObj) {
		if (jsonObj == null) {
			return null;
		}
		
		try {
			MemberData mData = new MemberData();
			
			mData.userId = jsonObj.getString(USER_ID_KEY);
			mData.userName = new String(jsonObj.getString(USER_NAME_KEY).getBytes("ISO-8859-1"), "UTF-8");
			mData.userPhoneNumber = jsonObj.getString(USER_PHONE_KEY);
			
			mData.groupMemberId = jsonObj.getInt(GROUP_MEMBER_ID_KEY);
			mData.parentGroupMemberId = jsonObj.getInt(PARENT_GROUP_MEMBER_ID_KEY);
			mData.groupId = jsonObj.getInt(GROUP_ID_KEY);
			
			mData.edgeStatus = jsonObj.getInt(EDGE_STATUS_KEY);
			mData.edgeType = jsonObj.getInt(EDGE_TYPE_KEY);
			
			mData.manageMode = jsonObj.getInt(MANAGE_MODE_KEY);
			mData.managedLocationRadius = jsonObj.getInt(MANAGE_LOCATION_RADIUS_KEY);
			mData.managingTotalNumber = jsonObj.getInt(MANAGING_TOTAL_NUMBER_KEY);
			mData.managingNumber = jsonObj.getInt(MANAGING_NUMBER_KEY);			
			
			mData.accumulateWarning = jsonObj.getInt(ACCUMULATE_WARNING_KEY);
			
			try {
				mData.longitude = jsonObj.getDouble(LONGITUDE_KEY);
				mData.latitude = jsonObj.getDouble(LATITUDE_KEY);
			}
			catch (Exception e) {
				mData.longitude = null;
				mData.latitude = null;
			}
			
			return mData;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
