package com.ssm.peopleTree.application;

import com.ssm.peopleTree.data.MemberData;

public class MyManager {
	private volatile static MyManager instance = null;

	private MyManager() {
		myData = new MemberData();
		myParentData = new MemberData();
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
	private MemberData myParentData;
	
	public void setMyData(MemberData myData,MemberData myParentData) {

		if (myData != null) {
			this.myData.userId = myData.userId;
			this.myData.userName = myData.userName;
			this.myData.userPhoneNumber = myData.userPhoneNumber;

			this.myData.groupMemberId = myData.groupMemberId;
			this.myData.parentGroupMemberId = myData.parentGroupMemberId;
			this.myData.groupId = myData.groupId;

			this.myData.edgeStatus = myData.edgeStatus;
			this.myData.edgeType = myData.edgeType;

			this.myData.manageMode = myData.manageMode;
			this.myData.managedLocationRadius = myData.managedLocationRadius;
			this.myData.managingTotalNumber = myData.managingTotalNumber;
			this.myData.managingNumber = myData.managingNumber;

			this.myData.longitude = myData.longitude;
			this.myData.latitude = myData.latitude;
			
			this.myData.accumulateWarning = myData.accumulateWarning;
		}
		// ----------
		if (myParentData != null) {
			this.myParentData.userId = myParentData.userId;
			this.myParentData.userName = myParentData.userName;
			this.myParentData.userPhoneNumber = myParentData.userPhoneNumber;

			this.myParentData.groupMemberId = myParentData.groupMemberId;
			this.myParentData.parentGroupMemberId = myParentData.parentGroupMemberId;
			this.myParentData.groupId = myParentData.groupId;

			this.myParentData.edgeStatus = myParentData.edgeStatus;
			this.myParentData.edgeType = myParentData.edgeType;

			this.myParentData.manageMode = myParentData.manageMode;
			this.myParentData.managedLocationRadius = myParentData.managedLocationRadius;
			this.myParentData.managingTotalNumber = myParentData.managingTotalNumber;
			this.myParentData.managingNumber = myParentData.managingNumber;
			
			this.myData.accumulateWarning = myData.accumulateWarning;
		} 

	}
	
	public MemberData getMyParentData() {
		return myParentData;
	}
	
	public boolean hasParent() {
		return (myData.parentGroupMemberId != myData.groupMemberId); 
	}
	
	public boolean isAvailableMyLocation() {
		return myData.latitude != null && myData.longitude != null;
	}
	
	public boolean isAvailableParentLocation() {
		return myParentData.latitude != null && myParentData.longitude != null;
	}

	public boolean isAbsent() {
		return myData.accumulateWarning > 0;
	}
	
	public MemberData getMyData() {
		return myData;
	}
	
	public int getEdgeStatus() {
		return myData.edgeStatus;
	}
	
	public int getEdgeType() {
		return myData.edgeType;
	}
	
	public int getGroupId() {
		return myData.groupId;
	}
	
	public int getGroupMemberId() {
		return myData.groupMemberId;
	}
	
	public Double getLatitude() {
		// TODO
		if (myData.latitude == null) {
			return 37.554509;
		}
		return myData.latitude;
	}
	
	public Double getLongitude() {
		if (myData.latitude == null) {
			return 126.946132;
		}
		return myData.longitude;
	}
	
	public int getManagedLocationRadius() {
		//return myData.managedLocationRadius;
		return 50;
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

	public String getUserPhoneNumber() {
		return myData.userPhoneNumber;
	}
	
	public String getManagingInfoStr() {
		return myData.managingNumber + "/" + myData.managingTotalNumber;
	}
	
	public void setEdgeStatus(int edgeStatus) {
		myData.edgeStatus = edgeStatus;
	}
	
	public void setEdgeType(int edgeType) {
		myData.edgeType = edgeType;
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
	
	public void setUserPhoneNumber(String userPhoneNumber) {
		myData.userPhoneNumber = userPhoneNumber;
	}
}
