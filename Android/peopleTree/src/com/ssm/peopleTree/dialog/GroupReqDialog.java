package com.ssm.peopleTree.dialog;


import com.ssm.peopleTree.R;







import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class GroupReqDialog extends Dialog  {
	Context mContext;
	TextView reqTitleTxtv;
	String reqTitle;
	Button btn1;
	
	public GroupReqDialog(Context context) {
		super(context);
		mContext = context;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_req_group);
		btn1 =(Button)this.findViewById(R.id.reqDialog_btn1);
		btn1.setText("ø‰√ª");
		
		
	
	}

	public void setReqTitle(String reqtitle){
		this.reqTitle = reqtitle;
		reqTitleTxtv = (TextView)this.findViewById(R.id.reqDialog_reqtitle);
	
		reqTitleTxtv.setText(reqtitle);
		
	}


}
