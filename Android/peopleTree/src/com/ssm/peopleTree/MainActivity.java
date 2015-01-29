package com.ssm.peopleTree;

import java.util.ArrayList;

import com.ssm.peopleTree.network.NetworkManager;

import android.app.Activity;
import android.content.Context;
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

public class MainActivity extends Activity {
	
	private TextView tvResult;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		NetworkManager.getInstance().initialize(this);
		
		
		
		/**/
		C.wifiTextView1 = (TextView) findViewById(R.id.WifiTextView1);

		
		/**/
		
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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
