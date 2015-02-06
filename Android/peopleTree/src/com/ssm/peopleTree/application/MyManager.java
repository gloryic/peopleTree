package com.ssm.peopleTree.application;

import com.ssm.peopleTree.data.MemberData;

public class MyManager {
	private volatile static MyManager instance;

	private MyManager() {
		instance = null;
		myData = new MemberData();
	}
	
	public static MyManager getInstance() {
		if (null == instance) {
			synchronized (MyManager.class) {
				instance = new MyManager();
			}
		}
		
		return instance;
	}

	private MemberData myData;
	
	public void setMyData(MemberData myData) {

		this.myData.userId = myData.userId;
		this.myData.userName = myData.userName;
		this.myData.userNumber = myData.userNumber;
		this.myData.userPhoneNumber = myData.userPhoneNumber;
		
		this.myData.groupMemberId = myData.groupMemberId;
		this.myData.parentGroupMemberId = myData.parentGroupMemberId;
		this.myData.groupId = myData.groupId;
		
		this.myData.edgeStatus = myData.edgeStatus;
		
		this.myData.manageMode = myData.manageMode;
		this.myData.managedLocationRadius = myData.managedLocationRadius;
		this.myData.managingTotalNumber = myData.managingTotalNumber;
		this.myData.managingNumber = myData.managingNumber;
		
		this.myData.longitude = myData.longitude;
		this.myData.latitude = myData.latitude;
	}
	
	public int getEdgeStatus() {
		return myData.edgeStatus;
	}
	
	public int getGroupId() {
		return myData.groupId;
	}
	
	public int getGroupMemberId() {
		return myData.groupMemberId;
	}
	
	public Double getLatitude() {
		return myData.latitude;
	}
	
	public Double getLongitude() {
		return myData.longitude;
	}
	
	public int getManagedLocationRadius() {
		return myData.managedLocationRadius;
	}
	
	public int getManageMode() {
		return myData.manageMode;
	}
	
	public int getManagingNumber() {
		return myData.managingNumber;
	}
	
	public int getManagingTotalNumber() {
		return myData.managingTotalNumber;
	}
	
	public int getParentGroupMemberId() {
		return myData.parentGroupMemberId;
	}
	
	public String getUserId() {
		return myData.userId;
	}
	
	public String getUserName() {
		return myData.userName;
	}
	
	public int getUserNumber() {
		return myData.userNumber;
	}
	
	public int getUserPhoneNumber() {
		return myData.userPhoneNumber;
	}
	
	public String getManagingInfoStr() {
		return myData.managingNumber + "/" + myData.managingTotalNumber;
	}
	
	public void setEdgeStatus(int edgeStatus) {
		myData.edgeStatus = edgeStatus;
	}
	
	public void setGroupId(int groupId) {
		myData.groupId = groupId;
	}
	
	public void setGroupMemberId(int groupMemberId) {
		myData.groupMemberId = groupMemberId;
	}
	
	public void setLatitude(Double latitude) {
		myData.latitude = latitude;
	}
	
	public void setLongitude(Double longitude) {
		myData.longitude = longitude;
	}
	
	public void setManagedLocationRadius(int managedLocationRadius) {
		myData.managedLocationRadius = managedLocationRadius;
	}
	
	public void setManageMode(int manageMode) {
		myData.manageMode = manageMode;
	}
	
	public void setManagingNumber(int managingNumber) {
		myData.managingNumber = managingNumber;
	}
	
	public void setManagingTotalNumber(int managingTotalNumber) {
		myData.managingTotalNumber = managingTotalNumber;
	}
	
	public void setParentGroupMemberId(int parentGroupMemberId) {
		myData.parentGroupMemberId = parentGroupMemberId;
	}
	
	public void setUserId(String userId) {
		myData.userId = userId;
	}
	
	public void setUserName(String userName) {
		myData.userName = userName;
	}
	
	public void setUserNumber(int userNumber) {
		myData.userNumber = userNumber;
	}
	
	public void setUserPhoneNumber(int userPhoneNumber) {
		myData.userPhoneNumber = userPhoneNumber;
	}
}
