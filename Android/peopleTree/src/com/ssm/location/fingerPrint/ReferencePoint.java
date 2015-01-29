package com.ssm.location.fingerPrint;

import java.util.ArrayList;

public class ReferencePoint {
	int xPoint;
	int yPoint;
	ArrayList<ApMeasureInfo> apMeasureInfos;
	
	public ReferencePoint(int xPoint,int yPoint){
		this.xPoint = xPoint;
		this.yPoint = yPoint;
		apMeasureInfos = new ArrayList<ApMeasureInfo>();
	}
	
 
	public void addApMeasureInfo(String bssi,String ssid,int level){
		
		apMeasureInfos.add(new ApMeasureInfo(bssi,ssid,level));
	}
	
	
	public int matchingBssiToLevel(String bssi){
		for(ApMeasureInfo iter : apMeasureInfos){
			if(iter.bssi.compareTo(bssi) == 0){
				return iter.level;
			}
		}
		return -1;
	}
		
}

class ApMeasureInfo{
	String bssi;
	String ssid;	
	int level;
	public ApMeasureInfo(String bssi,String ssid,int level){
		this.bssi = bssi;
		this.ssid = ssid;
		this.level = level;
	}
	
}

