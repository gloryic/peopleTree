package com.ssm.peopleTree.UI;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.RelativeLayout;

import com.ssm.peopleTree.R;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.dialog.GroupReqDialog;
import com.ssm.peopleTree.dialog.MyMenuDialog;
import com.ssm.peopleTree.dialog.ParentInfoDialog;
import com.ssm.peopleTree.dialog.SearchUserDialog;
import com.ssm.peopleTree.group.GroupManager;

public class GroupListController extends Fragment implements Observer {
	Context mContext;

	
	LayoutInflater inflater;


	GroupListviewCustomAdapter glvca;
	ListView glv;
	
	
	ImageView mmenu;
	
	ImageButton parentAddBtn;
	ImageButton childAddBtn;
	
	GroupReqDialog groupReqDialog;
	SearchUserDialog searchUserDialog;
	MyMenuDialog myMenuDialog;
	ParentInfoDialog parentInfoDialog;
	
	private RelativeLayout layout;
	
	public GroupListController(Context context, Observable observable) {
		this.mContext = context;
		observable.addObserver(this);
	}
	
	public void setListAdapter(GroupListviewCustomAdapter adap){
		this.glvca = adap;
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		layout = (RelativeLayout)inflater.inflate(R.layout.grouplist_layout, container, false);

		glv = (ListView) layout.findViewById(R.id.groupList);
		glv.setAdapter(glvca);

		this.inflater = inflater;
		
		setParent(GroupManager.getInstance().getParent());
		setCur(GroupManager.getInstance().getCur());

		childAddBtn = (ImageButton) layout.findViewById(R.id.imgbtn_childadder);
		childAddBtn.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {
            	searchUserDialog = new SearchUserDialog(mContext);
            	searchUserDialog.setMode(SearchUserDialog.CHILD_ADD_MODE);
				searchUserDialog.show();
			}
		});
		
		mmenu = (ImageView)layout.findViewById(R.id.grouplist_mymenu);
		mmenu.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				myMenuDialog = new MyMenuDialog(mContext);

				myMenuDialog.setMytitle("메뉴");
				myMenuDialog.show();
				
			}
		});
		

		return layout;
	}	

	private void setCur(MemberData myData) {
		RelativeLayout curLayout = (RelativeLayout) layout.findViewById(R.id.groupcur_layout);
		curLayout.removeAllViews();
		
		inflater.inflate(R.layout.grouplist_cur, curLayout, true);
		
		TextView myNum = (TextView)curLayout.findViewById(R.id.grouplist_cur_num);
		if (myNum != null) {
			String myNumStr = "";
			if (myData != null && myData.managingTotalNumber > 0) {
				myNumStr = myData.managingNumber + "/" + myData.managingTotalNumber;
			}
			myNum.setText(myNumStr);
		}
		
		TextView myName = (TextView)curLayout.findViewById(R.id.grouplist_cur_name);
		if (myName != null) {
			String myNameStr = "";
			if (myData != null) {
				myNameStr = myData.userName;
			}
			myName.setText(myNameStr);
		}
		

		

		curLayout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {

					
				}
			});
	}

	private void setParent(MemberData parentData) {
		RelativeLayout parentLayout = (RelativeLayout) layout.findViewById(R.id.groupParent_layout);
		final MemberData fparentData = parentData;
		parentLayout.removeAllViews();
		if(parentData == null){
			inflater.inflate(R.layout.grouplist_parent_adder,parentLayout,true);
			parentAddBtn = (ImageButton)parentLayout.findViewById(R.id.parent_add_btn);
			parentAddBtn.setOnClickListener(new View.OnClickListener() {
				
	            public void onClick(View arg0) {
	            	searchUserDialog = new SearchUserDialog(mContext);
	            	searchUserDialog.setMode(SearchUserDialog.PARENT_ADD_MODE);
	            	searchUserDialog.show();
	            }
	        });
			
		}
		else {
			inflater.inflate(R.layout.grouplist_parent,parentLayout,true);
			
			TextView parentName = (TextView)parentLayout.findViewById(R.id.parent_name);
			if (parentName != null) {
				String parentNameStr = "";
				if (parentData != null) {
					parentNameStr = parentData.userName;
				}
				parentName.setText(parentNameStr);
			}
			
			ImageButton parentBtn = (ImageButton)parentLayout.findViewById(R.id.parent_btn);			
			if (parentBtn != null) {
				parentBtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
				
						parentInfoDialog = new ParentInfoDialog(mContext);
						parentInfoDialog.show();
						parentInfoDialog.setParentData(fparentData);
		
						
					}
				});
			}

			parentLayout.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						MemberData mydata = MyManager.getInstance().getMyData();
						
						if(mydata.parentGroupMemberId!= fparentData.groupMemberId ||mydata.groupMemberId ==fparentData.groupMemberId   ){
							
							GroupManager.getInstance().update(fparentData.groupMemberId);
							
						}else{
							
							AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
							builder.setTitle("알림")
									.setMessage("자신의 상위 그룹 보기는 금지되어있습니다.")
									.setCancelable(true)
									// 뒤로 버튼 클릭시 취소 가능 설정
									.setPositiveButton("확인", new DialogInterface.OnClickListener() {
										// 확인 버튼 클릭시 설정
										public void onClick(DialogInterface dialog, int whichButton) {
											
											

											dialog.cancel();
										}

									});
							AlertDialog dialog = builder.create(); // 알림창 객체 생성
							dialog.show();
						}
					}
				});
			
		}
	}

	@Override
	public void update(Observable observable, Object data) {
		GroupManager groupManager = (GroupManager)observable;	
		if (groupManager == null) {
			return;
		}
		
		setParent(groupManager.getParent());
		setCur(groupManager.getCur());
		if (glv != null) {
			glv.removeAllViewsInLayout();
		}
	}
}
