package com.ssm.peopleTree.dialog;

import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.ssm.peopleTree.R;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.Status;
import com.ssm.peopleTree.network.protocol.MakeGroupRequest;
import com.ssm.peopleTree.network.protocol.MakeGroupResponse;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class JoinDialog implements DialogInterface.OnClickListener, Listener<JSONObject> {

	private AlertDialog joinDialog;
	private SimpleAlertDialog alertDialog;
	
	private String successMsg;
	private String failMsg;
	
	public JoinDialog(Context context) {
		final View innerView = ((Activity)context).getLayoutInflater().inflate(R.layout.dialog_join, null);
		AlertDialog.Builder ab = new AlertDialog.Builder(context);
		ab.setTitle(R.string.join);
		ab.setView(innerView);
		ab.setPositiveButton(R.string.join, this);
		ab.setNegativeButton(R.string.cancel, this);
		
		joinDialog = ab.create();
		
		alertDialog = new SimpleAlertDialog(context);
		alertDialog.setOnClickListener(this);

		successMsg = context.getString(R.string.join_success);
		failMsg = context.getString(R.string.join_fail);
		
	}

	private void requestJoin() {
		
		String pNum = ((EditText)joinDialog.findViewById(R.id.editTextPhone)).getText().toString();
		String id = ((EditText)joinDialog.findViewById(R.id.editTextId)).getText().toString();
		String pw = ((EditText)joinDialog.findViewById(R.id.editTextPw)).getText().toString();
		String uName = ((EditText)joinDialog.findViewById(R.id.editTextName)).getText().toString();
		//String gName = ((EditText)joinDialog.findViewById(R.id.reqDialog_phoneNum)).getText().toString();
		String gName = "groupName";
		
		MakeGroupRequest req = new MakeGroupRequest(pNum, id, pw, uName, gName);
		if (req.isValid()) {
			NetworkManager.getInstance().request(req, this, null);
		}
		else {
			onInvalFail(req.valiStr+"\n문자열 길이를 조절하세요");
		}
	}
	
	public void show() {
		joinDialog.show();
	}
		
	public void dismiss(){
        if(joinDialog != null && joinDialog.isShowing())
        	joinDialog.dismiss();
    }
	
	private void onJoinSuccess() {
		alertDialog.setTitle(R.string.success);
		alertDialog.setMessage(successMsg);
		alertDialog.show();
	}
	
	private void onJoinFail() {
		alertDialog.setTitle(R.string.fail);
		alertDialog.setMessage(failMsg);
		alertDialog.show();
	}
	private void onInvalFail(String invalstr) {
		alertDialog.setTitle(R.string.fail);
		alertDialog.setMessage(invalstr);
		alertDialog.show();
	}
	@Override
	public void onClick(DialogInterface dialog, int which) {
		
		Log.e("test", "alert" + which);
		if (dialog == joinDialog) {
			if (which == -1) {
				requestJoin();
			}
			else if (which == -2){
				dialog.dismiss();
			}
		}
		else if (dialog == alertDialog) {
			alertDialog.dismiss();
		}
	}
	
	@Override
	public void onResponse(JSONObject arg0) {
		MakeGroupResponse res = new MakeGroupResponse(arg0);
		if (res.getStatus() == Status.SUCCESS) {
			onJoinSuccess();
		}
		else {
			onJoinFail();
		}
	}
}
