package com.ssm.peopleTree.broadcast;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ssm.peopleTree.IntroActivity;
import com.ssm.peopleTree.MainActivity;
import com.ssm.peopleTree.R;
import com.ssm.peopleTree.TestActivity;
import com.ssm.peopleTree.map.ManageMode;


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

					pushMessageControl(context, json);
					
					
				}
			}

		} catch (JSONException e) {
			Log.d(TAG, "JSONException: " + e.getMessage());
		}
	}
	


    
    
    private boolean pushMessageControl(Context context,JSONObject msg) throws JSONException{
    


		String userName = msg.getString("userName");

		int statusCode = msg.getInt("statusCode");
		int from = msg.getInt("from");

		int to = msg.getInt("to");
		
		if (statusCode >= 2000 && statusCode <= 5000) {
			
			JSONObject msgObj = new JSONObject(msg.getString("message"));
			//msgObj.getBoolean("isToggle");
			
			PushManager.getInstance().pushMessageAcceptEx(from,to,msgObj);
			sendNotificationEx(context,from,to, userName, statusCode ,msgObj);
			return !msgObj.getBoolean("validation");
		} else {

	    	String msgstr = msg.getString("message");


			//String userName int statusCode String msgstr int from int to 

			PushManager.getInstance().pushMessageAccept(new PushData(statusCode, userName, msgstr, from, to));
			 sendNotification(context,userName, statusCode, msgstr,from, to);
			return true;
		}
	}
    /*
    
     */
    private void sendNotificationEx(Context context,int from,int to,String userName,int statusCode, JSONObject msgObj) throws JSONException {
    	int parentManageMode;
		int radius;
		double distance;
		int edgeStatus;
		int accumulateWarning;
		boolean toggle;
		parentManageMode = msgObj.getInt("parentManageMode");
		toggle = msgObj.getBoolean("isToggle");

		
		Calendar calen = Calendar.getInstance();
		String timeStr1 = "" + (calen.get(Calendar.MONTH)+1) + "월 " + calen.get(Calendar.DAY_OF_MONTH)+"일    "
				+ calen.get(Calendar.HOUR_OF_DAY) + ":"
				+ calen.get(Calendar.MINUTE) +"\n";
		String message="";
		
		
		mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, TestActivity.class), 0);
		
		if(from == to){
			
		
			switch(ManageMode.getMode(parentManageMode)){
			case TRACKING:
				radius = msgObj.getInt("radius");
				distance = msgObj.getDouble("distance");
				edgeStatus  = msgObj.getInt("edgeStatus");
				accumulateWarning = msgObj.getInt("accumulateWarning");
				if(toggle){
					
					message +="관리자 주변으로 복귀하였습니다.";
				}else{

					message +="관리자에게서 이탈하였습니다.";
				}
				break;
			case AREA:
				radius = msgObj.getInt("radius");
				distance = msgObj.getInt("distance");
				edgeStatus  = msgObj.getInt("edgeStatus");
				accumulateWarning = msgObj.getInt("accumulateWarning");
				if(toggle){
					
					message +="관리자가 지정한 지역으로 복귀하였습니다.";
				}else{

					message +="관리자가 지정한 지역을 이탈하였습니다.";
				}
				break;
			case GEOFENCE:
				edgeStatus  = msgObj.getInt("edgeStatus");
				accumulateWarning = msgObj.getInt("accumulateWarning");
				if(toggle){
					
					message +="관리자가 지정한 지역으로 복귀하였습니다.";
				}else{

					message +="관리자가 지정한 지역을 이탈하였습니다.";
				}
				break;
			}
		
		}else{
			if(toggle){
				
				message +="괸리대상이 복귀하였습니다.";
			}else{

				message +="괸리대상이 이탈하였습니다.";
			}
			
		}
		


    	
       
        
    
        String subMessage = message.length() > 20 ? message.substring(0, 20)+"..." : message;
        
        
        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle()
        .addLine(message)//메세지 전체
        .setBigContentTitle(userName)
        .setSummaryText(getSummmaryText(statusCode));
        
        
        NotificationCompat.Builder mBuilder =  new NotificationCompat.Builder(context)
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentInfo("1")
        .setTicker("피플트리")
        .setContentTitle(userName)
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
    
    
    private void sendNotification(Context context, String userName, int statusCode,String message,int from, int to) {
    	
        mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
 
        
        
        ActivityManager activity_manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);

        
        
        Intent intent;
        intent = new Intent(context, TestActivity.class);
      //  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//?

        
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);
        
        
    
        String subMessage = message.length() > 20 ? message.substring(0, 20)+"..." : message;
        
        
        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle()
        .addLine(message)//메세지 전체
        .setBigContentTitle(userName)
        .setSummaryText(getSummmaryText(statusCode));
        
        
        NotificationCompat.Builder mBuilder =  new NotificationCompat.Builder(context)
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentInfo("1")
        .setTicker("피플트리")
        .setContentTitle(userName)
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
    	case 417: return "피플트리 변경 알림";
    	case 425: return "피플트리 수락 알림";
    	case 427: return "피플트리 변경 알림";
    	case 515: return "피플트리 수락 알림";
    	case 517: return "피플트리 변경 알림";
    	case 525: return "피플트리 수락 알림";
    	case 527: return "피플트리 변경 알림";
    	case 600: return "피플트리 메세지";
    	default : return "피플트리";
    	}
    }
        
}