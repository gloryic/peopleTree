package com.ssm.peopleTree.UI;

import com.ssm.peopleTree.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;


public class SettingLayoutController extends Fragment {
	Context mContext;
	public SettingLayoutController(	Context context){
		this.mContext = context;
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.setting_layout, container, false);

		

		return layout;
	}
}
