package com.ssm.peopleTree;


import java.util.Observable;
import java.util.Observer;

import com.ssm.peopleTree.BackPressCloseHandler;
import com.ssm.peopleTree.UI.BroadCastLayoutController;
import com.ssm.peopleTree.UI.BroadCastListViewCustomAdapter;
import com.ssm.peopleTree.UI.GroupListController;
import com.ssm.peopleTree.UI.GroupListviewCustomAdapter;
import com.ssm.peopleTree.UI.PushMessageListViewCustomAdapter;
import com.ssm.peopleTree.UI.PushmsgLayoutController;
import com.ssm.peopleTree.UI.RequestLayoutController;
import com.ssm.peopleTree.UI.RequestListViewCustomAdapter;
import com.ssm.peopleTree.UI.SettingLayoutController;
import com.ssm.peopleTree.application.LoginManager;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.broadcast.PushManager;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.dialog.NetworkProgressDialog;
import com.ssm.peopleTree.group.GroupManager;
import com.ssm.peopleTree.group.GroupManager.GroupListener;
import com.ssm.peopleTree.group.Navigator;
import com.ssm.peopleTree.map.ManageMode;
import com.ssm.peopleTree.network.NetworkManager;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class TestActivity extends FragmentActivity implements Progressable, OnClickListener {
	
	private int NUM_PAGES = 5;		// 최대 페이지의 수 
	
	/* Fragment numbering */
	public final static int FRAGMENT_PAGE1 = 0;
	public final static int FRAGMENT_PAGE2 = 1;
	public final static int FRAGMENT_PAGE3 = 2;
	public final static int FRAGMENT_PAGE4 = 3;
	public final static int FRAGMENT_PAGE5 = 4;
	public static boolean isGPSdialogPop = false;
	private BackPressCloseHandler backPressCloseHandler;
	
	ViewPager mViewPager;			// View pager를 지칭할 변수 
	
	Button page1Btn, page2Btn, page3Btn,page4Btn,page5Btn;
	

	PushMessageListViewCustomAdapter pmlvca;
	BroadCastListViewCustomAdapter bclvca;
	RequestListViewCustomAdapter upRqlvca;
	RequestListViewCustomAdapter downRqlvca;
	GroupListviewCustomAdapter glvca;
	
	private NetworkManager networkManager;
	private MyManager myManager;
	private GroupManager groupManager;
	private RelativeLayout bottomBar;
	
	GroupListController groupListController;
	RequestLayoutController requestLayoutController;
	BroadCastLayoutController broadCastLayoutController;
	PushmsgLayoutController pushmsgLayoutController;
	SettingLayoutController settingLayoutController;
	
	private NetworkProgressDialog progDialog;
	private AlertDialog alertDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.tframe);

		
		startService(new Intent("android.servcice.MAIN"));
		
		IntentFilter filter = new IntentFilter("android.location.PROVIDERS_CHANGED");
        this.registerReceiver(mReceivedSMSReceiver, filter);
        
		progDialog = new NetworkProgressDialog(this);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("알림")
				.setMessage("그룹이 변경되었습니다.\n초기 그룹화면으로 돌아갑니다.")
				.setCancelable(true)
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						groupManager.updateSelf();
						dialog.cancel();
					}
				});
		
		alertDialog = builder.create();

		backPressCloseHandler = new BackPressCloseHandler(this);
		
		networkManager = NetworkManager.getInstance();
		myManager = MyManager.getInstance();
		groupManager = GroupManager.getInstance();
		groupManager.setGroupListener(new GroupListener() {
						
			@Override
			public void onUpdateStart(Object arg) {
				progDialog.show();
			}
			
			@Override
			public void onUpdateSuccess(Object arg) {
				
				if (ManageMode.getMode(myManager.getMyParentData().manageMode) != ManageMode.NOTHING) {
					chkGpsService();
				}
				
				progDialog.complete();
			}
			
			@Override
			public void onUpdateFail(Object arg) {
				if (arg instanceof Integer) {
					Integer id = (Integer)arg;
					
					if (id != myManager.getGroupMemberId()) {
						alertDialog.show();
						groupManager.updateSelf();
					}
				}
				GroupManager.getInstance().navigateHome();
			}
		});
	
		page1Btn = (Button) findViewById(R.id.Page1Btn);
		page1Btn.setOnClickListener(this);
		page2Btn = (Button) findViewById(R.id.Page2Btn);
		page2Btn.setOnClickListener(this);
		page3Btn = (Button) findViewById(R.id.Page3Btn);
		page3Btn.setOnClickListener(this);
		page4Btn = (Button) findViewById(R.id.Page4Btn);
		page4Btn.setOnClickListener(this);
		page5Btn = (Button) findViewById(R.id.Page5Btn);
		page5Btn.setOnClickListener(this);
		
		page1Btn.setText("그룹");
		page2Btn.setText("요청");
		page3Btn.setText("메시지");
		page4Btn.setText("알림");
		page5Btn.setText("설정");
		
				
		bclvca = new BroadCastListViewCustomAdapter(this);
		upRqlvca = new RequestListViewCustomAdapter(this);
		downRqlvca = new RequestListViewCustomAdapter(this);
		


		
		glvca = new GroupListviewCustomAdapter(this);
		pmlvca = new PushMessageListViewCustomAdapter(this);



		groupListController = new GroupListController(this, groupManager);
		requestLayoutController =  new RequestLayoutController(this);
		broadCastLayoutController = new BroadCastLayoutController(this);
		pushmsgLayoutController = new PushmsgLayoutController(this);
		settingLayoutController = new SettingLayoutController(this);

		groupListController.setListAdapter(glvca);
		requestLayoutController.setListAdapter(upRqlvca, downRqlvca);
		broadCastLayoutController.setListAdapter(bclvca);
		pushmsgLayoutController.setListAdapter(pmlvca);
		PushManager.getInstance().setAdapters(pmlvca, bclvca, upRqlvca, downRqlvca, glvca);
		
		// ViewPager를 검색하고 Adapter를 달아주고, 첫 페이지를 선정해준다.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(new pagerAdapter(getSupportFragmentManager()));
		mViewPager.setCurrentItem(FRAGMENT_PAGE1);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

				page1Btn.setSelected(false);
				page2Btn.setSelected(false);
				page3Btn.setSelected(false);
				page4Btn.setSelected(false);
				page5Btn.setSelected(false);

				switch(position){
					case 0:
						page1Btn.setSelected(true);
						break;
					case 1:
						page2Btn.setSelected(true);
						break;
					case 2:
						page3Btn.setSelected(true);
						break;
					case 3:
						page4Btn.setSelected(true);
						break;
					case 4:
						page5Btn.setSelected(true);
						break;
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		page1Btn.setSelected(true);
		
		
		if (ManageMode.getMode(myManager.getMyParentData().manageMode) != ManageMode.NOTHING)
			chkGpsService();

	}
	
	
	
	@Override
	public void onBackPressed() {
		
		

		MyManager myManager = MyManager.getInstance();
		MemberData myData = myManager.getMyData();
		MemberData curData = groupManager.getCur();
		MemberData parentData = myManager.getMyParentData();
	
		
		if(myData.groupMemberId != curData.groupMemberId ){
			
			if(curData.parentGroupMemberId == curData.groupMemberId) {
				GroupManager.getInstance().updateSelf();
			}
			else {
				GroupManager.getInstance().update(curData.parentGroupMemberId);
				GroupManager.getInstance().navigateUp(parentData);
			}
		}else{
			
			backPressCloseHandler.onBackPressed();

		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO
		
		menu.add(0, 1, Menu.NONE, "Logout(실제)");
        menu.add(0, 2, Menu.NONE, "Logout(가상)");
        menu.add(0, 3, Menu.NONE, "Refresh");
		/*
		 * .setIcon(android.R.drawable.ic_menu_rotate);
        menu.add(0, 3, Menu.NONE, "").setIcon(android.R.drawable.ic_menu_agenda);
        menu.add(0, 4, Menu.NONE, "").setIcon(android.R.drawable.ic_menu_add);
        menu.add(0, 5, Menu.NONE, "");
        */
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		
		switch (item.getItemId()) {
        case 1:
            Toast.makeText(TestActivity.this, "Logout", Toast.LENGTH_SHORT).show();
            LoginManager.getInstance().logout();
            
    		intent = new Intent(TestActivity.this, LoginActivity.class);
    		startActivity(intent);
    		finish();
            break;
 
        case 2:
        	LoginManager.getInstance().clear();
    		intent = new Intent(TestActivity.this, LoginActivity.class);
    		startActivity(intent);
    		finish();
            break;
 
        case 3:
        	groupManager.updateSelf();
        	GroupManager.getInstance().navigateHome();
            break;
 
        default:
            break;
        }
		return super.onOptionsItemSelected(item);
	}	
	
	public void nextActivity(Class<?> cls) {
		Intent intent;
		intent = new Intent(TestActivity.this, cls);
		startActivity(intent);
	}
	
	// FragmentPageAdater : Fragment로써 각각의 페이지를 어떻게 보여줄지 정의한다. 
	private class pagerAdapter extends FragmentPagerAdapter{

		public pagerAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
		}


		@Override
		public Fragment getItem(int position) {
			
			switch(position){
				case 0:
					return groupListController;
				case 1:
					return requestLayoutController;
				case 2:
					return broadCastLayoutController;
				case 3:
					return pushmsgLayoutController;
				case 4:
					return settingLayoutController;
				default:
					return null;
			}
		}
		
		// 생성 가능한 페이지 개수를 반환해준다.
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return NUM_PAGES;
		}
	}

	@Override
	public void onClick(View v) {

		switch(v.getId()){
			case R.id.Page1Btn:
				mViewPager.setCurrentItem(FRAGMENT_PAGE1);
				break;
			case R.id.Page2Btn:
				mViewPager.setCurrentItem(FRAGMENT_PAGE2);
				break;
			case R.id.Page3Btn:
				mViewPager.setCurrentItem(FRAGMENT_PAGE3);
				break;
			case R.id.Page4Btn:
				mViewPager.setCurrentItem(FRAGMENT_PAGE4);
				break;
			case R.id.Page5Btn:
				mViewPager.setCurrentItem(FRAGMENT_PAGE5);
				break;

		}
	}

	@Override
	public void progress() {
		nextActivity(MapActivity.class);
	}

	
	private void displayAlert()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?").setCancelable(
            false).setPositiveButton("Yes",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            }).setNegativeButton("No",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
        AlertDialog alert = builder.create();
        alert.show();
    }
	
	
	private final BroadcastReceiver mReceivedSMSReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) 
            {
            	//GPS 키라고 호출
                chkGpsService();
            }
        }
    };

	
    public boolean chkGpsService() {
    	String gs = android.provider.Settings.Secure.getString(getContentResolver(),
    	android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
    	if (gs.indexOf("gps", 0) < 0 && !isGPSdialogPop) {
    		// GPS OFF 일때 Dialog 띄워서 설정 화면으로 튀어봅니다..
    		isGPSdialogPop = true;
    		AlertDialog.Builder gsDialog = new AlertDialog.Builder(this);
    		gsDialog.setTitle("상위관리자가 GPS를 켜기 원합니다.");
    		gsDialog.setMessage("GPS를 설정하는 곳으로 이동합니다.");
    		gsDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				// GPS설정 화면으로 튀어요
    				Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    				intent.addCategory(Intent.CATEGORY_DEFAULT);
    				startActivity(intent);
    				
    				isGPSdialogPop = false;
    				
    			}
    		}).create().show();
    		
    		
    		return false;
    	} else {
    		return true;
    	}
    }
    
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
}

