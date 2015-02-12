package com.ssm.peopleTree.UI;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.ssm.peopleTree.R;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.dialog.ChildInfoDialog;
import com.ssm.peopleTree.dialog.GroupReqDialog;
import com.ssm.peopleTree.dialog.MyMenuDialog;
import com.ssm.peopleTree.dialog.ParentInfoDialog;
import com.ssm.peopleTree.dialog.SearchUserDialog;
import com.ssm.peopleTree.group.GroupManager;
import com.ssm.peopleTree.group.Navigator;
import com.ssm.peopleTree.map.MapManager;

public class GroupListController extends Fragment implements Observer {
	Context mContext;

	
	LayoutInflater inflater;


	GroupListviewCustomAdapter glvca;
	//Listview glv
	PullToRefreshListView glv;
	
	
	ImageView mmenu;
	
	RelativeLayout parentAddBtn;
	RelativeLayout childAddBtn;
	
	GroupReqDialog groupReqDialog;
	SearchUserDialog searchUserDialog;
	MyMenuDialog myMenuDialog;
	ParentInfoDialog parentInfoDialog;
	ChildInfoDialog childInfoDialog;
	private RelativeLayout layout;
	boolean dragFlag = true;
	boolean firstDragFlag = true;
	float startYposition;
	float endYPosition;
	private Animation slideup, slidedown, slideup_top, slidedown_top;
	private RelativeLayout mBottomBar, mTopBar;

	
	private TextView naviText;
	
	public GroupListController(Context context, Observable observable) {
		this.mContext = context;
		observable.addObserver(this);
		
		GroupManager.getInstance().getNavigator().addObserver(this);
	}
	
