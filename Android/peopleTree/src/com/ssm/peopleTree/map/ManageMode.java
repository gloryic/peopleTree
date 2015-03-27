package com.ssm.peopleTree.map;

import com.ssm.peopleTree.R;

public enum ManageMode {

	NOTHING(200, R.string.menu_manage_not_set),
	TRACKING(210, R.string.menu_manage_tracking),
	AREA(220, R.string.menu_manage_area),
	GEOFENCE(230, R.string.menu_manage_geofence),
	INDOOR(300, R.string.empty)
	;
	
	public static final String KEY = "ManageMode";
	private int code;
	private int stringId;
	
	private ManageMode(int code, int stringId) {
		this.code = code;
		this.stringId = stringId;
	}
	
	public static ManageMode getMode(int code) {
		for (ManageMode mode : values()) {
			if (mode.code == code)
				return mode;
		}
		
		return NOTHING;
	}
	
	public int getCode() {
		return code;
	}
	
	public int getStringId() {
		return stringId;
	}
}
