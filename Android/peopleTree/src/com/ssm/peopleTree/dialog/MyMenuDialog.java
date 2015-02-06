package com.ssm.peopleTree.dialog;


import com.ssm.peopleTree.R;




import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.Button;


public class MyMenuDialog extends Dialog  {


	Button btn1;
	Button btn2;
	public MyMenuDialog(Context context) {
		
		super(context);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_group_mymenu);
		btn1 =(Button)this.findViewById(R.id.mymenuDialog_Btn1);
		btn1.setText("지역설정");
		btn2 =(Button)this.findViewById(R.id.mymenuDialog_Btn2);
		btn2.setText("그룹 나가기");
		
	}

	


}
