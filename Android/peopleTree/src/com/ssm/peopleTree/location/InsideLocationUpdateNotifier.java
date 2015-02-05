package com.ssm.peopleTree.location;

import java.util.ArrayList;


class InsideLocationUpdateNotifier implements UpdateNotifier{
	InsideLocationListener parent;
	
	
	
	
	@Override
	public void notifyUpdate(Object arg) {
	
		
		PeopleTreeLocationManager pltm = PeopleTreeLocationManager.getInstance();		
		long curTime = System.currentTimeMillis();
		if(!parent.isValidLocation() && (curTime - pltm.getLastChangeTime() ) >  PeopleTreeLocationManager.MINTIMEINTERVAL){
			
			pltm.changeLocationMeasureMode();
			
		}else{
			
			
			//위치전송
		}
		
		
	}

}
