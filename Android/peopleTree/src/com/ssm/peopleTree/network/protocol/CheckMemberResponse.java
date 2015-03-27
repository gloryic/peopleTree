package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

public class CheckMemberResponse extends Response {
	// ����̽� ���� üũ �� ��ġ ���,�˻� �������� ���� 

	public boolean validation;
	public int accumulateWarning;
	
	
	public CheckMemberResponse(JSONObject jsonObj) {
		super(jsonObj);
	}
	
	@Override
	protected void OnSuccess(Object responseData) {
		if (responseData instanceof String) {
			validation = true;
			accumulateWarning = 0;
		}
		else if (responseData instanceof JSONObject) {
			try {
				JSONObject jsonObj = (JSONObject)responseData;
				validation = jsonObj.getBoolean(VALIDATION_KEY);
				accumulateWarning = jsonObj.getInt(ACCUMULATE_WARNING_KEY);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
