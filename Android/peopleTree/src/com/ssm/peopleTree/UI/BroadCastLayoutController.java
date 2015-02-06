package com.ssm.peopleTree.UI;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.ssm.peopleTree.R;
import com.ssm.peopleTree.dialog.GroupReqDialog;
import com.ssm.peopleTree.dialog.MsgSendDialog;

public class BroadCastLayoutController {
	Context mContext;

	ListView bclv;
	CheckBox bclv_chkbox1;
	boolean bclv_chkval = false;
	ImageButton imgBtn_bclist_delete;
	ImageButton imgBtn_bclist_send;
	
	MsgSendDialog msgSendDialog;
	
	BroadCastListViewCustomAdapter bclvca;
	
	public BroadCastLayoutController(Context context) {
		this.mContext = context;
		
		
	}
	
	public void setupLayout(BroadCastListViewCustomAdapter adap){
		this.bclvca = adap;
		bclv = (ListView) ((Activity)mContext).findViewById(R.id.bcList);
		bclv.setAdapter(bclvca);
		
		bclv_chkbox1 = (CheckBox) ((Activity)mContext).findViewById(R.id.checkBox_bclsit_allselect);
		bclv_chkbox1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				bclv_chkval = isChecked;
				bclvca.chekingAllItem(isChecked);
				
			}
			

		});
		bclv_chkbox1.setChecked(bclv_chkval);
		
		imgBtn_bclist_send = (ImageButton) ((Activity)mContext).findViewById(R.id.imgBtn_bclist_send);
		
		imgBtn_bclist_send.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {
            	
            	msgSendDialog = new MsgSendDialog(mContext);
            	msgSendDialog.show();
  
            	/*
            	LinearLayout contentsLayout;
            	LayoutInflater inflater;
            	contentsLayout = (LinearLayout) ((Activity)mContext).findViewById(R.id.broadcastlist_layout);
        		inflater = (LayoutInflater) ((Activity)mContext).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	   	contentsLayout.removeAllViews();
        		inflater.inflate(R.layout.msg_send_layout,contentsLayout,true );	
        		*/
            }
        });
		
	}
}
