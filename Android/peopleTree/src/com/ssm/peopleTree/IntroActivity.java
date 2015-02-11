package com.ssm.peopleTree;

import com.ssm.peopleTree.application.LoginManager;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.application.LoginManager.LoginListener;
import com.ssm.peopleTree.dialog.SimpleAlertDialog;
import com.ssm.peopleTree.map.MapManager;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.Status;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class IntroActivity extends Activity implements LoginListener {
	
	private static final int LOGO_DURATION = 2000;
	
	private ImageView logo;
	
	private ProgressDialog progressDialog;
	private SimpleAlertDialog alertDialog;
	
	private boolean checkComplete;
	private boolean loginSuccess;
	private Intro intro;
	
	private LoginManager loginManager;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		getActionBar().hide();
		
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
}
