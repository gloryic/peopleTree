package com.ssm.location;

import java.util.ArrayList;


class InsideLocationUpdateNotifier implements UpdateNotifier{
	InsideLocationListener parent;
	
	
	
	
	@Override
	public void notifyUpdate(Object arg) {
		parent = (InsideLocationListener)arg; 
		
		

		
		
	}

}
