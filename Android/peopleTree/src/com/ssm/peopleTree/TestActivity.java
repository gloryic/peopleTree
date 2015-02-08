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


public class TestActivity extends FragmentActivity implements OnClickListener {
	
	private int NUM_PAGES = 5;		// 최대 페이지의 수 
	
	/* Fragment numbering */
	public final static int FRAGMENT_PAGE1 = 0;
	public final static int FRAGMENT_PAGE2 = 1;
	public final static int FRAGMENT_PAGE3 = 2;
	public final static int FRAGMENT_PAGE4 = 3;
	public final static int FRAGMENT_PAGE5 = 4;

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
		
		page1Btn.setText("그룹");
		page2Btn.setText("요청");
		page3Btn.setText("메시지");
		page4Btn.setText("알림");
		page5Btn.setText("설정");
		
	
		upRqlvca = new RequestListViewCustomAdapter(this);
		/*
		for(int i=0;i<2;i++){
			upRqlvca.addItem("uprq"+i);
		}
		*/
		
		upRqlvca.addItem("주영광 010-0000-0000");
		upRqlvca.addItem("봉대장 010-0000-0000");
		
		
		downRqlvca = new RequestListViewCustomAdapter(this);
		

		downRqlvca.addItem("구민정 010-0000-0000");
		downRqlvca.addItem("김형조 010-0000-0000");
		downRqlvca.addItem("박지선 010-0000-0000");
		
		/*
		for(int i=0;i<4;i++){
			downRqlvca.addItem("down rq "+i);
		}
		*/
		

		bclvca = new BroadCastListViewCustomAdapter(this);
		

		bclvca.addItem("", null, "봉대장", "3시  반까지 갤럭시로!!  ", "");

		bclvca.addItem("", null, "봉대장", "다들 식사하셨나요??  ", "");

		bclvca.addItem("", null, "봉대장", "4시  반까지 갤럭시로!!  ", "");

		bclvca.addItem("", null, "봉대장", "다들 힘내요! ^^  ", "");

		bclvca.addItem("", null, "봉대장", "5시 까지 갤럭시로!!  ", "");
		/*
		for(int i=0;i<320;i++){
			bclvca.addItem("broadcast list ^^"+i, i, "aa"+i, "bb"+i, "cc"+i);
		}
		*/
		
		glvca = new GroupListviewCustomAdapter(this, new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				nextActivity(MapActivity.class);
			}
		});
		
		pmlvca = new PushMessageListViewCustomAdapter(this);
		pmlvca.addItem("", "03:08 이탈자 OOO 발생하였습니다.", null);
		pmlvca.addItem("", "22:01 봉대장과 연결되었습니다. \n 당신의 관리자는 봉대장입니다.", null);
		pmlvca.addItem("", "17:21 박진기와 연결되었습니다. \n 당신은 박진기의 관리자입니다.", null);
		
		
		

		/*
		for(int i=0;i<320;i++){
			pmlvca.addItem("testtxt"+i, "push message"+i, "");
		}
		*/
		groupListController = new GroupListController(this, groupManager);
		requestLayoutController =  new RequestLayoutController(this);
		broadCastLayoutController = new BroadCastLayoutController(this);
		pushmsgLayoutController = new PushmsgLayoutController(this);
		settingLayoutController = new SettingLayoutController(this);

		groupListController.setListAdapter(glvca);
		requestLayoutController.setListAdapter(upRqlvca, downRqlvca);
		broadCastLayoutController.setListAdapter(bclvca);
		pushmsgLayoutController.setListAdapter(pmlvca);
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
						groupManager.setChildren(myManager.getMyData());
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
		/*
		 * .setIcon(android.R.drawable.ic_menu_rotate);
        menu.add(0, 2, Menu.NONE, "").setIcon(android.R.drawable.ic_menu_add);
        menu.add(0, 3, Menu.NONE, "").setIcon(android.R.drawable.ic_menu_agenda);
        menu.add(0, 4, Menu.NONE, "");
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
            break;
 
        case 3:
            break;
 
        default:
            break;
        }
		return super.onOptionsItemSelected(item);
	}	
	
	private void nextActivity(Class<?> cls) {
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
}

