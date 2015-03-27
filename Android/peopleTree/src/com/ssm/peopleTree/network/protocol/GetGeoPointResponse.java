package com.ssm.peopleTree.network.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ssm.peopleTree.map.GeoPoint;
import com.ssm.peopleTree.map.ManageMode;

public class GetGeoPointResponse extends Response {
	// 지역 설정 프로토콜 응답

	public String desc;
	public int manageMode;
	public int radius;
	public ArrayList<GeoPoint> points;
	
	public GetGeoPointResponse(JSONObject jsonObj) {
		super(jsonObj);
	}
	@Override
	protected void OnInit() {
		points = new ArrayList<GeoPoint>();
		super.OnInit();
	}
	
	@Override
	protected void OnSuccess(Object responseData) {
		try {
			JSONObject jsonObj = (JSONObject)responseData;
			manageMode = jsonObj.getInt(MANAGE_MODE_KEY);

			JSONArray jsonArr = jsonObj.getJSONArray(DATA_KEY);
			radius = jsonArr.getInt(0);
			int len = (jsonArr.length() - 1) / 2;
			for (int i = 1; i <= len; i++) {
				points.add(new GeoPoint(jsonArr.getDouble(i), jsonArr.getDouble(i + 1)));
			}
		} catch (Exception e) {
			e.printStackTrace();
			manageMode = ManageMode.NOTHING.getCode();
			radius = 0;
			points.clear();
		}
	}
}
