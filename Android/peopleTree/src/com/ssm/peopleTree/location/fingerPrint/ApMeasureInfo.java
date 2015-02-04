package com.ssm.peopleTree.location.fingerPrint;

public class ApMeasureInfo{
	
	public static final int MINLEVEL = -90;
	String bssid;
	String ssid;	
	int level;
	public ApMeasureInfo(String bssid,String ssid,int level){
		this.bssid = bssid;
		this.ssid = ssid;
		this.level = level;
	}
	
	public String getBssid(){
		return bssid;
	}
	public String getSsid(){
		return ssid;
	}
	public int getlevel(){
		return level;
	}
	
	public void reviseLevel(int level){
		
		this.level =  (int)Math.round((this.level + level)/(double)2);
	}
	
	
	
	
}
