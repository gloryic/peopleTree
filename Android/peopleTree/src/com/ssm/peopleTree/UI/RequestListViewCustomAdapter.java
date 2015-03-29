package com.ssm.peopleTree.UI;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.ssm.peopleTree.R;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.dialog.BroacCastMsgSendDialog;
import com.ssm.peopleTree.group.GroupManager;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.Status;
import com.ssm.peopleTree.network.protocol.MakeEdgeRequest;
import com.ssm.peopleTree.network.protocol.MakeEdgeResponse;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class RequestListViewCustomAdapter extends BaseAdapter {

	private Context mContext = null;
    private ArrayList<MemberData> mListData = new ArrayList<MemberData>();
    private ArrayList<Integer> edgeTypes = new ArrayList<Integer>();
    
    public static final int FROMUP = 1;
    public static final int FROMDOWN =2;
    
    private int mode;
    private SharedPreferences prefs;
    private Listener<JSONObject> onMakeEdgeResponse;
    public RequestListViewCustomAdapter(Context mContext_) {
        super();
        this.mContext = mContext_;
        
       
        onMakeEdgeResponse  = new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
	
				MakeEdgeResponse res = new MakeEdgeResponse(arg0);
				Status status = res.getStatus();
				String str1 = "알림";
				String str2 = "";
				if (res.getStatus() == Status.SUCCESS) {

					str2 = "요청수락성공\n ";//[from:"+res.from +"]\n [to:"+res.to +"]";
					//str2+="\n status:" +res.statusCode;
					GroupManager.getInstance().updateSelf();
					GroupManager.getInstance().navigateHome();
				} else {
					str2 = "유효하지 않은 관리자 변경 요청입니다.";

				}
				
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setTitle(str1)
						.setMessage(str2)
						.setCancelable(true)
						// 뒤로 버튼 클릭시 취소 가능 설정
						.setPositiveButton("확인", new DialogInterface.OnClickListener() {
							// 확인 버튼 클릭시 설정
							public void onClick(DialogInterface dialog, int whichButton) {
								
								
								
								dialog.cancel();
								
							}

						});
				
				AlertDialog dialog = builder.create(); // 알림창 객체 생성
				dialog.show();
			
			}
		};
        
    }
    private static final String REQ_DATA = "reqmsg_data"; 
    private static final String REQ_ARR1_KEY = "req_arr1_key";
    private static final String REQ_ARR2_KEY = "req_arr2_key";
    private static final String REQ_ARR3_KEY = "req_arr3_key";
    
    private static final String REQ_ARR4_KEY = "req_arr4_key";
    private static final String REQ_ARR5_KEY = "req_arr5_key";
    private static final String REQ_ARR6_KEY = "req_arr6_key";
    
    private static final String REQ_ARR7_KEY = "req_arr7_key";
    private static final String REQ_ARR8_KEY = "req_arr8_key";
    
    private static final String REQ_ARR9_KEY = "req_arr9_key";
    private static final String REQ_ARR10_KEY = "req_arr10_key";
    private static final String REQ_ARR11_KEY = "req_arr11_key";
    private static final String REQ_ARR12_KEY = "req_arr12_key";
    private static final String REQ_ARR13_KEY = "req_arr13_key";
    
    private static final String REQ_ARR14_KEY = "req_arr14_key";
    private static final String REQ_ARR15_KEY = "req_arr15_key";
    
    private static final String REQ_ARR16_KEY = "req_arr16_key";
    
    public void reLoadFromLocal(){
		prefs = mContext.getSharedPreferences(REQ_DATA+mode,Context.MODE_PRIVATE);
		if(prefs==null){
			return;
		}
		mListData.clear();
		edgeTypes.clear();
		
		String str1 = prefs.getString(REQ_ARR1_KEY+mode, "");
		String str2 = prefs.getString(REQ_ARR2_KEY+mode, "");
		String str3 = prefs.getString(REQ_ARR3_KEY+mode, "");
		String str4 = prefs.getString(REQ_ARR4_KEY+mode, "");
		String str5 = prefs.getString(REQ_ARR5_KEY+mode, "");
		String str6 = prefs.getString(REQ_ARR6_KEY+mode, "");
		String str7 = prefs.getString(REQ_ARR7_KEY+mode, "");
		String str8 = prefs.getString(REQ_ARR8_KEY+mode, "");
		String str9 = prefs.getString(REQ_ARR9_KEY+mode, "");
		String str10 = prefs.getString(REQ_ARR10_KEY+mode, "");
		String str11 = prefs.getString(REQ_ARR11_KEY+mode, "");
		String str12 = prefs.getString(REQ_ARR12_KEY+mode, "");
		String str13 = prefs.getString(REQ_ARR13_KEY+mode, "");
		String str14 = prefs.getString(REQ_ARR14_KEY+mode, "");
		String str15 = prefs.getString(REQ_ARR15_KEY+mode, "");
		String str16 = prefs.getString(REQ_ARR16_KEY+mode, "");
		try {
			JSONArray jsonArr1 = new JSONArray(str1);
			JSONArray jsonArr2 = new JSONArray(str2);
			JSONArray jsonArr3 = new JSONArray(str3);
			JSONArray jsonArr4 = new JSONArray(str4);
			JSONArray jsonArr5 = new JSONArray(str5);
			JSONArray jsonArr6 = new JSONArray(str6);
			JSONArray jsonArr7 = new JSONArray(str7);
			JSONArray jsonArr8 = new JSONArray(str8);
			JSONArray jsonArr9 = new JSONArray(str9);
			JSONArray jsonArr10 = new JSONArray(str10);
			JSONArray jsonArr11 = new JSONArray(str11);
			JSONArray jsonArr12 = new JSONArray(str12);
			JSONArray jsonArr13 = new JSONArray(str13);
			JSONArray jsonArr14 = new JSONArray(str14);
			JSONArray jsonArr15 = new JSONArray(str15);
			JSONArray jsonArr16 = new JSONArray(str16);

			
			for (int i = 0; !jsonArr1.isNull(i); i++) {
				
				MemberData addInfo = new MemberData();
				addInfo.userId = jsonArr1.getString(i);
				addInfo.userName = jsonArr2.getString(i);
				addInfo.userPhoneNumber = jsonArr3.getString(i);
				
				addInfo.groupMemberId = jsonArr4.getInt(i);
				addInfo.parentGroupMemberId = jsonArr5.getInt(i);
				addInfo.groupId = jsonArr6.getInt(i);
				
				addInfo.edgeStatus =jsonArr7.getInt(i);
				addInfo.edgeType = jsonArr8.getInt(i);
				
				addInfo.manageMode = jsonArr9.getInt(i);
				addInfo.managedLocationRadius = jsonArr10.getInt(i);
				addInfo.managingTotalNumber = jsonArr11.getInt(i);
				addInfo.managingNumber = jsonArr12.getInt(i);
				addInfo.accumulateWarning = jsonArr13.getInt(i);
				
				addInfo.longitude = jsonArr14.getDouble(i);
				addInfo.latitude = jsonArr4.getDouble(i);
				
				
				Integer addint = new Integer(jsonArr16.getInt(i));
		        mListData.add(addInfo);
		        edgeTypes.add(addint);
			
			}
			
		} catch (Exception e) {
			mListData.clear();
			edgeTypes.clear();
		}
		this.dataChange();
	}
    
    public void reSaveToLocal(){
    	SharedPreferences.Editor ed = prefs.edit();
		JSONArray jsonArr1 = new JSONArray();
		JSONArray jsonArr2 = new JSONArray();
		JSONArray jsonArr3 = new JSONArray();
		JSONArray jsonArr4 = new JSONArray();
		JSONArray jsonArr5 = new JSONArray();
		JSONArray jsonArr6 = new JSONArray();
		JSONArray jsonArr7 = new JSONArray();
		JSONArray jsonArr8 = new JSONArray();
		JSONArray jsonArr9 = new JSONArray();
		JSONArray jsonArr10 = new JSONArray();
		JSONArray jsonArr11 = new JSONArray();
		JSONArray jsonArr12 = new JSONArray();
		JSONArray jsonArr13 = new JSONArray();
		JSONArray jsonArr14 = new JSONArray();
		JSONArray jsonArr15 = new JSONArray();
		JSONArray jsonArr16 = new JSONArray();
    	for(int i = 0 ;i<mListData.size();i++ ){
    		MemberData data = mListData.get(i);
    		Integer et = edgeTypes.get(i);
    		jsonArr1.put(data.userId);
    		jsonArr2.put(data.userName);
    		jsonArr3.put(data.userPhoneNumber);
    		
    		jsonArr4.put(data.groupMemberId);
    		jsonArr5.put(data.parentGroupMemberId);
    		jsonArr6.put(data.groupId);
    		
    		jsonArr7.put(data.edgeStatus);
    		jsonArr8.put(data.edgeType);
    		
    		jsonArr9.put(data.manageMode);
    		jsonArr10.put(data.managedLocationRadius);
    		jsonArr11.put(data.managingTotalNumber);
    		jsonArr12.put(data.managingNumber);
    		jsonArr13.put(data.accumulateWarning);
    		
    		jsonArr14.put(data.longitude);
    		jsonArr15.put(data.latitude);
    		
    		
    		jsonArr16.put(et.intValue());
    	}
    	ed.putString(REQ_ARR1_KEY+mode, jsonArr1.toString());
    	ed.putString(REQ_ARR2_KEY+mode, jsonArr2.toString());
    	ed.putString(REQ_ARR3_KEY+mode, jsonArr3.toString());
    	
    	ed.putString(REQ_ARR4_KEY+mode, jsonArr4.toString());
    	ed.putString(REQ_ARR5_KEY+mode, jsonArr5.toString());
    	ed.putString(REQ_ARR6_KEY+mode, jsonArr6.toString());
    	
    	ed.putString(REQ_ARR7_KEY+mode, jsonArr7.toString());
    	ed.putString(REQ_ARR8_KEY+mode, jsonArr8.toString());
    	
    	ed.putString(REQ_ARR9_KEY+mode, jsonArr9.toString());
    	ed.putString(REQ_ARR10_KEY+mode, jsonArr10.toString());
    	ed.putString(REQ_ARR11_KEY+mode, jsonArr11.toString());
    	ed.putString(REQ_ARR12_KEY+mode, jsonArr12.toString());
    	ed.putString(REQ_ARR13_KEY+mode, jsonArr13.toString());
    	
    	ed.putString(REQ_ARR14_KEY+mode, jsonArr14.toString());
    	ed.putString(REQ_ARR15_KEY+mode, jsonArr15.toString());
    	
    	ed.putString(REQ_ARR16_KEY+mode, jsonArr16.toString());
    	
    	
    	ed.commit();
    }
    
    
    
    
    public void setmode(int mode){
    	this.mode = mode;
    	this.reLoadFromLocal();
    }
    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	RequestViewHolder holder;
		final MemberData mData = mListData.get(mListData.size()-position -1);
		final int edgeType = edgeTypes.get(mListData.size()-position -1);
		final int position_ = position;
		if (convertView == null){
			holder = new RequestViewHolder();

			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.request_item, null);

			holder.text1 = (TextView) convertView
					.findViewById(R.id.rqlist_item_txt1);
			holder.img1 = (ImageView) convertView
					.findViewById(R.id.rqlist_item_imgView1);
			
			holder.btn1 = (Button) convertView
					.findViewById(R.id.rqlist_item_btn1);
			holder.btn2 = (Button) convertView
					.findViewById(R.id.rqlist_item_btn2);
			
		
			
			
			convertView.setTag(holder);
		}else{
			holder = (RequestViewHolder) convertView.getTag();
		}
		
		String edgeTypeStr = "";
		
		if(edgeType%100 == 10){
			
			edgeTypeStr="정보보고관계 요청\n";
		}else if(edgeType%100 == 20){
			
			edgeTypeStr="위치관리관계 요청\n";
		}
		
		
		holder.text1.setText(""+edgeTypeStr+ "  "+ mData.userName + "  "+ mData.userPhoneNumber);
		
		holder.btn1.setText("거절");
		holder.btn2.setText("수락");
		holder.btn1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext); 
				builder.setTitle("")
						.setMessage("거절 하시겠습니까?")
						.setCancelable(true)
						.setPositiveButton("확인",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {

										remove(position_);
									}
								})
						.setNegativeButton("취소",
								new DialogInterface.OnClickListener() {
									// 취소 버튼 클릭시 설정
									public void onClick(DialogInterface dialog,
											int whichButton) {

										dialog.cancel();
									}
								});
				AlertDialog dialog = builder.create(); // 알림창 객체 생성
				dialog.show(); // 알림창 띄우기
			}
		
		});
		holder.btn2.setOnClickListener(new OnClickListener(){
			
			
			@Override
			public void onClick(View v) {
				MakeEdgeRequest mer = null;

				MemberData parentData = GroupManager.getInstance().getParent();
				int myid = MyManager.getInstance().getGroupMemberId();
				if(mode == FROMUP){
					
					if(parentData == null){
						
						mer = new MakeEdgeRequest(myid,mData.groupMemberId,edgeType);
						
					}else{
						
						
						AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
						builder.setTitle("알림")
								.setMessage("이미 관리자가있습니다!")
								.setCancelable(true)
								.setPositiveButton("확인", new DialogInterface.OnClickListener() {
		
									public void onClick(DialogInterface dialog, int whichButton) {
										
										
										
										dialog.cancel();
										
									}

								});
						
						AlertDialog dialog = builder.create(); // 알림창 객체 생성
						dialog.show();

					}
					
					
				}else if(mode ==FROMDOWN){
					
					mer = new MakeEdgeRequest(myid,mData.groupMemberId,edgeType) ;


					
				}else{
					return;
	
				}
				if(mer != null){
					
					NetworkManager.getInstance().request(mer, onMakeEdgeResponse, null);
				}
				

				
				remove(position_);
			}
		});
		
		
		
		
		return convertView;
	}

	public void addItem(MemberData mdata, int edgeType){

        mListData.add(mdata);
        edgeTypes.add(edgeType);
        
        this.reSaveToLocal();
    }

    public void remove(int position){
        mListData.remove(position);
        edgeTypes.remove(position);
       
        this.reSaveToLocal();
        this.reLoadFromLocal();
        dataChange();
    }

    


    public void dataChange(){
        this.notifyDataSetChanged();
    }

}


class RequestViewHolder {
    public RequestViewHolder() {
		// TODO Auto-generated constructor stub
	}

    public ImageView img1;
    public TextView text1;
    public Button btn1;
    public Button btn2;
    

}

