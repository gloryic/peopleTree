package com.ssm.peopleTree.broadcast;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.ssm.peopleTree.R;
import com.ssm.peopleTree.UI.BroadCastListViewCustomAdapter;
import com.ssm.peopleTree.UI.GroupListviewCustomAdapter;
import com.ssm.peopleTree.UI.PushMessageListViewCustomAdapter;
import com.ssm.peopleTree.UI.RequestListViewCustomAdapter;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.dialog.MyMenuDialog;
import com.ssm.peopleTree.group.GroupManager;
import com.ssm.peopleTree.map.ManageMode;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.Status;
import com.ssm.peopleTree.network.protocol.GetInfoRequest;
import com.ssm.peopleTree.network.protocol.GetInfoResponse;
import com.ssm.peopleTree.network.protocol.GroupOutResponse;
import com.ssm.peopleTree.network.protocol.SearchMemberRequest;

public class PushManager {
	private volatile static PushManager instance = null;
	
	private PushManager() {
		

	}
	
	public static PushManager getInstance() {
		if (null == instance) {
			synchronized (GroupManager.class) {
				instance = new PushManager();
			}
		}
		
		return instance;
	}
	PushMessageListViewCustomAdapter pmlvca;
	BroadCastListViewCustomAdapter bclvca;
	RequestListViewCustomAdapter upRqlvca;
	RequestListViewCustomAdapter downRqlvca;

	
	public void setAdapters(PushMessageListViewCustomAdapter pmlvca,
			BroadCastListViewCustomAdapter bclvca,RequestListViewCustomAdapter upRqlvca,
			RequestListViewCustomAdapter downRqlvca,GroupListviewCustomAdapter glvca){
		
		this.pmlvca = pmlvca;
		this.bclvca = bclvca;
		this.upRqlvca = upRqlvca;
		this.downRqlvca = downRqlvca;
		
	}

	
	
