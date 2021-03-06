/*******************************************************************************
 * Copyright 2014 if{y} team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package pl.poznan.put.cs.ify.api.group;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.poznan.put.cs.ify.api.log.YLog;
import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamType;

/**
 * Data used in communication with server.
 */
public class YCommData {
	public static final String EVENT = "event";
	public static final String VALUES = "values";
	public static final String TAG = "tag";
	public static final String TARGET = "target";
	public static final String USER = "user";
	public static final String SEPARATOR = "@";

	private YUserData mUserData;
	private int mEventTag;
	private String mEventTarget;
	private Map<String, YParam> mValues;

	public int getEventTag() {
		return mEventTag;
	}

	public void setEventTag(int eventTag) {
		mEventTag = eventTag;
	}

	public String getEventTarget() {
		return mEventTarget;
	}

	public void setEventTarget(String eventTarget) {
		mEventTarget = eventTarget;
	}

	public Map<String, YParam> getValues() {
		return mValues;
	}

	public void setValues(Map<String, YParam> values) {
		mValues = values;
	}

	private YCommData() {
		mValues = new HashMap<String, YParam>();
	}

	public YCommData(int eventTag, String eventTarget, YUserData userData) {
		mEventTag = eventTag;
		mEventTarget = eventTarget;
		mUserData = userData;
		mValues = new HashMap<String, YParam>();
	}

	public static YCommData fromJson(String json) {
		try {
			YLog.i("YCommData", "JSONObject");
			JSONObject jo = new JSONObject(json);
			YLog.i("YCommData", "JSONObject created");
			return fromJsonObject(jo);
		} catch (JSONException e) {
		}
		return null;
	}

	public static YCommData fromJsonObject(JSONObject json) {
		try {
			YCommData data = new YCommData();
			JSONObject user = json.getJSONObject(USER);
			data.mUserData = YUserData.fromJsonObject(user);
			JSONObject event = json.getJSONObject(EVENT);
			data.mEventTag = event.getInt(TAG);
			data.mEventTarget = event.getString(TARGET);
			JSONObject values = json.getJSONObject(VALUES);
			if (values != null) {
				JSONArray names = values.names();
				YLog.i("YCommData", "Values");
				if (names != null) {
					for (int i = 0; i < names.length(); i++) {
						String name = names.getString(i);
						JSONObject val = values.getJSONObject(name);
						data.mValues.put(name, YParam.fromJsonObj(val));
					}

				}
			}
			YLog.i("YCommData", "Values filled");
			return data;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public YUserData getUserData() {
		return mUserData;
	}

	public void add(String name, YParamType type, Object value) {
		add(name, new YParam(type, value));
	}

	public void add(String name, YParam value) {
		mValues.put(name, value);
	}

	public JSONObject toJsonObject() throws JSONException {
		JSONObject event = eventToJsonObject();
		JSONObject values = valuesToJsonObject();
		JSONObject user = mUserData.toJsonObject();
		JSONObject me = new JSONObject();
		me.put(EVENT, event);
		me.put(VALUES, values);
		me.put(USER, user);
		return me;
	}

	private JSONObject valuesToJsonObject() throws JSONException {
		JSONObject values = new JSONObject();
		for (Map.Entry<String, YParam> value : mValues.entrySet()) {
			values.put(value.getKey(), value.getValue().toJsonObj());
		}
		return values;
	}

	private JSONObject eventToJsonObject() throws JSONException {
		JSONObject event = new JSONObject();
		event.put(TAG, mEventTag);
		event.put(TARGET, mEventTarget);
		return event;
	}

	public YParam getData(String name, String user) {
		return mValues.get(name + SEPARATOR + user);
	}

	public YParam getData(String name) {
		return mValues.get(name);
	}

	public String getDataAsString(String name) {
		YParam val = mValues.get(name);
		if (val == null)
			return "null";
		return val.getValue().toString();
	}

	public String getDataAsString(String name, String user) {
		YParam val = getData(name, user);
		if (val == null)
			return "null";
		return val.getValue().toString();
	}

	public String toJson() {
		try {
			return toJsonObject().toString();
		} catch (JSONException e) {
			YLog.w("YCommData", e.toString());
			return "{}";
		}
	}

	void setValuesAddingUser(Map<String, YParam> map, String user) {
		Map<String, YParam> newMap = new HashMap<String, YParam>();
		for (Map.Entry<String, YParam> entry : map.entrySet()) {
			newMap.put(entry.getKey() + SEPARATOR + user, entry.getValue());
		}
		setValues(newMap);
	}
}