	public void setListAdapter(GroupListviewCustomAdapter adap){
		this.glvca = adap;
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		layout = (RelativeLayout)inflater.inflate(R.layout.grouplist_layout, container, false);

		//glv = (ListView) layout.findViewById(R.id.groupList);
		
		//TODO//
		glv = (PullToRefreshListView)layout.findViewById(R.id.groupList);

		
		//mBottomBar = (RelativeLayout) layout.findViewById(R.id.bottom_bar);
		//mTopBar = (RelativeLayout) layout.findViewById(R.id.groupParent_layout_Total);
		
		glv.setAdapter(glvca);

		this.inflater = inflater;
		
		setParent(GroupManager.getInstance().getParent());
		setCur(GroupManager.getInstance().getCur());

		childAddBtn = (RelativeLayout) layout.findViewById(R.id.child_adder);
		RelativeLayout rr;
		childAddBtn.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View arg0) {
            	searchUserDialog = new SearchUserDialog(mContext);
            	searchUserDialog.setMode(SearchUserDialog.CHILD_ADD_MODE);
				searchUserDialog.show();
			}
		});
		

		
		//mBottomBar.bringToFront();
		//mBottomBar.setVisibility(View.GONE);
		//mBottomBar.invalidate();
		
		//mTopBar.bringToFront();
		//mTopBar.setVisibility(View.GONE);
		//mTopBar.invalidate();
		
		slideup = AnimationUtils.loadAnimation(mContext, R.anim.slide_from_bottom);
		slidedown = AnimationUtils.loadAnimation(mContext, R.anim.slide_to_bottom);
		
		slideup_top = AnimationUtils.loadAnimation(mContext, R.anim.slide_from_top);
		slidedown_top = AnimationUtils.loadAnimation(mContext, R.anim.slide_to_top);
		
		glv.setMode(Mode.PULL_FROM_START);
		
		/*
		glv.setOnTouchListener(new OnTouchListener() {
            
		    @Override
		    public boolean onTouch(View arg0, MotionEvent event) {
		    	
		    	
		    	switch (event.getAction()) {
		    	case MotionEvent.ACTION_MOVE:
		    		dragFlag = true;
		    		if(firstDragFlag){
		    			startYposition = event.getY();
		    			firstDragFlag = false;
		    		}
		    		
		    		break;
		    		
		    		
		    	case MotionEvent.ACTION_UP :
		    		endYPosition = event.getY();
		    		firstDragFlag = true;
		    		if(dragFlag){
		    			
		    			Log.i("11",startYposition +">" + endYPosition + "&&" +startYposition +"-"+ endYPosition+"스크롤");
		    			if((startYposition > endYPosition) && (startYposition - endYPosition) > 13){
		    				Log.i("111","스크롤업");
							if (mBottomBar.getVisibility() != View.VISIBLE ) {//&& mTopBar.getVisibility() != View.VISIBLE
								mBottomBar.startAnimation(slideup);
								mBottomBar.setVisibility(View.VISIBLE);
							}	
							/*
							else if (mTopBar.getVisibility() != View.GONE) {
								mTopBar.startAnimation(slideup_top);
								mTopBar.setVisibility(View.GONE);
							}
							else {}
							
		    			}
		    			else if((startYposition < endYPosition) && (endYPosition - startYposition) > 13){
		    				Log.i("111","스크롤다운");
							if (mBottomBar.getVisibility() != View.GONE) {
								mBottomBar.startAnimation(slidedown);
								mBottomBar.setVisibility(View.GONE);
							}
							/*
							else if (mTopBar.getVisibility() != View.VISIBLE) {
								mTopBar.startAnimation(slidedown_top);
								mTopBar.setVisibility(View.VISIBLE);
							}
							
							else{}
						}
		    		}
			        startYposition = 0.0f;
			        endYPosition = 0.0f;
			        //motionFlag = false;
			        break;
		    	}
		    	
		    	
		    	return false;

		    }
		});*/
		
		
		
		
		glv.setOnRefreshListener(new OnRefreshListener<ListView>() {
			
			
				@Override
				public void onRefresh(PullToRefreshBase<ListView> refreshView) {
					
					
					GroupManager.getInstance().update(GroupManager.getInstance().getCur().groupMemberId);
					
					new ProcessTask().execute();
					//glv.setAdapter(adapter);
					
				}
			});
		

		final HorizontalScrollView hsv = (HorizontalScrollView)layout.findViewById(R.id.navi_scroll);
		naviText = (TextView)layout.findViewById(R.id.navi_text_view);
		ViewTreeObserver vto = layout.getViewTreeObserver();
	    vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener(){
	        @Override
	        public void onGlobalLayout() {
	        	hsv.scrollTo(naviText.getWidth(), 0);
            }
        });
		
		
		return layout;
	}	

	private void setCur(MemberData myData) {
		RelativeLayout curLayout = (RelativeLayout) layout.findViewById(R.id.groupcur_layout);
		curLayout.removeAllViews();
		
		inflater.inflate(R.layout.grouplist_item_cur, curLayout, true);
		
		TextView myNum = (TextView)curLayout.findViewById(R.id.grouplist_cur_num);
		if (myNum != null) {
			String myNumStr = "";
			if (myData != null && myData.managingTotalNumber > 0) {
				myNumStr = myData.managingNumber + "/" + myData.managingTotalNumber;
			}
			myNum.setText(myNumStr);
		}
		
		TextView myName = (TextView)curLayout.findViewById(R.id.grouplist_cur_name);
		if (myName != null) {
			String myNameStr = "";
			if (myData != null) {
				myNameStr = myData.userName;
			}
			myName.setText(myNameStr);
		}
		
		mmenu = (ImageView) layout.findViewById(R.id.grouplist_cur_btn);
		mmenu.setOnClickListener(new View.OnClickListener() {

				public void onClick(View arg0) {
					int curId = GroupManager.getInstance().getCur().groupMemberId;
					int myID = MyManager.getInstance().getGroupMemberId();
					MemberData curData = GroupManager.getInstance().getCur();
					if(curId == myID){
						myMenuDialog = new MyMenuDialog(mContext);

						myMenuDialog.setMytitle("메뉴");
						myMenuDialog.show();
					}else{
						 childInfoDialog = new ChildInfoDialog(mContext, curData);
						 MapManager.getInstance().setChild(curData);
						 childInfoDialog.setchildInfoTitle(curData.userName);
						 childInfoDialog.show();
					}
				}
			});
		

		if(myData.accumulateWarning>0){
			Drawable da = mContext.getResources().getDrawable(R.drawable.gclist_ex2_selector);
			curLayout.setBackground(da);
			
		}else if(myData.managingNumber != myData.managingTotalNumber){
			Drawable da = mContext.getResources().getDrawable(R.drawable.gclist_ex1_selector);
			curLayout.setBackground(da);
			
		}else{
			curLayout.setBackgroundColor(Color.parseColor("#C3EFAD"));
			
		}
		
		Log.i("log","color-------------");
		curLayout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {

					
				}
			});
	}

	private void setParent(MemberData parentData) {
		RelativeLayout parentLayout = (RelativeLayout) layout.findViewById(R.id.groupParent_layout);
		final MemberData fparentData = parentData;
		parentLayout.removeAllViews();
		if(parentData == null){
			inflater.inflate(R.layout.grouplist_item_parent_adder,parentLayout,true);
			parentAddBtn = (RelativeLayout)parentLayout.findViewById(R.id.parent_add_btn);
			parentAddBtn.setOnClickListener(new View.OnClickListener() {
				
	            public void onClick(View arg0) {
	            	searchUserDialog = new SearchUserDialog(mContext);
	            	searchUserDialog.setMode(SearchUserDialog.PARENT_ADD_MODE);
	            	searchUserDialog.show();
	            }
	        });
			
		}
		else {
			inflater.inflate(R.layout.grouplist_item_parent,parentLayout,true);
			
			TextView parentName = (TextView)parentLayout.findViewById(R.id.parent_name);
			if (parentName != null) {
				String parentNameStr = "";
				if (parentData != null) {
					parentNameStr = parentData.userName;
				}
				parentName.setText(parentNameStr);
			}
			
			ImageButton parentBtn = (ImageButton)parentLayout.findViewById(R.id.parent_btn);			
			if (parentBtn != null) {
				parentBtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
				
						parentInfoDialog = new ParentInfoDialog(mContext);
						parentInfoDialog.show();
						parentInfoDialog.setParentData(fparentData);
		
						
					}
				});
			}

			parentLayout.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						MemberData mydata = MyManager.getInstance().getMyData();
						
						if(mydata.parentGroupMemberId!= fparentData.groupMemberId ||mydata.groupMemberId ==fparentData.groupMemberId   ){

							GroupManager.getInstance().update(fparentData.groupMemberId);
							GroupManager.getInstance().navigateUp(fparentData);
							

						}else{
							
							AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
							builder.setTitle("알림")
									.setMessage("자신의 상위 그룹 보기는 금지되어있습니다.")
									.setCancelable(true)
									// 뒤로 버튼 클릭시 취소 가능 설정
									.setPositiveButton("확인", new DialogInterface.OnClickListener() {
										// 확인 버튼 클릭시 설정
										public void onClick(DialogInterface dialog, int whichButton) {
											
											

											dialog.cancel();
										}

									});
							AlertDialog dialog = builder.create(); // 알림창 객체 생성
							dialog.show();
						}
					}
				});
			
		}
	}

	@Override
	public void update(Observable observable, Object data) {
			
		if (observable instanceof GroupManager) {
			GroupManager groupManager = (GroupManager)observable;
			
			setParent(groupManager.getParent());
			setCur(groupManager.getCur());
			if (glv != null) {
				//glv.removeAllViewsInLayout();
				glv.setAdapter(glvca);
			}
		}
		else if (observable instanceof Navigator) {
			Navigator navi = (Navigator)observable;
			naviText.setText(navi.toString());
		}
	}
	

	public class ProcessTask extends AsyncTask<String, Integer, Long> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		}

		@Override
		protected Long doInBackground(String... params) {
			try {
				Thread.sleep(1000);
				//Toast.makeText(mContext, "dddd",Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				return 0l;
			}
			return 1l;
		}

		@Override
		protected void onPostExecute(Long result) {
			super.onPostExecute(result);

			glv.onRefreshComplete();
		}
	}

}
