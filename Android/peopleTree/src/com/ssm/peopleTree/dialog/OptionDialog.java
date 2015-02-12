package com.ssm.peopleTree.dialog;

import com.ssm.peopleTree.R;
import com.ssm.peopleTree.TestActivity;
import com.ssm.peopleTree.group.GroupManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

public class OptionDialog extends Dialog implements View.OnClickListener {

	private Context mContext;
	
	private AlertDialog alertDialog;
	
	public OptionDialog(Context context) {
		super(context);

		this.mContext = context;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("경고")
			.setMessage("로그아웃 하시겠습니까?")
			.setCancelable(true)
			.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			})
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					((TestActivity)mContext).logout();
					dialog.cancel();
				}

			});
		alertDialog = builder.create();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.menu_option_layout);

		Button btn = (Button) findViewById(R.id.btn_close);
		if (btn != null) {
			btn.setOnClickListener(this);
		}

		RelativeLayout layout = (RelativeLayout)findViewById(R.id.logout_layout);
		layout.setOnClickListener(this);
		layout = (RelativeLayout)findViewById(R.id.refresh_layout);
		layout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_close:
			dismiss();
			break;
		case R.id.logout_layout:
			alertDialog.show();
			break;
		case R.id.refresh_layout:
			GroupManager.getInstance().updateSelf();
        	GroupManager.getInstance().navigateHome();
			break;
		default:
			break;
		}
	}
}
