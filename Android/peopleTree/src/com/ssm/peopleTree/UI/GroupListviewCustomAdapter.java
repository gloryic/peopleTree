package com.ssm.peopleTree.UI;

import java.util.ArrayList;

import com.ssm.peopleTree.R;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.dialog.ChildInfoDialog;
import com.ssm.peopleTree.group.GroupManager;
import com.ssm.peopleTree.map.MapManager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class GroupListviewCustomAdapter extends BaseAdapter implements OnClickListener {

	private Context mContext = null;
    private ArrayList<MemberData> mListData;
    private ChildInfoDialog childInfoDialog;

    public GroupListviewCustomAdapter(Context mContext) {
        super();
        this.mContext = mContext;
        
        mListData = GroupManager.getInstance().getChildren();
       
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


		if (convertView == null) {
			holder = new GroupListViewHolder();

			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.grouplist_item_child, null);
			
		
			holder.nametxtv = (TextView) convertView.findViewById(R.id.list_item_name);
			holder.numtxtv1 = (TextView) convertView.findViewById(R.id.list_item_num);
			holder.btn1 = (ImageButton) convertView.findViewById(R.id.list_item_btn);
			holder.imgVeiw = (ImageView) convertView.findViewById(R.id.list_item_img);
			
			
			
			convertView.setTag(holder);
		}else{
			holder = (GroupListViewHolder) convertView.getTag();
		}
		holder.nametxtv.setText(mData.userName);
		if (mData.managingTotalNumber == 0) {
			holder.numtxtv1.setText("");
		}
		else {
			holder.numtxtv1.setText(mData.managingNumber + "/" + mData.managingTotalNumber);
		}
		holder.btn1.setOnClickListener(this);
		holder.btn1.setTag(mData);

		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				GroupManager.getInstance().update(mData.groupMemberId);
				GroupManager.getInstance().navigateDown(mData);
			}
		});
		
		Drawable da = mContext.getResources().getDrawable(R.drawable.list_item_selector);

		if(mData.accumulateWarning>0){
			da = mContext.getResources().getDrawable(R.drawable.list_item_red_selector);
		}else if(mData.managingNumber != mData.managingTotalNumber){
			da = mContext.getResources().getDrawable(R.drawable.list_item_orange_selector);
		}
		convertView.setBackground(da);

		return convertView;
	}

	@Override
	public void onClick(View v) {
		ImageButton btn = (ImageButton)v;
		if (btn == null) {
			return;
		}
		
		MemberData mData = (MemberData)btn.getTag();
		if (mData == null) {
			return;
		}
		childInfoDialog = new ChildInfoDialog(mContext,mData);
		MapManager.getInstance().setChild(mData);
		childInfoDialog.setChildData(mData);
		childInfoDialog.show();
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

}
