package com.ssm.peopleTree.network.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ssm.peopleTree.data.MemberData;

public class GetInfoAllResponse extends Response {
	// 사용자 정보 가져오기 프로토콜 결과

	public MemberData parentData;
	public MemberData curData;
	public ArrayList<MemberData> children;
	
	public GetInfoAllResponse(JSONObject jsonObj) {
		super(jsonObj);
	}
	
	@Override
	protected void OnInit() {
		children = new ArrayList<MemberData>();
	}
	
	@Override
	protected void OnSuccess(Object responseData) {
		
		try {
			JSONObject jsonObj = (JSONObject)responseData;
			parentData = parseInfo(jsonObj.getJSONObject(PARENT_INFO_KEY));
			curData = parseInfo(jsonObj.getJSONObject(CUR_INFO_KEY));
			
			JSONArray jsonArr = jsonObj.getJSONArray(CHILDREN_INFO_KEY);
			for (int i = 0; !jsonArr.isNull(i); i++) {
				children.add(parseInfo(jsonArr.getJSONObject(i)));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			parentData = null;
			curData = null;
			children.clear();
		}
	}
	

	private static MemberData parse(JSONObject jsonObj) throws JSONException {
		MemberData mData = new MemberData();
		
		mData.userId = jsonObj.getString(USER_ID_KEY);
		mData.userName = jsonObj.getString(USER_NAME_KEY);
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
		
		return mData;
	}

}
