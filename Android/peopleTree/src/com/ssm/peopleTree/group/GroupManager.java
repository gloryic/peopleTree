package com.ssm.peopleTree.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Stack;

import org.json.JSONObject;

import android.util.Log;

import com.android.volley.Response.Listener;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.Status;
import com.ssm.peopleTree.network.protocol.GetInfoAllRequest;
import com.ssm.peopleTree.network.protocol.GetInfoAllResponse;

public class GroupManager extends Observable {

	private volatile static GroupManager instance = null;
	
	private GroupManager() {
		children = new ArrayList<MemberData>();	
		cur = new MemberData();
		parent = new MemberData();
		navigator = new Navigator();
	}
	
	public static GroupManager getInstance() {
		if (null == instance) {
			synchronized (GroupManager.class) {
				instance = new GroupManager();
			}
		}
		
		return instance;
	}
	
	private Navigator navigator;
	private ArrayList<MemberData> children;
	private MemberData cur;
	private MemberData parent;
	
	private GroupListener groupListener;
	
	public void createGroup() {
		//TODO
	}
	
	public void exitGroup() {
		//TODO
	}
	
	public void makeEdge() {
		//TODO
	}
	
	public void updateSelf() {
		int myId = MyManager.getInstance().getGroupMemberId();
		update(myId);
	}
	
	public void update(int groupMemberId) {
		update(groupMemberId, groupListener);
	}
	
	public void update(int groupMemberId, GroupListener groupListener) {
		final int id = groupMemberId;
		final GroupListener listener = groupListener;
		Listener<JSONObject> getInfoListener = new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				GetInfoAllResponse res = new GetInfoAllResponse(arg0);
				if (res.getStatus() == Status.SUCCESS) {
					setGroup(res.parentData, res.curData, res.children);
					
					if (listener != null) {
						listener.onUpdateSuccess(null);
					}
				} 
				else {
					Log.e("test", "Fail");					
					if (listener != null) {
						listener.onUpdateFail(id);
					}
				}
			}
			
		};
		
		int myId = MyManager.getInstance().getGroupMemberId();
		NetworkManager.getInstance().request(new GetInfoAllRequest(myId, groupMemberId), getInfoListener, null);
		groupListener.onUpdateStart(null);
	}
	
	public void navigateUp(MemberData mData) {
		navigator.navigateUp(mData);
	}
	
	public void navigateDown(MemberData mData) {
		navigator.navigateDown(mData);
	}
	
	public void navigateHome() {
		navigator.navigateHome();
	}
		
	public void groupChanged() {
		setChanged();
		notifyObservers();
	}
	
	public MemberData getParent() {
		return parent;
	}
	
	public MemberData getCur() {
		return cur;
	}
	
	public ArrayList<MemberData> getChildren() {
		return children;
	}
	
	public Navigator getNavigator() {
		return navigator;
	}
	
	public void setGroup(MemberData parent, MemberData cur, ArrayList<MemberData> children) {
		
		if(MyManager.getInstance().getGroupMemberId() == cur.groupMemberId ){
			MyManager.getInstance().setMyData(cur, parent);
		}
		this.parent = parent;
		this.cur = cur;
		this.children.clear();
		this.children.addAll(children);
		groupChanged();
	}
	
	public void setGroupListener(GroupListener groupListener) {
		this.groupListener = groupListener;
	}
	
	public interface GroupListener {
		public void onUpdateStart(Object arg);
		public void onUpdateSuccess(Object arg);
		public void onUpdateFail(Object arg);
	}
	
}
