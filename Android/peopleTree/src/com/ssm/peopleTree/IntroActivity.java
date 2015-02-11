package com.ssm.peopleTree;


import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.ssm.peopleTree.application.LoginManager;
import com.ssm.peopleTree.application.LoginManager.LoginListener;
import com.ssm.peopleTree.dialog.SimpleAlertDialog;
import com.ssm.peopleTree.map.MapManager;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.Status;

public class IntroActivity extends Activity implements LoginListener {
	
	private static final int LOGO_DURATION = 1200;
	
	private ImageView logo;
	
	private ProgressDialog progressDialog;
	private SimpleAlertDialog alertDialog;
	
	private boolean checkComplete;
	private boolean loginSuccess;
	private Intro intro;
    private Toast toast;
    
    private Activity activity;
	
	private LoginManager loginManager;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		activity = this;
		
		NetworkManager.getInstance().initialize(getApplicationContext());
		MapManager.getInstance().initialize(getApplicationContext());
		loginManager = LoginManager.getInstance();
		loginManager.initialize(getApplicationContext());
		
		
		logo = (ImageView)findViewById(R.id.imageLogo);
		logo.setAlpha(0f);
		logo.animate()
			.alpha(1f)
			.setDuration(LOGO_DURATION)
			.setListener(null);
		
		progressDialog = new ProgressDialog(this);
		alertDialog = new SimpleAlertDialog(this);
		alertDialog.setTitle(R.string.fail);
		alertDialog.setMessage(getApplicationContext().getString(R.string.login_fail));
		
		
		checkComplete = false;
		loginSuccess = false;
		
		loginManager.setLoginListener(this);
		loginManager.autoLogin();
				
		intro = new Intro();
		intro.start();
		
		//service
		startService(new Intent("android.servcice.MAIN"));
		
		//TODO
		if (isNetworkStat()) {
			Log.i("IntroActivity", "NetworkSate enabled");
			
		}
		else{
			Log.i("IntroActivity", "NetworkSate unabled");
		}
		
	}

	private class Intro extends Thread {
		
		@Override
		public void run() {
			try {
				Thread.sleep(LOGO_DURATION);				
				while(!checkComplete) {
					;
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			
			if (loginSuccess) {
				//TODO
				//nextActivity(MainActivity.class);
				nextActivity(TestActivity.class);
				
			}
			else {
				nextActivity(LoginActivity.class);
			}
		}
	}
	
	
	private void nextActivity(Class<?> cls) {
		Intent intent;
		intent = new Intent(IntroActivity.this, cls);
		startActivity(intent);
		finish();
	}


	@Override
	public void onLoginSuccess() {
		loginSuccess = true;
		checkComplete = true;
	}


	@Override
	public void onLoginFail(Status status) {
		loginSuccess = false;
		checkComplete = true;
	}
	
	public boolean isNetworkStat() {
		ConnectivityManager localConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobile = localConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifi = localConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean flag = false;
		
		if (mobile.isConnected() || wifi.isConnected()){
		      Log.d("TestActivity", "Network connect success");
		      flag = true;
		}else{
			
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("네트워크 에러");
			alertDialog.setMessage("네트워크 연결이 되지 않았습니다. 네트워크 연결을 확인해주세요");
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					finish();
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			});
			alertDialog.show();
		}
		return flag;
	}
}
