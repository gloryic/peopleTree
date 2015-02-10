package com.ssm.peopleTree.broadcast;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ssm.peopleTree.MainActivity;
import com.ssm.peopleTree.R;


public class ParseBroadcastReceiver extends BroadcastReceiver {
	private static final String TAG = "ParseBroadcastReceiver";
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			if (intent == null)
			{
				Log.d(TAG, "Receiver intent null");
			}
			else
			{
				String action = intent.getAction();
				Log.d(TAG, "got action " + action );
				if (action.equals("com.ssm.peopleTree.message"))
				{
					String channel = intent.getExtras().getString("com.parse.Channel");
					JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));

					Log.d(TAG, "got action " + action + " on channel " + channel + " with:");
					Iterator itr = json.keys();
					while (itr.hasNext()) {
						String key = (String) itr.next();
						
						/*
						if (key.equals("customdata"))
						{
							Intent pupInt = new Intent(context, ShowPopUp.class);
							pupInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
							context.getApplicationContext().startActivity(pupInt);
						}*/
						
						/*
						 * 
						 *         
        
								Bundle extras = intent.getExtras();
							    String jsonData = extras.getString("com.parse.Data");
							    JSONObject jsonObject;
							    try {
							    	Log.i("TAG","DATA : xxxxx : " + jsonData);
							        jsonObject = new JSONObject(jsonData);
							        String heading = jsonObject.getString("heading");
							        String dataString = jsonObject.getString("dataString");
							    } catch (JSONException e) {
							        e.printStackTrace();
							    }
						 * 
						 * 
						 * */
						
						
						Log.d(TAG, key + " => " + json.getString(key));
					}
					
					sendNotification(context, json);
				}
			}

		} catch (JSONException e) {
			Log.d(TAG, "JSONException: " + e.getMessage());
		}
	}
	
    private void sendNotification(Context context, JSONObject msg) throws JSONException {
    	
        mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
        
        
        String message = msg.getString("message");
        String subMessage = message.length() > 20 ? message.substring(0, 20)+"..." : message;
        
        
        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle()
        .addLine(message)//메세지 전체
        .setBigContentTitle(msg.getString("userName"))
        .setSummaryText(msg.getString("statusCode"));
        
        
        NotificationCompat.Builder mBuilder =  new NotificationCompat.Builder(context)
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentInfo("1")
        .setTicker("피플트리")
        .setContentTitle(getSummmaryText(msg.getInt("userName")))
        .setContentText(subMessage)//메세지의 요악본
        .setStyle(style);    
        
        //.setStyle(new NotificationCompat.BigTextStyle()
        //.bigText("3"));
        
        mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 }); 
        mBuilder.setLights(Color.RED, 3000, 3000); 
        mBuilder.setContentIntent(contentIntent);
        
        Notification noti = mBuilder.build();
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        
        mNotificationManager.notify(NOTIFICATION_ID, noti);
    }  
    
    private String getSummmaryText(int statusCode){
    	
    	switch(statusCode)
    	{
    	case 100: return "피플트리 공지사항";
    	case 200: return "피플트리 변경 알림";
    	case 210: return "피플트리 변경 알림";
    	case 220: return "피플트리 변경 알림";
    	case 230: return "피플트리 변경 알림";
    	case 410: return "피플트리 요청 알림";
    	case 420: return "피플트리 요청 알림";
    	case 510: return "피플트리 요청 알림";
    	case 520: return "피플트리 요청 알림";
    	case 415: return "피플트리 수락 알림";
    	case 425: return "피플트리 수락 알림";
    	case 515: return "피플트리 수락 알림";
    	case 525: return "피플트리 수락 알림";
    	case 600: return "피플트리 메세지";
    	default : return "피플트리";
    	}
    }
        
}