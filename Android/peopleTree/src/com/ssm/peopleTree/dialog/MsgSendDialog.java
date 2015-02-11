package com.ssm.peopleTree.dialog;


import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.ssm.peopleTree.R;










import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.Status;
import com.ssm.peopleTree.network.protocol.BroadcastMsgRequest;
import com.ssm.peopleTree.network.protocol.BroadcastMsgResponse;
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


public class MsgSendDialog extends Dialog  {
	Context mContext;
	
	
	ImageButton imgbtn_cacel,imgbtn_send;

	EditText edtxt;

	MemberData receiver;
	private Listener<JSONObject> onBroadcastMsgResponse;
	int depthnum = 1;
	public MsgSendDialog(Context context,MemberData receiver_) {
		super(context);
		this.receiver= receiver_;
		
		mContext = context;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_msgsend);
		
		onBroadcastMsgResponse = new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
	
				BroadcastMsgResponse res = new BroadcastMsgResponse(arg0);
				Status status = res.getStatus();
				String str;
				if (res.getStatus() == Status.SUCCESS) {

					str = "메시지전송 완료";

				} else {
					str = "메시지전송 실패";

				}

				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setTitle("알림")
						.setMessage(str)
						.setCancelable(true)
						// 뒤로 버튼 클릭시 취소 가능 설정
						.setPositiveButton("확인", new DialogInterface.OnClickListener() {
							// 확인 버튼 클릭시 설정
							public void onClick(DialogInterface dialog, int whichButton) {
								
								
								
								dialog.cancel();
								MsgSendDialog.this.dismiss();
							}

						});
				AlertDialog dialog = builder.create(); // 알림창 객체 생성
				dialog.show();
			
			}
		};
		
		
		
		edtxt = (EditText)this.findViewById(R.id.msgsend_editText);

		imgbtn_send= (ImageButton)this.findViewById(R.id.msgsend_send);
		
		imgbtn_send.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {
            	String msg = edtxt.getText().toString();
            	int rid = receiver.groupMemberId;
    
          
   
            	BroadcastMsgRequest bcdr = new BroadcastMsgRequest(MyManager.getInstance().getGroupMemberId(),rid,600,msg);
            	
            	NetworkManager.getInstance().request(bcdr, onBroadcastMsgResponse, null);
            	
            }
        });
		
		
		imgbtn_cacel = (ImageButton)this.findViewById(R.id.msgsend_cancel);
		
		imgbtn_cacel.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {
            	MsgSendDialog.this.dismiss();
           
            }
        });
		

		
		
	}


}
