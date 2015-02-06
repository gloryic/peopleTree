package com.ssm.peopleTree;

import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.data.LoginData;
import com.ssm.peopleTree.dialog.SimpleAlertDialog;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.Status;
import com.ssm.peopleTree.network.protocol.GetUserInfoRequest;
import com.ssm.peopleTree.network.protocol.GetUserInfoResponse;
import com.ssm.peopleTree.network.protocol.LoginRequest;
import com.ssm.peopleTree.network.protocol.LoginResponse;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class IntroActivity extends Activity {
	
	private static final int LOGO_DURATION = 2000;
	
	private ImageView logo;
	
	private ProgressDialog progressDialog;
	private SimpleAlertDialog alertDialog;
	
	private boolean introValidCheck;
	private boolean loginSuccess;
	private LoginData loginData;	
	private Intro intro;
	
	private NetworkManager networkManager;
	private MyManager myManager;
	
	private Listener<JSONObject> onLoginListener;
	private Listener<JSONObject> onGetInfoListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		networkManager = NetworkManager.getInstance();
		networkManager.initialize(getApplicationContext());
		myManager = MyManager.getInstance();
		
		onLoginListener = new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {

				LoginResponse lRes = new LoginResponse(arg0);
				if (lRes.getStatus() == Status.SUCCESS) {
					loginData.setLogin(true);
					loginData.save();
					
					progressDialog.show();
					GetUserInfoRequest req = new GetUserInfoRequest(lRes.userNumber);
					networkManager.request(req, onGetInfoListener, null);	
				}
				else {
					loginSuccess = false;
					introValidCheck = true;
				}
				
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
			}
			
		};
		onGetInfoListener = new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				
				GetUserInfoResponse res = new GetUserInfoResponse(arg0);
				if (res.getStatus() == Status.SUCCESS) {
					myManager.setMyData(res.mData);
					loginSuccess = true;
					introValidCheck = true;
				}
				else {
					loginSuccess = false;
					introValidCheck = true;
				}
				
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
			}
		};
		
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
		
		loginData = new LoginData();
		loginData.load(IntroActivity.this);
		if (loginData.isLogined()) {
			introValidCheck = false;
			loginSuccess = false;
			networkManager.request(new LoginRequest(loginData.getSavedId(), loginData.getSavedPw()), onLoginListener, null);
		}
		else {
			introValidCheck = true;
			loginSuccess = false;
		}
				
		intro = new Intro();
		intro.start();
	}

	private class Intro extends Thread {
		
		@Override
		public void run() {
			try {
				Thread.sleep(LOGO_DURATION);				
				while(!introValidCheck) {
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
}
