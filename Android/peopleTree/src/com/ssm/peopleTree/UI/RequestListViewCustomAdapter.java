package com.ssm.peopleTree.UI;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

					str2 = "요청수락성공\n [from:"+res.from +"]\n [to:"+res.to +"]";
					str2+="\n status:" +res.statusCode;
					GroupManager.getInstance().updateSelf();
					GroupManager.getInstance().navigateHome();
				} else {
					str2 = "요청수락이 실패하였습니다";

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
    public void setmode(int mode){
    	this.mode = mode;
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
    }

    public void remove(int position){
        mListData.remove(position);
        edgeTypes.remove(position);
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

