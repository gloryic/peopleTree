package com.ssm.peopleTree.dialog;


import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.ssm.peopleTree.R;








import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.group.GroupManager;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.Status;
import com.ssm.peopleTree.network.protocol.GetInfoAllRequest;
import com.ssm.peopleTree.network.protocol.GetInfoAllResponse;
import com.ssm.peopleTree.network.protocol.SearchMemberRequest;
import com.ssm.peopleTree.network.protocol.SearchMemberResponse;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class SearchUserDialog extends Dialog  {
	Context mContext;
	SearchUserDialogListViewAdapter sudlva;
	
	ListView lv;
	EditText editText;
	Button searchBtn;
	TextView searchNumTxtv;
	TextView titleTxtv;
	CheckBox chkbx;
	
	public static final int NOT_VALID_MODE =0;
	public static final int PARENT_ADD_MODE =1;
	public static final int CHILD_ADD_MODE =2;
	public static final int MSG_SEND_MODE =3;
	int mode = NOT_VALID_MODE;
	
	
	private Listener<JSONObject> onSearchMemberResponse;
	
	public SearchUserDialog(Context context) {
		super(context);
		mContext = context;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_searchuser);
		lv = (ListView) this.findViewById(R.id.searchuser_listview);
		chkbx = (CheckBox)this.findViewById(R.id.searchuser_chkbx);
		
		sudlva = new SearchUserDialogListViewAdapter(mContext);
		sudlva.setParent(this);
		lv.setAdapter(sudlva);
	
		
		onSearchMemberResponse = new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
	
				SearchMemberResponse res = new SearchMemberResponse(arg0);
				Status status = res.getStatus();
				
				if (res.getStatus() == Status.SUCCESS) {
						
						sudlva.setmListData(res.groupMembersInfo);

				}
				else {
					sudlva.clear();
				}
				searchNumTxtv.setText(""+sudlva.getCount()+"Έν");
			}
		};
		
		
		searchBtn= (Button) this.findViewById(R.id.searchuser_searchbtn);
		searchNumTxtv = (TextView) this.findViewById(R.id.searchuser_seachnum_txtv);
		editText = (EditText)this.findViewById(R.id.searchuser_edittxt);
		
		searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	sudlva.clear();
            	
            	String qstr= editText.getText().toString();
            	NetworkManager.getInstance().request(new SearchMemberRequest(qstr), onSearchMemberResponse, null);
            	
            	
            }
        });

		
	
		

	}
	public void setMode(int m){
		this.mode = m;
		if(mode == MSG_SEND_MODE){
			chkbx.setVisibility(View.INVISIBLE);
		}
		
	}
	public void setTitle(String title){
		
		titleTxtv = (TextView)this.findViewById(R.id.searchuser_title_txtv);
		titleTxtv.setText(title);
	}
	public int getMode(){
		return this.mode;
	}



}


