package com.ssm.peopleTree.location;

import java.util.Timer;
import java.util.TimerTask;

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

class OutsideLocationListener extends Service implements LocationListener,
		LocationMeasurer {

	LocationManager locationManager;
	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;
	boolean isLocationRequested = false;
	boolean isGetLocation = false;
	private Context mContext;
	UpdateNotifier updateNotifier = null;

	Location location = null;

	// 요청중일때 프로바이더가 값을 업데이트해줄때마다 카운트
	public int changedCnt = 0;

	long validTime = 1000 * 60;
	float validAccuracy = (float) 50.0;
	long lastUpdateTime = 0;
	Timer jobScheduler;
	
	long savedDistanceForUpdate=0;
	long savedTimeForUpdate =0;
	OutsideLocationListener(Context context) {
		this.mContext = context;

		this.updateNotifier = new OutsideLocationUpdateNotifier();
		 jobScheduler = new Timer();
		 lastUpdateTime = System.currentTimeMillis();
		
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

	public boolean isLocReqPossible() {
		boolean ret = false;

		return ret;
	}

	public boolean startRequest(long distanceForUpdate, long timeForUpdate) {
		locationManager = (LocationManager) mContext
				.getSystemService(LOCATION_SERVICE);
		isGPSEnabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		isNetworkEnabled = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		
		this.savedDistanceForUpdate = distanceForUpdate;
		this.savedTimeForUpdate = timeForUpdate;
		if (!isGPSEnabled) {

			return false;
		}
		if (isLocationRequested == false) {
			this.isLocationRequested = true;
			this.isGetLocation = false;
			this.locationRequest(distanceForUpdate, timeForUpdate);

		}
		
		jobScheduler.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				long curtime = System.currentTimeMillis();
				long timediff = curtime- lastUpdateTime;
				if(timediff>=savedTimeForUpdate*2){
					if (updateNotifier != null) {
						updateNotifier.notifyUpdate(this);
					}
				}
			}	
		}, 0,1000*5);
		return true;
	}

	public void stopRequest() {
		jobScheduler.cancel();
		if (locationManager != null) {
			this.isGetLocation = false;
			isLocationRequested = false;
			locationManager.removeUpdates(OutsideLocationListener.this);

		}
	}

	public void setUpdateNotifier(UpdateNotifier u) {
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
		if (location == null) {

			return;
		}
		boolean isBetter= false;
		if (this.location != null) {
			long timeDiff = location.getTime() - this.location.getTime();
			if (timeDiff >= validTime
					|| location.getAccuracy() <= this.validAccuracy) {
				isBetter= true;
			}
		}else{
			isBetter=true;
			
		}
		Log.i("Log", "onLocationChanged");
		if (isBetter) {
			statecnt++;
			this.location = location;
			isGetLocation = true;
			if (updateNotifier != null) {
				lastUpdateTime = System.currentTimeMillis();
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

	@Override
	public boolean isGetLcoation() {
		// TODO Auto-generated method stub
		return this.isGetLocation;
	}

}
