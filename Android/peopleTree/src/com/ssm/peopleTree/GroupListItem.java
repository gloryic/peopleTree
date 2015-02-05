package com.ssm.peopleTree;

import com.ssm.peopleTree.data.MemberData;

public class GroupListItem {
	
	private MemberData itemData;
	private ItemType type;
	
	public GroupListItem(MemberData itemData, ItemType type) {
		this.itemData = itemData;
		this.type = type;
	}
	
	public String getManagingInfoStr() {
		return itemData.managingNumber + "/" + itemData.managingTotalNumber;
	}
	
	public String getUserName() {
		return itemData.userName;
	}
	
	public ItemType getType() {
		return type;
	}
}
