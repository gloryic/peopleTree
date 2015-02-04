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
        
        // ��� ���� �ʱ�ȭ
        itemList = new ArrayList<GroupListItem>();
 
        // ����Ϳ� ����Ʈ�� �ʱ�ȭ
        adapter = new GroupListAdapter(this, itemList);
        listView = (ListView) findViewById(R.id.groupList);
 
        // ��ũ�� �����ʸ� ����մϴ�. onScroll�� �߰������� ���ݴϴ�.
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
        data.userName = "������";
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
        data.userName = "������";
        data.managingNumber = 1;
        data.managingTotalNumber = 1;
        GroupListItem item = new GroupListItem(data, ItemType.CHILD);
        itemList.add(item);
        
        data = new MemberData();
        data.userName = "����ȯ";
        data.managingNumber = 1;
        data.managingTotalNumber = 1;
        item = new GroupListItem(data, ItemType.CHILD);
        itemList.add(item);
        
        MemberData[] dataArr = new MemberData[100];
        GroupListItem[] itemArr = new GroupListItem[100];
        for (int i = 0; i < 100; i++) {
        	dataArr[i] = new MemberData();
        	dataArr[i].userName = "���" + i;
        	dataArr[i].managingNumber = i;
        	dataArr[i].managingTotalNumber = 99;
        	itemArr[i] = new GroupListItem(dataArr[i], ItemType.CHILD);
        	itemList.add(itemArr[i]);
        }
	}
}
