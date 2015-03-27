package com.ssm.peopleTree.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Stack;

import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.Status;
import com.ssm.peopleTree.network.protocol.GetGeoPointRequest;
import com.ssm.peopleTree.network.protocol.GetGeoPointResponse;
import com.ssm.peopleTree.network.protocol.SetGeoPointRequest;
import com.ssm.peopleTree.network.protocol.SetGeoPointResponse;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPOIItem.CalloutBalloonButtonType;
import net.daum.mf.map.api.MapPOIItem.MarkerType;
import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;
import net.daum.mf.map.api.MapView.MapViewEventListener;
import net.daum.mf.map.api.MapView.POIItemEventListener;
import android.content.Context;
import android.graphics.Color;

public class MapManager implements MapViewEventListener, POIItemEventListener{
	
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
	
	private static final String MAP_API_KEY = "f81dfaaef9aee66fd9cf7eae9cfa3dff";
		
	private static final int RADIUS_MIN = 5;
	private static final int RADIUS_MAX = 5000;

	private MemberData childData;
	
	private ManageMode manageMode;
	private ManageMode newManageMode;
	private ArrayList<GeoPoint> geoPoints;
	private ArrayList<GeoPoint> newGeoPoints;
	private int radius;	
	private int newRadius;

	private LinkedList<SettingMode> settingQueue;
	private SettingMode settingMode;
	private boolean isSetting;
	
	private OnStartSettingListener startSettingListener;
	private OnFinishSettingListener finishSettingLisetner;
		
	public void initialize(Context context) {
		geoPoints = new ArrayList<GeoPoint>();
		newGeoPoints = new ArrayList<GeoPoint>();
		settingQueue = new LinkedList<SettingMode>();
		
		settingMode = SettingMode.NO_SETTING;
		isSetting = false;
	}

	
	public static String getMapApiKey() {
		return MAP_API_KEY;
	}
	
	public void loadChild(MapView mapView) {
		if (childData == null) {
			return;
		}
		else if (childData.latitude == null || childData.longitude == null) {
			return;
		}
		
		MapPOIItem item = new MapPOIItem();
		item.setItemName(childData.userName);
		item.setMarkerType(MarkerType.YellowPin);
		item.setMapPoint(MapPoint.mapPointWithGeoCoord(childData.latitude, childData.longitude));
		mapView.addPOIItem(item);
		
		MapCircle[] circles = mapView.getCircles();
		MapPOIItem[] items = mapView.getPOIItems();
		
		MapPointBounds mapPointBounds;
		if (circles.length > 0) {
			MapPointBounds[] mapPointBoundsArray = new MapPointBounds[circles.length];
			for (int i = 0; i < circles.length; i++) {
				mapPointBoundsArray[i] = circles[i].getBound();
			}
			mapPointBounds = new MapPointBounds(mapPointBoundsArray);
		}
		else {
			mapPointBounds = new MapPointBounds();
		}
		
		for (MapPOIItem i : items) {
			mapPointBounds.add(i.getMapPoint());
		}
		
		int padding = 100; // px
		mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
	}
	
