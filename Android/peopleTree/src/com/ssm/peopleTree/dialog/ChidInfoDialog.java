package com.ssm.peopleTree.dialog;


import com.ssm.peopleTree.R;







import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class ChidInfoDialog extends Dialog  {
	Context mContext;
	TextView reqTitleTxtv;
	String reqTitle;
	
	
	Button btn1;
	Button btn2;
	public ChidInfoDialog(Context context) {
		super(context);
		mContext = context;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_group_childinfo);

		btn1 =(Button)this.findViewById(R.id.childInfoDialog_btn1);
		btn2 =(Button)this.findViewById(R.id.childInfoDialog_btn2);
		btn1.setText("위치보기");
		btn2.setText("하위그룹보기");
		
	}

	public void setchildInfoTitle(String reqtitle){
		this.reqTitle = reqtitle;
		reqTitleTxtv = (TextView)this.findViewById(R.id.childInfoDialog_title);
	
		reqTitleTxtv.setText(reqtitle);
		
		
	}


}
