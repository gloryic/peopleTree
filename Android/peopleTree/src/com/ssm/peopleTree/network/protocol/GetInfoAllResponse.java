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
	
}
