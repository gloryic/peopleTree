package com.ssm.peopleTree.dialog;


import com.ssm.peopleTree.Progressable;
import com.ssm.peopleTree.R;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.group.GroupManager;
import com.ssm.peopleTree.map.MapManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class ChildInfoDialog extends Dialog  {
	Context mContext;
	TextView reqTitleTxtv;
	String reqTitle;
	
	Button btn1;
	Button btn2;

	//private MemberData childData;
	
	public ChildInfoDialog(Context context) {
		super(context);
		mContext = context;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_group_childinfo);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("�˸�")
				.setMessage("��ġ�� ǥ���� �� �����ϴ�.")
				.setCancelable(true)
				// �ڷ� ��ư Ŭ���� ��� ���� ����
				.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
					// Ȯ�� ��ư Ŭ���� ����
					public void onClick(DialogInterface dialog, int whichButton) {
						
						

						dialog.cancel();
					}

				});
		final AlertDialog dialog = builder.create(); // �˸�â ��ü ����	

		btn1 =(Button)this.findViewById(R.id.childInfoDialog_btn1);
		
		btn1.setText("��ġ����");
		btn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Progressable p = (Progressable)mContext;
				if (p != null && MapManager.getInstance().hasValidLocation()) {
					p.progress();
				}
				else {
					dialog.show();
				}
			}
		});

	}

	public void setchildInfoTitle(String reqtitle){
		this.reqTitle = reqtitle;
		reqTitleTxtv = (TextView)this.findViewById(R.id.childInfoDialog_title);
	
		reqTitleTxtv.setText(reqtitle);
	}


	
}
