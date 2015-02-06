package com.ssm.peopleTree.UI;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
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

	Button btn1;
	Button btn2;
	
	public RequestLayoutController(Context context){
		this.mContext = context;
		
	}
	
	public void setupLayout(RequestListViewCustomAdapter upAdap,RequestListViewCustomAdapter downAdap){
		this.upRqlvca = upAdap;
		this.downRqlvca = downAdap;
		reqlv = (ListView) ((Activity)mContext).findViewById(R.id.rqList);
		
		
		
		
		btn1 = (Button) ((Activity)mContext).findViewById(R.id.reqlist_btn1);	
		btn2 = (Button) ((Activity)mContext).findViewById(R.id.reqlist_btn2);
		
		btn1.setText("위에서");
		btn2.setText("아래에서");
		
		btn1.setOnClickListener(new View.OnClickListener() {
 			 
            public void onClick(View arg0) {

            	
            	reqlv = (ListView) ((Activity)mContext).findViewById(R.id.rqList);
        		//#d3F1F5
        
            	reqlv.setAdapter(upRqlvca);
            	btn1.setBackgroundColor(Color.parseColor("#A0E5E5"));
            	btn2.setBackgroundColor(Color.parseColor("#E5F5F5"));
        		
            }
        });
		btn2.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {


            	reqlv = (ListView) ((Activity)mContext).findViewById(R.id.rqList);
            	reqlv.setAdapter(downRqlvca);
            	
             	btn1.setBackgroundColor(Color.parseColor("#E5F5F5"));
            	btn2.setBackgroundColor(Color.parseColor("#A0E5E5"));
            }
        });
		
		btn1.callOnClick();
		}
	

	

}
