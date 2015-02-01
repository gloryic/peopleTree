package com.ssm.peopleTree;

import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.ssm.peopleTree.application.LoginData;
import com.ssm.peopleTree.dialog.JoinDialog;
import com.ssm.peopleTree.dialog.SimpleAlertDialog;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.protocol.LoginRequest;
import com.ssm.peopleTree.network.protocol.LoginResponse;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LoginActivity extends Activity implements OnClickListener, Listener<JSONObject> {
	
	private static final int LOGO_DURATION = 2000;
	private static final int INPUT_DURATION = 1500;
	
	private ImageView logo;
	private LinearLayout loginSet;
	private Button loginBtn;
	private Button findPasswordBtn;
	private Button joinBtn;
	private EditText idET;
	private EditText pwET;
	
	private ProgressDialog progressDialog;
	private JoinDialog joinDialog;
	private SimpleAlertDialog alertDialog;
	
	private boolean introValidCheck;
	private boolean loginSuccess;
	private LoginData loginData;	
	private Intro intro;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		NetworkManager.getInstance().initialize(getApplicationContext());
		
		logo = (ImageView)findViewById(R.id.imageLogo);
		logo.setAlpha(0f);
		logo.animate()
			.alpha(1f)
			.setDuration(LOGO_DURATION)
			.setListener(null);
		loginSet = (LinearLayout)findViewById(R.id.layoutLoginSet);
		//loginSet.setAlpha(0f);
		loginBtn = (Button)findViewById(R.id.buttonLogin);
		loginBtn.setOnClickListener(this);
		joinBtn = (Button)findViewById(R.id.buttonJoin);
		joinBtn.setOnClickListener(this);
		idET = (EditText)findViewById(R.id.editTextId);
		pwET = (EditText)findViewById(R.id.editTextPw);
		
		progressDialog = new ProgressDialog(this);
		joinDialog = new JoinDialog(this);
		alertDialog = new SimpleAlertDialog(this);
		alertDialog.setTitle(R.string.fail);
		alertDialog.setMessage(getApplicationContext().getString(R.string.login_fail));
		
		loginData = new LoginData();
		loginData.load(LoginActivity.this);
		Log.e("test", "auto request:" + loginData.isLogined());
		if (loginData.isLogined()) {
			introValidCheck = false;
			loginSuccess = false;
			
			Log.e("test", "auto request: id:" + loginData.getSavedId() + ", pw:" + loginData.getSavedPw());
			requestLogin(loginData.getSavedId(), loginData.getSavedPw());
		}
		else {
			introValidCheck = true;
			loginSuccess = false;
		}
		
		intro = new Intro();
		intro.start();
	}

	@Override
	public void onClick(View v) {

		if (v == loginBtn) {
			progressDialog.show();
			
			String id = idET.getText().toString();
			String pw = pwET.getText().toString();
			loginData.setSavedId(id);
			loginData.setSavedPw(pw);
						
			Log.e("test", "manual request: id:" + id + ", pw:" + pw);
			requestLogin(id, pw);
		}
		else if (v == joinBtn) {
			joinDialog.show();
		}
	}
	
	@Override
	public void onResponse(JSONObject arg0) {		
		
		LoginResponse lRes = new LoginResponse(arg0);
		Log.e("test", "status : " + lRes.status);
		if (lRes.status == 200) {
			if (introValidCheck) {
				loginData.setLogin(true);
				loginData.save();
				nextActivity();
			}
			else {
				loginSuccess = true;
				introValidCheck =  true;
			}
		}
		else {
			if (introValidCheck) {
				alertDialog.show();
			}
			else {
				loginSuccess = false;
				introValidCheck =  true;
			}
		}
		
		if (progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}
	
	private void requestLogin(String id, String pw) {
		NetworkManager.getInstance().request(new LoginRequest(id, pw), this, null);
	}
	
	private void nextActivity() {
		Intent intent;
		intent = new Intent(LoginActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
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
				nextActivity();
			}
			else {
				loginSet.animate()
					.alpha(1f)
					.setDuration(INPUT_DURATION)
					.setListener(null);
				
				try {
					Thread.sleep(INPUT_DURATION);					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
