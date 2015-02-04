package com.ssm.peopleTree;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GroupListAdapter extends BaseAdapter {
	
	private ArrayList<GroupListItem> items;
	private Context context;
	
	public GroupListAdapter(Context context, ArrayList<GroupListItem> items) {
        super();
        
        this.context = context;
        this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {	
        View v = convertView;
        if (v == null) {
         	v = View.inflate(context, R.layout.list_item_child, null);
        }
        
        GroupListItem item = items.get(position);
        if (item != null) {
            TextView num = (TextView) v.findViewById(R.id.list_item_num);
            TextView name = (TextView) v.findViewById(R.id.list_item_name);
            num.setText(item.getManagingInfoStr());
            name.setText(item.getUserName());    
        }
        return v;
    }
}
