package com.ssm.peopleTree.group;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Stack;

import org.json.JSONObject;

import android.util.Log;

import com.android.volley.Response.Listener;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.Status;
import com.ssm.peopleTree.network.protocol.ChildrenRequest;
import com.ssm.peopleTree.network.protocol.ChildrenResponse;
import com.ssm.peopleTree.network.protocol.GetInfoAllRequest;
import com.ssm.peopleTree.network.protocol.GetInfoAllResponse;
import com.ssm.peopleTree.network.protocol.GetInfoRequest;
import com.ssm.peopleTree.network.protocol.GetInfoResponse;

public class GroupManager extends Observable implements Listener<JSONObject> {

	private volatile static GroupManager instance = null;
	
	private GroupManager() {
		children = new ArrayList<MemberData>();	
		curStack = new Stack<MemberData>();
		parent = new MemberData();
	}
	
	public static GroupManager getInstance() {
		if (null == instance) {
			synchronized (GroupManager.class) {
				instance = new GroupManager();
			}
		}
		
		return instance;
	}
	
	private ArrayList<MemberData> children;
	private Stack<MemberData> curStack;
	private MemberData cur;
	private MemberData parent;
	
	public void createGroup() {
		//TODO
	}
	
	public void exitGroup() {
		//TODO
	}
	
	public void makeEdge() {
		//TODO
	}
			
	public void goNext(MemberData selectedChild) {
		if (selectedChild == null) {
			return;
		}
		
		/*
		if (!children.contains(selectedChild)) {
			return;
		}
		
		curStack.push(selectedChild);
		NetworkManager.getInstance().request(new GetUserInfoRequest(selectedChild.parentGroupMemberId), listener, null);
		NetworkManager.getInstance().request(new ChildrenRequest(selectedChild.groupMemberId), listener, null);
		*/
	}
	
	public void goBack() {
		/*
		if (curStack.isEmpty())
			return;
		
		MemberData md = curStack.pop();
		NetworkManager.getInstance().request(new ChildrenRequest(md.groupMemberId), listener, null);
		*/
	}
	
	public void goHome() {
		
	}
	
	public void update(int groupMemberId) {
		NetworkManager.getInstance().request(new GetInfoAllRequest(groupMemberId), this, null);
	}
	
	public void groupChanged() {
		notifyObservers();
	}
	
	public void update() {
		
	}
	
	public MemberData getParent() {
		return parent;
	}
	
	public MemberData getCur() {
		if (curStack.isEmpty()) {
			return null;
		}
		
		return curStack.peek();
	}
	
	public ArrayList<MemberData> getChildren() {
		return children;
	}
	
	public void setChildren(MemberData memberData) {
		curStack.push(memberData);
		NetworkManager.getInstance().request(new ChildrenRequest(memberData.groupMemberId), this, null);
	}

	@Override
	public void onResponse(JSONObject arg0) {
		GetInfoAllResponse res = new GetInfoAllResponse(arg0);
		if (res.getStatus() == Status.SUCCESS) {
			parent = res.parentData;
			cur = res.curData;
			children.clear();
			children.addAll(res.children);
			groupChanged();
		} 
		else {
			Log.e("test", "Fail");
		}
	}
	
	public interface GroupListener {
		public void onSetChildrenSuccess();
		public void onSetChildrenFail();
	}
	
}