	public void pushMessageAccept(PushData pd){

		final PushData pushdata = pd;
		
		Listener<JSONObject>  onGetInfoResponse = new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				
				GetInfoResponse res = new GetInfoResponse(arg0);
				Status status = res.getStatus();
				String str1 = "";
		
				Calendar calen = Calendar.getInstance();
				String timeStr1 = "" + (calen.get(Calendar.MONTH)+1) + "�� " + calen.get(Calendar.DAY_OF_MONTH)+"��    "
						+ calen.get(Calendar.HOUR_OF_DAY) + ":"
						+ calen.get(Calendar.MINUTE) +"\n";

				if (res.getStatus() == Status.SUCCESS) {
					 MemberData mData = res.mData;
					 
					 if(pushdata.statusCode >=2000 && pushdata.statusCode <=3000){
						 str1 = "������� ���º�ȭ "+mData.userName+"\n" + pushdata.msgstr;
						 
						 pmlvca.addItem("", timeStr1 + str1, "");
						 pmlvca.dataChange();
						 
					 }
					 Log.i("log", "pushlog _" + pushdata.statusCode);
					switch (pushdata.statusCode) {
					
					case 100:
						bclvca.addItem("", null, "[��������]", timeStr1+mData.userName+"��(��) �����Ͽ����ϴ�\n"+pushdata.msgstr, "");
						bclvca.dataChange();
						break;
					case 200:
						str1 = "������ "+mData.userName+"��(��) ��ġ������ ���̻� ���� �ʽ��ϴ�.";
						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().updateSelf();
						break;
					case 210:
						str1 = "������ "+mData.userName+"�� ��尡 Ʈ��ŷ���� ����Ǿ����ϴ�.";
						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().updateSelf();
						break;
					case 220:
						str1 = "������ "+mData.userName+"�� ��尡 �������� ����Ǿ����ϴ�.";
						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().updateSelf();
						break;
					case 230:
						str1 = "������ "+mData.userName+"�� ��尡 �����潺���� ����Ǿ����ϴ�.";
						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().updateSelf();
						break;
					case 300:
						str1 = ""+mData.userName+"��(��) ��Ż������ϴ�!\n";
						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().updateSelf();
						break;
					case 600:
						bclvca.addItem("", null,  "["+mData.userName+"]\n", timeStr1+pushdata.msgstr, "");
						bclvca.dataChange();
						break;
					case 410:
						upRqlvca.addItem(mData, pushdata.statusCode);
						upRqlvca.dataChange();
						break;
					case 420:
						upRqlvca.addItem(mData, pushdata.statusCode);
						upRqlvca.dataChange();
						break;
					case 510:
						downRqlvca.addItem(mData, pushdata.statusCode);
						downRqlvca.dataChange();
						break;
					case 520:
						downRqlvca.addItem(mData, pushdata.statusCode);
						downRqlvca.dataChange();
						break;
					case 415:
						str1 = mData.userName + "�� ����Ǿ����ϴ�.\n" + "����� "
								+ mData.userName + "�� �������� �������Դϴ�.";

						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().updateSelf();
						GroupManager.getInstance().navigateHome();
						break;
					case 425:
						str1 = mData.userName + "�� ����Ǿ����ϴ�.\n" + "����� "
								+ mData.userName + "�� ��ġ�������� �������Դϴ�.";

						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().updateSelf();
						GroupManager.getInstance().navigateHome();
						break;
					case 515:
						str1 = mData.userName + "�� ����Ǿ����ϴ�.\n" + "�����  �����ڴ� "
								+ mData.userName + "�Դϴ�."
										+ "(�����������)";

						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().updateSelf();
						GroupManager.getInstance().navigateHome();
						break;
					case 525:
						str1 = mData.userName + "�� ����Ǿ����ϴ�.\n" + "�����  �����ڴ� "
								+ mData.userName + "�Դϴ�."
										+ "(��ġ��������)";

						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().updateSelf();
						GroupManager.getInstance().navigateHome();
						break;
					
					case 417:
						str1 = mData.userName + "�� ����Ǿ����ϴ�.\n" + "�����  �����ڴ� "
								+ mData.userName + "�Դϴ�."
										+ "(�����������)";

						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();					
						GroupManager.getInstance().updateSelf();
						GroupManager.getInstance().navigateHome();
						break;
					case 427:
						str1 = mData.userName + "�� ����Ǿ����ϴ�.\n" + "�����  �����ڴ� "
								+ mData.userName + "�Դϴ�."
										+ "(��ġ��������)";

						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().updateSelf();
						GroupManager.getInstance().navigateHome();
						break;
					case 517:
						str1 = mData.userName + "�� ����Ǿ����ϴ�.\n" + "����� "
								+ mData.userName + "�� �������� �������Դϴ�.";

						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().updateSelf();
						GroupManager.getInstance().navigateHome();
						break;
					case 527:
						str1 = mData.userName + "�� ����Ǿ����ϴ�.\n" + "����� "
								+ mData.userName + "�� ��ġ�������� �������Դϴ�.";

						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().updateSelf();
						GroupManager.getInstance().navigateHome();
						break;
					case 700:
						str1 = mData.userName + "��(��) �׷��� �������ϴ�.";
						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().update(MyManager.getInstance().getGroupMemberId());
						break;
					case 710:
						str1 = mData.userName + "��(��) �α׾ƿ� �Ͽ����ϴ�.";
						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().update(MyManager.getInstance().getGroupMemberId());
						break;		

					}

				}
				else {
					Log.i("log", "PushManager.pushMessageAccept ERR"  + pushdata.statusCode);

				}
				
			}
		};
		if(pushdata.statusCode == 710){
			Calendar calen = Calendar.getInstance();
			String timeStr1 = "" + (calen.get(Calendar.MONTH)+1) + "�� " + calen.get(Calendar.DAY_OF_MONTH)+"��    "
					+ calen.get(Calendar.HOUR_OF_DAY) + ":"
					+ calen.get(Calendar.MINUTE) +"\n";
			String str1 = "������� "+ pushdata.userName +"��(��)  �α׾ƿ� �Ͽ����ϴ�.";
			pmlvca.addItem("", timeStr1 + str1, "");
			pmlvca.dataChange();
			GroupManager.getInstance().update(MyManager.getInstance().getGroupMemberId());
			return;
		}
		NetworkManager.getInstance().request(new GetInfoRequest(pd.from), onGetInfoResponse, null);
    	

	}
	
	public void pushMessageAcceptEx(int from,int to,JSONObject msgObj) throws JSONException{
		int parentManageMode;
		int radius;
		double distance;
		int edgeStatus;
		int accumulateWarning;
		parentManageMode = msgObj.getInt("parentManageMode");
		final boolean toggle = msgObj.getBoolean("isToggle");

		
		Calendar calen = Calendar.getInstance();
		String timeStr1 = "" + (calen.get(Calendar.MONTH)+1) + "�� " + calen.get(Calendar.DAY_OF_MONTH)+"��    "
				+ calen.get(Calendar.HOUR_OF_DAY) + ":"
				+ calen.get(Calendar.MINUTE) +"\n";
		String str1 = "";

		
		if(from == to){
			
			switch(ManageMode.getMode(parentManageMode)){
			case TRACKING:
				radius = msgObj.getInt("radius");
				distance = msgObj.getDouble("distance");
				edgeStatus  = msgObj.getInt("edgeStatus");
				accumulateWarning = msgObj.getInt("accumulateWarning");
				if(toggle){
					
					str1 +="������ �ֺ����� �����Ͽ����ϴ�.\n";
				}else{

					str1 +="�����ڿ��Լ� ��Ż�Ͽ����ϴ�.\n";
				}
				pmlvca.addItem("", timeStr1 + str1, "");
				pmlvca.dataChange();
				GroupManager.getInstance().update(MyManager.getInstance().getGroupMemberId());
				break;
			case AREA:
				radius = msgObj.getInt("radius");
				distance = msgObj.getInt("distance");
				edgeStatus  = msgObj.getInt("edgeStatus");
				accumulateWarning = msgObj.getInt("accumulateWarning");
				if(toggle){
					
					str1 +="�����ڰ� ������ �������� �����Ͽ����ϴ�.\n";
				}else{

					str1 +="�����ڰ� ������ ������ ��Ż�Ͽ����ϴ�.\n";
				}
				pmlvca.addItem("", timeStr1 + str1, "");
				pmlvca.dataChange();
				GroupManager.getInstance().update(MyManager.getInstance().getGroupMemberId());
				break;
			case GEOFENCE:
				edgeStatus  = msgObj.getInt("edgeStatus");
				accumulateWarning = msgObj.getInt("accumulateWarning");
				if(toggle){
					
					str1 +="�����ڰ� ������ �������� �����Ͽ����ϴ�.\n";
				}else{

					str1 +="�����ڰ� ������ ������ ��Ż�Ͽ����ϴ�.\n";
				}
				pmlvca.addItem("", timeStr1 + str1, "");
				pmlvca.dataChange();
				GroupManager.getInstance().update(MyManager.getInstance().getGroupMemberId());
				break;
			case INDOOR:
				edgeStatus  = msgObj.getInt("edgeStatus");
				accumulateWarning = msgObj.getInt("accumulateWarning");
				if(toggle){
					
					str1 +="�����ڰ� ������ �������� �����Ͽ����ϴ�.\n";
				}else{

					str1 +="�����ڰ� ������ ������ ��Ż�Ͽ����ϴ�.\n";
				}
				pmlvca.addItem("", timeStr1 + str1, "");
				pmlvca.dataChange();
				GroupManager.getInstance().update(MyManager.getInstance().getGroupMemberId());
				break;
			}
			GroupManager.getInstance().updateSelf();
			GroupManager.getInstance().navigateHome();
		}else{
			
			Listener<JSONObject>  onGetInfoResponse = new Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject arg0) {
					
					GetInfoResponse res = new GetInfoResponse(arg0);
					Status status = res.getStatus();
					String str1 = "";
			
					Calendar calen = Calendar.getInstance();
					String timeStr1 = "" + (calen.get(Calendar.MONTH)+1) + "�� " + calen.get(Calendar.DAY_OF_MONTH)+"��    "
							+ calen.get(Calendar.HOUR_OF_DAY) + ":"
							+ calen.get(Calendar.MINUTE) +"\n";

					if (res.getStatus() == Status.SUCCESS) {
						 MemberData mData = res.mData;
						 
						if(toggle){
								
								str1 +="������� "+ mData.userName+"��(��) �����Ͽ����ϴ�.";
						}else{

								str1 +="������� "+ mData.userName +"��(��) ��Ż�Ͽ����ϴ�.";
						}
						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						
						GroupManager.getInstance().updateSelf();
						GroupManager.getInstance().navigateHome();
					}
				}
			};
			
			NetworkManager.getInstance().request(new GetInfoRequest(from), onGetInfoResponse, null);
			
		}
		

		
		
		
	}
	
	
}
