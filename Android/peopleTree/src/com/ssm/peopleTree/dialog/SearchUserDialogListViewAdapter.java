package com.ssm.peopleTree.dialog;

import java.util.ArrayList;

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

import com.ssm.peopleTree.R;
import com.ssm.peopleTree.data.MemberData;

public class SearchUserDialogListViewAdapter extends BaseAdapter {

	private Context mContext = null;
	private ArrayList<MemberData> mListData = new ArrayList<MemberData>();

	private SearchUserDialog parentDialog;

	public SearchUserDialogListViewAdapter(Context mContext) {
		super();
		this.mContext = mContext;

	}

	public void setParent(SearchUserDialog parentDialog) {
		this.parentDialog = parentDialog;
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
			convertView = inflater.inflate(R.layout.dialog_searchuser_item,
					null);

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

		holder.textvID.setText(mData.getUserId());
		holder.textvName.setText(mData.getUserName());
		holder.textvTel.setText(mData.getUserPhoneNumber());
		holder.selectBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (parentDialog == null) {
					return;
				}
				String modeStr = "[]";
				
				
				switch (parentDialog.getMode()) {
				case SearchUserDialog.CHILD_ADD_MODE:
					modeStr ="자식추가";
					break;
				case SearchUserDialog.PARENT_ADD_MODE:
					modeStr = "부모추가";
					break;
				default:
					break;

				}

				AlertDialog.Builder builder = new AlertDialog.Builder(mContext); // 여기서
																					// this는
																					// Activity의
																					// this
				builder.setTitle(modeStr)
						.setMessage(mData.userName +"을(를) 선택하시겠습니까?")
						.setCancelable(true)
						// 뒤로 버튼 클릭시 취소 가능 설정
						.setPositiveButton("확인",
								new DialogInterface.OnClickListener() {
									// 확인 버튼 클릭시 설정
									public void onClick(DialogInterface dialog,
											int whichButton) {
										
										
										
										
										

										parentDialog.dismiss();
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
				// parentDialog.dismiss();

			}
		});

		return convertView;
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
