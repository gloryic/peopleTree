package com.ssm.peopleTree;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;
import net.daum.mf.map.api.MapView.MapViewEventListener;

import com.ssm.peopleTree.application.MyManager;
import com.ssm.peopleTree.data.MemberData;
import com.ssm.peopleTree.map.MapManager;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ParentLocationActivity extends Activity implements MapViewEventListener, OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_map_parent);
		
		Button btn = (Button)findViewById(R.id.btn_close);
		btn.setOnClickListener(this);
		
		
		MapView mapView = new MapView(this);
		mapView.setDaumMapApiKey(MapManager.getMapApiKey());
		mapView.setMapViewEventListener(this);	
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_close:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onMapViewInitialized(MapView mapView) {

		MyManager myManager = MyManager.getInstance();
		MapPointBounds mapPointBounds = new MapPointBounds();
		
		MapPoint myMp = MapPoint.mapPointWithGeoCoord(myManager.getLatitude(), myManager.getLongitude());
		MapPOIItem myMarker = new MapPOIItem();
		myMarker.setItemName("³ª");
		myMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
		myMarker.setMapPoint(myMp);
		mapView.addPOIItem(myMarker);
		mapPointBounds.add(myMp);
		
		MemberData pData = myManager.getMyParentData();
		MapPoint parentMp = MapPoint.mapPointWithGeoCoord(pData.latitude, pData.longitude);
		MapPOIItem parentMarker = new MapPOIItem();
		parentMarker.setItemName(pData.userName);
		parentMarker.setMarkerType(MapPOIItem.MarkerType.RedPin);
		parentMarker.setMapPoint(parentMp);
		mapView.addPOIItem(parentMarker);
		mapPointBounds.add(parentMp);
		
		MapPolyline polyline = new MapPolyline();
		polyline.setLineColor(Color.argb(128, 255, 51, 0));
		polyline.addPoint(myMp);
		polyline.addPoint(parentMp);
		mapView.addPolyline(polyline);
		
		int padding = 100; // px
		mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
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
}
