package com.ssm.location;

import android.content.Context;
import android.widget.TextView;

public class PeopleTreeLocationManager  {


	static private LocationMeasurer lm = null;
	
	public static LocationMeasurer getMeasurer(Context context){
		if(lm==null){
			lm = new OutsideLocationListener(context);
		}
		return lm;
	}
	
	
	//테스트용...
	static public TextView txt1;
	static public TextView txt2;
}
