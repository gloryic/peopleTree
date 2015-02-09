package com.ssm.peopleTree.map;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

import com.google.android.gms.internal.ma;
import com.google.android.gms.internal.my;
import com.ssm.peopleTree.R;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.data.MemberData;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPOIItem.CalloutBalloonButtonType;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;
import net.daum.mf.map.api.MapView.MapViewEventListener;
import net.daum.mf.map.api.MapView.POIItemEventListener;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;

public class MapManager implements POIItemEventListener, MapViewEventListener {
	
	private volatile static MapManager instance = null;

	private MapManager() {
		memberMap = new HashMap<Integer, MemberData>();
		geoPoints = new ArrayList<GeoPoint>();
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
	
	private static final String MAP_API_KEY = "f81dfaaef9aee66fd9cf7eae9cfa3dff";
	
	private static final String MANAGE_DATA_NAME = "manageData";
	private static final String MANAGE_MODE_KEY = "manageMode";
	private static final String MANAGE_RADIUS_KEY = "manageRadius";
	private static final String GEO_POINTS_KEY = "geoPoints";
	
	
	private SharedPreferences prefs;

	private ManageMode manageMode;
	private ArrayList<GeoPoint> geoPoints;
	private int manageRadius;
	private boolean loaded;
	
	private HashMap<Integer, MemberData> memberMap;
	private MemberData childData;
	
	//private MapView mapView;
	private MapView rangeMapView;
	
	private MapMode mode;
	
	public void initialize(Context context) {
		
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
			/*try {
				
				//TODO
				JSONArray jsonArr = new JSONArray();
				jsonArr.
				svedUserNumber = prefs.getInt(USER_NUMBER_KEY, USER_NUMBER_DEFAULT);	
			} catch (Exception e) {
				geoPoints.clear();
			}
			*/
		}
	}
	
	private void save() {
		try {
			if (!loaded)
				throw new Exception("LoginManager must be used after initializing");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SharedPreferences.Editor ed = prefs.edit();
		ed.putInt(MANAGE_MODE_KEY, manageMode.getCode());
		ed.putInt(MANAGE_RADIUS_KEY, manageRadius);		
		ed.commit();
	}
	
	public void clear() {
		try {
			if (!loaded)
				throw new Exception("LoginManager must be used after initializing");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SharedPreferences.Editor ed = prefs.edit();		
		ed.remove(MANAGE_MODE_KEY);	
		ed.remove(MANAGE_RADIUS_KEY);
		ed.remove(GEO_POINTS_KEY);
		ed.commit();
	}
	
	public void showGroupLocation(Activity activity) {
		setMode(MapMode.GROUP_LOCATION);		
	
		MyManager myManager = MyManager.getInstance();
		
		MapView mapView = new MapView(activity);
		mapView.setDaumMapApiKey(MAP_API_KEY);
		mapView.setMapViewEventListener(this);
		mapView.setPOIItemEventListener(this);
		
		int tag = -1;
	
		if (myManager.getLatitude() != null && myManager.getLongitude() != null) {
			MapPoint mp = MapPoint.mapPointWithGeoCoord(myManager.getLatitude(), myManager.getLongitude());
			MapPOIItem myMarker = new MapPOIItem();
			myMarker.setItemName("me");
			myMarker.setTag(++tag);
			myMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
			myMarker.setMapPoint(mp);
			mapView.addPOIItem(myMarker);
		}
		
		for (MemberData mData : memberMap.values()) {
			if (mData.latitude == null || mData.longitude == null) {
				continue;
			}
			MapPoint mp = MapPoint.mapPointWithGeoCoord(mData.latitude, mData.longitude);
			MapPOIItem  marker = new MapPOIItem();
			marker.setItemName(mData.userName);
			marker.setTag(++tag);
			marker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
			marker.setMapPoint(mp);
			mapView.addPOIItem(marker);
		}
		
		if (childData != null && childData.latitude != null && childData.longitude != null) {
			MapPoint mp = MapPoint.mapPointWithGeoCoord(childData.latitude, childData.longitude);
			MapPOIItem childMarker = new MapPOIItem();
			childMarker.setItemName(childData.userName);
			childMarker.setTag(+tag);
			childMarker.setMarkerType(MapPOIItem.MarkerType.RedPin);
			mapView.addPOIItem(childMarker);
			mapView.setMapCenterPoint(mp, true);
		}

		ViewGroup mapViewContainer = (ViewGroup)activity.findViewById(R.id.map_view);
		mapViewContainer.addView(mapView);

	}
	
	public void showRangeSetting(Activity activity) {
		setMode(MapMode.SET_GEOPOINT);
		
		MyManager myManager = MyManager.getInstance();
		
		rangeMapView = new MapView(activity);
		rangeMapView.setDaumMapApiKey(MAP_API_KEY);
		rangeMapView.setMapViewEventListener(this);
		rangeMapView.setPOIItemEventListener(this);
		
		int tag = -1;
		MapPoint mp = MapPoint.mapPointWithGeoCoord(myManager.getLatitude(), myManager.getLongitude());
	}
	
	@Override
	public void onMapViewCenterPointMoved(MapView arg0, MapPoint arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapViewDoubleTapped(MapView arg0, MapPoint arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapViewDragEnded(MapView arg0, MapPoint arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapViewDragStarted(MapView arg0, MapPoint arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapViewInitialized(MapView arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapViewLongPressed(MapView arg0, MapPoint arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapViewMoveFinished(MapView arg0, MapPoint arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapViewSingleTapped(MapView arg0, MapPoint arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapViewZoomLevelChanged(MapView arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCalloutBalloonOfPOIItemTouched(MapView arg0, MapPOIItem arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCalloutBalloonOfPOIItemTouched(MapView arg0, MapPOIItem arg1,
			CalloutBalloonButtonType arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDraggablePOIItemMoved(MapView arg0, MapPOIItem arg1,
			MapPoint arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPOIItemSelected(MapView arg0, MapPOIItem arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void addMember(MemberData memberData) {
		if (memberData == null) {
			return;
		}
		
		memberMap.put(memberData.groupMemberId, memberData);
	}
	
	public void removeMember(int groupMemeberId) {
		memberMap.remove(groupMemeberId);
	}
	
	public void clearMember() {
		memberMap.clear();
		childData = null;
	}
	
	public MapMode getMode() {
		return mode;
	}
	
	public void setMode(MapMode mode) {
		if (mode == null) {
			return;
		}
		this.mode = mode;
	}
	
	public void setChild(MemberData childData) {
		this.childData = childData;
	}
	
	public boolean hasValidLocation() {
		return childData.latitude != null && childData.longitude != null;
	}
}
