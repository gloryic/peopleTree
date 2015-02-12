package com.ssm.peopleTree.dialog;

import com.ssm.peopleTree.Progressable;
import com.ssm.peopleTree.R;

import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.ssm.peopleTree.map.MapManager;
import com.ssm.peopleTree.map.ManageMode;


import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.group.GroupManager;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.Status;
import com.ssm.peopleTree.network.protocol.GroupOutRequest;
import com.ssm.peopleTree.network.protocol.GroupOutResponse;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MyMenuDialog extends Dialog  {
	
	private RelativeLayout messageBtn;
	private RelativeLayout locationBtn;
	private RelativeLayout groupOutBtn;
	
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

					Toast.makeText(mContext,"�׷��� �������ϴ�.", Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(mContext,"�׷� �����Ⱑ �����ʽ��ϴ�.", Toast.LENGTH_SHORT).show();

				}
				MyMenuDialog.this.dismiss();
				GroupManager.getInstance().updateSelf();
				GroupManager.getInstance().navigateHome();
			}
		};
		
		messageBtn = (RelativeLayout)findViewById(R.id.message_layout);
 		messageBtn.setOnClickListener(new View.OnClickListener() { 
			  
            public void onClick(View arg0) { 
           	 BroacCastMsgSendDialog msgSendDialog;
           	 msgSendDialog = new BroacCastMsgSendDialog(mContext);
				msgSendDialog.show();
            } 
        }); 
		locationBtn = (RelativeLayout)findViewById(R.id.location_layout);
		locationBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectDialog.show();
				return;
			}
		});
		
		groupOutBtn = (RelativeLayout)findViewById(R.id.group_out_layout);		
 		groupOutBtn.setOnClickListener(new View.OnClickListener() { 
 			  
             public void onClick(View arg0) { 
             	int myid = MyManager.getInstance().getGroupMemberId(); 
 
             	NetworkManager.getInstance().request(new GroupOutRequest(myid), onGroupOutResponse, null); 
             } 

         }); 
 		 
 	} 

 
 	public void setMytitle(String title) { 
 		
 		TextView tv= (TextView) this.findViewById(R.id.title); 
 		tv.setText(title); 
 	} 

}