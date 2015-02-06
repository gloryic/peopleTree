package com.ssm.peopleTree.UI;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import com.ssm.peopleTree.R;
import com.ssm.peopleTree.dialog.GroupReqDialog;
import com.ssm.peopleTree.dialog.MyMenuDialog;

public class GroupListController {
	Context mContext;

	RelativeLayout contentsLayout;
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
		
		contentsLayout = (RelativeLayout) ((Activity)mContext).findViewById(R.id.groupParent_layout);
		inflater = (LayoutInflater) ((Activity)mContext).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		contentsLayout.removeAllViews();
		if(testVal_isParentExist){
			inflater.inflate(R.layout.grouplist_parent_adder,contentsLayout,true );
			
			parentAddBtn = (ImageButton) ((Activity)mContext).findViewById(R.id.parent_add_btn);
			parentAddBtn.setOnClickListener(new View.OnClickListener() {
				 
	            public void onClick(View arg0) {
	            	groupReqDialog = new GroupReqDialog(mContext);
	            	groupReqDialog.show();
	            	groupReqDialog.setReqTitle("부모추가 대화상자");
	            }
	        });
			
			
		}else{
			inflater.inflate(R.layout.grouplist_parent,contentsLayout,true );
		}
		setCur();
		
		childAddBtn = (ImageButton) ((Activity)mContext).findViewById(R.id.imgbtn_childadder);
		childAddBtn.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {
            	groupReqDialog = new GroupReqDialog(mContext);
				groupReqDialog.show();
				groupReqDialog.setReqTitle("자식추가 대화상자");
			}
		});
	}

	public void setCur() {
		
		contentsLayout = (RelativeLayout) ((Activity)mContext).findViewById(R.id.groupcur_layout);
		inflater = (LayoutInflater) ((Activity)mContext).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.grouplist_cur, contentsLayout, true);

		curBtn = (ImageButton) ((Activity) mContext)
				.findViewById(R.id.grouplist_cur_btn);
		curBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				myMenuDialog = new MyMenuDialog(mContext);

				myMenuDialog.show();
			}
		});
	}
}
