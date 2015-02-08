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
import com.ssm.peopleTree.network.protocol.GetInfoAllRequest;
import com.ssm.peopleTree.network.protocol.GetInfoAllResponse;

public class GroupManager extends Observable implements Listener<JSONObject> {

	private volatile static GroupManager instance = null;
	
	private GroupManager() {
		children = new ArrayList<MemberData>();	
		cur = new MemberData();
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
	
	public void update(int groupMemberId) {
		NetworkManager.getInstance().request(new GetInfoAllRequest(groupMemberId), this, null);
	}
	
	public void groupChanged() {
		notifyObservers();
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
	
	public MemberData getParent() {
		return parent;
	}
	
	public MemberData getCur() {
		return cur;
	}
	
	public ArrayList<MemberData> getChildren() {
		return children;
	}
	
	public void setGroup(MemberData parent, MemberData cur, ArrayList<MemberData> children) {
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
		public void onUpdateSuccess();
		public void onUpdateFail();
	}
	
}
