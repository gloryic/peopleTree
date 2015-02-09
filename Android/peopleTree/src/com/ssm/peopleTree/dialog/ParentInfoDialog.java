package com.ssm.peopleTree.dialog;


import com.ssm.peopleTree.Progressable;
import com.ssm.peopleTree.R;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.group.GroupManager;
import com.ssm.peopleTree.map.MapManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class ParentInfoDialog extends Dialog  {
	Context mContext;
	TextView titleTxtv;
	
	
	Button btn1;
	Button btn2;

	private MemberData parentData;
	
	public ParentInfoDialog(Context context) {
		super(context);
		mContext = context;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_group_parentinfo);
		
		btn1 = (Button)this.findViewById(R.id.parentInfoDialog_btn1);
		titleTxtv = (TextView)this.findViewById(R.id.parentInfoDialog_title);
	}

	public void setParentData(MemberData ptData){


		titleTxtv.setText(ptData.userName);
		btn1.setText(ptData.userPhoneNumber);
	}


	
}
