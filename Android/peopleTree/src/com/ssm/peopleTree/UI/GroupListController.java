package com.ssm.peopleTree.UI;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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

public class GroupListController {
	Context mContext;

	private RelativeLayout parentLayout;
	private RelativeLayout curLayout;
	LayoutInflater inflater;

	boolean testVal_isParentExist = true;

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
	
	public void setupLayout(GroupListviewCustomAdapter adap){
		this.glvca = adap;
		glv = (ListView) ((Activity)mContext).findViewById(R.id.groupList);
		glv.setAdapter(glvca);
		
		inflater = (LayoutInflater) ((Activity)mContext).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		parentLayout = (RelativeLayout) ((Activity)mContext).findViewById(R.id.groupParent_layout);
		curLayout = (RelativeLayout) ((Activity)mContext).findViewById(R.id.groupcur_layout);
		inflater.inflate(R.layout.grouplist_cur, curLayout, true);
		
		setParent(null);
		setCur(null);
		
		childAddBtn = (ImageButton) ((Activity)mContext).findViewById(R.id.imgbtn_childadder);
		childAddBtn.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {
            	groupReqDialog = new GroupReqDialog(mContext);
				groupReqDialog.show();
				groupReqDialog.setReqTitle("�ڽ��߰� ��ȭ����");
			}
		});
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
	            	groupReqDialog.setReqTitle("�θ��߰� ��ȭ����");
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
