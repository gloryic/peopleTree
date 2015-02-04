package com.ssm.peopleTree.location.fingerPrint;

public class ApMeasureInfo {
	
	public static final int MINLEVEL = 10;
	
	public String bssid;
	public String ssid;
	public int level;

	public ApMeasureInfo(String bssid, String ssid, int level) {
		// TODO Auto-generated constructor stub
	}
	
	public String getBssid() {
		return bssid;
	}
}
