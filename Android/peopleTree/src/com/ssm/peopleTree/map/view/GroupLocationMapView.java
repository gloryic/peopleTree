package com.ssm.peopleTree.map.view;

import android.app.Activity;

import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.map.MapManager;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPOIItem.CalloutBalloonButtonType;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;
import net.daum.mf.map.api.MapView.MapViewEventListener;
import net.daum.mf.map.api.MapView.POIItemEventListener;

public class GroupLocationMapView extends MapView implements MapViewEventListener, POIItemEventListener {
	
	private static final String MAP_API_KEY = "f81dfaaef9aee66fd9cf7eae9cfa3dff";
	
	public GroupLocationMapView(Activity activity) {
		super(activity);
		
		setDaumMapApiKey(MAP_API_KEY);
		setMapViewEventListener(this);
		setPOIItemEventListener(this);
	}

	@Override
	public void onMapViewInitialized(MapView mapView) {

		MyManager myManager = MyManager.getInstance();
		MapPointBounds mapPointBounds = new MapPointBounds();
		
		int tag = -1;
	
		if (myManager.getLatitude() != null && myManager.getLongitude() != null) {
			MapPoint mp = MapPoint.mapPointWithGeoCoord(myManager.getLatitude(), myManager.getLongitude());
			MapPOIItem myMarker = new MapPOIItem();
			myMarker.setItemName("me");
			myMarker.setTag(++tag);
			myMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
			myMarker.setMapPoint(mp);
			mapView.addPOIItem(myMarker);
			mapPointBounds.add(mp);
		}
		
		/*
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
			mapPointBounds.add(mp);
		}
		*/
		
		MemberData childData = MapManager.getInstance().getChildData();
		if (childData != null && childData.latitude != null && childData.longitude != null) {
			MapPoint mp = MapPoint.mapPointWithGeoCoord(childData.latitude, childData.longitude);
			MapPOIItem childMarker = new MapPOIItem();
			childMarker.setItemName(childData.userName);
			childMarker.setTag(+tag);
			childMarker.setMarkerType(MapPOIItem.MarkerType.RedPin);
			childMarker.setMapPoint(mp);
			mapView.addPOIItem(childMarker);
			mapView.setMapCenterPoint(mp, true);
			mapPointBounds.add(mp);
		}
		
		int padding = 100; // px
		mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
	}
	
	@Override
	public void onMapViewCenterPointMoved(MapView arg0, MapPoint arg1) {	
	}

	@Override
	public void onMapViewDoubleTapped(MapView arg0, MapPoint arg1) {
	}

	@Override
	public void onMapViewDragEnded(MapView arg0, MapPoint arg1) {	
	}

	@Override
	public void onMapViewDragStarted(MapView arg0, MapPoint arg1) {	
	}


	@Override
	public void onMapViewLongPressed(MapView arg0, MapPoint arg1) {
	}

	@Override
	public void onMapViewMoveFinished(MapView arg0, MapPoint arg1) {
	}

	@Override
	public void onMapViewSingleTapped(MapView arg0, MapPoint arg1) {
	}

	@Override
	public void onMapViewZoomLevelChanged(MapView arg0, int arg1) {
	}

	@Override
	public void onCalloutBalloonOfPOIItemTouched(MapView arg0, MapPOIItem arg1) {
	}

	@Override
	public void onCalloutBalloonOfPOIItemTouched(MapView arg0, MapPOIItem arg1,
			CalloutBalloonButtonType arg2) {
	}

	@Override
	public void onDraggablePOIItemMoved(MapView arg0, MapPOIItem arg1,
			MapPoint arg2) {	
	}

	@Override
	public void onPOIItemSelected(MapView arg0, MapPOIItem arg1) {
	}

}
