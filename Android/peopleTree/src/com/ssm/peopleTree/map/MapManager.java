package com.ssm.peopleTree.map;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.ssm.peopleTree.R;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.map.view.AreaModeMapView;
import com.ssm.peopleTree.map.view.GeofenceModeMapView;
import com.ssm.peopleTree.map.view.GroupLocationMapView;
import com.ssm.peopleTree.map.view.TrackingModeMapView;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.protocol.SetGeoPointRequest;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPOIItem.CalloutBalloonButtonType;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;
import net.daum.mf.map.api.MapView.MapViewEventListener;
import net.daum.mf.map.api.MapView.POIItemEventListener;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class MapManager {
	
	private volatile static MapManager instance = null;

	private MapManager() {
		memberMap = new HashMap<Integer, MemberData>();
		geoPoints = new ArrayList<GeoPoint>();
		tempGeoPoints = new ArrayList<GeoPoint>();
		childData = null;
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
	
	private static final int RADIUS_MIN = 1;
	private static final int RADIUS_MAX = 5000;
	
	private SharedPreferences prefs;

	private ManageMode manageMode;
	private ArrayList<GeoPoint> geoPoints;
	private ArrayList<GeoPoint> tempGeoPoints;
	private int manageRadius;
	private int tempRadius;
	
	private boolean loaded;
	
	private HashMap<Integer, MemberData> memberMap;
	private MemberData childData;
	
	private ManageMode mode;
	
	private boolean geoPointsSettingStarted;
	
	private Listener<JSONObject> onSetGeoPointResponse;
	
	public void initialize(Context context) {
				
		mode = ManageMode.NOTHING;
		
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
			manageMode = ManageMode.getMode(prefs.getInt(MANAGE_MODE_KEY, ManageMode.INVALID.getCode()));
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
	
	public void show(Activity activity) {

		ViewGroup mapViewContainer = (ViewGroup)activity.findViewById(R.id.map_view);
		
		switch(mode) {
		case TRAKING:
			mapViewContainer.addView(new TrackingModeMapView(activity));
			break;
			
		case AREA:
			geoPointsSettingStarted = false;
			mapViewContainer.addView(new AreaModeMapView(activity));
			break;
			
		case GEOFENCE:
			mapViewContainer.addView(new GeofenceModeMapView(activity));
			break;
			
		default:
			mapViewContainer.addView(new GroupLocationMapView(activity));
			break;
		}
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
	
	public void clearGeoPoints() {
		tempGeoPoints.clear();
	}
	
	public void removeMember(int groupMemeberId) {
		memberMap.remove(groupMemeberId);
	}
	
	public void clearMember() {
		memberMap.clear();
		childData = null;
	}
	
	public ManageMode getMode() {
		return mode;
	}
	
	public void setMode(ManageMode mode) {
		if (mode == null) {
			return;
		}
		this.mode = mode;
	}
	
	public void setChild(MemberData childData) {
		this.childData = childData;
	}
	
	public void setTempRadius(int manageRadius) {
		this.manageRadius = Math.min(RADIUS_MAX, Math.max(RADIUS_MIN, manageRadius));
	}
	
	public void startGeoPointSetting() {
		geoPointsSettingStarted = true;
	}
	
	public void finishGeoPointSetting() {
		geoPointsSettingStarted = false;
	}
	
	public void initSetting() {
		tempRadius = manageRadius;
		tempGeoPoints.clear();
		tempGeoPoints.addAll(geoPoints);
	}

	public void finishAllSetting() {
		
		int id = MyManager.getInstance().getGroupMemberId();
		SetGeoPointRequest req = null;
		switch(mode) {
		case TRAKING:
			req = new SetGeoPointRequest(id, manageRadius);
			break;
		case AREA:
			req = new SetGeoPointRequest(id, manageRadius, geoPoints);
			break;
		case GEOFENCE:
			req = new SetGeoPointRequest(id, geoPoints);
			break;
		}
		
		if (req == null) {
			return;
		}
		
		NetworkManager.getInstance().request(req, onSetGeoPointResponse, null);
		
		save();
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
}
