package com.ssm.peopleTree.UI;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;

import com.ssm.peopleTree.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;



public class BroadCastListViewCustomAdapter extends BaseAdapter {

	private Context mContext = null;
    private ArrayList<BroadcastListData> mListData = new ArrayList<BroadcastListData>();
	private SharedPreferences prefs;

    
	 private static final String BCMSG_DATA = "bcmsg_data"; 
	    private static final String BCMSG_ARR1_KEY = "bcmsg_arr1_key";
	    private static final String BCMSG_ARR2_KEY = "bcmsg_arr2_key";
	    private static final String BCMSG_ARR3_KEY = "bcmsg_arr3_key";
	    private static final String BCMSG_ARR4_KEY = "bcmsg_arr4_key";
    public BroadCastListViewCustomAdapter(Context mContext) {
        super();
        this.mContext = mContext;
        this.reLoadFromLocal();
       // Collections.reverse(mListData); 
      
    }
    
    public void reLoadFromLocal(){
		prefs = mContext.getSharedPreferences(BCMSG_DATA,Context.MODE_PRIVATE);
		if(prefs==null){
			return;
		}
		mListData.clear();
		
		String str1 = prefs.getString(BCMSG_ARR1_KEY, "");
		String str2 = prefs.getString(BCMSG_ARR2_KEY, "");
		String str3 = prefs.getString(BCMSG_ARR3_KEY, "");
		String str4 = prefs.getString(BCMSG_ARR4_KEY, "");
		
		try {
			JSONArray jsonArr1 = new JSONArray(str1);
			JSONArray jsonArr2 = new JSONArray(str2);
			JSONArray jsonArr3 = new JSONArray(str3);
			JSONArray jsonArr4 = new JSONArray(str4);

			for (int i = 0; !jsonArr1.isNull(i); i++) {
				
				BroadcastListData addInfo = new BroadcastListData(jsonArr1.getString(i),null,jsonArr2.getString(i),jsonArr3.getString(i),jsonArr4.getString(i));
		        mListData.add(addInfo);
			
			}
			
		} catch (Exception e) {
			mListData.clear();
		}
		this.dataChange();
	}
    /* String broacdcastMessageId, Object img, String name, String text1, String text2 */
    public void reSaveToLocal(){
    	SharedPreferences.Editor ed = prefs.edit();
    	JSONArray jsonArr1 = new JSONArray();
    	JSONArray jsonArr2 = new JSONArray();
    	JSONArray jsonArr3 = new JSONArray();
    	JSONArray jsonArr4 = new JSONArray();
    	for(BroadcastListData iter : mListData){
    		
    		jsonArr1.put(iter.getBroacdcastMessageId());
    		jsonArr2.put(iter.getName());
    		jsonArr3.put(iter.getText1());
    		jsonArr4.put(iter.getText2());
    		
    	}
    	ed.putString(BCMSG_ARR1_KEY, jsonArr1.toString());
    	ed.putString(BCMSG_ARR2_KEY, jsonArr2.toString());
    	ed.putString(BCMSG_ARR3_KEY, jsonArr3.toString());
    	ed.putString(BCMSG_ARR4_KEY, jsonArr4.toString());
    	ed.commit();
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
    	BroadcastListViewHolder holder;
		final BroadcastListData mData = mListData.get(mListData.size()-position -1);
		final int checkBoxPosition = position;
	
		if (convertView == null){
			holder = new BroadcastListViewHolder();

			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.broadcastlist_item, null);

			holder.imgVeiw = (ImageView) convertView.findViewById(R.id.bclist_item_imgView1);
			holder.nametxtv = (TextView) convertView.findViewById(R.id.bclist_item_nametxt1);
			holder.textv1 = (TextView) convertView.findViewById(R.id.bclist_item_txt1);
			holder.textv2 = (TextView) convertView.findViewById(R.id.bclist_item_txt2);
			holder.chkbox = (CheckBox) convertView.findViewById(R.id.bclist_item_chkbox1);

			convertView.setTag(holder);
		}else{
			holder = (BroadcastListViewHolder) convertView.getTag();
		}

		holder.chkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				mData.setChk(isChecked);
			}

		});
		//holder.imgVeiw = 
		holder.nametxtv.setText(mData.getName());
		holder.textv1.setText(mData.getText1());
		holder.textv2.setText(mData.getText2());
		holder.chkbox.setChecked(mData.isChk());		
		
		return convertView;
	}

	public void addItem(String broacdcastMessageId, Object img, String name, String text1, String text2){

		BroadcastListData addInfo = new BroadcastListData(broacdcastMessageId,img,name,text1,text2);

        mListData.add(addInfo);
        this.reSaveToLocal();
        
    }

    public void remove(int position){
        mListData.remove(position);
        this.reSaveToLocal();
        this.reLoadFromLocal();
        
        dataChange();
    }
    public void removeChecked(){
    	
    	for(int i =0;i<mListData.size();i++){
    		if(mListData.get(i).isChk()){
    			mListData.remove(i);
    			i--;
    		}
    		
    	}
        this.reSaveToLocal();
        this.reLoadFromLocal();
        
    	
        dataChange();
    }

    public void sort(){
        Collections.sort(mListData, BroadcastListData.ALPHA_COMPARATOR);
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

class BroadcastListViewHolder {
    public BroadcastListViewHolder() {
		// TODO Auto-generated constructor stub
	}

    public TextView nametxtv;
    public TextView textv1;
    public TextView textv2;
    public ImageView imgVeiw;

    public CheckBox chkbox;
}

class BroadcastListData {

	public BroadcastListData(String broacdcastMessageId, Object img, String name, String text1, String text2){
		this.broacdcastMessageId =broacdcastMessageId;
		this.img = img;
		this.name = name;
		this.text1 = text1;
		this.text2 = text2;
	}
	
	private boolean chk = false;
	private String broacdcastMessageId;
	
	private Object img;
	private String name;
	private String text1;

	private String text2;

	
	
	public static final Comparator<BroadcastListData> ALPHA_COMPARATOR = new Comparator<BroadcastListData>() {
		private final Collator sCollator = Collator.getInstance();

		@Override
		public int compare(BroadcastListData mListDate_1,
				BroadcastListData mListDate_2) {
			return sCollator.compare(mListDate_1.name, mListDate_2.name);
		}
	};
	public boolean isChk() {
		return chk;
	}

	public String getBroacdcastMessageId() {
		return broacdcastMessageId;
	}

	public Object getImg() {
		return img;
	}

	public String getName() {
		return name;
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
	public void setImg(Object img) {
		this.img = img;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}
}