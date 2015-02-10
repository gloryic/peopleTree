package com.ssm.peopleTree.parse.pushnotifications;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;
import com.ssm.peopleTree.MainActivity;

public class Application extends android.app.Application {

	  public Application() {
	  }

	  @Override
	  public void onCreate() {
	    super.onCreate();

		//Initialize the Parse SDK.
	    Parse.initialize(this, "sDGocHwgCiClL6qWbc2sOZzDbHtg6JCWWmhGZWIj", "2mXQl45GSFhtAmDZmfoPtRC3WpX2hgAtP7Djq4EH");  

		//Specify an Activity to handle all pushes by default.
		PushService.setDefaultPushCallback(this, com.ssm.peopleTree.MainActivity.class);
	  }
	}