package com.ssm.peopleTree.dialog;


import com.ssm.peopleTree.R;









import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class MsgSendDialog extends Dialog  {
	Context mContext;
	
	
	ImageButton imgbtn_cacel,imgbtn_send;
	ImageButton upbtn, downbtn;
	EditText edtxt;
	TextView depthTxtv;
	
	int depthnum = 0;
	public MsgSendDialog(Context context) {
		super(context);

		
		mContext = context;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_msgsend);
		
		
		edtxt = (EditText)this.findViewById(R.id.msgsend_editText);
		depthTxtv = (TextView)this.findViewById(R.id.msgsend_depth_txtv);
		depthTxtv.setText(""+depthnum);
		imgbtn_send= (ImageButton)this.findViewById(R.id.msgsend_send);
		
		imgbtn_send.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {
            	
            	
            }
        });
		
		
		imgbtn_cacel = (ImageButton)this.findViewById(R.id.msgsend_cancel);
		
		imgbtn_cacel.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {
            	MsgSendDialog.this.dismiss();
           
            }
        });
		
		upbtn = (ImageButton)this.findViewById(R.id.msgsend_up_btn);
		upbtn.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {
            	depthnum++;
            	depthTxtv.setText(""+depthnum);
            }
        });
		downbtn = (ImageButton)this.findViewById(R.id.msgsend_down_btn);
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
