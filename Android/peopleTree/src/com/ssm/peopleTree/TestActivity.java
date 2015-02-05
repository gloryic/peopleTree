package com.ssm.peopleTree;

import java.util.ArrayList;

import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.Status;
import com.ssm.peopleTree.network.protocol.GetUserInfoRequest;
import com.ssm.peopleTree.network.protocol.GetUserInfoResponse;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ListView;

public class TestActivity extends Activity {
	
	private GroupListAdapter adapter;
	private ListView listView;
	private ArrayList<GroupListItem> itemList;
	
	private MyManager myManager;
	private NetworkManager networkManager;
	
	private Listener<JSONObject> getParentListener;
	private Listener<JSONObject> getChildrenListener;
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_group);
 
        myManager = MyManager.getInstance();
        networkManager = NetworkManager.getInstance();
        
        // 멤버 변수 초기화
        itemList = new ArrayList<GroupListItem>();
 
        // 어댑터와 리스트뷰 초기화
        adapter = new GroupListAdapter(this, itemList);
        listView = (ListView) findViewById(R.id.groupList);
 
        // 스크롤 리스너를 등록합니다. onScroll에 추가구현을 해줍니다.
        listView.setAdapter(adapter);
        
        TextView tvNum = (TextView)findViewById(R.id.my_num);
        TextView tvName = (TextView)findViewById(R.id.my_name);
        tvNum.setText(myManager.getManagingInfoStr());
        tvName.setText(myManager.getUserName());    

        getParentListener = new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				
				GetUserInfoResponse res = new GetUserInfoResponse(arg0);
				
				if (res.getStatus() == Status.SUCCESS) {
					showParent();
				}
			}
		};
		
		getChildrenListener = new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				// TODO Auto-generated method stub
				
			}
		};
        
        GetUserInfoRequest req = new GetUserInfoRequest(myManager.getParentGroupMemberId());
        networkManager.request(req, getParentListener, null);
        //networkManager.request(req, getChildrenListener, null);
        // TODO
        
        showChildList();
	}
	
	private void showParent() {
		   
        MemberData data = new MemberData();
        data.userName = "봉대장";
        data.managingNumber = 7;
        data.managingTotalNumber = 10;
        GroupListItem item = new GroupListItem(data, ItemType.PARENT);
        
        TextView tvNum = (TextView)findViewById(R.id.parent_num);
        TextView tvName = (TextView)findViewById(R.id.parent_name);
        tvNum.setText(item.getManagingInfoStr());
        tvName.setText(item.getUserName());   
	}
	
	private void showChildList() {
		  
		MemberData data = new MemberData();
        data.userName = "박진기";
        data.managingNumber = 1;
        data.managingTotalNumber = 1;
        GroupListItem item = new GroupListItem(data, ItemType.CHILD);
        itemList.add(item);
        
        data = new MemberData();
        data.userName = "이재환";
        data.managingNumber = 1;
        data.managingTotalNumber = 1;
        item = new GroupListItem(data, ItemType.CHILD);
        itemList.add(item);
        
        MemberData[] dataArr = new MemberData[100];
        GroupListItem[] itemArr = new GroupListItem[100];
        for (int i = 0; i < 100; i++) {
        	dataArr[i] = new MemberData();
        	dataArr[i].userName = "사람" + i;
        	dataArr[i].managingNumber = i;
        	dataArr[i].managingTotalNumber = 99;
        	itemArr[i] = new GroupListItem(dataArr[i], ItemType.CHILD);
        	itemList.add(itemArr[i]);
        }
	}
}
