package com.ssm.location;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

class OutsideLocationListener extends Service implements
		LocationListener, LocationMeasurer {


	LocationManager locationManager;
	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;
	boolean isLocationRequested = false;

	private Context mContext;

	UpdateNotifier updateNotifier = null;

	Location location = null;

	// 요청중일때 프로바이더가 값을 업데이트해줄때마다 카운트
	public int changedCnt = 0;

	long validTime = 1000 * 60;
	float validAccuracy = (float) 40.0;

	OutsideLocationListener(Context context) {
		this.mContext = context;

		this.updateNotifier = new OutsideLocationUpdateNotifier();


	}

	public boolean isGPSEnabled() {
		return isGPSEnabled;
	}

	public boolean isNetworkEnabled() {
		return isNetworkEnabled;
	}



	public void setValidCond(long validTime, float validAccuracy) {
		this.validTime = validTime;
		this.validAccuracy = validAccuracy;
	}

	public long getValidTime() {
		return validTime;
	}

	public float getValidAccuracy() {
		return validAccuracy;
	}

	public boolean isValidLocation() {
		long timeDiff = System.currentTimeMillis() - location.getTime();

		if (timeDiff <= this.validTime
				&& location.getAccuracy() <= this.validAccuracy) {
			return true;
		}
		return false;

	}
	public boolean isLocReqPossible(){
		boolean ret = false;
		
		return ret;
	}
	public boolean startRequest(long distanceForUpdate, long timeForUpdate) {
		
		isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		
		if(!isGPSEnabled){
			
			return false;
		}
		if (isLocationRequested == false) {
			this.isLocationRequested = true;
			Log.i("Log","startRequest");
			this.locationRequest(distanceForUpdate, timeForUpdate);
	
		}
		return true;
	}

	
	public void stopRequest() {
		if (locationManager != null) {
			isLocationRequested = false;
			locationManager.removeUpdates(OutsideLocationListener.this);

		}
	}
	public void setUpdateNotifier(UpdateNotifier u){
		this.updateNotifier = u;
	}

	private void locationRequest(long distanceForUpdate, long timeForUpdate) {

		try {
			locationManager = (LocationManager) mContext
					.getSystemService(LOCATION_SERVICE);

			if (locationManager != null) {
				changedCnt = 0;

				
				locationManager.requestLocationUpdates(
						LocationManager.GPS_PROVIDER, distanceForUpdate,
						timeForUpdate, this);

				locationManager.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER, distanceForUpdate,
						timeForUpdate, this);

				if (this.isNetworkEnabled) {
					location = locationManager
							.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				}

				if (this.isGPSEnabled) {
					location = locationManager
							.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int statecnt = 0;

	@Override
	public void onLocationChanged(Location location) {
		long timeDiff = location.getTime() - this.location.getTime();
		Log.i("Log", "onLocationChanged");
		if (timeDiff >= validTime
				|| location.getAccuracy() <= this.validAccuracy) {

			statecnt++;
			this.location = location;
			if (updateNotifier != null) {

				updateNotifier.notifyUpdate(this);
			}
		}

	}

	public boolean isLocationRequested() {
		return this.isLocationRequested;
	}

	public Location getLocation() {

		return location;
	}

	public double getLatitude() {
		return location.getLatitude();

	}

	public double getLongitude() {
		return location.getLongitude();

	}

	public float getAccuracy() {
		return location.getAccuracy();

	}

	public double getTime() {
		return location.getTime();

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

		if (this.isLocationRequested) {
			switch (status) {
			case LocationProvider.OUT_OF_SERVICE:
				if (provider.compareTo("gps") == 0) {
					this.isGPSEnabled = false;
				} else if (provider.compareTo("network") == 0) {
					this.isNetworkEnabled = false;
				}

				break;
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				if (provider.compareTo("gps") == 0) {
					this.isGPSEnabled = false;
				} else if (provider.compareTo("network") == 0) {
					this.isNetworkEnabled = false;
				}
				break;
			case LocationProvider.AVAILABLE:
				if (provider.compareTo("gps") == 0) {
					this.isGPSEnabled = true;
				} else if (provider.compareTo("network") == 0) {
					this.isNetworkEnabled = true;
				}
				break;

			}
		}
	}

	// gps network
	@Override
	public void onProviderEnabled(String provider) {

		if (this.isLocationRequested) {
			if (provider.compareTo("gps") == 0) {

				this.isGPSEnabled = true;
			} else if (provider.compareTo("network") == 0) {

				this.isNetworkEnabled = true;
			}
		}
	}

	@Override
	public void onProviderDisabled(String provider) {

		if (this.isLocationRequested) {
			if (provider.compareTo("gps") == 0) {

				this.isGPSEnabled = false;
			} else if (provider.compareTo("network") == 0) {

				this.isNetworkEnabled = false;
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}


}
