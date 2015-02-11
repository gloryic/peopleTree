package com.ssm.peopleTree.service;

import com.ssm.peopleTree.device.DeviceStatusReceiver;
import com.ssm.peopleTree.location.PeopleTreeLocationManager;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
 
public class peopleTreeService extends Service{
     
	static final String TAG = "peopleTreeService";
	DeviceStatusReceiver mReceiver = new DeviceStatusReceiver();
	
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
     
	@Override
    public int onStartCommand(Intent intent, int flag, int startId) {
        Log.d(TAG, "onStartCommand()");
        super.onStartCommand(intent,START_STICKY,startId);
        //서비스 등록
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        this.registerReceiver(mReceiver, filter);
        Log.i("service start","service start");
        

        PeopleTreeLocationManager.getInstance().initialize(this);
        PeopleTreeLocationManager.getInstance().startLocationMeasure();

        return 0;
    }
     
    @Override
    public void onDestroy() { 
    	Log.i("service dead","service dead");
    	PeopleTreeLocationManager.getInstance().stopLocationMeasure();
        
        Log.d(TAG, "onDestroy()");
        this.unregisterReceiver(mReceiver);
        super.onDestroy();
    }

}