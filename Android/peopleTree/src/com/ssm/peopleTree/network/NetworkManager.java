package com.ssm.peopleTree.network;


import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.http.client.utils.URLEncodedUtils;
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
import com.ssm.peopleTree.network.protocol.Parameterizable;

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
	
	public void request(Parameterizable param, Listener<JSONObject> listener, ErrorListener errorListener) {
		if (requestQueue == null) {
            throw new IllegalStateException("Volley Request Queue is not initialized.");
        }
		if (C.networkLogging) {
			Log.e("test-network", "request: " + param.toURI());
		}
				
		String url = C.baseURL;
		JSONObject jsonRequest = null;
		int method = param.getMethod();
		
		if (C.networkAllGet) {
			method = Method.GET;
		}
		
		if (method == Method.GET) {
			url += param.toURI();
		}
		else {
			jsonRequest = param.toJSonObject();
		}
		
		Log.e("test", url);
		
		JsonObjectRequest req = new JsonObjectRequest(method, url, jsonRequest, listener, errorListener);
		requestQueue.add(req);
	}
	
	public String getString(int resId) {
		try {
			return context.getString(resId);
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String getEncodedStr(String str) {
		try {
			str = URLEncoder.encode(str, "UTF-8");
			String result = new String(str.getBytes("UTF8"));
			return result; 
		} catch (Exception e) {
			e.printStackTrace();
			return str;
		}
	}
	
	public static String getDecodedStr(String str) {
		try {
			return URLDecoder.decode(str, "utf-8");
		} catch (Exception ee) {
			return str;
		}
	}
}
