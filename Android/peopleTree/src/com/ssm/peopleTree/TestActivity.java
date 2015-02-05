package com.ssm.peopleTree;

import java.util.ArrayList;

import org.json.JSONObject;

import com.android.volley.Response.Listener;

import com.ssm.peopleTree.UI.BroadCastListViewCustomAdapter;
import com.ssm.peopleTree.UI.GroupListviewCustomAdapter;
import com.ssm.peopleTree.UI.PushMessageListViewCustomAdapter;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ListView;

public class TestActivity extends Activity {
	
	
	PushMessageListViewCustomAdapter pmlvca;
	BroadCastListViewCustomAdapter bclvca;
	GroupListviewCustomAdapter glvca;
	
	
	LinearLayout contentsLayout;
	LayoutInflater inflater;
	ListView pmlv;
	ListView bclv;
	ListView glv;
	
	ImageButton frameImgBtn1;
	ImageButton frameImgBtn2;
	ImageButton frameImgBtn3;
	ImageButton frameImgBtn4;
	
	
	RequestListViewCustomAdapter upRqlvca;
	RequestListViewCustomAdapter downRqlvca;

	ListView reqlv;

	ImageButton reqImgBtn1;
	ImageButton reqImgBtn2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		setContentView(R.layout.mframe);
		contentsLayout = (LinearLayout) findViewById(R.id.contentsLayout1);
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		frameImgBtn1 = (ImageButton) findViewById(R.id.mframe_ImgBtn1);	
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
		for(int i=0;i<32;i++){
			bclvca.addItem("broadcast list ^^"+i, i, "aa"+i, "bb"+i, "cc"+i);
		}
		pmlvca = new PushMessageListViewCustomAdapter(this);
		for(int i=0;i<32;i++){
			pmlvca.addItem("testtxt"+i, "push message"+i, "");
		}
		
		glvca = new GroupListviewCustomAdapter(this);
		for(int i=0;i<32;i++){
			MemberData md = new MemberData();
			md.setUserName("name"+i);
			glvca.addItem(md);
		}
		
		
		frameImgBtn1.setOnClickListener(new View.OnClickListener() {
			 
			 
            public void onClick(View arg0) {
            
            	contentsLayout.removeAllViews();
        		inflater.inflate(R.layout.grouplist_layout,contentsLayout,true );	
        		glv = (ListView) findViewById(R.id.groupList);
        		glv.setAdapter(glvca);
        		
            }
        });
		frameImgBtn2.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {

            	contentsLayout.removeAllViews();
        		inflater.inflate(R.layout.requestlist,contentsLayout,true );
        		reqImgBtn1 = (ImageButton) findViewById(R.id.reqFromUp_imgbtn);	
        		reqImgBtn2 = (ImageButton) findViewById(R.id.reqFromDown_imgbtn);
        		reqImgBtn1.setOnClickListener(new View.OnClickListener() {
         			 
                    public void onClick(View arg0) {

                    	
                    	reqlv = (ListView) findViewById(R.id.rqList);
                		
                		
                		
                    	reqlv.setAdapter(upRqlvca);
                    }
                });
        		reqImgBtn2.setOnClickListener(new View.OnClickListener() {
        			 
                    public void onClick(View arg0) {


                    	reqlv = (ListView) findViewById(R.id.rqList);
                    	reqlv.setAdapter(downRqlvca);
                    }
                });
            }
        });
		frameImgBtn3.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {
            	Log.i("log","btn3 clicked");
            	contentsLayout.removeAllViews();
        		inflater.inflate(R.layout.broadcastlist_layout,contentsLayout,true );	
        		bclv = (ListView) findViewById(R.id.bcList);
        		bclv.setAdapter(bclvca);
        		
            }
        });
		
		frameImgBtn4.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {
            	Log.i("log","btn4 clicked");

            	contentsLayout.removeAllViews();
        		inflater.inflate(R.layout.pushmessagelist_layout,contentsLayout,true );
        		
        		pmlv = (ListView) findViewById(R.id.pmList);
        		
        		
        		
        		pmlv.setAdapter(pmlvca);

       
            }
        });
	}
		
	
}
