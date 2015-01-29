package com.ssm.location.fingerPrint;

import java.util.ArrayList;


public class FingerPrintManager {
	private volatile static FingerPrintManager instance;
	private static ArrayList<FingerPrintLocationInfo> LocInfos;
	
	
	public static final int MIN_AP_NUM = 5;
	
	
	
	private FingerPrintManager() {
		instance = null;
	}

	public static FingerPrintManager getInstance(){
		if (null == instance){
			instance = new FingerPrintManager();
		}
		return instance;
	}
	
	
	public static void addLocInfo(FingerPrintLocationInfo locInfo){
		LocInfos.add(locInfo);
	}
	
	
	
	
	public static FingerPrintLocationInfo validLocInfoFind(ArrayList<String> bssiInfos_){
		FingerPrintLocationInfo ret = null;
		int cnt;
		for(FingerPrintLocationInfo iter : LocInfos){
			cnt = iter.bssiMatchingCnt(bssiInfos_);
			if(cnt>= MIN_AP_NUM){
				ret = iter;
				break;
			}
		}
		return ret;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
