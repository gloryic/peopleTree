package com.ssm.peopleTree.map;

import org.json.JSONObject;

public class GeoPoint {
	private static final String LAT_KEY = "lat";
	private static final String LNG_KEY = "lng";
	
	private double lat;
	private double lng;
	
	public GeoPoint() {
		this(0, 0);
	}
	
	public GeoPoint(double lat, double lng) {
		setLat(lat);
		setLng(lng);
	}
	
	public GeoPoint(JSONObject jsonObj) {
		if (jsonObj == null) {
			setLat(0);
			setLng(0);
		}
		else {
			try {
				setLat(jsonObj.getDouble(LAT_KEY));
				setLng(jsonObj.getDouble(LNG_KEY));
			} catch (Exception e) {
				setLat(0);
				setLng(0);
			}
		}
	}
	
	public double getLat() {
		return lat;
	}
	
	public double getLng() {
		return lng;
	}
	
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(LAT_KEY, lat);
			json.put(LNG_KEY, lng);			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return json;
	}
	
	@Override
	public String toString() {
		String result = "{" + LAT_KEY + ":" + lat + ",";
		result = LNG_KEY + ":" + lng + "}";
		return result;
	}
}
