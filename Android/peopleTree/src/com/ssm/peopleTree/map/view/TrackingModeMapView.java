package com.ssm.peopleTree.map.view;

import android.app.Activity;
import android.graphics.Color;

import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.map.ManageMode;
import com.ssm.peopleTree.map.MapManager;

import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class TrackingModeMapView extends GroupLocationMapView implements RadiusSettable {

	private MapView mapView;
	private MapCircle circle;
	
	public TrackingModeMapView(Activity activity) {
		super(activity);
	}

	@Override
	public void onMapViewInitialized(MapView mapView) {
		super.onMapViewInitialized(mapView);
		
		this.mapView = mapView;
				
		double lat = MyManager.getInstance().getLatitude();
		double lon = MyManager.getInstance().getLongitude();
		MapPoint mp = MapPoint.mapPointWithGeoCoord(lat, lon);
		circle = new MapCircle(mp, 0, Color.argb(128, 255, 0, 0), Color.argb(128, 0, 255, 0));
		mapView.addCircle(circle);
		
		//setRadius(MapManager.getInstance().getTempRadius());
	}

	@Override
	public void setRadius(int radius) {
		try {
			mapView.removeCircle(circle);
			double lat = MyManager.getInstance().getLatitude();
			double lon = MyManager.getInstance().getLongitude();
			MapPoint mp = MapPoint.mapPointWithGeoCoord(lat, lon);
			circle.setCenter(mp);
			circle.setRadius(radius);
			circle.setTag(ManageMode.TRACKING.getCode());
			mapView.addCircle(circle);
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
