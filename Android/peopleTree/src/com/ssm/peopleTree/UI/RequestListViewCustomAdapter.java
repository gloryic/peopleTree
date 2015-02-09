package com.ssm.peopleTree.UI;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.ssm.peopleTree.R;

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
    private ArrayList<RequestListData> mListData = new ArrayList<RequestListData>();

    public RequestListViewCustomAdapter(Context mContext) {
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
    	RequestViewHolder holder;
		final RequestListData mData = mListData.get(position);
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
	
		holder.text1.setText(mData.getText());
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
				
			}
		});
		
		
		
		
		return convertView;
	}

	public void addItem(String text){
		RequestListData addInfo = null;
        addInfo = new RequestListData();
        addInfo.text = text; 
        mListData.add(addInfo);
    }

    public void remove(int position){
        mListData.remove(position);
        dataChange();
    }

    

    public void sort(){
        Collections.sort(mListData, RequestListData.ALPHA_COMPARATOR);
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

class RequestListData {

	public String name;
	public String text;
	public Object img;
	
	public String getName() {
		return name;
	}
	public String getText() {
		return text;
	}
	public Object getImg() {
		return img;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setImg(Object img) {
		this.img = img;
	}


	public static final Comparator<RequestListData> ALPHA_COMPARATOR = new Comparator<RequestListData>() {
		private final Collator sCollator = Collator.getInstance();

		@Override
		public int compare(RequestListData mListDate_1,
				RequestListData mListDate_2) {
			return sCollator.compare(mListDate_1.name, mListDate_2.name);
		}
	};

}