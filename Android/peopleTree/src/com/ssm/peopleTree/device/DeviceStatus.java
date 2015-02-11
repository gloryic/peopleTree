package com.ssm.peopleTree.device;

public enum DeviceStatus {

	INVALID(2049),
	GPS_OFF(2050),
	WIFI_OFF(2052),
	BATTERY_LACK(2056),
	POWER_OFF(2064)
	;
	
	private static final int NORMAL_STATUS = 2048;
	private static int status = NORMAL_STATUS;	
	private int code;
	
	private DeviceStatus(int code) {
		this.code = code;
	}
	
	public static void clear() {
		status = NORMAL_STATUS;
	}
	
	public static void set(DeviceStatus status) {
		if (status == null) {
			return;
		}
		
		DeviceStatus.status |= status.code;
	}
	
	public static void clear(DeviceStatus status) {
		if (status == null) {
			return;
		}
		
		DeviceStatus.status &= ~status.code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static int getStatus() {
		return status;
	}

}
