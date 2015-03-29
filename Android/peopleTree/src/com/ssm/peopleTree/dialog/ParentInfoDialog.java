package com.ssm.peopleTree.dialog;


import com.ssm.peopleTree.ParentLocationActivity;
import com.ssm.peopleTree.R;
import com.ssm.peopleTree.TestActivity;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.map.ManageMode;
import com.ssm.peopleTree.map.MapManager;

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


public class ParentInfoDialog extends Dialog implements View.OnClickListener {

	private AlertDialog authorAlertDialog;
	private AlertDialog locAlertDialog;
	private AlertDialog indoorAlertDialog;
	
	private TextView titleTv;
	private TextView phoneTv;
	
	private Context mContext;
	
	private MsgSendDialog msgSendDialog;
	
	public ParentInfoDialog(Context context, MemberData parentData_) {
		super(context);
		mContext = context;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.menu_parent_layout);
		
		RelativeLayout layout = (RelativeLayout)this.findViewById(R.id.phone_layout);
		layout.setOnClickListener(this);
		
		layout = (RelativeLayout)this.findViewById(R.id.location_layout);
		layout.setOnClickListener(this);
		
		layout = (RelativeLayout)this.findViewById(R.id.message_layout);
		layout.setOnClickListener(this);
		
		Button btn = (Button)this.findViewById(R.id.btn_close);
		btn.setOnClickListener(this);
			
		titleTv = (TextView)this.findViewById(R.id.title);
		phoneTv = (TextView)this.findViewById(R.id.phone_text);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("알림")
			.setMessage("현재 실내 모드로 관리되고 있습니다.")
			.setCancelable(true)
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
				}

			});
		indoorAlertDialog = builder.create();
			
		builder = new AlertDialog.Builder(context);
		builder.setTitle("경고")
			.setMessage("관리지역은 이탈 시에만 확인할 수 있습니다.")
			.setCancelable(true)
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
				}

			});
		authorAlertDialog = builder.create();
		
		builder = new AlertDialog.Builder(context);
		builder.setTitle("경고")
			.setMessage("현재 관리지역 설정을 확인할 수 없습니다. 잠시 후 다시 시도해 주십시오.")
			.setCancelable(true)
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
				}

			});
		locAlertDialog = builder.create();
		
		msgSendDialog = new MsgSendDialog(context, parentData_);
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
			if (!myManager.isAbsent()) {
				authorAlertDialog.show();
			}
			else {
				ManageMode parentManageMode = ManageMode.getMode(myManager.getMyParentData().manageMode);
				if (parentManageMode == ManageMode.INDOOR) {
					indoorAlertDialog.show();
				}
				else if (parentManageMode == ManageMode.TRACKING) {
					if (!myManager.isAvailableParentLocation()) {
						locAlertDialog.show();
					}
					else {
						MemberData pData = myManager.getMyParentData();
						MapManager.getInstance().updateParentLoaction(pData.latitude, pData.longitude);
						Intent intent = new Intent(mContext, ParentLocationActivity.class);
						mContext.startActivity(intent);
					}
				}
				else {
					Intent intent = new Intent(mContext, ParentLocationActivity.class);
					mContext.startActivity(intent);
				}
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
