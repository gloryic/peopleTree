package com.ssm.peopleTree.dialog;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.ssm.peopleTree.R;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.group.GroupManager;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.Status;
import com.ssm.peopleTree.network.protocol.GetInfoAllResponse;
import com.ssm.peopleTree.network.protocol.RequestEdgeRequest;
import com.ssm.peopleTree.network.protocol.RequestEdgeResponse;
import com.ssm.peopleTree.network.protocol.SearchMemberRequest;
import com.ssm.peopleTree.network.protocol.SearchMemberResponse;

public class SearchUserDialogListViewAdapter extends BaseAdapter {

	private Context mContext = null;
	private ArrayList<MemberData> mListData = new ArrayList<MemberData>();

	private SearchUserDialog parentDialog;

	private Listener<JSONObject> onRequestEdgeResponse;

	public SearchUserDialogListViewAdapter(Context mContext_) {
		super();
		this.mContext = mContext_;
		
		
		
		onRequestEdgeResponse = new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
	
				RequestEdgeResponse res = new RequestEdgeResponse(arg0);
				Status status = res.getStatus();
				String str;
				if (res.getStatus() == Status.SUCCESS) {
					
					
					str= "��û�Ǿ����ϴ�.";
				}
				else {
					str = "��û����";
					
					
				}
				
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setTitle("�˸�")
						.setMessage(str)
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

	public void setParent(SearchUserDialog parentDialog) {
		this.parentDialog = parentDialog;
	}

	public void setmListData(ArrayList<MemberData> mListData) {
		this.mListData.clear();
		this.mListData.addAll(mListData);
		dataChange();
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
		SeartchUserDialogListViewHolder holder;
		final MemberData mData = mListData.get(position);

		if (convertView == null) {
			holder = new SeartchUserDialogListViewHolder();

			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.dialog_searchuser_item,	null);

			holder.textvID = (TextView) convertView
					.findViewById(R.id.searchUserItem_txtv_id);
			holder.textvName = (TextView) convertView
					.findViewById(R.id.searchUserItem_txtv_name);
			holder.textvTel = (TextView) convertView
					.findViewById(R.id.searchUserItem_txtv_tel);

			holder.selectBtn = (Button) convertView
					.findViewById(R.id.searchUserItem_btn_select);

			convertView.setTag(holder);
		} else {
			holder = (SeartchUserDialogListViewHolder) convertView.getTag();
		}

		holder.textvID.setText(mData.userId);
		holder.textvName.setText(mData.userName);
		holder.textvTel.setText(mData.userPhoneNumber);
		holder.selectBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (parentDialog == null) {
					return;
				}
				
				AlertDialog.Builder builder = settingAlertDialog(mData);

				AlertDialog dialog = builder.create(); // �˸�â ��ü ����
				dialog.show(); // �˸�â ����
				

			}
		});

		
		return convertView;
	}

	private AlertDialog.Builder settingAlertDialog(MemberData mData_) {
		final MemberData mData = mData_;
		String str1="";
		String str2="";
		int modeSettingNum = 0;
		if(parentDialog.chkbx.isChecked()){
			str1 = "��ġ���� ";
			modeSettingNum = 20;
		}else{
			modeSettingNum = 10;
			str1 = "�������� ";
		}
		boolean validEdge = true;
	
		switch (parentDialog.getMode()) {
		case SearchUserDialog.CHILD_ADD_MODE:
			str2 = "����������� ��û";
			modeSettingNum+=400;
			
			if(MyManager.getInstance().getEdgeType()== 200 && modeSettingNum == 10){
				validEdge = false;
			}
			
			break;
		case SearchUserDialog.PARENT_ADD_MODE:
			str2 = "�����ڷ� ��û";
			modeSettingNum+=500;
			if(mData.edgeType== 200 && modeSettingNum == 10){
				validEdge = false;
			}
			
			
			break;
		default:
			break;

		}
		
	
		
		final int statusCode = modeSettingNum;
		
		
		
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		
		
		
		if (validEdge) {
			builder.setTitle(str1 + str2)
					.setMessage(mData.userName + "��(��) �����Ͻðڽ��ϱ�?")
					.setCancelable(true)
					// �ڷ� ��ư Ŭ���� ��� ���� ����
					.setPositiveButton("Ȯ��",
							new DialogInterface.OnClickListener() {
								// Ȯ�� ��ư Ŭ���� ����
								public void onClick(DialogInterface dialog,
										int whichButton) {

									int myid = MyManager.getInstance()
											.getGroupMemberId();
									int yid = mData.groupMemberId;
									RequestEdgeRequest rer = new RequestEdgeRequest(
											myid, yid, statusCode);
									NetworkManager.getInstance().request(rer,
											onRequestEdgeResponse, null);

									parentDialog.dismiss();
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
		} else {
			

			builder.setTitle("�˸�")
					.setMessage("��ȿ���� ���� �����û�Դϴ�!\n��û����� ��ġ���� �������θ� Ȯ���ϼ���")
					.setCancelable(true)
					// �ڷ� ��ư Ŭ���� ��� ���� ����
					.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
						// Ȯ�� ��ư Ŭ���� ����
						public void onClick(DialogInterface dialog, int whichButton) {
							
							

							dialog.cancel();
						}

					});
		}
		return builder;

	}

	public void addItem(MemberData data) {
		mListData.add(data);
	}

	public void remove(int position) {
		mListData.remove(position);
		dataChange();
	}

	public void clear() {
		mListData.clear();
		dataChange();
	}

	public void dataChange() {
		this.notifyDataSetChanged();
	}

}

class SeartchUserDialogListViewHolder {
	public SeartchUserDialogListViewHolder() {
		// TODO Auto-generated constructor stub
	}

	public TextView textvID;

	public TextView textvName;
	public TextView textvTel;
	public Button selectBtn;

}
