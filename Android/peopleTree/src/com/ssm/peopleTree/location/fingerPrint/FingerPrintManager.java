package com.ssm.peopleTree.location.fingerPrint;

import java.util.ArrayList;


public class FingerPrintManager {
	private volatile static FingerPrintManager instance;
	private ArrayList<FingerPrintLocationInfo> LocInfos;
	
	
	public static final int MIN_AP_NUM = 5;
	
	
	
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
		FingerPrintLocationInfo bfinfo = new FingerPrintLocationInfo(1000,
				"loc1", 1010, 1010);

		ReferencePoint rp = new ReferencePoint(500, 500);

		bfinfo.addBssiInfo("54:a0:50:d8:1b:18");
		rp.addApMeasureInfo("54:a0:50:d8:1b:18", "a1", -40);

		bfinfo.addBssiInfo("64:e5:99:da:b3:e4");
		rp.addApMeasureInfo("64:e5:99:da:b3:e4", "a1", -63);

		bfinfo.addBssiInfo("54:a0:50:d8:1b:1c");
		rp.addApMeasureInfo("54:a0:50:d8:1b:1c", "a1", -46);

		bfinfo.addBssiInfo("64:e5:99:da:b3:e0");
		rp.addApMeasureInfo("64:e5:99:da:b3:e0", "a1", -64);

		bfinfo.addBssiInfo("64:e5:99:62:6f:24");
		rp.addApMeasureInfo("64:e5:99:62:6f:24", "a1", -51);

		bfinfo.addBssiInfo("64:e5:99:da:b8:38");
		rp.addApMeasureInfo("64:e5:99:da:b8:38", "a1", -71);

		bfinfo.addBssiInfo("00:26:66:1b:e8:ac");
		rp.addApMeasureInfo("00:26:66:1b:e8:ac", "a1", -65);

		bfinfo.addBssiInfo("00:8e:f2:60:69:a0");
		rp.addApMeasureInfo("00:8e:f2:60:69:a0", "a1", -72);

		bfinfo.addBssiInfo("00:8e:f2:60:69:a0");
		rp.addApMeasureInfo("00:8e:f2:60:69:a0", "a1", -72);

		bfinfo.addBssiInfo("64:e5:99:62:6f:20");
		rp.addApMeasureInfo("64:e5:99:62:6f:20", "a1", -57);

		bfinfo.addBssiInfo("64:e5:99:da:b8:3c");
		rp.addApMeasureInfo("64:e5:99:da:b8:3c", "a1", -69);

		bfinfo.addReferencePointInfo(rp);

		rp = new ReferencePoint(500, 510);

		bfinfo.addBssiInfo("54:a0:50:d8:1b:18");
		rp.addApMeasureInfo("54:a0:50:d8:1b:18", "a1", -57);

		bfinfo.addBssiInfo("54:a0:50:d8:1b:1c");
		rp.addApMeasureInfo("54:a0:50:d8:1b:1c", "a1", -55);

		bfinfo.addBssiInfo("64:e5:99:da:b3:e0");
		rp.addApMeasureInfo("64:e5:99:da:b3:e0", "a1", -69);

		bfinfo.addBssiInfo("64:e5:99:62:6f:24");
		rp.addApMeasureInfo("64:e5:99:62:6f:24", "a1", -63);

		bfinfo.addBssiInfo("00:8e:f2:60:69:a0");
		rp.addApMeasureInfo("00:8e:f2:60:69:a0", "a1", -82);

		bfinfo.addBssiInfo("64:e5:99:da:b8:38");
		rp.addApMeasureInfo("64:e5:99:da:b8:38", "a1", -82);

		bfinfo.addBssiInfo("64:e5:99:da:b8:38");
		rp.addApMeasureInfo("64:e5:99:da:b8:38", "a1", -82);

		bfinfo.addBssiInfo("00:26:66:1b:e8:ac");
		rp.addApMeasureInfo("00:26:66:1b:e8:ac", "a1", -76);

		bfinfo.addBssiInfo("64:e5:99:da:b8:3c");
		rp.addApMeasureInfo("64:e5:99:da:b8:3c", "a1", -78);

		bfinfo.addBssiInfo("00:8e:f2:60:69:b0");
		rp.addApMeasureInfo("00:8e:f2:60:69:b0", "a1", -78);

