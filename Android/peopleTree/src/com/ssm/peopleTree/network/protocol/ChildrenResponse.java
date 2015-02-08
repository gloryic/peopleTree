package com.ssm.peopleTree.network.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class ChildrenResponse extends Response {
	
	public int numberOfChildren;
	public ArrayList<Integer> children;
	
	public ChildrenResponse(JSONObject jsonObj) {
		super(jsonObj);
	}
	
	@Override
	protected void OnInit() {
		children = new ArrayList<Integer>();
	}
	
	@Override
	protected void OnSuccess(Object responseData) {
		try {
			JSONObject jsonObj = (JSONObject)responseData;
			JSONArray jsonArr = jsonObj.getJSONArray(CHILDREN_KEY);
			for (int i = 0; !jsonArr.isNull(i); i++) {
				children.add(jsonArr.getInt(i));
			}
			numberOfChildren = jsonObj.getInt(NUMBER_OF_CHILDREN_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
