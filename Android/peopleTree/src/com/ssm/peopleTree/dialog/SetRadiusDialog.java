package com.ssm.peopleTree.dialog;

import com.ssm.peopleTree.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class SetRadiusDialog extends Dialog {

	private EditText editText;
	private Button cancelBtn;
	private Button nextBtn;
	
	public SetRadiusDialog(Context context) {
		super(context);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.map_radius_layout);
		
		editText = (EditText)findViewById(R.id.edit_radius);
		
		cancelBtn = (Button)findViewById(R.id.btn_cancel);
		nextBtn = (Button)findViewById(R.id.btn_next);
	}
	
	public int getRadiusSetting() {
		try {
			Integer radius = Integer.parseInt(editText.getEditableText().toString());
			if (radius == null) {
				return 0;
			}
			else {
				return radius;
			}
		} catch (Exception e) {
			return 0;
		}
	}
	
	public void setOnClickLisener(View.OnClickListener onClickListener) {
		
		cancelBtn.setOnClickListener(onClickListener);
		nextBtn.setOnClickListener(onClickListener);
	}
}