	public void loadSetting(MapView arg, OnLoadFinishListener finishListener) {
		final MapView mapView = arg;
		final OnLoadFinishListener listener = finishListener;
		final MyManager myManager = MyManager.getInstance();
		int id = myManager.getGroupMemberId();
		GetGeoPointRequest req = new GetGeoPointRequest(id);
		NetworkManager.getInstance().request(req, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				GetGeoPointResponse res = new GetGeoPointResponse(arg0);
				if (res.getStatus() == Status.SUCCESS) {
					if (listener != null) {
						listener.onLoadFinish(ManageMode.getMode(res.manageMode));
					}
					manageMode = ManageMode.getMode(res.manageMode);
					geoPoints.clear();
					geoPoints.addAll(res.points);
					radius = res.radius;
					
					if (manageMode != ManageMode.TRACKING || myManager.isAvailableMyLocation()) {
						showCurrentSetting(mapView);
					}
				}
				else {
					if (listener != null) {
						listener.onLoadFinish(ManageMode.NOTHING);
					}
					manageMode = ManageMode.NOTHING;
					geoPoints.clear();
					radius = 0;
					
					mapView.removeAllCircles();
					mapView.removeAllPOIItems();
					mapView.removeAllPolylines();
				}
			}
			
		}, null);
		
		settingMode = SettingMode.NO_SETTING;
		isSetting = false;
	}
	
	public void startSetting(ManageMode manageMode, MapView mapView) {
		newManageMode = manageMode;
		newGeoPoints.clear();
		newRadius = 0;
		
		isSetting = true;
		settingQueue.clear();
		
		switch(manageMode) {
		case NOTHING:
			settingQueue.add(SettingMode.NO_SETTING);
			break;
		case TRACKING:
			settingQueue.add(SettingMode.RADIUS_SETTING);
			settingQueue.add(SettingMode.FINISH_SETTING);
			break;
		case AREA:
			settingQueue.add(SettingMode.RADIUS_SETTING);
			settingQueue.add(SettingMode.POINT_SETTING);
			settingQueue.add(SettingMode.FINISH_SETTING);
			break;
		case GEOFENCE:
			settingQueue.add(SettingMode.POINT_SETTING);
			settingQueue.add(SettingMode.FINISH_SETTING);
			break;
		default:
			break;
		}
		
		if (!settingQueue.isEmpty()) {
			settingMode = settingQueue.pop();
		}

		if (startSettingListener != null) {
			startSettingListener.onStartSetting(manageMode);
		}
		
		mapView.removeAllCircles();
		mapView.removeAllPOIItems();
		mapView.removeAllPolylines();
	}
	
	public void finishSetting() {
		
		manageMode = newManageMode;
		radius = newRadius;
		geoPoints.clear();
		geoPoints.addAll(newGeoPoints);
		
		int id = MyManager.getInstance().getGroupMemberId();
		SetGeoPointRequest req = new SetGeoPointRequest(id, radius, geoPoints) ;		
		NetworkManager.getInstance().request(req, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				SetGeoPointResponse res = new SetGeoPointResponse(arg0);
				if (res.getStatus() == Status.SUCCESS) {
				}
				else {
				}
			}
		}, null);
		
		settingMode = SettingMode.NO_SETTING;
		settingQueue.clear();
		isSetting = false;
		
		newGeoPoints.clear();
		newManageMode = ManageMode.NOTHING;
		newRadius = 0;
		
		if (finishSettingLisetner != null) {
			finishSettingLisetner.onFinishSetting();
		}
	}
	
	public void cancelSetting(MapView mapView) {
		settingMode = SettingMode.NO_SETTING;
		settingQueue.clear();
		
		newGeoPoints.clear();
		newManageMode = ManageMode.NOTHING;
		newRadius = 0;
		
		showCurrentSetting(mapView);
	}
	
	public void showCurrentSetting(MapView mapView) {
		
		mapView.removeAllCircles();
		mapView.removeAllPOIItems();
		mapView.removeAllPolylines();
		
		switch(manageMode) {
		case TRACKING:
			Double myLat = MyManager.getInstance().getLatitude();
			Double myLng = MyManager.getInstance().getLongitude();
			if (myLat == null || myLng == null) {
				// fail
			}
			else {
				MapPoint mp = MapPoint.mapPointWithGeoCoord(myLat, myLng);
				MapPOIItem item = new MapPOIItem();
				item.setItemName("Center");
				item.setMarkerType(MarkerType.BluePin);
				item.setMapPoint(mp);
				mapView.addPOIItem(item);
				
				MapCircle circle = new MapCircle(mp, 0, Color.argb(128, 255, 0, 0), Color.argb(128, 0, 255, 0));
				circle.setCenter(mp);
				mapView.addCircle(circle);
				
				// 지도뷰의 중심좌표와 줌레벨을 Circle이 모두 나오도록 조정.
				MapPointBounds[] mapPointBoundsArray = { circle.getBound() };
				MapPointBounds mapPointBounds = new MapPointBounds(mapPointBoundsArray);
				int padding = 100; // px
				mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
			}
			break;
			
		case AREA:
			{
				GeoPoint gp = geoPoints.get(0);
				MapPoint mp = MapPoint.mapPointWithGeoCoord(gp.getLat(), gp.getLng());
				MapPOIItem item = new MapPOIItem();
				item.setItemName("Center");
				item.setMarkerType(MarkerType.BluePin);
				item.setMapPoint(mp);
				mapView.addPOIItem(item);
				
				MapCircle circle = new MapCircle(mp, radius, Color.argb(128, 255, 0, 0), Color.argb(128, 0, 255, 0));
				circle.setCenter(mp);
				mapView.addCircle(circle);
				
				// 지도뷰의 중심좌표와 줌레벨을 Circle이 모두 나오도록 조정.
				MapPointBounds[] mapPointBoundsArray = { circle.getBound() };
				MapPointBounds mapPointBounds = new MapPointBounds(mapPointBoundsArray);
				int padding = 100; // px
				mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
			}
			break;
			
		case GEOFENCE:
			MapPolyline polyline = new MapPolyline();
			polyline.setLineColor(Color.argb(128, 255, 51, 0));
			
			for (GeoPoint geoPoint : geoPoints) {
				MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(geoPoint.getLat(), geoPoint.getLng());
				MapPOIItem item = new MapPOIItem();
				item.setMapPoint(mapPoint);
				item.setItemName("Point");
				item.setMarkerType(MarkerType.BluePin);
				mapView.addPOIItem(item);
				polyline.addPoint(mapPoint);
			}
			if (geoPoints.size() > 2) {
				GeoPoint geoPoint = geoPoints.get(0);
				MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(geoPoint.getLat(), geoPoint.getLng());
				polyline.addPoint(mapPoint);
				mapView.addPolyline(polyline);
				
				// 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정.
				MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
				int padding = 120; // px
				mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
			}		
			break;
			
		default:
			break;
		}
	}
	
	public boolean isCCW(GeoPoint basePoint, GeoPoint a, GeoPoint b) {
		double ax = a.getLat() - basePoint.getLat();
		double ay = a.getLng() - basePoint.getLng();
		double bx = b.getLat() - basePoint.getLat();
		double by = b.getLng() - basePoint.getLng();
		double cross = ax * by - bx * ay;
		
		return cross >= 0;
	}
	
	public void changeToConvexHull(ArrayList<GeoPoint> points) {
		if (points.size() < 3) {
			return;
		}
		
		GeoPoint basePoint = points.get(0);
		for (int i = 1; i < points.size(); i++) {
			GeoPoint gp = points.get(i);
			if (gp.getLng() < basePoint.getLng()) {
				basePoint = gp;
			}
			else if (gp.getLng() == basePoint.getLng() && gp.getLat() > basePoint.getLat()) {
				basePoint = gp;
			}
		}
		points.remove(basePoint);
		
		final GeoPoint cmpBase = basePoint;//new GeoPoint(basePoint.getLat(), basePoint.getLng());
		Comparator<GeoPoint> angleComparator = new Comparator<GeoPoint>() {
			
			@Override
			public int compare(GeoPoint lhs, GeoPoint rhs) {
				if (isCCW(cmpBase, lhs, rhs)) {
					return -1;
				}
				else {
					return 1;
				}
			}
		};
		Collections.sort(points, angleComparator);
		
		Stack<GeoPoint> stack = new Stack<GeoPoint>();
		stack.push(basePoint);	
		stack.push(points.remove(0));
		stack.push(points.remove(0));
		
		while (!points.isEmpty()) {
			GeoPoint next = points.remove(0);
			while (isCCW(stack.peek(), stack.get(stack.size() - 2), next)) {
				stack.pop();
			}
			stack.push(next);
		}
		
		points.clear();
		while (!stack.isEmpty()) {
			points.add(stack.pop());
		}
	}
	
	public void setStartSettingListener(OnStartSettingListener startSettingListener) {
		this.startSettingListener = startSettingListener;
	}
	
	public void setFinishSettingLisetner(OnFinishSettingListener finishSettingLisetner) {
		this.finishSettingLisetner = finishSettingLisetner;
	}
	
	public void setRadius(int radius) {
		if (!isSetting || settingMode != SettingMode.RADIUS_SETTING) {
			return;
		}
		newRadius = radius;
		
		settingMode = settingQueue.pop();
	}
	
	public void setChild(MemberData childData) {
		
	}
	
	public void onStartSetting(MapView mapView) {
		mapView.removeAllPOIItems();
		mapView.removeAllCircles();
		mapView.removeAllPolylines();
	}
	
	public boolean isSettingMode() {
		return isSetting;
	}
	
	
	/******************************************************************************************/
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
	public void onMapViewSingleTapped(MapView mapView, MapPoint mp) {
		if (!isSetting || settingMode != SettingMode.POINT_SETTING) {
			return;
		}

		mapView.removeAllCircles();
		mapView.removeAllPOIItems();
		mapView.removeAllPolylines();
		
		switch(newManageMode) {
		case AREA: {
				MapPOIItem center = new MapPOIItem();
				center.setMapPoint(mp);
				center.setItemName("Center");
				center.setMarkerType(MarkerType.BluePin);
				mapView.addPOIItem(center);
				
				MapCircle circle = new MapCircle(mp, newRadius, Color.argb(128, 255, 0, 0), Color.argb(128, 0, 255, 0));
				circle.setCenter(mp);
				mapView.addCircle(circle);
				
				newGeoPoints.clear();
				newGeoPoints.add(new GeoPoint(mp.getMapPointGeoCoord().latitude, mp.getMapPointGeoCoord().longitude));
				
				// 지도뷰의 중심좌표와 줌레벨을 Circle이 모두 나오도록 조정.
				MapPointBounds[] mapPointBoundsArray = { circle.getBound() };
				MapPointBounds mapPointBounds = new MapPointBounds(mapPointBoundsArray);
				int padding = 100; // px
				mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
			}
			break;
			
		case GEOFENCE: {
				MapPolyline polyline = new MapPolyline();
				polyline.setLineColor(Color.argb(128, 255, 51, 0));
				newGeoPoints.add(new GeoPoint(mp.getMapPointGeoCoord().latitude, mp.getMapPointGeoCoord().longitude));
				//
				changeToConvexHull(newGeoPoints);
				//
				for (GeoPoint geoPoint : newGeoPoints) {
					MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(geoPoint.getLat(), geoPoint.getLng());
					MapPOIItem item = new MapPOIItem();
					item.setMapPoint(mapPoint);
					item.setItemName("Point");
					item.setMarkerType(MarkerType.BluePin);
					mapView.addPOIItem(item);
					polyline.addPoint(mapPoint);
				}
				if (newGeoPoints.size() > 2) {
					GeoPoint geoPoint = newGeoPoints.get(0);
					MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(geoPoint.getLat(), geoPoint.getLng());
					polyline.addPoint(mapPoint);
					mapView.addPolyline(polyline);
					
					// 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정.
					MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
					int padding = 120; // px
					mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
				}
			}
			break;
		
		default:
			break;
		}
		
	}

	@Override
	public void onMapViewZoomLevelChanged(MapView arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
}
