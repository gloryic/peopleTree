package com.ssm.peopleTree.dialog;


import com.ssm.peopleTree.ChildLocationActivity;
import com.ssm.peopleTree.ParentLocationActivity;
import com.ssm.peopleTree.R;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.data.MemberData;
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


public class ChildInfoDialog extends Dialog implements View.OnClickListener {

	private AlertDialog locAlertDialog;
	private AlertDialog indoorAlertDialog;
	
	private TextView titleTv;
	private TextView phoneTv;
	
	private Context mContext;
	
	private MsgSendDialog msgSendDialog;
	
	private MemberData childData;
	
	public ChildInfoDialog(Context context,MemberData childData_) {
		super(context);
		mContext = context;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.menu_child_layout);
		
		RelativeLayout layout = (RelativeLayout)this.findViewById(R.id.phone_layout);
		layout.setOnClickListener(this);
		
		layout = (RelativeLayout)this.findViewById(R.id.message_layout);
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
		
		builder = new AlertDialog.Builder(context);
		builder.setTitle("경고")
			.setMessage("선택한 관리 대상은 실내에 있어 현재 위치를 확인할 수 없습니다.")
			.setCancelable(true)
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
				}

			});
		indoorAlertDialog = builder.create();
				
		msgSendDialog = new MsgSendDialog(context, childData_);
	}	

	public void setChildData(MemberData cData){
		titleTv.setText(cData.userName);
		phoneTv.setText(cData.userPhoneNumber);
		
		childData = cData;
	}
	
	private void callToChild() {
		String str = "tel:"+phoneTv.getText().toString();
    	Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(str));
    	mContext.startActivity(intent);
	}
	
	public boolean isAvailableLoaction() {
		if (childData == null) {
			return false;
		}
		else if (childData.latitude == null || childData.longitude == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean isIndoorLocation() {
		return childData.latitude >= 500f && childData.longitude >= 500f;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.phone_layout:
			callToChild();
			break;
		case R.id.location_layout:
			MapManager mapManager = MapManager.getInstance();
			mapManager.setChild(childData);
			if (isAvailableLoaction()) {
				if (isIndoorLocation()) {
					indoorAlertDialog.show();
				}
				else {
					Intent intent = new Intent(mContext, ChildLocationActivity.class);
					mContext.startActivity(intent);
				}
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
