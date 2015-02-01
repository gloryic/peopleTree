package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

public interface Param {
	abstract JSONObject toJSonObject();
	abstract int getMethod();
}
