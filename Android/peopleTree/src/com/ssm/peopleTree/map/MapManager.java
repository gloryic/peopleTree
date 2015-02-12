package com.ssm.peopleTree.map;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.protocol.SetGeoPointRequest;

import android.content.Context;
import android.content.SharedPreferences;

public class MapManager {
	
	private volatile static MapManager instance = null;

	private MapManager() {
	}
	
	public static MapManager getInstance() {
		if (null == instance) {
			synchronized (MapManager.class) {
				instance = new MapManager();
			}
		}
		
		return instance;
	}
	
	private static final String MANAGE_DATA_NAME = "manageData";
	
	private static final String MANAGE_MODE_KEY = "manageMode";
	private static final String MANAGE_RADIUS_KEY = "manageRadius";
	private static final String GEO_POINTS_KEY = "geoPoints";
	
	private static final int RADIUS_MIN = 5;
	private static final int RADIUS_MAX = 5000;
	
	private SharedPreferences prefs;

	private ManageMode manageMode;
	private ManageMode tempManageMode;
	private ArrayList<GeoPoint> geoPoints;
	private ArrayList<GeoPoint> tempGeoPoints;
	private int manageRadius;
	private int tempRadius;
	
	private boolean loaded;
	
	private HashMap<Integer, MemberData> memberMap;
	private MemberData childData;
	
	private boolean geoPointsSettingStarted;
	
	private Listener<JSONObject> onSetGeoPointResponse;
	
	public void initialize(Context context) {
		
		memberMap = new HashMap<Integer, MemberData>();
		geoPoints = new ArrayList<GeoPoint>();
		tempGeoPoints = new ArrayList<GeoPoint>();
		childData = null;
		
		onSetGeoPointResponse = new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
			}
		};
		
		if (!loaded) {
			load(context);
		}
	}

	private void load(Context context) {
		prefs = context.getSharedPreferences(MANAGE_DATA_NAME, Context.MODE_PRIVATE);
		loaded = (prefs != null);
		
		if (loaded) {
			manageMode = ManageMode.getMode(prefs.getInt(MANAGE_MODE_KEY, ManageMode.NOTHING.getCode()));
			manageRadius = prefs.getInt(MANAGE_RADIUS_KEY, 0);
			try {
				geoPoints.clear();
				String str = prefs.getString(GEO_POINTS_KEY, "");
				JSONArray jsonArr = new JSONArray(str);
				for (int i = 0; !jsonArr.isNull(i); i++) {
					geoPoints.add(new GeoPoint(jsonArr.getJSONObject(i)));
				}
			} catch (Exception e) {
				geoPoints.clear();
			}
			
		}
	}
	
	private void save() {
		try {
			if (!loaded)
				throw new Exception("MapManager must be used after initializing");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SharedPreferences.Editor ed = prefs.edit();
		ed.putInt(MANAGE_MODE_KEY, manageMode.getCode());
		ed.putInt(MANAGE_RADIUS_KEY, manageRadius);		
		try {
			JSONArray jsonArr = new JSONArray();
			for (GeoPoint geoPoint : geoPoints) {
				jsonArr.put(geoPoint.toJSONObject());
			}
			ed.putString(GEO_POINTS_KEY, jsonArr.toString());
			
		} catch (Exception e) {
			ed.putString(GEO_POINTS_KEY, "");
		}
		ed.commit();
		
	}
	
	public void clear() {
		try {
			if (!loaded)
				throw new Exception("MapManager must be used after initializing");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SharedPreferences.Editor ed = prefs.edit();		
		ed.remove(MANAGE_MODE_KEY);	
		ed.remove(MANAGE_RADIUS_KEY);
		ed.remove(GEO_POINTS_KEY);
		ed.commit();
	}
	
	public void addMember(MemberData memberData) {
		if (memberData == null) {
			return;
		}
		
		memberMap.put(memberData.groupMemberId, memberData);
	}
	
	public void addTempGeoPoint(GeoPoint geoPoint) {
		tempGeoPoints.add(geoPoint);
	}
	
	public void clearTempGeoPoints() {
		tempGeoPoints.clear();
	}
	
	public void removeMember(int groupMemeberId) {
		memberMap.remove(groupMemeberId);
	}
	
	public void clearMember() {
		memberMap.clear();
		childData = null;
	}
	
	public void setChild(MemberData childData) {
		this.childData = childData;
	}
	
	public void setTempRadius(int manageRadius) {
		this.tempRadius = Math.min(RADIUS_MAX, Math.max(RADIUS_MIN, manageRadius));
	}
	
	public void setTempManageMode(ManageMode mode) {
		this.tempManageMode = mode;
	}
	
	public void startGeoPointSetting() {
		geoPointsSettingStarted = true;
	}
	
	public void finishGeoPointSetting() {
		geoPointsSettingStarted = false;
	}
	
	public void initSetting() {
		tempRadius = 0;
		//tempRadius = manageRadius;
		tempGeoPoints.clear();
		//tempGeoPoints.addAll(geoPoints);
	}

	public boolean finishAllSetting() {
		
		if (!isValidSetting()) {
			return false;
		}
			
		int id = MyManager.getInstance().getGroupMemberId();
		
		SetGeoPointRequest req = null;
		switch(tempManageMode) {
		case NOTHING:
			req = new SetGeoPointRequest(id, tempRadius, tempGeoPoints);
			break;
		case TRAKING:
			req = new SetGeoPointRequest(id, tempRadius);
			break;
		case AREA:
			req = new SetGeoPointRequest(id, tempRadius, tempGeoPoints);
			break;
		case GEOFENCE:
			req = new SetGeoPointRequest(id, tempGeoPoints);
			break;
		}
		
		if (req == null) {
			return false;
		}
				
		NetworkManager.getInstance().request(req, onSetGeoPointResponse, null);
		
		manageMode = tempManageMode;
		manageRadius = tempRadius;
		geoPoints.clear();
		geoPoints.addAll(tempGeoPoints);
		
		//TODO
		initSetting();
		
		save();
		return true;
	}
	
	public boolean isValidSetting() {
		// TODO
		
		
		return true;
	}
	
	public boolean isGeoPointSettingStarted() {
		return geoPointsSettingStarted;
	}
	
	public boolean hasValidLocation() {
		if (childData == null) {
			return false;
		}
		return childData.latitude != null && childData.longitude != null;
	}

	public MemberData getChildData() {
		return childData;
	}
	
	public int getTempRadius() {
		return tempRadius;
	}
	
	public ManageMode getTempManageMode() {
		return tempManageMode;
	}
}
