package com.ssm.peopleTree.dialog;


import com.ssm.peopleTree.R;






import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;


public class ChidInfoDialog extends Dialog  {
	Context mContext;
	TextView reqTitleTxtv;
	String reqTitle;
	public ChidInfoDialog(Context context) {
		super(context);
		mContext = context;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_group_childinfo);
		
	}

	public void setchildInfoTitle(String reqtitle){
		this.reqTitle = reqtitle;
		reqTitleTxtv = (TextView)this.findViewById(R.id.childInfoDialog_title);
	
		reqTitleTxtv.setText(reqtitle);
		
		
	}


}
