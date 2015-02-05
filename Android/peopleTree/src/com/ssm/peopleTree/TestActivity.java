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
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

	
	ImageButton frameImgBtn1;
	ImageButton frameImgBtn2;
	ImageButton frameImgBtn3;
	ImageButton frameImgBtn4;
	
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

		frameImgBtn1 = (ImageButton) findViewById(R.id.reqDialog_ImgBtn1);	
		frameImgBtn2 = (ImageButton) findViewById(R.id.mframe_ImgBtn2);
		frameImgBtn3 = (ImageButton) findViewById(R.id.mframe_ImgBtn3);	
		frameImgBtn4 = (ImageButton) findViewById(R.id.mframe_ImgBtn4);

		
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
		
		
		
		
		frameImgBtn1.setOnClickListener(new View.OnClickListener() {
			 
			 
            public void onClick(View arg0) {
            
            	contentsLayout.removeAllViews();
        		inflater.inflate(R.layout.grouplist_layout,contentsLayout,true );
        		groupListController.setupLayout(glvca);
        		
            }
        });
		frameImgBtn2.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {

            	contentsLayout.removeAllViews();
        		inflater.inflate(R.layout.requestlist,contentsLayout,true );
        		requestLayoutController.setupLayout(upRqlvca, downRqlvca);
      
            }
        });
		frameImgBtn3.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {
            	Log.i("log","btn3 clicked");
            	contentsLayout.removeAllViews();
        		inflater.inflate(R.layout.broadcastlist_layout,contentsLayout,true );		
        		broadCastLayoutController.setupLayout(bclvca);
            }
        });
		
		frameImgBtn4.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {
            	contentsLayout.removeAllViews();
        		inflater.inflate(R.layout.pushmessagelist_layout,contentsLayout,true );
        		pushmsgLayoutController.setupLayout(pmlvca);
        		
            }
        });
	}
		
	
}
