package com.ssm.peopleTree.UI;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.ssm.peopleTree.R;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.dialog.ChidInfoDialog;
import com.ssm.peopleTree.dialog.GroupReqDialog;

public class GroupListviewCustomAdapter extends BaseAdapter {

	private Context mContext = null;
	private ArrayList<MemberData> mListData = new ArrayList<MemberData>();

	public GroupListviewCustomAdapter(Context mContext) {
		super();
		this.mContext = mContext;

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
		GroupListViewHolder holder;
		final MemberData mData = mListData.get(position);
		final int checkBoxPosition = position;

		if (convertView == null) {
			holder = new GroupListViewHolder();

			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.grouplist_item_child, null);

			convertView.setBackgroundColor(Color.parseColor("#AFEFEF"));
			holder.nametxtv = (TextView) convertView
					.findViewById(R.id.list_item_name);
			holder.numtxtv1 = (TextView) convertView
					.findViewById(R.id.list_item_num);
			holder.btn1 = (ImageButton) convertView
					.findViewById(R.id.list_item_btn);
			holder.imgVeiw = (ImageView) convertView
					.findViewById(R.id.list_item_img);

			convertView.setTag(holder);
		} else {
			holder = (GroupListViewHolder) convertView.getTag();
		}
		holder.nametxtv.setText(mData.getUserName());
		holder.btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ChidInfoDialog cid = new ChidInfoDialog(mContext);
				cid.show();
				cid.setchildInfoTitle(mData.getUserName());

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

	public void dataChange() {
		this.notifyDataSetChanged();
	}

}

class GroupListViewHolder {
	public GroupListViewHolder() {
		// TODO Auto-generated constructor stub
	}

	public TextView nametxtv;
	public TextView numtxtv1;
	public ImageButton btn1;
	public ImageView imgVeiw;
}