		bfinfo.addBssiInfo("64:e5:99:da:b3:e4");
		rp.addApMeasureInfo("64:e5:99:da:b3:e4", "a1", -67);

		bfinfo.addReferencePointInfo(rp);

		rp = new ReferencePoint(510, 500);

		bfinfo.addBssiInfo("54:a0:50:d8:1b:18");
		rp.addApMeasureInfo("54:a0:50:d8:1b:18", "a1", -44);

		bfinfo.addBssiInfo("54:a0:50:d8:1b:1c");
		rp.addApMeasureInfo("54:a0:50:d8:1b:1c", "a1", -48);

		bfinfo.addBssiInfo("64:e5:99:da:b3:e0");
		rp.addApMeasureInfo("64:e5:99:da:b3:e0", "a1", -65);

		bfinfo.addBssiInfo("64:e5:99:62:6f:24");
		rp.addApMeasureInfo("64:e5:99:62:6f:24", "a1", -53);

		bfinfo.addBssiInfo("00:26:66:1b:e8:ac");
		rp.addApMeasureInfo("00:26:66:1b:e8:ac", "a1", -72);

		bfinfo.addBssiInfo("00:8e:f2:60:69:a0");
		rp.addApMeasureInfo("00:8e:f2:60:69:a0", "a1", -72);

		bfinfo.addBssiInfo("64:e5:99:da:b8:3c");
		rp.addApMeasureInfo("64:e5:99:da:b8:3c", "a1", -65);

		bfinfo.addBssiInfo("64:e5:99:62:6f:20");
		rp.addApMeasureInfo("64:e5:99:62:6f:20", "a1", -58);

		bfinfo.addBssiInfo("64:e5:99:da:b3:e4");
		rp.addApMeasureInfo("64:e5:99:da:b3:e4", "a1", -64);

		bfinfo.addBssiInfo("00:8e:f2:60:69:b0");
		rp.addApMeasureInfo("00:8e:f2:60:69:b0", "a1", -73);

		bfinfo.addReferencePointInfo(rp);

		rp = new ReferencePoint(510, 510);

		bfinfo.addBssiInfo("54:a0:50:d8:1b:18");
		rp.addApMeasureInfo("54:a0:50:d8:1b:18", "a1", -51);

		bfinfo.addBssiInfo("54:a0:50:d8:1b:1c");
		rp.addApMeasureInfo("54:a0:50:d8:1b:1c", "a1", -54);

		bfinfo.addBssiInfo("64:e5:99:da:b3:e0");
		rp.addApMeasureInfo("64:e5:99:da:b3:e0", "a1", -68);

		bfinfo.addBssiInfo("64:e5:99:62:6f:24");
		rp.addApMeasureInfo("64:e5:99:62:6f:24", "a1", -58);

		bfinfo.addBssiInfo("00:8e:f2:60:69:a0");
		rp.addApMeasureInfo("00:8e:f2:60:69:a0", "a1", -79);

		bfinfo.addBssiInfo("64:e5:99:da:b8:3c");
		rp.addApMeasureInfo("64:e5:99:da:b8:3c", "a1", -76);

		bfinfo.addBssiInfo("64:e5:99:62:6f:20");
		rp.addApMeasureInfo("64:e5:99:62:6f:20", "a1", -65);

		bfinfo.addBssiInfo("64:e5:99:da:b3:e4");
		rp.addApMeasureInfo("64:e5:99:da:b3:e4", "a1", -64);

		bfinfo.addBssiInfo("64:e5:99:da:b8:38");
		rp.addApMeasureInfo("64:e5:99:da:b8:38", "a1", -78);

		bfinfo.addBssiInfo("00:26:66:1b:e8:ac");
		rp.addApMeasureInfo("00:26:66:1b:e8:ac", "a1", -74);

		bfinfo.addReferencePointInfo(rp);

		rp = new ReferencePoint(520, 500);

		bfinfo.addBssiInfo("54:a0:50:d8:1b:18");
		rp.addApMeasureInfo("54:a0:50:d8:1b:18", "a1", -38);

		bfinfo.addBssiInfo("54:a0:50:d8:1b:1c");
		rp.addApMeasureInfo("54:a0:50:d8:1b:1c", "a1", -48);

		bfinfo.addBssiInfo("64:e5:99:da:b3:e0");
		rp.addApMeasureInfo("64:e5:99:da:b3:e0", "a1", -59);

