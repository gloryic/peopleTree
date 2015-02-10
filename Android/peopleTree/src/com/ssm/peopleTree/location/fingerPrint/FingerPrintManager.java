package com.ssm.peopleTree.location.fingerPrint;

import java.util.ArrayList;


public class FingerPrintManager {
	private volatile static FingerPrintManager instance;
	private ArrayList<FingerPrintLocationInfo> LocInfos;
	
	
	public static final int MIN_AP_NUM = 3;
	
	
	
	private FingerPrintManager() {
		LocInfos = new  ArrayList<FingerPrintLocationInfo>();
		hardCodeBuringPowerFunction();
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
	
	
	
	private void hardCodeBuringPowerFunction(){
		FingerPrintLocationInfo bfinfo = new FingerPrintLocationInfo(1000, "loc1", 1010,1010);
		
		ReferencePoint rp = new ReferencePoint(1310,1310);
		
		bfinfo.addBssiInfo("00:26:66:23:06:50");
		rp.addApMeasureInfo("00:26:66:23:06:50", "a1", -65);	
		bfinfo.addBssiInfo("00:26:66:1b:ec:e8");
		rp.addApMeasureInfo("00:26:66:1b:ec:e8", "a1", -61);	
		bfinfo.addBssiInfo("00:26:66:6d:bf:dc");
		rp.addApMeasureInfo("00:26:66:6d:bf:dc", "a1", -79);	
		bfinfo.addBssiInfo("00:26:66:6d:c7:24");
		rp.addApMeasureInfo("00:26:66:6d:c7:24", "a1", -78);
		bfinfo.addBssiInfo("00:26:66:6d:bf:d8");
		rp.addApMeasureInfo("00:26:66:6d:bf:d8", "a1", -70);
		bfinfo.addBssiInfo("10:c3:7b:40:ef:68");
		rp.addApMeasureInfo("10:c3:7b:40:ef:68", "a1", -82);
		bfinfo.addBssiInfo("10:c3:7b:40:ef:6c");
		rp.addApMeasureInfo("10:c3:7b:40:ef:6c", "a1", -73);
		
		bfinfo.addReferencePointInfo(rp);
		
	
		
		rp = new ReferencePoint(1310,1320);
		

		rp.addApMeasureInfo("00:26:66:23:06:50", "a1", -61);
		

		rp.addApMeasureInfo("00:26:66:1b:ec:e8", "a1", -64);	
		
		rp.addApMeasureInfo("00:26:66:6d:bf:d8", "a1", -79);	
		
		rp.addApMeasureInfo("10:c3:7b:40:ef:6c", "a1", -76);
		
		rp.addApMeasureInfo("00:26:66:6d:c7:24", "a1", -78);
		
		rp.addApMeasureInfo("10:c3:7b:40:ef:68", "a1", -79);
	
		rp.addApMeasureInfo("00:26:66:6d:bf:dc", "a1", -77);
		
		bfinfo.addReferencePointInfo(rp);
		
		rp = new ReferencePoint(1310,1330);
		
		
		rp.addApMeasureInfo("00:26:66:23:06:50", "a1", -61);
		
	
		rp.addApMeasureInfo("00:26:66:1b:ec:e8", "a1", -67);
		
	
		rp.addApMeasureInfo("00:26:66:6d:bf:d8", "a1", -77);
		
		
		rp.addApMeasureInfo("10:c3:7b:40:ef:68", "a1", -79);

		rp.addApMeasureInfo("10:c3:7b:40:ef:6c", "a1", -74);
		rp.addApMeasureInfo("00:26:66:6d:c7:24", "a1", -79);
		rp.addApMeasureInfo("00:26:66:6d:bf:dc", "a1", -78);
		
		bfinfo.addReferencePointInfo(rp);
		
	
		LocInfos.add(bfinfo);
	}
	
	
	
	
	
	
	
	
	
	
	
}
