package com.ssm.peopleTree.map;

public enum ManageMode {

	NOTHING(200),
	TRACKING(210),
	AREA(220),
	GEOFENCE(230),
	INDOOR(300)
	;
	
	private int code;
	
	private ManageMode(int code) {
		this.code = code;
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
}
