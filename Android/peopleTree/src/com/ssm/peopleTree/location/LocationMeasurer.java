package com.ssm.peopleTree.location;

public interface LocationMeasurer {

	public boolean startRequest(long distanceForUpdate, long timeForUpdate);

	public void stopRequest();
	public boolean isValidLocation();
	public void setUpdateNotifier(UpdateNotifier u);
	public boolean isLocReqPossible();
	public boolean isGetLcoation();
}
