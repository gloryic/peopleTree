package com.ssm.peopleTree.network;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ssm.peopleTree.C;
import com.ssm.peopleTree.network.protocol.Param;

public class NetworkManager {
	
	private volatile static NetworkManager instance = null;
	
	private NetworkManager() {
	}
	
	public static NetworkManager getInstance() {
		if (null == instance) {
			synchronized (NetworkManager.class) {
				instance = new NetworkManager();
			}
		}
		
		return instance;
	}
	
	private RequestQueue requestQueue = null;
	private Context context = null;
	
    public void initialize(Context _context) {
    	if (context == null) {
    		context = _context;
    		requestQueue = Volley.newRequestQueue(context);
    	}
    }

	
	public void request(int method, String url, JSONObject jsonRequest, Listener<JSONObject> listener, ErrorListener errorListener) {
		if (requestQueue == null) {
            throw new IllegalStateException("Volley Request Queue is not initialized.");
        }
		
		if (C.networkAllGet) {
			method = Method.GET;
		}
		
		JsonObjectRequest req = new JsonObjectRequest(method, url, jsonRequest, listener, errorListener); 
		requestQueue.add(req);
	}
	
	public void request(Param param, Listener<JSONObject> listener, ErrorListener errorListener) {
		if (requestQueue == null) {
            throw new IllegalStateException("Volley Request Queue is not initialized.");
        }
				
		String url = C.baseURL;
		JSONObject jsonRequest = null;
		int method = param.getMethod();
		
		if (C.networkAllGet) {
			method = Method.GET;
		}
		
		if (method == Method.GET) {
			url += param.toString();
		}
		else {
			jsonRequest = param.toJSonObject();
		}
		
		Log.e("test", url);
		
		JsonObjectRequest req = new JsonObjectRequest(method, url, jsonRequest, listener, errorListener);
		requestQueue.add(req);
	}
}
