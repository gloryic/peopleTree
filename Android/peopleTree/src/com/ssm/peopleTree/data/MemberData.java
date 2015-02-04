package com.ssm.peopleTree.data;

public class MemberData {
	
	public String userId;
	public String userName;
	public int userNumber;
	public int userPhoneNumber;
	
	public int groupMemberId;
	public int parentGroupMemberId;
	public int groupId;
	
	public int edgeStatus;
	
	public int manageMode;
	public int managedLocationRadius;
	public int managingTotalNumber;
	public int managingNumber;
	
	public Double longitude;
	public Double latitude;
	
	public int getEdgeStatus() {
		return edgeStatus;
	}
	
	public int getGroupId() {
		return groupId;
	}
	
	public int getGroupMemberId() {
		return groupMemberId;
	}
	
	public Double getLatitude() {
		return latitude;
	}
	
	public Double getLongitude() {
		return longitude;
	}
	
	public int getManagedLocationRadius() {
		return managedLocationRadius;
	}
	
	public int getManageMode() {
		return manageMode;
	}
	
	public int getManagingNumber() {
		return managingNumber;
	}
	
	public int getManagingTotalNumber() {
		return managingTotalNumber;
	}
	
	public int getParentGroupMemberId() {
		return parentGroupMemberId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public int getUserNumber() {
		return userNumber;
	}
	
	public int getUserPhoneNumber() {
		return userPhoneNumber;
	}
	
	public void setEdgeStatus(int edgeStatus) {
		this.edgeStatus = edgeStatus;
	}
	
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	public void setGroupMemberId(int groupMemberId) {
		this.groupMemberId = groupMemberId;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public void setManagedLocationRadius(int managedLocationRadius) {
		this.managedLocationRadius = managedLocationRadius;
	}
	
	public void setManageMode(int manageMode) {
		this.manageMode = manageMode;
	}
	
	public void setManagingNumber(int managingNumber) {
		this.managingNumber = managingNumber;
	}
	
	public void setManagingTotalNumber(int managingTotalNumber) {
		this.managingTotalNumber = managingTotalNumber;
	}
	
	public void setParentGroupMemberId(int parentGroupMemberId) {
		this.parentGroupMemberId = parentGroupMemberId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setUserNumber(int userNumber) {
		this.userNumber = userNumber;
	}
	
	public void setUserPhoneNumber(int userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}
}
