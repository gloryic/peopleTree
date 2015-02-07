package com.ssm.peopleTree.UI;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import com.ssm.peopleTree.R;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.dialog.GroupReqDialog;
import com.ssm.peopleTree.dialog.MyMenuDialog;

public class GroupListController  extends Fragment{
	Context mContext;

	private RelativeLayout parentLayout;
	private RelativeLayout curLayout;
	LayoutInflater inflater;


	GroupListviewCustomAdapter glvca;
	ListView glv;
	
	
	ImageButton curBtn;
	
	ImageButton parentAddBtn;
	ImageButton childAddBtn;
	
	GroupReqDialog groupReqDialog;
	MyMenuDialog myMenuDialog;
	
	public GroupListController(Context context) {
		this.mContext = context;	

	}
	
	public void setListAdapter(GroupListviewCustomAdapter adap){
		this.glvca = adap;
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.grouplist_layout, container, false);
		glv = (ListView) layout.findViewById(R.id.groupList);
		glv.setAdapter(glvca);

		this.inflater = inflater;
		
		parentLayout = (RelativeLayout) layout.findViewById(R.id.groupParent_layout);
		curLayout = (RelativeLayout) layout.findViewById(R.id.groupcur_layout);
		inflater.inflate(R.layout.grouplist_cur, curLayout, true);

		setParent(null);
		setCur(null);
		

		childAddBtn = (ImageButton) layout.findViewById(R.id.imgbtn_childadder);
		childAddBtn.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {
            	groupReqDialog = new GroupReqDialog(mContext);
				groupReqDialog.show();
				groupReqDialog.setReqTitle("자식추가 대화상자");
			}
		});

		return layout;
	}	

	public void setCur(MemberData myData) {
		
		TextView myNum = (TextView)curLayout.findViewById(R.id.grouplist_cur_num);
		TextView myName = (TextView)curLayout.findViewById(R.id.grouplist_cur_name);
		
		if (myData != null) {
			if (myNum != null) {
				myNum.setText(myData.managingNumber + "/" + myData.managingTotalNumber);
			}
			
			if (myName != null) {
				myName.setText(myData.userName);	
			}
		}
		else {
			if (myNum != null) {
				myNum.setText("");
			}
			
			if (myName != null) {
				myName.setText("");	
			}
		}
		
		curBtn = (ImageButton)curLayout.findViewById(R.id.grouplist_cur_btn);
		curBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				myMenuDialog = new MyMenuDialog(mContext);

				myMenuDialog.show();
			}
		});
	}

	public void setParent(MemberData parentData) {
		parentLayout.removeAllViews();
		if(parentData == null){
			inflater.inflate(R.layout.grouplist_parent_adder,parentLayout,true);
			parentAddBtn = (ImageButton)parentLayout.findViewById(R.id.parent_add_btn);
			parentAddBtn.setOnClickListener(new View.OnClickListener() {
				
	            public void onClick(View arg0) {
	            	groupReqDialog = new GroupReqDialog(mContext);
	            	groupReqDialog.show();
	            	groupReqDialog.setReqTitle("부모추가 대화상자");
	            }
	        });
			
		}
		else {

			inflater.inflate(R.layout.grouplist_parent,parentLayout,true);


			TextView parentNum = (TextView)parentLayout.findViewById(R.id.parent_num);
			TextView parentName = (TextView)parentLayout.findViewById(R.id.parent_name);
			ImageButton parentBtn = (ImageButton)((Activity)mContext).findViewById(R.id.parent_btn);
			
			


			if (parentNum != null) {
				parentNum.setText(parentData.managingNumber + "/" + parentData.managingTotalNumber);
			}
			
			if (parentName != null) {
				parentName.setText(parentData.userName);	
			}
			
			if (parentBtn != null) {
				parentBtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		}
	}
}
