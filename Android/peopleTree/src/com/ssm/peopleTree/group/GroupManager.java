package com.ssm.peopleTree.group;

import java.util.ArrayList;
import java.util.Stack;

import org.json.JSONObject;

import android.util.Log;

import com.android.volley.Response.Listener;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.Status;
import com.ssm.peopleTree.network.protocol.ChildrenRequest;
import com.ssm.peopleTree.network.protocol.ChildrenResponse;
import com.ssm.peopleTree.network.protocol.GetUserInfoRequest;
import com.ssm.peopleTree.network.protocol.GetUserInfoResponse;

public class GroupManager implements Listener<JSONObject> {

	private volatile static GroupManager instance = null;
	
	private GroupManager() {
		children = new ArrayList<MemberData>();	
		curStack = new Stack<MemberData>();
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
	
	public void createGroup() {
		//TODO
	}
	
	public void exitGroup() {
		//TODO
	}
	
	public void makeEdge() {
		//TODO
	}
		
	public void goNext(int groupMemberId) {
		/*
		NetworkManager.getInstance().request(new ChildrenRequest(md.groupMemberId), listener, null);
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
	
	public ArrayList<MemberData> getChildren() {
		return children;
	}
	
	public void setChildren(MemberData memberData) {
		curStack.push(memberData);
		NetworkManager.getInstance().request(new ChildrenRequest(memberData.groupMemberId), this, null);
	}

	@Override
	public void onResponse(JSONObject arg0) {
		ChildrenResponse res = new ChildrenResponse(arg0);
		if (res.getStatus() == Status.SUCCESS) {
			children.clear();
			for (int i = 0; i < res.children.size(); i++) {
				int child = res.children.get(i);
				ChildrenAdder adder = new ChildrenAdder(child, curStack.peek().userNumber);
			}
			/*
			for (int child : res.children) {
				new ChildrenAdder(child, curStack.peek().userNumber);
			}
			*/
		} 
		else {
			Log.e("test", "Fail");
		}
	}
	
	public interface GroupListener {
		public void onSetChildrenSuccess();
		public void onSetChildrenFail();
	}
	
	private class ChildrenAdder implements Listener<JSONObject> {
		private int groupMemberId;
		private int parentGroupMemberId;
		
		public ChildrenAdder(int groupMemberId, int parentGroupMemberId) {
			this.groupMemberId = groupMemberId;
			this.parentGroupMemberId = parentGroupMemberId;
			NetworkManager.getInstance().request(new GetUserInfoRequest(groupMemberId), this, null);
		}

		@Override
		public void onResponse(JSONObject arg0) {
			GetUserInfoResponse res = new GetUserInfoResponse(arg0);
			
			if (res.getStatus() == Status.SUCCESS) {
				children.add(res.mData);
			}
			else if (parentGroupMemberId == curStack.peek().userNumber){
				NetworkManager.getInstance().request(new GetUserInfoRequest(groupMemberId), this, null);
			}
		}
	}

}
