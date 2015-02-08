package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

public class SetGeoPointResponse extends Response {
	// ���� ���� �������� ����

	public String desc;
	
	public SetGeoPointResponse(JSONObject jsonObj) {
		super(jsonObj);
	}	
	
	@Override
	protected void OnSuccess(Object responseData) {
		desc = (String)responseData;
		if (desc == null) {
			desc = "";
		}
	}
}
