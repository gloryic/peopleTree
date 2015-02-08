package com.ssm.peopleTree.network.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ssm.peopleTree.data.MemberData;

public class SearchMemberResponse extends Response {

	public int groupMembersNumber;
	public ArrayList<MemberData> groupMembersInfo;
	
	public SearchMemberResponse(JSONObject jsonObj) {
		super(jsonObj);
	}
	
	@Override
	protected void OnInit() {
		groupMembersInfo = new ArrayList<MemberData>();
	}
	
	@Override
	protected void OnSuccess(Object responseData) {
		try {
			JSONObject jsonObj = (JSONObject)responseData;
			
			groupMembersNumber = jsonObj.getInt(GROUP_MEMBERS_NUMBER_KEY);
			
			JSONArray jsonArr = jsonObj.getJSONArray(GROUP_MEMBERS_INFO_KEY);
			for (int i = 0; !jsonArr.isNull(i); i++) {
				groupMembersInfo.add(parseInfo(jsonArr.getJSONObject(i)));
			}
		} catch (Exception e) {
			e.printStackTrace();
			groupMembersInfo.clear();
		}
	}
}
