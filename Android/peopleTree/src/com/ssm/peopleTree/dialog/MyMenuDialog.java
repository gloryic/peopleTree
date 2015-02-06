package com.ssm.peopleTree.dialog;


import com.ssm.peopleTree.R;



import android.app.Dialog;
import android.content.Context;
import android.view.Window;


public class MyMenuDialog extends Dialog  {



	public MyMenuDialog(Context context) {
		
		super(context);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_group_mymenu);
		
	}

	


}
