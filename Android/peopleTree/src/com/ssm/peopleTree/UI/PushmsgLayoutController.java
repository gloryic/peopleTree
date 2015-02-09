package com.ssm.peopleTree.UI;

import com.ssm.peopleTree.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class PushmsgLayoutController extends Fragment{
	Context mContext;
	
	ListView pmlv;
	CheckBox pmlv_chkbox1;
	boolean pmlv_chkval = false;
	PushMessageListViewCustomAdapter pmlvca;
	ImageButton delete_btn;
	public PushmsgLayoutController(Context context) {
		this.mContext = context;
		
	}
	
	public void setListAdapter(PushMessageListViewCustomAdapter adap){
		pmlvca = adap;
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.pushmessagelist_layout, container, false);
		pmlv = (ListView) layout.findViewById(R.id.pmList);
		pmlv.setAdapter(pmlvca);
		
		pmlv_chkbox1 = (CheckBox) layout.findViewById(R.id.checkBox_pmlist_allselect);
		
		
		pmlv_chkbox1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
					pmlv_chkval = isChecked;
					pmlvca.chekingAllItem(isChecked);
				
			}
		
		});
		pmlv_chkbox1.setChecked(pmlv_chkval);
		
		
		delete_btn= (ImageButton) layout.findViewById(R.id.imgBtn_pmlist_delete);
		delete_btn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {

				pmlvca.removeChecked();
			
			}
		});
		return layout;
	}


}
