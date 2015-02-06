package com.ssm.peopleTree.map;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPOIItem.CalloutBalloonButtonType;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;
import net.daum.mf.map.api.MapView.MapViewEventListener;
import net.daum.mf.map.api.MapView.POIItemEventListener;
import android.app.Activity;
import android.view.ViewGroup;

public class MapManager  implements POIItemEventListener, MapViewEventListener{

	private static final String MAP_API_KEY = "f81dfaaef9aee66fd9cf7eae9cfa3dff";
	private MapView mapView;
	private MapPOIItem myMarker;
	
	public MapManager(Activity activity, int containerId) {

		myMarker = new MapPOIItem();
		myMarker.setItemName("나");
		myMarker.setTag(0);
		myMarker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
		myMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
		
		mapView = new MapView(activity);
		mapView.setDaumMapApiKey(MAP_API_KEY);
		mapView.setMapViewEventListener(this);
		mapView.setPOIItemEventListener(this);
		mapView.addPOIItem(myMarker);

		ViewGroup mapViewContainer = (ViewGroup)activity.findViewById(containerId);
		mapViewContainer.addView(mapView);

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
	
	public void setMe(double lat, double lng) {
		MapPoint mp = MapPoint.mapPointWithGeoCoord(lat, lng);
		myMarker.setMapPoint(mp);
		mapView.setMapCenterPoint(mp, true);
	}
}
