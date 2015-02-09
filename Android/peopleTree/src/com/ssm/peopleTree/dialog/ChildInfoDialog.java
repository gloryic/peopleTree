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
		builder.setTitle("알림")
				.setMessage("위치를 표시할 수 없습니다.")
				.setCancelable(true)
				// 뒤로 버튼 클릭시 취소 가능 설정
				.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					// 확인 버튼 클릭시 설정
					public void onClick(DialogInterface dialog, int whichButton) {
						
						

						dialog.cancel();
					}

				});
		final AlertDialog dialog = builder.create(); // 알림창 객체 생성	

		btn1 =(Button)this.findViewById(R.id.childInfoDialog_btn1);
		
		btn1.setText("위치보기");
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
