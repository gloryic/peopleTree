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

	
	public void pushMessageAccept(PushData pd){

		final PushData pushdata = pd;
		
		Listener<JSONObject>  onGetInfoResponse = new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
	
				GetInfoResponse res = new GetInfoResponse(arg0);
				Status status = res.getStatus();
				String str1 = "";
		
				Calendar calen = Calendar.getInstance();
				String timeStr1 = "" + (calen.get(Calendar.MONTH)+1) + "월 " + calen.get(Calendar.DAY_OF_MONTH)+"일    "
						+ calen.get(Calendar.HOUR_OF_DAY) + ":"
						+ calen.get(Calendar.MINUTE) +"\n";

				if (res.getStatus() == Status.SUCCESS) {
					 MemberData mData = res.mData;
					 
					 if(pushdata.statusCode >=2000 && pushdata.statusCode <=3000){
						 str1 = "관리대상 상태변화 "+mData.userName+"\n" + pushdata.msgstr;
						 
						 pmlvca.addItem("", timeStr1 + str1, "");
						 pmlvca.dataChange();
						 
					 }

					switch (pushdata.statusCode) {
					
					case 100:
						bclvca.addItem("", null, "[공지사항]", timeStr1+pushdata.msgstr, "");
						bclvca.dataChange();
						break;
					case 200:
						str1 = "관리자 "+mData.userName+"가 위치관리를 더이상 하지 않습니다.";
						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().update(MyManager.getInstance().getGroupMemberId());
						break;
					case 210:
						str1 = "관리자 "+mData.userName+"의 모드가 트레킹모드로 변경되었습니다.";
						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().update(MyManager.getInstance().getGroupMemberId());
						break;
					case 220:
						str1 = "관리자 "+mData.userName+"의 모드가 지역모드로 변경되었습니다.";
						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().update(MyManager.getInstance().getGroupMemberId());
						break;
					case 230:
						str1 = "관리자 "+mData.userName+"의 모드가 지오펜스모드로 변경되었습니다.";
						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().update(MyManager.getInstance().getGroupMemberId());
						break;
					case 300:
						str1 = ""+mData.userName+"가 이탈퇴었습니다!\n";
						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().update(MyManager.getInstance().getGroupMemberId());
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
						str1 = mData.userName + "와 연결되었습니다.\n" + "당신은 "
								+ mData.userName + "의 정보보고 관리자입니다.";

						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().update(MyManager.getInstance().getGroupMemberId());
						break;
					case 425:
						str1 = mData.userName + "와 연결되었습니다.\n" + "당신은 "
								+ mData.userName + "의 위치관리관계 관리자입니다.";

						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().update(MyManager.getInstance().getGroupMemberId());
						break;
					case 515:
						str1 = mData.userName + "와 연결되었습니다.\n" + "당신의  관리자는 "
								+ mData.userName + "입니다."
										+ "(정보보고관계)";

						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().update(MyManager.getInstance().getGroupMemberId());
						break;
					case 525:
						str1 = mData.userName + "와 연결되었습니다.\n" + "당신의  관리자는 "
								+ mData.userName + "입니다."
										+ "(위치관리관계)";

						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().update(MyManager.getInstance().getGroupMemberId());
						break;
					
					case 417:
						str1 = mData.userName + "와 연결되었습니다.\n" + "당신의  관리자는 "
								+ mData.userName + "입니다."
										+ "(정보보고관계)";

						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();					
						GroupManager.getInstance().update(MyManager.getInstance().getGroupMemberId());
						break;
					case 427:
						str1 = mData.userName + "와 연결되었습니다.\n" + "당신의  관리자는 "
								+ mData.userName + "입니다."
										+ "(위치관리관계)";

						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().update(MyManager.getInstance().getGroupMemberId());
						break;
					case 517:
						str1 = mData.userName + "와 연결되었습니다.\n" + "당신은 "
								+ mData.userName + "의 정보보고 관리자입니다.";

						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().update(MyManager.getInstance().getGroupMemberId());
						break;
					case 527:
						str1 = mData.userName + "와 연결되었습니다.\n" + "당신은 "
								+ mData.userName + "의 위치관리관계 관리자입니다.";

						pmlvca.addItem("", timeStr1 + str1, "");
						pmlvca.dataChange();
						GroupManager.getInstance().update(MyManager.getInstance().getGroupMemberId());
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
	
	public void pushMessageAcceptEx(JSONObject msgObj) throws JSONException{
		int manageMode;
		int radius;
		double distance;
		int edgeStatus;
		int accumulateWarning;
		
		manageMode = msgObj.getInt("manageMode");
		/*
		INVALID(100, 0),
		NOTHING(200, R.layout.activity_map),
		TRAKING(210, R.layout.activity_map_tracking),
		AREA(220, R.layout.activity_map_area),
		GEOFENCE(230, R.layout.activity_map_geofence)
		
		*/
		
		Calendar calen = Calendar.getInstance();
		String timeStr1 = "" + (calen.get(Calendar.MONTH)+1) + "월 " + calen.get(Calendar.DAY_OF_MONTH)+"일    "
				+ calen.get(Calendar.HOUR_OF_DAY) + ":"
				+ calen.get(Calendar.MINUTE) +"\n";
		String str1;

		switch(ManageMode.getMode(manageMode)){
		case TRAKING:
			radius = msgObj.getInt("radius");
			distance = msgObj.getDouble("distance");
			edgeStatus  = msgObj.getInt("edgeStatus");
			accumulateWarning = msgObj.getInt("accumulateWarning");
			
			str1 = "관리자에게서 벗어났습니다.\n ";
			
			pmlvca.addItem("", timeStr1 + str1, "");
			pmlvca.dataChange();
			break;
		case AREA:
			radius = msgObj.getInt("radius");
			distance = msgObj.getInt("distance");
			edgeStatus  = msgObj.getInt("edgeStatus");
			accumulateWarning = msgObj.getInt("accumulateWarning");
			
			str1 = "관리지역 위치를 벗어났습니다.\n ";
			pmlvca.addItem("", timeStr1 + str1, "");
			pmlvca.dataChange();
			break;
		case GEOFENCE:
			edgeStatus  = msgObj.getInt("edgeStatus");
			accumulateWarning = msgObj.getInt("accumulateWarning");
			
			str1 = "지오펜싱 관리 위치를 벗어났습니다.";
			pmlvca.addItem("", timeStr1 + str1, "");
			pmlvca.dataChange();
			break;
			
				
				//
			
		}
		
		
		
	}
	
	
}
