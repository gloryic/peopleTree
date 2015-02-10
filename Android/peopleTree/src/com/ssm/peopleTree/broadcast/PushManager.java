package com.ssm.peopleTree.broadcast;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONObject;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.ssm.peopleTree.UI.BroadCastListViewCustomAdapter;
import com.ssm.peopleTree.UI.GroupListviewCustomAdapter;
import com.ssm.peopleTree.UI.PushMessageListViewCustomAdapter;
import com.ssm.peopleTree.UI.RequestListViewCustomAdapter;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.dialog.MyMenuDialog;
import com.ssm.peopleTree.group.GroupManager;
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
	GroupListviewCustomAdapter glvca;
	
	
	public void setAdapters(PushMessageListViewCustomAdapter pmlvca,
			BroadCastListViewCustomAdapter bclvca,RequestListViewCustomAdapter upRqlvca,
			RequestListViewCustomAdapter downRqlvca,GroupListviewCustomAdapter glvca){
		
		this.pmlvca = pmlvca;
		this.bclvca = bclvca;
		this.upRqlvca = upRqlvca;
		this.downRqlvca = downRqlvca;
		this.glvca = glvca;
		
	}
	
	
//5
	
//7
	

	
	
	
	
	
	public void pushMessageAccept(PushData pd){

		final PushData pushdata = pd;
		
		Listener<JSONObject>  onGetInfoResponse = new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
	
				GetInfoResponse res = new GetInfoResponse(arg0);
				Status status = res.getStatus();
				String str1 = "";
				//long time =
				Calendar calen = Calendar.getInstance();
				String timeStr1 = "" + (calen.get(Calendar.MONTH)+1) + "�� " + calen.get(Calendar.DAY_OF_MONTH)+"��    "
						+ calen.get(Calendar.HOUR_OF_DAY) + ":"
						+ calen.get(Calendar.MINUTE) +"\n";

				if (res.getStatus() == Status.SUCCESS) {
					 MemberData mData = res.mData;

					switch (pushdata.statusCode) {
					
					case 100:
						bclvca.addItem("", null, "[��������]", timeStr1+pushdata.msgstr, "");
						pmlvca.dataChange();
						break;
					case 200:
						str1 = "������ "+mData.userName+"�� ��ġ������ ���̻� ���� �ʽ��ϴ�.";
						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						break;
					case 210:
						str1 = "������ "+mData.userName+"�� ��尡 Ʈ��ŷ���� ����Ǿ����ϴ�.";
						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						break;
					case 220:
						str1 = "������ "+mData.userName+"�� ��尡 �������� ����Ǿ����ϴ�.";
						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						break;
					case 230:
						str1 = "������ "+mData.userName+"�� ��尡 �����潺���� ����Ǿ����ϴ�.";
						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						break;
					case 300:
						str1 = ""+mData.userName+"�� ��Ż������ϴ�!\n";
						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						break;
					case 600:
						bclvca.addItem("", null,  "["+mData.userName+"]\n", timeStr1+pushdata.msgstr, "");
						pmlvca.dataChange();
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
						break;
					case 425:
						str1 = mData.userName + "�� ����Ǿ����ϴ�.\n" + "����� "
								+ mData.userName + "�� ��ġ�������� �������Դϴ�.";

						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						break;
					case 515:
						str1 = mData.userName + "�� ����Ǿ����ϴ�.\n" + "�����  �����ڴ� "
								+ mData.userName + "�Դϴ�."
										+ "(�����������)";

						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						break;
					case 525:
						str1 = mData.userName + "�� ����Ǿ����ϴ�.\n" + "�����  �����ڴ� "
								+ mData.userName + "�Դϴ�."
										+ "(��ġ��������)";

						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						break;
					
					case 417:
						str1 = mData.userName + "�� ����Ǿ����ϴ�.\n" + "�����  �����ڴ� "
								+ mData.userName + "�Դϴ�."
										+ "(�����������)";

						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();					

						break;
					case 427:
						str1 = mData.userName + "�� ����Ǿ����ϴ�.\n" + "�����  �����ڴ� "
								+ mData.userName + "�Դϴ�."
										+ "(��ġ��������)";

						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						break;
					case 517:
						str1 = mData.userName + "�� ����Ǿ����ϴ�.\n" + "����� "
								+ mData.userName + "�� �������� �������Դϴ�.";

						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						break;
					case 527:
						str1 = mData.userName + "�� ����Ǿ����ϴ�.\n" + "����� "
								+ mData.userName + "�� ��ġ�������� �������Դϴ�.";

						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						break;
									

					}

				}
				else {
					Log.i("log", "PushManager.pushMessageAccept ERR");

				}
				
			}
		};

		NetworkManager.getInstance().request(new GetInfoRequest(pd.from), onGetInfoResponse, null);
    	
    	
		
		
		
	}
	
	
	
	
}
