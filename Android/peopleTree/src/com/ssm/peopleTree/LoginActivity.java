package com.ssm.peopleTree;

import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.ssm.peopleTree.application.LoginManager;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.application.LoginManager.LoginListener;
import com.ssm.peopleTree.dialog.JoinDialog;
import com.ssm.peopleTree.dialog.SimpleAlertDialog;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.Status;
import com.ssm.peopleTree.network.protocol.GetInfoRequest;
import com.ssm.peopleTree.network.protocol.GetInfoResponse;
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
import android.widget.LinearLayout;

public class LoginActivity extends Activity implements OnClickListener, LoginListener {
	
	private LinearLayout loginSet;
	private Button loginBtn;
	private Button joinBtn;
	private EditText idET;
	private EditText pwET;
	
	private ProgressDialog progressDialog;
	private JoinDialog joinDialog;
	private SimpleAlertDialog alertDialog;

	private LoginManager loginManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		loginSet = (LinearLayout)findViewById(R.id.layoutLoginSet);
		loginSet.setAlpha(1f);
		loginBtn = (Button)loginSet.findViewById(R.id.buttonLogin);
		loginBtn.setOnClickListener(this);
		joinBtn = (Button)loginSet.findViewById(R.id.buttonJoin);
		joinBtn.setOnClickListener(this);
		idET = (EditText)loginSet.findViewById(R.id.editTextId);
		pwET = (EditText)loginSet.findViewById(R.id.editTextPw);
		
		progressDialog = new ProgressDialog(this);
		joinDialog = new JoinDialog(this);
		alertDialog = new SimpleAlertDialog(this);
		alertDialog.setTitle(R.string.fail);
		alertDialog.setMessage(getApplicationContext().getString(R.string.login_fail));
		
		
		loginManager = LoginManager.getInstance();
		loginManager.setLoginListener(this);
	}

	@Override
	public void onClick(View v) {

		if (v == loginBtn) {
			progressDialog.show();
			
			String id = idET.getText().toString();
			String pw = pwET.getText().toString();
			loginManager.login(id, pw);
		}
		else if (v == joinBtn) {
			joinDialog.show();
		}
	}
	
	private void nextActivity(Class<?> cls) {
		Intent intent;
		intent = new Intent(LoginActivity.this, cls);
		startActivity(intent);
		finish();
	}

	@Override
	public void onLoginSuccess() {
		if (progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
		nextActivity(TestActivity.class);
	}

	@Override
	public void onLoginFail(Status status) {
		if (progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
		alertDialog.show();
	}
}
