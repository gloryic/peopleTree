package com.ssm.peopleTree.map.view;

import java.util.ArrayList;

import com.ssm.peopleTree.map.GeoPoint;
import com.ssm.peopleTree.map.MapManager;

import android.app.Activity;
import android.graphics.Color;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

public class GeofenceModeMapView extends GroupLocationMapView {

	private MapManager mapManager;
	private MapPolyline polyline;
	
	private MapView mapView;
	private ArrayList<MapPOIItem> items; 
	
	public GeofenceModeMapView(Activity activity) {
		super(activity);
		
		items = new ArrayList<MapPOIItem>();
	}
	
	@Override
	public void onMapViewInitialized(MapView mapView) {
		super.onMapViewInitialized(mapView);
		
		mapManager = MapManager.getInstance();
		
		this.mapView = mapView; 
		
		items.clear();
		
		polyline = new MapPolyline();
		polyline.setLineColor(Color.argb(128, 255, 51, 0));
	}
	

	@Override
	public void onMapViewSingleTapped(MapView mapView, MapPoint mp) {
		if (!mapManager.isGeoPointSettingStarted()) {
			return;
		}
		
		MapPOIItem areaMarker = new MapPOIItem();
		areaMarker.setItemName("point");
		areaMarker.setMarkerType(MapPOIItem.MarkerType.RedPin);
		areaMarker.setMapPoint(mp);
		mapView.addPOIItem(areaMarker);
		items.add(areaMarker);
				
		addPolyline(mapView, mp);
		mapManager.addTempGeoPoint(new GeoPoint(mp.getMapPointGeoCoord().latitude, mp.getMapPointGeoCoord().longitude));
	}
	
	public void addPolyline(MapView mapView, MapPoint mp) {
		polyline.addPoint(mp);
		mapView.addPolyline(polyline);
		
		polyline = new MapPolyline();
		//polyline.setTag(1000);
		polyline.setLineColor(Color.argb(128, 255, 51, 0));
		polyline.addPoint(mp);
	}

	public void clearSetting() { 
		for (MapPOIItem item : items) {
			mapView.removePOIItem(item);
		}
		mapView.removeAllPolylines();
		mapManager.clearTempGeoPoints();
		polyline = new MapPolyline();
		polyline.setLineColor(Color.argb(128, 255, 51, 0));
	}
}
