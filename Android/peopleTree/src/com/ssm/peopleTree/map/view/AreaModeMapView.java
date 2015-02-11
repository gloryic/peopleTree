package com.ssm.peopleTree.map.view;

import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.map.GeoPoint;
import com.ssm.peopleTree.map.ManageMode;
import com.ssm.peopleTree.map.MapManager;

import android.app.Activity;
import android.graphics.Color;
import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class AreaModeMapView extends GroupLocationMapView implements RadiusSettable {
	private MapView mapView;
	private MapManager mapManager;
	private MapPOIItem areaMarker;
	
	private MapCircle circle;
	
	public AreaModeMapView(Activity activity) {
		super(activity);
	}

	@Override
	public void onMapViewInitialized(MapView mapView) {
		super.onMapViewInitialized(mapView);
		
		this.mapView = mapView;
		
		mapManager = MapManager.getInstance();
		
		//setRadius(MyManager.getInstance().getManagedLocationRadius());
		
		double lat = MyManager.getInstance().getLatitude();
		double lon = MyManager.getInstance().getLongitude();
		MapPoint mp = MapPoint.mapPointWithGeoCoord(lat, lon);
		
		areaMarker = new MapPOIItem();
		areaMarker.setItemName("Center");
		areaMarker.setMarkerType(MapPOIItem.MarkerType.RedPin);
		areaMarker.setMapPoint(mp);
		
		circle = new MapCircle(mp, 0, Color.argb(128, 255, 0, 0), Color.argb(128, 0, 255, 0));
		mapView.addCircle(circle);
		
		setGeoPoint(mp.getMapPointGeoCoord().latitude, mp.getMapPointGeoCoord().longitude);
	}
	
	@Override
	public void onMapViewSingleTapped(MapView mapView, MapPoint mp) {
		if (!mapManager.isGeoPointSettingStarted()) {
			return;
		}
		
		mapView.removePOIItem(areaMarker);
		areaMarker.setMapPoint(mp);
		mapView.addPOIItem(areaMarker);
		
		mapView.removeCircle(circle);
		circle.setCenter(mp);
		mapView.addCircle(circle);
		
		setGeoPoint(mp.getMapPointGeoCoord().latitude, mp.getMapPointGeoCoord().longitude);
		mapManager.finishGeoPointSetting();
	}

	public void setGeoPoint(double lat, double lng) {
		mapManager.clearTempGeoPoints();
		mapManager.addTempGeoPoint(new GeoPoint(lat, lng));
	}

	@Override
	public void setRadius(int radius) {
		try {
			mapView.removeCircle(circle);
			circle.setCenter(areaMarker.getMapPoint());
			circle.setRadius(radius);
			circle.setTag(ManageMode.TRAKING.getCode());
			mapView.addCircle(circle);
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}
