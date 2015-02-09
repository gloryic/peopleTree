package com.ssm.peopleTree.dialog;

import com.ssm.peopleTree.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class SimpleAlertDialog {

	private AlertDialog dialog;
	
	private DialogInterface.OnClickListener onClickListener; 
	
    public SimpleAlertDialog(Context context) {
		AlertDialog.Builder ab = new AlertDialog.Builder(context);
		ab.setTitle(R.string.alert);
		ab.setPositiveButton(R.string.ok, onClickListener);	
		dialog = ab.create();
	}
    
    public void setTitle(String title) {
    	dialog.setTitle(title);
    }
    
    public void setTitle(int titleId) {
    	dialog.setTitle(titleId);
    }
    
    public void setMessage(String message) {
    	dialog.setMessage(message);
    }
    
    public void setOnClickListener(DialogInterface.OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}
    
    public void show() {
    	dialog.show();
    }
    
	public void dismiss(){
        if(dialog != null && dialog.isShowing())
        	dialog.dismiss();
    }
}
