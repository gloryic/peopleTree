package com.ssm.peopleTree.dialog;


import com.ssm.peopleTree.Progressable;
import com.ssm.peopleTree.R;






import com.ssm.peopleTree.map.MapManager;
import com.ssm.peopleTree.map.ManageMode;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class MyMenuDialog extends Dialog  {


	Button btn1;
	Button btn2;
	
	private Context mContext;
	
	public MyMenuDialog(Context context) {
		super(context);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_group_mymenu);
		this.mContext = context;
				
		btn1 =(Button)this.findViewById(R.id.mymenuDialog_Btn1);
		btn1.setText("지역설정");
		btn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Progressable p = (Progressable)mContext;
				if (p != null) {
					MapManager.getInstance().setMode(ManageMode.GEOFENCE);
					p.progress();
				}
				else {
					
				}
			}
		});
		btn2 =(Button)this.findViewById(R.id.mymenuDialog_Btn2);
		btn2.setText("그룹 나가기");
		
	}

	


}
