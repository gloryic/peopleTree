package com.ssm.peopleTree.UI;

import com.ssm.peopleTree.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class SettingLayoutController extends Fragment {
	Context mContext;
	
	Button logoutbtn;
	Button debug_btn1;
	Button debug_btn2;
	Button debug_btn3;
	Button debug_btn4;
	
	TextView debug_txtv1;
	TextView debug_txtv2;
	TextView debug_txtv3;
	TextView debug_txtv4;
	
	public SettingLayoutController(	Context context){
		this.mContext = context;
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.setting_layout, container, false);
		debug_txtv1 = (TextView)layout.findViewById(R.id.setting_dbg_txtv1);	
		debug_txtv2 = (TextView)layout.findViewById(R.id.setting_dbg_txtv2);	
		debug_txtv3 = (TextView)layout.findViewById(R.id.setting_dbg_txtv3);	
		debug_txtv4 = (TextView)layout.findViewById(R.id.setting_dbg_txtv4);	
		
		
		logoutbtn  = (Button)layout.findViewById(R.id.setting_logoutbtn);	
		debug_btn1  = (Button)layout.findViewById(R.id.setting_dbg_btn1);	
		debug_btn1  = (Button)layout.findViewById(R.id.setting_dbg_btn2);	
		debug_btn1  = (Button)layout.findViewById(R.id.setting_dbg_btn3);	
		debug_btn1  = (Button)layout.findViewById(R.id.setting_dbg_btn4);	
		

		

		return layout;
	}
}
