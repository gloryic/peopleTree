package com.ssm.location.fingerPrint;

import java.util.ArrayList;


public class FingerPrintLocationInfo{
	int locationNumber;
	String locationName;
	double longitude;
	double latitude;
	
	
	ArrayList<String> bssidInfos;
	ArrayList<ReferencePoint> referencePoints;
	
	public FingerPrintLocationInfo(int locationNumber,String locationName,double longitude,double latitude){
		this.locationNumber = locationNumber;
		this.locationName = locationName;
		this.longitude = longitude;
		this.latitude = latitude;
		bssidInfos = new ArrayList<String>();
		referencePoints = new ArrayList<ReferencePoint>();
	}
	
	public void addBssiInfo(String bssid){
		bssidInfos.add(bssid);
	}
	
	public void addReferencePointInfo(ReferencePoint rp){
		referencePoints.add(rp);
	}
	public int  bssidMatchingCnt (ArrayList<String> bssidInfos_){
		int cnt = 0;
		for( String iter1 : bssidInfos){
			for(String iter2 : bssidInfos_){
				if(iter2.compareTo(iter1)== 0){
					cnt++;
					break;
				}
			}
		}
		return cnt;
	}
	public ArrayList<ReferencePoint> getReferencePoints(){
		return this.referencePoints;
	}
	
	public ArrayList<String>  getBssidInfos(){
		return this.bssidInfos;
	}
}







