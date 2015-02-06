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

public class RequestLayoutController {
	Context mContext;


	
	RequestListViewCustomAdapter upRqlvca;
	RequestListViewCustomAdapter downRqlvca;
	
	ListView reqlv;

	ImageButton reqImgBtn1;
	ImageButton reqImgBtn2;
	
	public RequestLayoutController(Context context){
		this.mContext = context;
		
	}
	
	public void setupLayout(RequestListViewCustomAdapter upAdap,RequestListViewCustomAdapter downAdap){
		this.upRqlvca = upAdap;
		this.downRqlvca = downAdap;
		reqlv = (ListView) ((Activity)mContext).findViewById(R.id.rqList);
		
		
		
		
		reqImgBtn1 = (ImageButton) ((Activity)mContext).findViewById(R.id.reqFromUp_imgbtn);	
		reqImgBtn2 = (ImageButton) ((Activity)mContext).findViewById(R.id.reqFromDown_imgbtn);
		reqImgBtn1.setOnClickListener(new View.OnClickListener() {
 			 
            public void onClick(View arg0) {

            	
            	reqlv = (ListView) ((Activity)mContext).findViewById(R.id.rqList);
        		
        		
        		
            	reqlv.setAdapter(upRqlvca);
            }
        });
		reqImgBtn2.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {


            	reqlv = (ListView) ((Activity)mContext).findViewById(R.id.rqList);
            	reqlv.setAdapter(downRqlvca);
            }
        });
		
		reqImgBtn1.callOnClick();
		}
	

	

}
