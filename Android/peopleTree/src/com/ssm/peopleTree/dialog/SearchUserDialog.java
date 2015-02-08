package com.ssm.peopleTree.dialog;


import com.ssm.peopleTree.R;








import com.ssm.peopleTree.data.MemberData;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
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
	
	
	public static final int NOT_VALID_MODE =0;
	public static final int PARENT_ADD_MODE =1;
	public static final int CHILD_ADD_MODE =2;
	int mode = NOT_VALID_MODE;
	
	public SearchUserDialog(Context context) {
		super(context);
		mContext = context;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_searchuser);
		lv = (ListView) this.findViewById(R.id.searchuser_listview);
		sudlva = new SearchUserDialogListViewAdapter(mContext);
		sudlva.setParent(this);
		lv.setAdapter(sudlva);
	
		searchBtn= (Button) this.findViewById(R.id.searchuser_searchbtn);
		searchNumTxtv = (TextView) this.findViewById(R.id.searchuser_seachnum_txtv);
		searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	sudlva.clear();
            	
            	
            	
            	searchNumTxtv.setText(""+sudlva.getCount()+"명");
            }
        });

		
		/*테스트용
		 */
		for(int i=0;i<32;i++){
			MemberData tmp = new MemberData();
			tmp.setUserId("ididid"+i);
			tmp.setUserName("nmnmnm"+i);
			tmp.setUserPhoneNumber("phphph"+i);
			
			sudlva.addItem(tmp);
		}

	}
	public void setMode(int m){
		this.mode = m;
	}
	public int getMode(){
		return this.mode;
	}



}


