package com.ssm.peopleTree.UI;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.ssm.peopleTree.R;
import com.ssm.peopleTree.dialog.GroupReqDialog;
import com.ssm.peopleTree.dialog.MsgSendDialog;

public class BroadCastLayoutController extends Fragment {

	Context mContext;
	ListView bclv;
	CheckBox bclv_chkbox1;
	boolean bclv_chkval = false;
	ImageButton imgBtn_bclist_delete;
	ImageButton imgBtn_bclist_send;

	MsgSendDialog msgSendDialog;

	BroadCastListViewCustomAdapter bclvca;

	public BroadCastLayoutController(Context context) {
		this.mContext = context;
	}

	public void setListAdapter(BroadCastListViewCustomAdapter adap) {
		this.bclvca = adap;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.broadcastlist_layout, container, false);
		bclv = (ListView) layout.findViewById(R.id.bcList);
		bclv.setAdapter(bclvca);
		bclv_chkbox1 = (CheckBox) layout
				.findViewById(R.id.checkBox_bclsit_allselect);
		bclv_chkbox1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				bclv_chkval = isChecked;
				bclvca.chekingAllItem(isChecked);

			}

		});
		bclv_chkbox1.setChecked(bclv_chkval);

		imgBtn_bclist_send = (ImageButton) layout
				.findViewById(R.id.imgBtn_bclist_send);

		imgBtn_bclist_send.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {

				msgSendDialog = new MsgSendDialog(mContext);
				msgSendDialog.show();

			}
		});

		return layout;
	}

}
