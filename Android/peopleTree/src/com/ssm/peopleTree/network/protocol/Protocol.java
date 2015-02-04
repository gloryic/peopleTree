package com.ssm.peopleTree.network.protocol;

abstract class Protocol {
	protected static final String GROUP_CODE_KEY = "groupCode";
	protected static final String GROUP_ID_KEY = "groupId";
	protected static final String GROUP_NAME_KEY = "groupName";
	protected static final String GROUP_MEMBER_ID_KEY = "groupMemberId";
	protected static final String MEMBER_ID_KEY = "memberId";
	protected static final String PARENT_GROUP_MEMBER_ID_KEY = "parentGroupMemberId";
	protected static final String SUB_ROOT_ID_KEY = "subRootId";
	
	protected static final String USER_ID_KEY = "userId";
	protected static final String USER_NUMBER_KEY = "userNumber";
	protected static final String USER_TYPE_KEY = "userType";
	protected static final String USER_NAME_KEY = "userName";
	protected static final String USER_PHONE_KEY = "userPhoneNumber";
	protected static final String USER_PASSWORD_KEY = "password";
	
	protected static final String MESSAGE_KEY = "message";
	
	protected static final String SEND_COUNT_KEY = "sendCount";
	
	protected static final String LATITUDE_KEY = "latitude";
	protected static final String LONGITUDE_KEY = "longitude";
	
	protected static final String OWN_PHONE_KEY = "ownPhoneNumber";
	protected static final String RADIUS_KEY = "radius";
	
	protected static final String STATUS_KEY = "status";
	protected static final String EDGE_TYPE_KEY = "edgeType";
	protected static final String EDGE_STATUS_KEY = "edgeStatus";
	protected static final String DEVICE_STATUS_KEY = "deviceStatus";
	
	protected static final String MANAGE_MODE_KEY = "manageMode";
	protected static final String MANAGE_LOCATION_RADIUS_KEY = "managedLocationRadius";
	protected static final String MANAGING_TOTAL_NUMBER_KEY = "managingTotalNumber";
	protected static final String MANAGING_NUMBER_KEY = "managingNumber";
	
	protected static final String VALID_USER_NUM_KEY = "validUserNumber";
	
	protected static final String POINTS_KEY = "points";
	
	protected static final String PHONE_PATTERN = "^[0-9]{11}$";
}
