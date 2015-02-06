package com.ssm.peopleTree;

import java.util.ArrayList;

import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.ssm.peopleTree.UI.BroadCastLayoutController;
import com.ssm.peopleTree.UI.BroadCastListViewCustomAdapter;
import com.ssm.peopleTree.UI.GroupListController;
import com.ssm.peopleTree.UI.GroupListviewCustomAdapter;
import com.ssm.peopleTree.UI.PushMessageListViewCustomAdapter;
import com.ssm.peopleTree.UI.PushmsgLayoutController;
import com.ssm.peopleTree.UI.RequestLayoutController;
import com.ssm.peopleTree.UI.RequestListViewCustomAdapter;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.Status;
import com.ssm.peopleTree.network.protocol.GetUserInfoRequest;
import com.ssm.peopleTree.network.protocol.GetUserInfoResponse;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class TestActivity extends Activity {

	LinearLayout contentsLayout;
	LayoutInflater inflater;

	
	Button mf_btn1;
	Button mf_btn2;
	Button mf_btn3;
	Button mf_btn4;
	
	
	PushmsgLayoutController pushmsgLayoutController;
	PushMessageListViewCustomAdapter pmlvca;
	BroadCastLayoutController broadCastLayoutController;
	BroadCastListViewCustomAdapter bclvca;
	RequestLayoutController requestLayoutController;
	RequestListViewCustomAdapter upRqlvca;
	RequestListViewCustomAdapter downRqlvca;
	GroupListController groupListController;
	GroupListviewCustomAdapter glvca;
	
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		setContentView(R.layout.mframe);
		contentsLayout = (LinearLayout) findViewById(R.id.contentsLayout1);
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mf_btn1 = (Button) findViewById(R.id.mframe_btn1);
		mf_btn2 = (Button) findViewById(R.id.mframe_btn2);
		mf_btn3 = (Button) findViewById(R.id.mframe_btn3);	
		mf_btn4 = (Button) findViewById(R.id.mframe_btn4);
		mf_btn1.setText("그룹");
		mf_btn2.setText("요청");
		mf_btn3.setText("메시지");
		mf_btn4.setText("알림");
		
		upRqlvca = new RequestListViewCustomAdapter(this);
		for(int i=0;i<2;i++){
			upRqlvca.addItem("uprq"+i);
		}
		
		
		downRqlvca = new RequestListViewCustomAdapter(this);
		for(int i=0;i<4;i++){
			downRqlvca.addItem("down rq "+i);
		}
		
		
		
		

		bclvca = new BroadCastListViewCustomAdapter(this);
		for(int i=0;i<32000;i++){
			bclvca.addItem("broadcast list ^^"+i, i, "aa"+i, "bb"+i, "cc"+i);
		}
		
		
		glvca = new GroupListviewCustomAdapter(this);
		for(int i=0;i<32;i++){
			MemberData md = new MemberData();
			md.setUserName("name"+i);
			glvca.addItem(md);
		}
		
		pmlvca = new PushMessageListViewCustomAdapter(this);
		for(int i=0;i<32000;i++){
			pmlvca.addItem("testtxt"+i, "push message"+i, "");
		}
		
		
		requestLayoutController = new RequestLayoutController(this);
		broadCastLayoutController = new BroadCastLayoutController(this);
		pushmsgLayoutController = new PushmsgLayoutController(this);
		groupListController = new GroupListController(this);
		
		
		
		
		mf_btn1.setOnClickListener(new View.OnClickListener() {
			 
			 
            public void onClick(View arg0) {
            
            	contentsLayout.removeAllViews();
        		inflater.inflate(R.layout.grouplist_layout,contentsLayout,true );
        		groupListController.setupLayout(glvca);
        		
        		mf_btn1.setBackgroundColor(Color.parseColor("#BBEEAA"));
        		mf_btn2.setBackgroundColor(Color.parseColor("#EEFFEE"));
        		mf_btn3.setBackgroundColor(Color.parseColor("#EEFFEE"));
        		mf_btn4.setBackgroundColor(Color.parseColor("#EEFFEE"));
        		
        		
            }
        });
		mf_btn2.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {

            	contentsLayout.removeAllViews();
        		inflater.inflate(R.layout.requestlist,contentsLayout,true );
        		requestLayoutController.setupLayout(upRqlvca, downRqlvca);
           		mf_btn1.setBackgroundColor(Color.parseColor("#EEFFEE"));
        		mf_btn2.setBackgroundColor(Color.parseColor("#BBEEAA"));
        		mf_btn3.setBackgroundColor(Color.parseColor("#EEFFEE"));
        		mf_btn4.setBackgroundColor(Color.parseColor("#EEFFEE"));
            }
        });
		mf_btn3.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {
            	Log.i("log","btn3 clicked");
            	contentsLayout.removeAllViews();
        		inflater.inflate(R.layout.broadcastlist_layout,contentsLayout,true );		
        		broadCastLayoutController.setupLayout(bclvca);
           		mf_btn1.setBackgroundColor(Color.parseColor("#EEFFEE"));
        		mf_btn2.setBackgroundColor(Color.parseColor("#EEFFEE"));
        		mf_btn3.setBackgroundColor(Color.parseColor("#BBEEAA"));
        		mf_btn4.setBackgroundColor(Color.parseColor("#EEFFEE"));
            }
        });
		
		mf_btn4.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {
            	contentsLayout.removeAllViews();
        		inflater.inflate(R.layout.pushmessagelist_layout,contentsLayout,true );
        		pushmsgLayoutController.setupLayout(pmlvca);
        		mf_btn1.setBackgroundColor(Color.parseColor("#EEFFEE"));
        		mf_btn2.setBackgroundColor(Color.parseColor("#EEFFEE"));
        		mf_btn3.setBackgroundColor(Color.parseColor("#EEFFEE"));
        		mf_btn4.setBackgroundColor(Color.parseColor("#BBEEAA"));
        		
            }
        });
		mf_btn1.callOnClick();
	}
		
	
}
