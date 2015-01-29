package com.ssm.location.fingerPrint;

import java.util.ArrayList;


public class FingerPrintLocationInfo{
	int locationNumber;
	String locationName;
	double longitude;
	double latitude;
	
	
	ArrayList<String> bssiInfos;
	ArrayList<ReferencePoint> referencePoints;
	
	public FingerPrintLocationInfo(int locationNumber,String locationName,double longitude,double latitude){
		this.locationNumber = locationNumber;
		this.locationName = locationName;
		this.longitude = longitude;
		this.latitude = latitude;
		bssiInfos = new ArrayList<String>();
		referencePoints = new ArrayList<ReferencePoint>();
	}
	
	public void addBssiInfo(String bssi){
		bssiInfos.add(bssi);
	}
	
	public void addReferencePointInfo(ReferencePoint rp){
		referencePoints.add(rp);
	}
	public int  bssiMatchingCnt (ArrayList<String> bssiInfos_){
		int cnt = 0;
		for( String iter1 : bssiInfos){
			for(String iter2 : bssiInfos_){
				if(iter2.compareTo(iter1)== 0){
					cnt++;
					break;
				}
			}
		}
		return cnt;
	}

	
	
}







