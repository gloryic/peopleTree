package com.ssm.peopleTree;

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
import com.ssm.peopleTree.map.ManageMode;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class TestActivity extends FragmentActivity implements Progressable, OnClickListener {
	
	private final static int NUM_PAGES = 4;		// �ִ� �������� �� 
	
	/* Fragment numbering */
	public final static int FRAGMENT_PAGE1 = 0;
	public final static int FRAGMENT_PAGE2 = 1;
	public final static int FRAGMENT_PAGE3 = 2;
	public final static int FRAGMENT_PAGE4 = 3;

	public static boolean isGPSdialogPop = false;
	private BackPressCloseHandler backPressCloseHandler;
	
	ViewPager mViewPager;			// View pager�� ��Ī�� ���� 
	
	
	private TextView barTextView;
	private ImageButton[] tabBtns;
	private String[] tabNames; 
	

	PushMessageListViewCustomAdapter pmlvca;
	BroadCastListViewCustomAdapter bclvca;
	RequestListViewCustomAdapter upRqlvca;
	RequestListViewCustomAdapter downRqlvca;
	GroupListviewCustomAdapter glvca;
	
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
	
		//
		startService(new Intent("android.servcice.MAIN"));		
		IntentFilter filter = new IntentFilter("android.location.PROVIDERS_CHANGED");
        this.registerReceiver(mReceivedSMSReceiver, filter);
        
        //
		progDialog = new NetworkProgressDialog(this);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("�˸�")
				.setMessage("�׷��� ����Ǿ����ϴ�.\n�ʱ� �׷�ȭ������ ���ư��ϴ�.")
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
		
		barTextView = (TextView)findViewById(R.id.bar_text);
		
		tabBtns = new ImageButton[NUM_PAGES];
		tabBtns[0] = (ImageButton)findViewById(R.id.Page1Btn);
		tabBtns[1] = (ImageButton)findViewById(R.id.Page2Btn);
		tabBtns[2] = (ImageButton)findViewById(R.id.Page3Btn);
		tabBtns[3] = (ImageButton)findViewById(R.id.Page4Btn);
		for (int i = 0; i < NUM_PAGES; i++) {
			tabBtns[i].setOnClickListener(this);
		}
		tabBtns[0].setSelected(true);
		
		tabNames = new String[NUM_PAGES];
		tabNames[0] = getString(R.string.tab_group);
		tabNames[1] = getString(R.string.tab_request);
		tabNames[2] = getString(R.string.tab_message);
		tabNames[3] = getString(R.string.tab_notification);
		
		
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
		
		// ViewPager�� �˻��ϰ� Adapter�� �޾��ְ�, ù �������� �������ش�.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(new pagerAdapter(getSupportFragmentManager()));
		mViewPager.setCurrentItem(FRAGMENT_PAGE1);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				
				for (int i = 0; i < NUM_PAGES; i++) {
					tabBtns[i].setSelected(false);
				}
				tabBtns[position].setSelected(true);
				
				barTextView.setText(tabNames[position]);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {		
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		
		if (ManageMode.getMode(myManager.getMyParentData().manageMode) != ManageMode.NOTHING) {
			chkGpsService();
		}
	}
	
	
	
	@Override
	public void onBackPressed() {

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
		
		menu.add(0, 1, Menu.NONE, "Logout(����)");
        menu.add(0, 2, Menu.NONE, "Logout(����)");
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
	
	// FragmentPageAdater : Fragment�ν� ������ �������� ��� �������� �����Ѵ�. 
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
		
		// ���� ������ ������ ������ ��ȯ���ش�.
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
            	//GPS Ű��� ȣ��
                chkGpsService();
            }
        }
    };

	
    public boolean chkGpsService() {
    	String gs = android.provider.Settings.Secure.getString(getContentResolver(),
    	android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
    	if (gs.indexOf("gps", 0) < 0 && !isGPSdialogPop) {
    		// GPS OFF �϶� Dialog ����� ���� ȭ������ Ƣ��ϴ�..
    		isGPSdialogPop = true;
    		AlertDialog.Builder gsDialog = new AlertDialog.Builder(this);
    		gsDialog.setTitle("���������ڰ� GPS�� �ѱ� ���մϴ�.");
    		gsDialog.setMessage("GPS�� �����ϴ� ������ �̵��մϴ�.");
    		gsDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				// GPS���� ȭ������ Ƣ���
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

