package com.ssm.peopleTree.application;

public class UserData {
	private volatile static UserData instance;

	private UserData() {
		instance = null;
	}
	
	public static UserData getInstance() {
		if (null == instance) {
			synchronized (UserData.class) {
				instance = new UserData();
			}
		}
		
		return instance;
	}
	
	
	
}
