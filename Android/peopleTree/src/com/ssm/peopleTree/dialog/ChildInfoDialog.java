package com.ssm.peopleTree.dialog;


import com.ssm.peopleTree.ParentLocationActivity;
import com.ssm.peopleTree.R;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.data.MemberData;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ChildInfoDialog extends Dialog implements View.OnClickListener {

	private AlertDialog locAlertDialog;
	
	private TextView titleTv;
	private TextView phoneTv;
	
	private Context mContext;
	
	private MsgSendDialog msgSendDialog;
	
	public ChildInfoDialog(Context context,MemberData childData_) {
		super(context);
		mContext = context;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.menu_child_layout);
		
		RelativeLayout layout = (RelativeLayout)this.findViewById(R.id.phone_layout);
		layout.setOnClickListener(this);
		
		layout = (RelativeLayout)this.findViewById(R.id.location_layout);
		layout.setOnClickListener(this);
		
		Button btn = (Button)this.findViewById(R.id.btn_close);
		btn.setOnClickListener(this);
			
		titleTv = (TextView)this.findViewById(R.id.title);
		phoneTv = (TextView)this.findViewById(R.id.phone_text);
			
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("경고")
			.setMessage("현재 저장된 위치 정보는 확인할 수 없습니다.")
			.setCancelable(true)
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
				}

			});
		locAlertDialog = builder.create();
	}	

	public void setChildData(MemberData cData){
		titleTv.setText(cData.userName);
		phoneTv.setText(cData.userPhoneNumber);
	}


	public void setParentData(MemberData ptData){
		titleTv.setText(ptData.userName);
		phoneTv.setText(ptData.userPhoneNumber);
	}
	
	private void callToParent() {
		String str = "tel:"+phoneTv.getText().toString();
    	Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(str));
    	mContext.startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.phone_layout:
			callToParent();
			break;
		case R.id.location_layout:
			MyManager myManager = MyManager.getInstance();
			if (myManager.isAvailableParentLocation() && myManager.isAvailableMyLocation()) {
				Intent intent = new Intent(mContext, ParentLocationActivity.class);
				mContext.startActivity(intent);
			}
			else {
				locAlertDialog.show();
			}
			break;
		case R.id.message_layout:
			msgSendDialog.show();
			break;
		case R.id.btn_close:
			dismiss();
			break;
		}
	}
}
