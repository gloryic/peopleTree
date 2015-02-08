package com.ssm.peopleTree.network.protocol;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.network.NetworkManager;

public class GetInfoResponse extends Response {
	// 사용자 정보 가져오기 프로토콜 결과

	public MemberData mData;
	
	public GetInfoResponse(JSONObject jsonObj) {
		super(jsonObj);
	}
	
	@Override
	protected void OnInit() {
		mData = new MemberData(); 
	}
	
	@Override
	protected void OnSuccess(Object responseData) {
		try {
			JSONObject jsonObj = (JSONObject)responseData;
			
			mData.userId = jsonObj.getString(USER_ID_KEY);
			mData.userName = jsonObj.getString(USER_NAME_KEY);//NetworkManager.getDecodedStr(jsonObj.getString(USER_NAME_KEY));
			mData.userNumber = jsonObj.getInt(USER_NUMBER_KEY);
			mData.userPhoneNumber = jsonObj.getString(USER_PHONE_KEY);
			
			mData.groupMemberId = jsonObj.getInt(GROUP_MEMBER_ID_KEY);
			mData.parentGroupMemberId = jsonObj.getInt(PARENT_GROUP_MEMBER_ID_KEY);
			mData.groupId = jsonObj.getInt(GROUP_ID_KEY);
			
			mData.edgeStatus = jsonObj.getInt(EDGE_STATUS_KEY);
			
			mData.manageMode = jsonObj.getInt(MANAGE_MODE_KEY);
			mData.managedLocationRadius = jsonObj.getInt(MANAGE_LOCATION_RADIUS_KEY);
			mData.managingTotalNumber = jsonObj.getInt(MANAGING_TOTAL_NUMBER_KEY);
			mData.managingNumber = jsonObj.getInt(MANAGING_NUMBER_KEY);
			
			try {
				mData.longitude = jsonObj.getDouble(LONGITUDE_KEY);
				mData.latitude = jsonObj.getDouble(LATITUDE_KEY);
			}
			catch (Exception e) {
				mData.longitude = null;
				mData.latitude = null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
