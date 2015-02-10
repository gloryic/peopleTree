package com.ssm.peopleTree.UI;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;



import com.ssm.peopleTree.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;



public class PushMessageListViewCustomAdapter extends BaseAdapter {

	private Context mContext = null;
    private ArrayList<PushMessageListData> mListData = new ArrayList<PushMessageListData>();

    public PushMessageListViewCustomAdapter(Context mContext) {
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
    	PushMessageViewHolder holder;
		final PushMessageListData mData = mListData.get(mListData.size()-position -1);
		final int checkBoxPosition = position;
		
		if (convertView == null){
			holder = new PushMessageViewHolder();

			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.pushmessagelist_item, null);

			holder.textv1 = (TextView) convertView
					.findViewById(R.id.pmlist_item_txt1);
			holder.textv2 = (TextView) convertView
					.findViewById(R.id.pmlist_item_txt2);
			holder.chkbox = (CheckBox) convertView
					.findViewById(R.id.pmlist_item_chkbox1);

			convertView.setTag(holder);
		}else{
			holder = (PushMessageViewHolder) convertView.getTag();
		}
		holder.chkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				mData.setChk(isChecked);
			}

		});
		holder.textv1.setText(mData.getText1());
		holder.textv2.setText(mData.getText2());
		holder.chkbox.setChecked(mData.isChk());
		return convertView;
	}

	public void addItem(String pushMessageId,String text1,String text2){
    	PushMessageListData addInfo = new PushMessageListData(pushMessageId,text1,text2);
        mListData.add(addInfo);
    }

    public void remove(int position){
        mListData.remove(position);
        dataChange();
    }
    
    public void removeChecked(){
    	
    	for(int i =0;i<mListData.size();i++){
    		if(mListData.get(i).isChk()){
    			mListData.remove(i);

        		i--;
    		}
    	}
    	
    	
        dataChange();
    }

    public void sort(){
        Collections.sort(mListData, PushMessageListData.ALPHA_COMPARATOR);
        dataChange();
    }

    public void dataChange(){
        this.notifyDataSetChanged();
    }
    
    public void chekingAllItem(boolean chk){
    	
    	for(int i=0;i< mListData.size();i++)
    	{
    		mListData.get(i).setChk(chk);
   
    	}
        this.notifyDataSetChanged();
    }


}

class PushMessageViewHolder {
    public PushMessageViewHolder() {
		// TODO Auto-generated constructor stub
	}

    public TextView textv1;
    public TextView textv2;
    public CheckBox chkbox;
}

class PushMessageListData {

	   

	public boolean chk = false;
	public String pushMessageId;
	public String text1;
	public String text2;
	public PushMessageListData(String pushMessageId,String text1,String text2){
		this.pushMessageId = pushMessageId;
		this.text1 = text1;
		this.text2 = text2;
	}
	public boolean isChk() {
		return chk;
	}
	public String getPushMessageId() {
		return pushMessageId;
	}
	public String getText1() {
		return text1;
	}
	public String getText2() {
		return text2;
	}
	public void setChk(boolean chk) {
		this.chk = chk;
	}
	public void setText1(String text1) {
		this.text1 = text1;
	}
	public void setText2(String text2) {
		this.text2 = text2;
	}
	
	public static final Comparator<PushMessageListData> ALPHA_COMPARATOR = new Comparator<PushMessageListData>() {
		private final Collator sCollator = Collator.getInstance();

		@Override
		public int compare(PushMessageListData mListDate_1,
				PushMessageListData mListDate_2) {
			return sCollator.compare(mListDate_1.text1, mListDate_2.text1);
		}
	};

}