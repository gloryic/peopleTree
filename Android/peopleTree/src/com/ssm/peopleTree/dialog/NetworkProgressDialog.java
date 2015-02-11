package com.ssm.peopleTree.dialog;

import com.ssm.peopleTree.R;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

public class NetworkProgressDialog extends ProgressDialog {// implements DialogInterface.OnClickListener {
	
	private static final int DURATION = 500;
	
	private AlertDialog alertDialog;
	
	private Handler handler;
	private ProgressTimer timer;
	private boolean complete;

	public NetworkProgressDialog(Context context) {
		super(context);
		
		/*
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("경고")
				.setMessage("진행을 멈추시겠습니까?")
				.setCancelable(true)
				.setNegativeButton(R.string.cancel, this)
				.setPositiveButton(R.string.ok, this);
		
		alertDialog = builder.create();
		*/
		
		handler = new Handler() {
			public void handleMessage(Message msg) {
				//alertDialog.cancel();
				
				if (isShowing()) {
					dismiss();
				}
			};
		};
	}
	
	public void completeProgress() {
		complete = true;
	}
	
	@Override
	public void show() {
		super.show();
		
		complete = false;
		timer = new ProgressTimer();
		timer.start();
	}

	@Override
	public void onBackPressed() {
		//alertDialog.show();
	}

	/*
	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == -1) {
			dismiss();
		}
		dialog.cancel();
	}
	*/
	
	private class ProgressTimer extends Thread {

		@Override
		public void run() {
			try {
				Thread.sleep(DURATION);				
				while(!complete) {
					;
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			handler.sendEmptyMessage(0);
		}
	}
}
