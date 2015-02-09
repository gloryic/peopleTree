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
import com.ssm.peopleTree.dialog.MsgSendDialog;
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
				String str1 = "�˸�";
				String str2 = "";
				if (res.getStatus() == Status.SUCCESS) {

					str2 = "��û��������\n [from:"+res.from +"]\n [to:"+res.to +"]";
					str2+="\n status:" +res.statusCode;

				} else {
					str2 = "��û������ �����Ͽ����ϴ�";

				}
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setTitle(str1)
						.setMessage(str2)
						.setCancelable(true)
						// �ڷ� ��ư Ŭ���� ��� ���� ����
						.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
							// Ȯ�� ��ư Ŭ���� ����
							public void onClick(DialogInterface dialog, int whichButton) {
								
								
								
								dialog.cancel();
								
							}

						});
				
				AlertDialog dialog = builder.create(); // �˸�â ��ü ����
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
		final MemberData mData = mListData.get(position);
		final int edgeType = edgeTypes.get(position);
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
	
		holder.text1.setText(""+ mData.userName + "  "+ mData.userPhoneNumber);
		
		holder.btn1.setText("����");
		holder.btn2.setText("����");
		holder.btn1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext); 
				builder.setTitle("")
						.setMessage("���� �Ͻðڽ��ϱ�?")
						.setCancelable(true)
						.setPositiveButton("Ȯ��",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {

										remove(position_);
									}
								})
						.setNegativeButton("���",
								new DialogInterface.OnClickListener() {
									// ��� ��ư Ŭ���� ����
									public void onClick(DialogInterface dialog,
											int whichButton) {

										dialog.cancel();
									}
								});
				AlertDialog dialog = builder.create(); // �˸�â ��ü ����
				dialog.show(); // �˸�â ����
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

