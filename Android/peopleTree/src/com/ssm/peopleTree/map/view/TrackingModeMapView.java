package com.ssm.peopleTree.map.view;

import android.app.Activity;
import android.graphics.Color;

import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.map.ManageMode;

import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class TrackingModeMapView extends GroupLocationMapView {

	private MapView mapView;
	
	public TrackingModeMapView(Activity activity) {
		super(activity);
	}

	@Override
	public void onMapViewInitialized(MapView mapView) {
		super.onMapViewInitialized(mapView);
		
		this.mapView = mapView;
				
		setCircleRadius(MyManager.getInstance().getManagedLocationRadius());
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
}
