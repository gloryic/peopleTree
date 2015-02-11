package com.ssm.peopleTree.dialog;


import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.ssm.peopleTree.R;










import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.Status;
import com.ssm.peopleTree.network.protocol.BroadcastDownRequest;
import com.ssm.peopleTree.network.protocol.BroadcastDownResponse;
import com.ssm.peopleTree.network.protocol.SearchMemberRequest;
import com.ssm.peopleTree.network.protocol.SearchMemberResponse;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class BroacCastMsgSendDialog extends Dialog  {
	Context mContext;
	
	
	ImageButton imgbtn_cacel,imgbtn_send;
	ImageButton upbtn, downbtn;
	EditText edtxt;
	TextView depthTxtv;
	
	
	private Listener<JSONObject> onBroadcastDownResponse;
	int depthnum = 1;
	public BroacCastMsgSendDialog(Context context) {
		super(context);

		
		mContext = context;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_bc_msgsend);
		
		onBroadcastDownResponse = new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
	
				BroadcastDownResponse res = new BroadcastDownResponse(arg0);
				Status status = res.getStatus();
				String str;
				if (res.getStatus() == Status.SUCCESS) {

					str = "�޽������� �Ϸ�";

				} else {
					str = "�޽������� ����";

				}

				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setTitle("�˸�")
						.setMessage(str)
						.setCancelable(true)
						// �ڷ� ��ư Ŭ���� ��� ���� ����
						.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
							// Ȯ�� ��ư Ŭ���� ����
							public void onClick(DialogInterface dialog, int whichButton) {
								
								
								
								dialog.cancel();
								BroacCastMsgSendDialog.this.dismiss();
							}

						});
				AlertDialog dialog = builder.create(); // �˸�â ��ü ����
				dialog.show();
			
			}
		};
		
		
		
		edtxt = (EditText)this.findViewById(R.id.bcmsgsend_editText);
		depthTxtv = (TextView)this.findViewById(R.id.bcmsgsend_depth_txtv);
		depthTxtv.setText(""+depthnum);
		imgbtn_send= (ImageButton)this.findViewById(R.id.bcmsgsend_send);
		
		imgbtn_send.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {
            	String msg = edtxt.getText().toString();
            	int mid = MyManager.getInstance().getGroupMemberId();
            	int sendDepth;
            	if(depthnum == 0){
            		sendDepth = 1000;
            	}else{
            		sendDepth = depthnum;
            	}
          
   
            	BroadcastDownRequest bcdr = new BroadcastDownRequest(mid,sendDepth,msg);
            	
            	NetworkManager.getInstance().request(bcdr, onBroadcastDownResponse, null);
            	
            }
        });
		
		
		imgbtn_cacel = (ImageButton)this.findViewById(R.id.bcmsgsend_cancel);
		
		imgbtn_cacel.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {
            	BroacCastMsgSendDialog.this.dismiss();
           
            }
        });
		
		upbtn = (ImageButton)this.findViewById(R.id.bcmsgsend_up_btn);
		upbtn.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {
            	depthnum++;
            	depthTxtv.setText(""+depthnum);
            }
        });
		downbtn = (ImageButton)this.findViewById(R.id.bcmsgsend_down_btn);
		downbtn.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {
            	if(depthnum>0){
            		depthnum--;
            		
            	}
 	
            	depthTxtv.setText(""+depthnum);
            }
        });
		
	
		
		
	}


}
