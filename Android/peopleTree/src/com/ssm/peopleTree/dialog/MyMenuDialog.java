package com.ssm.peopleTree.dialog;

import com.ssm.peopleTree.R;

import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.group.GroupManager;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.Status;
import com.ssm.peopleTree.network.protocol.GroupOutRequest;
import com.ssm.peopleTree.network.protocol.GroupOutResponse;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MyMenuDialog extends Dialog implements View.OnClickListener {
	
	private Listener<JSONObject> onGroupOutResponse;
	Context mContext;
	
	private ManageSelectDialog selectDialog;

	public MyMenuDialog(Context context) {
		super(context);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.menu_cur_layout);
		this.mContext = context;
		
		selectDialog = new ManageSelectDialog(context);
		
		onGroupOutResponse = new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
	
				GroupOutResponse res = new GroupOutResponse(arg0);
				Status status = res.getStatus();
				
				if (status == Status.SUCCESS) {

					Toast.makeText(mContext,"그룹을 나갔습니다.", Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(mContext,"그룹 나가기가 되지않습니다.", Toast.LENGTH_SHORT).show();

				}
				MyMenuDialog.this.dismiss();
				GroupManager.getInstance().updateSelf();
				GroupManager.getInstance().navigateHome();
			}
		};
		
		RelativeLayout layout = (RelativeLayout)findViewById(R.id.message_layout);
		layout.setOnClickListener(this);
		layout = (RelativeLayout)findViewById(R.id.location_layout);
		layout.setOnClickListener(this);
		layout = (RelativeLayout)findViewById(R.id.group_out_layout);		
		layout.setOnClickListener(this);
 		
 		Button btn = (Button)findViewById(R.id.btn_close);
		btn.setOnClickListener(this);
 	} 

 
 	public void setMytitle(String title) { 
 		
 		TextView tv= (TextView) this.findViewById(R.id.title); 
 		tv.setText(title); 
 	}


	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.message_layout:
			BroacCastMsgSendDialog msgSendDialog;
			msgSendDialog = new BroacCastMsgSendDialog(mContext);
			msgSendDialog.show();
			break;
		case R.id.location_layout:
			selectDialog.show();
			break;
		case R.id.group_out_layout:
			int myid = MyManager.getInstance().getGroupMemberId(); 		 
         	NetworkManager.getInstance().request(new GroupOutRequest(myid), onGroupOutResponse, null); 
			break;
		case R.id.btn_close:
			dismiss();
			break;
		}
	} 

}