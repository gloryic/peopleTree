package com.ssm.peopleTree.UI;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.ssm.peopleTree.R;

public class RequestLayoutController extends Fragment {
	Context mContext;

	LinearLayout layout;
	
	RequestListViewCustomAdapter upRqlvca;
	RequestListViewCustomAdapter downRqlvca;
	
	ListView reqlv;

	Button btn1;
	Button btn2;
	
	public RequestLayoutController(Context context){
		this.mContext = context;
	}
	
	public void setListAdapter(RequestListViewCustomAdapter upAdap,RequestListViewCustomAdapter downAdap){
		this.upRqlvca = upAdap;
		this.downRqlvca = downAdap;
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		layout = (LinearLayout)inflater.inflate(R.layout.requestlist, container, false);
		btn1 = (Button) layout.findViewById(R.id.reqlist_btn1);	
		btn2 = (Button) layout.findViewById(R.id.reqlist_btn2);
		
		btn1.setText("위에서");
		btn2.setText("아래에서");
		
		btn1.setOnClickListener(new View.OnClickListener() {
 			 
            public void onClick(View arg0) {

            	btn1.setSelected(true);
            	btn2.setSelected(false);
            	
            	reqlv = (ListView) layout.findViewById(R.id.rqList);
        		//#d3F1F5
        
            	reqlv.setAdapter(upRqlvca);
 
        		
            }
        });
		btn2.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {
            	btn2.setSelected(true);
            	btn1.setSelected(false);
            	

            	reqlv = (ListView) layout.findViewById(R.id.rqList);
            	reqlv.setAdapter(downRqlvca);
            	
            }
        });
		
		btn1.callOnClick();
		

		return layout;
	}

	

	

}
