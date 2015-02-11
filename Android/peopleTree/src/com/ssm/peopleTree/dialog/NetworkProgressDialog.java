package com.ssm.peopleTree.dialog;

import com.ssm.peopleTree.R;

import android.R.attr;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;

public class NetworkProgressDialog extends Dialog {
	
	private static final int DURATION = 500;
	
	private Handler handler;
	private ProgressTimer timer;
	
	private boolean progressComplete;

	public NetworkProgressDialog(Context context) {
		super(context, R.style.NetworkProgessDialog);

		setCancelable(false);
		setCanceledOnTouchOutside(false);
		ProgressBar pb = new ProgressBar(context, null, attr.progressBarStyleLargeInverse);
		addContentView(pb, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		handler = new Handler() {
			public void handleMessage(Message msg) {
	
				if (isShowing()) {
					dismiss();
				}
			};
		};
	}
	
	public void complete() {
		progressComplete = true;
	}
	
	@Override
	public void show() {
		progressComplete = false;
		timer = new ProgressTimer();
		timer.start();
		
		super.show();
	}
	
	private class ProgressTimer extends Thread {

		@Override
		public void run() {
			try {
				Thread.sleep(DURATION);				
				while(!progressComplete) {
					;
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			handler.sendEmptyMessage(0);
		}
	}
}
