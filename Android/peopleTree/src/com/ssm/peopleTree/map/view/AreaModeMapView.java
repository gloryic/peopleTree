package com.ssm.peopleTree.map.view;

import com.ssm.peopleTree.R;
import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.map.GeoPoint;
import com.ssm.peopleTree.map.ManageMode;
import com.ssm.peopleTree.map.MapManager;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class AreaModeMapView extends GroupLocationMapView {
	private MapView mapView;
	private MapManager mapManager;
	
	public AreaModeMapView(Activity activity) {
		super(activity);
	}

	@Override
	public void onMapViewInitialized(MapView mapView) {
		super.onMapViewInitialized(mapView);
		
		this.mapView = mapView;
		
		mapManager = MapManager.getInstance();
		
		setCircleRadius(MyManager.getInstance().getManagedLocationRadius());
	}
	
	@Override
	public void onMapViewSingleTapped(MapView mapView, MapPoint mp) {
		if (!mapManager.isGeoPointSettingStarted()) {
			return;
		}
		
		setGeoPoint(mp.getMapPointGeoCoord().latitude, mp.getMapPointGeoCoord().longitude);
		mapManager.finishGeoPointSetting();
	}
	
	
	public void setCircleRadius(int radius) {
		try {
			double lat = MyManager.getInstance().getLatitude();
			double lon = MyManager.getInstance().getLongitude();
			MapPoint mp = MapPoint.mapPointWithGeoCoord(lat, lon); 
			MapCircle circle = new MapCircle(
				mp, // center
				500, // radius
				Color.argb(128, 255, 0, 0), // strokeColor 
				Color.argb(128, 0, 255, 0) // fillColor
			);
			circle.setTag(ManageMode.TRAKING.getCode());
			mapView.addCircle(circle);
		} catch(Exception e){
		}
	}
	
	public void setGeoPoint(double lat, double lng) {
		mapManager.clearGeoPoints();
		mapManager.addTempGeoPoint(new GeoPoint(lat, lng));
	}

}
