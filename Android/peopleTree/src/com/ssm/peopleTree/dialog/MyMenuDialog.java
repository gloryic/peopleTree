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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MyMenuDialog extends Dialog  {


	Button btn1;
	Button btn2;
	

	private Listener<JSONObject> onGroupOutResponse;
	Context mContext;

	public MyMenuDialog(Context context) {
		super(context);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_group_mymenu);
		this.mContext = context;
		
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
				GroupManager.getInstance().update(MyManager.getInstance().getGroupMemberId());
			}
		};
		
		btn1 =(Button)this.findViewById(R.id.mymenuDialog_btn1);
		btn1.setText("지역설정");
		btn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Progressable p = (Progressable)mContext;
				if (p != null) {
					ManageMode mode = ManageMode.getMode(MyManager.getInstance().getManageMode());
					MapManager.getInstance().setMode(mode);
					p.progress();
				}
				else {
					
				}
			}
		});
		btn2 =(Button)this.findViewById(R.id.mymenuDialog_btn2); 
 		btn2.setText("그룹 나가기"); 
 		btn2.setOnClickListener(new View.OnClickListener() { 
 			  
             public void onClick(View arg0) { 
             	int myid = MyManager.getInstance().getGroupMemberId(); 
 
             	NetworkManager.getInstance().request(new GroupOutRequest(myid), onGroupOutResponse, null); 
             } 
         }); 
 		 
 		 
 
 
 		 
 	} 
 
 	public void setMytitle(String title) { 

 		TextView tv= (TextView) this.findViewById(R.id.mymenuDialog_title); 
 		tv.setText(title); 
 		 
 		 
 	} 

}