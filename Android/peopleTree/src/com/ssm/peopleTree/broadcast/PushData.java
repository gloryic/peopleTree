package com.ssm.peopleTree.broadcast;

public class PushData {

	
	public int statusCode;
	public String userName;
	public String msgstr;
	public int from;
	public int to;
	
	public PushData(int statusCode,String userName,String msgstr,int from,int to){
		this.statusCode = statusCode;
		this.userName = userName;
		this.msgstr = msgstr;
		this.from = from;
		this.to = to;
		
	}
	
	
	
	
}