		bfinfo.addBssiInfo("64:e5:99:62:6f:24");
		rp.addApMeasureInfo("64:e5:99:62:6f:24", "a1", -47);

		bfinfo.addBssiInfo("00:26:66:1b:e8:ac");
		rp.addApMeasureInfo("00:26:66:1b:e8:ac", "a1", -62);
		bfinfo.addBssiInfo("00:8e:f2:60:69:a0");
		rp.addApMeasureInfo("00:8e:f2:60:69:a0", "a1", -66);

		bfinfo.addBssiInfo("64:e5:99:da:b8:3c");
		rp.addApMeasureInfo("64:e5:99:da:b8:3c", "a1", -65);

		bfinfo.addBssiInfo("64:e5:99:62:6f:20");
		rp.addApMeasureInfo("64:e5:99:62:6f:20", "a1", -59);

		bfinfo.addBssiInfo("64:e5:99:da:b3:e4");
		rp.addApMeasureInfo("64:e5:99:da:b3:e4", "a1", -64);

		bfinfo.addBssiInfo("64:e5:99:da:b8:38");
		rp.addApMeasureInfo("64:e5:99:da:b8:38", "a1", -67);

		bfinfo.addReferencePointInfo(rp);

		rp = new ReferencePoint(520, 510);

		bfinfo.addBssiInfo("54:a0:50:d8:1b:18");
		rp.addApMeasureInfo("54:a0:50:d8:1b:18", "a1", -39);
		bfinfo.addBssiInfo("54:a0:50:d8:1b:1c");
		rp.addApMeasureInfo("54:a0:50:d8:1b:1c", "a1", -49);
		bfinfo.addBssiInfo("64:e5:99:da:b3:e0");
		rp.addApMeasureInfo("64:e5:99:da:b3:e0", "a1", -57);
		bfinfo.addBssiInfo("64:e5:99:62:6f:24");
		rp.addApMeasureInfo("64:e5:99:62:6f:24", "a1", -50);
		bfinfo.addBssiInfo("00:26:66:1b:e8:ac");
		rp.addApMeasureInfo("00:26:66:1b:e8:ac", "a1", -60);

		bfinfo.addBssiInfo("00:8e:f2:60:69:a0");
		rp.addApMeasureInfo("00:8e:f2:60:69:a0", "a1", -64);

		bfinfo.addBssiInfo("64:e5:99:da:b8:3c");
		rp.addApMeasureInfo("64:e5:99:da:b8:3c", "a1", -63);

		bfinfo.addBssiInfo("64:e5:99:62:6f:20");
		rp.addApMeasureInfo("64:e5:99:62:6f:20", "a1", -61);

		bfinfo.addBssiInfo("64:e5:99:da:b3:e4");
		rp.addApMeasureInfo("64:e5:99:da:b3:e4", "a1", -60);

		bfinfo.addBssiInfo("64:e5:99:da:b8:38");
		rp.addApMeasureInfo("64:e5:99:da:b8:38", "a1", -67);
		bfinfo.addReferencePointInfo(rp);

		rp = new ReferencePoint(600, 600);

		bfinfo.addBssiInfo("54:a0:50:d8:1b:18");
		rp.addApMeasureInfo("54:a0:50:d8:1b:18", "a1", -60);

		bfinfo.addBssiInfo("64:e5:99:da:b3:e0");
		rp.addApMeasureInfo("64:e5:99:da:b3:e0", "a1", -51);

		bfinfo.addBssiInfo("64:e5:99:62:6f:24");
		rp.addApMeasureInfo("64:e5:99:62:6f:24", "a1", -69);

		bfinfo.addBssiInfo("00:8e:f2:60:69:a0");
		rp.addApMeasureInfo("00:8e:f2:60:69:a0", "a1", -82);

		bfinfo.addBssiInfo("64:e5:99:da:b8:38");
		rp.addApMeasureInfo("64:e5:99:da:b8:38", "a1", -78);

		bfinfo.addBssiInfo("00:26:66:1b:e8:ac");
		rp.addApMeasureInfo("00:26:66:1b:e8:ac", "a1", -74);

		bfinfo.addBssiInfo("00:8e:f2:60:69:b0");
		rp.addApMeasureInfo("00:8e:f2:60:69:b0", "a1", -74);

