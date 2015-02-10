package com.ssm.peopleTree;

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
import com.ssm.peopleTree.group.GroupManager;
import com.ssm.peopleTree.network.NetworkManager;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.Toast;


public class TestActivity extends FragmentActivity implements Progressable, OnClickListener {
	
	private int NUM_PAGES = 5;		// �ִ� �������� �� 
	
	/* Fragment numbering */
	public final static int FRAGMENT_PAGE1 = 0;
	public final static int FRAGMENT_PAGE2 = 1;
	public final static int FRAGMENT_PAGE3 = 2;
	public final static int FRAGMENT_PAGE4 = 3;
	public final static int FRAGMENT_PAGE5 = 4;

	ViewPager mViewPager;			// View pager�� ��Ī�� ���� 
	
	Button page1Btn, page2Btn, page3Btn,page4Btn,page5Btn;
	

	PushMessageListViewCustomAdapter pmlvca;
	BroadCastListViewCustomAdapter bclvca;
	RequestListViewCustomAdapter upRqlvca;
	RequestListViewCustomAdapter downRqlvca;
	GroupListviewCustomAdapter glvca;
	
	private NetworkManager networkManager;
	private MyManager myManager;
	private GroupManager groupManager;
	
	
	GroupListController groupListController;
	RequestLayoutController requestLayoutController;
	BroadCastLayoutController broadCastLayoutController;
	PushmsgLayoutController pushmsgLayoutController;
	SettingLayoutController settingLayoutController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tframe);
		
		networkManager = NetworkManager.getInstance();
		myManager = MyManager.getInstance();
		groupManager = GroupManager.getInstance();
	
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
		
		page1Btn.setText("�׷�");
		page2Btn.setText("��û");
		page3Btn.setText("�޽���");
		page4Btn.setText("�˸�");
		page5Btn.setText("����");
		
		
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO
		
		menu.add(0, 1, Menu.NONE, "Logout");
        menu.add(0, 2, Menu.NONE, "Refresh");
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
		
		switch (item.getItemId()) {
        case 1:
            Toast.makeText(TestActivity.this, "Logout", Toast.LENGTH_SHORT).show();
            LoginManager.getInstance().logout();
            
            Intent intent;
    		intent = new Intent(TestActivity.this, LoginActivity.class);
    		startActivity(intent);
    		finish();
            break;
 
        case 2:
        	groupManager.update(myManager.getGroupMemberId());
            break;
 
        case 3:
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
			case R.id.Page5Btn:
				mViewPager.setCurrentItem(FRAGMENT_PAGE5);
				break;

		}
	}

	@Override
	public void progress() {
		nextActivity(MapActivity.class);
	}	
}

