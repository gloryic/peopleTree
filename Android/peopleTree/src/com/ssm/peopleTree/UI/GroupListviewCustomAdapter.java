package com.ssm.peopleTree.UI;

import java.util.ArrayList;

import com.ssm.peopleTree.R;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.dialog.ChildInfoDialog;
import com.ssm.peopleTree.group.GroupManager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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

    public GroupListviewCustomAdapter(Context mContext, OnClickListener onClickListener) {
        super();
        this.mContext = mContext;
        
        mListData = GroupManager.getInstance().getChildren();
        childInfoDialog = new ChildInfoDialog(mContext, onClickListener);
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

	
		//    android:background="#AACCFF" 
		
		if (convertView == null) {
			holder = new GroupListViewHolder();

			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.grouplist_item_child, null);
			
			convertView.setBackgroundColor(Color.parseColor("#AFEFEF"));
			holder.nametxtv = (TextView) convertView.findViewById(R.id.list_item_name);
			holder.numtxtv1 = (TextView) convertView.findViewById(R.id.list_item_num);
			holder.btn1 = (ImageButton) convertView.findViewById(R.id.list_item_btn);
			holder.imgVeiw = (ImageView) convertView.findViewById(R.id.list_item_img);
			
			
			
			convertView.setTag(holder);
		}else{
			holder = (GroupListViewHolder) convertView.getTag();
		}
		holder.nametxtv.setText(mData.getUserName());
		holder.btn1.setOnClickListener(this);
		holder.btn1.setTag(mData.getUserName());

		int cnum = position %5;
		switch(cnum){
		case 0:
			convertView.setBackgroundColor(Color.parseColor("#7FBFFF"));
			break;
		case 1:
			convertView.setBackgroundColor(Color.parseColor("#93DAFF"));
			break;
		case 2:

			convertView.setBackgroundColor(Color.parseColor("#9FAFFF"));
			break;
		case 3:

			convertView.setBackgroundColor(Color.parseColor("#1EA4FF"));
			break;		
		case 4:

			convertView.setBackgroundColor(Color.parseColor("#87CEFA"));
			break;		
		}
		
		return convertView;
	}

	@Override
	public void onClick(View v) {
		ImageButton btn = (ImageButton)v;
		String title = "";
		if (btn != null) {
			title = (String)btn.getTag();
		}
		
		childInfoDialog.show();
		childInfoDialog.setchildInfoTitle(title);	
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
