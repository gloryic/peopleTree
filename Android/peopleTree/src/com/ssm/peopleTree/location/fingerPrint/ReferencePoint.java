package com.ssm.peopleTree.location.fingerPrint;

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
	public void addApMeasureInfo(String bssid,String ssid,int level){
		
		apMeasureInfos.add(new ApMeasureInfo(bssid,ssid,level));
	}
	
	
	public int matchingBssiToLevel(String bssid){
		for(ApMeasureInfo iter : apMeasureInfos){
			if(iter.bssid.compareTo(bssid) == 0){
				return iter.level;
			}
		}
		return -1;
	}
		
	
	public double compreToApMeasureInfos(ArrayList<ApMeasureInfo> others){
		int cnt = 0;
		boolean flag;
		int sum = 0;
		for(ApMeasureInfo iter1 : apMeasureInfos){
			flag = false;
			for(ApMeasureInfo iter2 : others){
				if(iter1.getBssid().compareTo(iter2.getBssid())==0 ){
					cnt++;
					
					int diff = iter1.level - iter2.level;
					
					diff = diff *diff;
					sum +=diff;
					flag = true;
					break;
				}
			}	
			if(!flag){
				int diff = iter1.level +105; // 해당 ap가 감지안되었을때 
				diff= diff*diff;
				sum += diff;
			}
		}
		double sqrt = Math.sqrt((double)sum);
		return sqrt;
	}
	public ArrayList<ApMeasureInfo> getApMeasureInfos(){
		return apMeasureInfos;
	}
	public int getX(){
		return xPoint;
	}
	public int getY(){
		return yPoint;
	}
}





