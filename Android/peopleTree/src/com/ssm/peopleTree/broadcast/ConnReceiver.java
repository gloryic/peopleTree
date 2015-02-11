package com.ssm.peopleTree.broadcast;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
 
public class ConnReceiver extends BroadcastReceiver {
	
	static final String TAG = "ConnReceiver";
    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
         
        
        //wifi를 켰는지 mobile 인터넷을 사용하지는 지
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION))
        {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
            NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            
            Toast.makeText(context,"Active Network Type : " + activeNetInfo.getTypeName() , Toast.LENGTH_SHORT).show();
            Toast.makeText(context,"Mobile Network Type : " + mobNetInfo.getTypeName() , Toast.LENGTH_SHORT).show();
        }
	
        
        //gps를 켰는지 안켰는지
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED"))
        {
            //Toast.makeText(context, "in android.location.PROVIDERS_CHANGED", Toast.LENGTH_SHORT).show();
        	
        } 

    }
}