		bfinfo.addBssiInfo("64:e5:99:da:b8:3c");
		rp.addApMeasureInfo("64:e5:99:da:b8:3c", "a1", -77);

		bfinfo.addBssiInfo("64:e5:99:da:b3:e4");
		rp.addApMeasureInfo("64:e5:99:da:b3:e4", "a1", -48);

		bfinfo.addBssiInfo("54:a0:50:d8:1b:1c");
		rp.addApMeasureInfo("54:a0:50:d8:1b:1c", "a1", -60);

		bfinfo.addReferencePointInfo(rp);

		rp = new ReferencePoint(550, 550);//610 600

		bfinfo.addBssiInfo("54:a0:50:d8:1b:18");
		rp.addApMeasureInfo("54:a0:50:d8:1b:18", "a1", -63);

		bfinfo.addBssiInfo("64:e5:99:da:b3:e0");
		rp.addApMeasureInfo("64:e5:99:da:b3:e0", "a1", -51);

		bfinfo.addBssiInfo("64:e5:99:62:6f:24");
		rp.addApMeasureInfo("64:e5:99:62:6f:24", "a1", -70);

		bfinfo.addBssiInfo("00:8e:f2:60:69:a0");
		rp.addApMeasureInfo("00:8e:f2:60:69:a0", "a1", -80);
		bfinfo.addBssiInfo("00:26:66:1b:e8:ac");
		rp.addApMeasureInfo("00:26:66:1b:e8:ac", "a1", -71);

		bfinfo.addBssiInfo("00:8e:f2:60:69:b0");
		rp.addApMeasureInfo("00:8e:f2:60:69:b0", "a1", -71);
		bfinfo.addBssiInfo("64:e5:99:da:b3:e4");
		rp.addApMeasureInfo("64:e5:99:da:b3:e4", "a1", -47);

		bfinfo.addBssiInfo("64:e5:99:da:b8:3c");
		rp.addApMeasureInfo("64:e5:99:da:b8:3c", "a1", -73);

		bfinfo.addBssiInfo("54:a0:50:d8:1b:1c");
		rp.addApMeasureInfo("54:a0:50:d8:1b:1c", "a1", -58);
		bfinfo.addBssiInfo("64:e5:99:da:b8:38");
		rp.addApMeasureInfo("64:e5:99:da:b8:38", "a1", -79);

		bfinfo.addReferencePointInfo(rp);

		rp = new ReferencePoint(560, 560);//630,600

		bfinfo.addBssiInfo("54:a0:50:d8:1b:18");
		rp.addApMeasureInfo("54:a0:50:d8:1b:18", "a1", -61);

		bfinfo.addBssiInfo("54:a0:50:d8:1b:1c");
		rp.addApMeasureInfo("54:a0:50:d8:1b:1c", "a1", -64);

		bfinfo.addBssiInfo("64:e5:99:da:b3:e0");
		rp.addApMeasureInfo("64:e5:99:da:b3:e0", "a1", -68);

		bfinfo.addBssiInfo("64:e5:99:62:6f:24");
		rp.addApMeasureInfo("64:e5:99:62:6f:24", "a1", -70);

		bfinfo.addBssiInfo("00:8e:f2:60:69:a0");
		rp.addApMeasureInfo("00:8e:f2:60:69:a0", "a1", -57);

		bfinfo.addBssiInfo("64:e5:99:da:b8:38");
		rp.addApMeasureInfo("64:e5:99:da:b8:38", "a1", -57);

		bfinfo.addBssiInfo("00:26:66:1b:e8:ac");
		rp.addApMeasureInfo("00:26:66:1b:e8:ac", "a1", -52);

		bfinfo.addBssiInfo("00:8e:f2:60:69:b0");
		rp.addApMeasureInfo("00:8e:f2:60:69:b0", "a1", -61);
		bfinfo.addBssiInfo("64:e5:99:da:b3:e4");
		rp.addApMeasureInfo("64:e5:99:da:b3:e4", "a1", -65);
		bfinfo.addBssiInfo("64:e5:99:da:b8:3c");
		rp.addApMeasureInfo("64:e5:99:da:b8:3c", "a1", -52);

		bfinfo.addReferencePointInfo(rp);

		LocInfos.add(bfinfo);
	}
	
	
	
	
	
	
}
