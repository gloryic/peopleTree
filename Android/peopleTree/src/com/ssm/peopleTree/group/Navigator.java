package com.ssm.peopleTree.group;

import java.util.Observable;
import java.util.Stack;

import com.ssm.peopleTree.data.MemberData;

public class Navigator extends Observable {
	private Stack<MemberData>datas;
	
	public Navigator() {
		datas = new Stack<MemberData>();
	}
		
	@Override
	public String toString() {
		String result = "";
		for (MemberData mData : datas) {
			result += mData.userName + ">";
		}
		
		return result;
	}
	
	public void navigateUp(MemberData mData) {

		while (!datas.isEmpty() && mData != null) {
			if (datas.peek().groupMemberId  == mData.groupMemberId) {
				break;
			}
			datas.pop();
		}
		
		dataChanged();
	}
	
	public void navigateDown(MemberData mData) {
		datas.push(mData);
		dataChanged();
	}
	
	public void navigateHome() {
		datas.clear();
		dataChanged();
	}
	
	public void dataChanged() {
		setChanged();
		notifyObservers();
	}
}
