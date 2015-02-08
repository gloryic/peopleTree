package com.ssm.peopleTree.battery;

import com.ssm.peopleTree.C;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

public class BatteryInfoReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {


			int level= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
			int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
			
			int batterCapa = (level*100 / scale);
					

		}

}
