package com.ssm.peopleTree;

import java.util.ArrayList;

import com.parse.ParseAnalytics;
import com.ssm.peopleTree.application.LoginManager;
import com.ssm.peopleTree.network.NetworkManager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private TextView tvResult;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//TODO		
		ParseAnalytics.trackAppOpened(getIntent());
		
		
		
		
   		//final ProgressDialog pDialog = new ProgressDialog(this);
		//pDialog.setMessage("Loading...");
		//pDialog.show();
		/*
		ArrayList<String> strList = new ArrayList<String>();
		strList.add("그룹 생성");
		strList.add("그룹 나가기");
		strList.add("관계 요청");
		strList.add("관계 변경");
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strList);
	
		ListView list = (ListView) findViewById(R.id.listview_test);
		list.setAdapter(adapter);
		list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		list.setDivider(new ColorDrawable(Color.WHITE));
		list.setDividerHeight(2);
		
		tvResult = (TextView) findViewById(R.id.textview_result);
		
		/*
		Button btnSimpleRequest = (Button) findViewById(R.id.btn_simple_request);
		responseText = (TextView) findViewById(R.id.test);
		
        btnSimpleRequest.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	
        		String url = C.baseURL+"/ptree/test";
        		
        		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET, url,
        				null, new Response.Listener<JSONObject>() {

        					@Override
        					public void onResponse(JSONObject response) {
        						Log.d("App", response.toString());
        						responseText.setText("Response:" + " "+ response.toString());
        						Log.i("App",response.toString());
        						//pDialog.hide();
        					}
        				}, new Response.ErrorListener() {

        					@Override
        					public void onErrorResponse(VolleyError error) {
        						VolleyLog.d("App_Error", "Error: " + error.getMessage());
        						Log.i("App_Error",error.getMessage());
        						// hide the progress dialog
        						//pDialog.hide();
        					}
        		});
        		VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
            }
        });
        */
		
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
            Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
            LoginManager.getInstance().logout();
            
            Intent intent;
    		intent = new Intent(MainActivity.this, LoginActivity.class);
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
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
