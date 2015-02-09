package com.ssm.peopleTree.notification;

import java.util.ArrayList;

public class NotificationManager {
	
	private volatile static NotificationManager instance;
	
	private NotificationManager() {
		instance = null;
	}
	
	public static NotificationManager getInstance() {
		if (null == instance) {
			synchronized (NotificationManager.class) {
				instance = new NotificationManager();
			}
		}
		
		return instance;
	}	
	
	public void sendMessage() {
		// TODO
	}
	
	public void removeMessage() {
		// TODO
	}
		
	public ArrayList<Notification> getNotificationList() {
		ArrayList<Notification> notiList = new ArrayList<Notification>();
		// TODO
		return notiList;
	}
	
	public ArrayList<Message> getMessageList() {
		ArrayList<Message> msgList = new ArrayList<Message>();
		// TODO
		return msgList;
	}
}
