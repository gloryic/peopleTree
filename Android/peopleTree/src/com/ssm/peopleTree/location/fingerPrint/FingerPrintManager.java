package com.ssm.peopleTree.location.fingerPrint;

import java.util.ArrayList;


public class FingerPrintManager {
	private volatile static FingerPrintManager instance;
	private ArrayList<FingerPrintLocationInfo> LocInfos;
	
	
	public static final int MIN_AP_NUM = 3;
	
	
	
	private FingerPrintManager() {
		LocInfos = new  ArrayList<FingerPrintLocationInfo>();
	}

	public static FingerPrintManager getInstance(){
		if (null == instance){
			instance = new FingerPrintManager();
		}
		return instance;
	}
	
	
	public  void addLocInfo(FingerPrintLocationInfo locInfo){
		LocInfos.add(locInfo);
	}
	
	
	
	
	public FingerPrintLocationInfo validLocInfoFind(ArrayList<String> bssidInfos_){
		FingerPrintLocationInfo ret = null;
		int cnt;
		for(FingerPrintLocationInfo iter : LocInfos){
			cnt = iter.bssidMatchingCnt(bssidInfos_);
			if(cnt>= MIN_AP_NUM){
				ret = iter;
				break;
			}
		}
		return ret;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
