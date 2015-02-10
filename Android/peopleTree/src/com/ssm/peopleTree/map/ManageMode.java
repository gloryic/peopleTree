package com.ssm.peopleTree.map;

import com.ssm.peopleTree.R;

public enum ManageMode {

	INVALID(100, 0),
	NOTHING(200, R.layout.activity_map),
	TRAKING(210, R.layout.activity_map_tracking),
	AREA(220, R.layout.activity_map_area),
	GEOFENCE(230, R.layout.activity_map_geofence)
	;
	
	private int code;
	private int layout;
	
	
	private ManageMode(int code, int layout) {
		this.code = code;
		this.layout = layout;
	}
	
	public int getCode() {
		return code;
	}
	
	public int getLayout() {
		return layout;
	}
	
	public static ManageMode getMode(int code) {
		for (ManageMode value : values()) {
			if (code == value.getCode()) {
				return value;
			}
		}
		
		return NOTHING;
	}
}
