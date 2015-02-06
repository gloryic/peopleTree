package com.ssm.peopleTree.UI;

import com.ssm.peopleTree.R;

import android.app.Activity;
import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class PushmsgLayoutController{
	Context mContext;
	
	ListView pmlv;
	CheckBox pmlv_chkbox1;
	boolean pmlv_chkval = false;
	PushMessageListViewCustomAdapter pmlvca;
	public PushmsgLayoutController(Context context) {
		this.mContext = context;
		
		
	}
	
	public void setupLayout(PushMessageListViewCustomAdapter adap){
		this.pmlvca = adap;
		pmlv = (ListView) ((Activity)mContext).findViewById(R.id.pmList);
		pmlv.setAdapter(pmlvca);
		pmlv_chkbox1 = (CheckBox) ((Activity)mContext).findViewById(R.id.checkBox_pmlist_allselect);
		
		
		pmlv_chkbox1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
					pmlv_chkval = isChecked;
					pmlvca.chekingAllItem(isChecked);
				
			}
			

		});
		pmlv_chkbox1.setChecked(pmlv_chkval);
	}

}
