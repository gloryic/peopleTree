package com.ssm.peopleTree.dialog;


import com.ssm.peopleTree.R;








import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;


public class MsgSendDialog extends Dialog  {
	Context mContext;
	
	
	ImageButton imgbtn_cacel;
	
	public MsgSendDialog(Context context) {
		super(context);

		
		mContext = context;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_msgsend);
		
		imgbtn_cacel = (ImageButton)this.findViewById(R.id.msgsend_cancel);
		
		imgbtn_cacel.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {
            	MsgSendDialog.this.dismiss();
           
            }
        });
		
		
		
	}



}
