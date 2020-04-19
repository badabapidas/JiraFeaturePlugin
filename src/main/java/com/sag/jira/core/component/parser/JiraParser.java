package com.sag.jira.core.component.parser;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sag.jira.util.JiraRestConfig;
import com.sun.jersey.api.client.ClientResponse;

public abstract class JiraParser {
	protected ClientResponse response;
	protected JSONObject jsonObject;
	protected final Logger log = LoggerFactory.getLogger(getClass());

	protected void parseResponse(ClientResponse response) throws JSONException {
		if (response != null) {
			this.response = response;
			String entity = response.getEntity(String.class);
			jsonObject = new JSONObject(entity);
		}
	}

	public String fetchAndValidateResponse(String valueToFetech) {
		if (jsonObject != null && jsonObject.length() > 0)
			return jsonObject.optString(valueToFetech);
		return "";
	}

	public ClientResponse getClientResponse() {
		return response;
	}

	public JSONObject getJsonResponse() {
		return jsonObject;
	}

	public String fetchAndValidateResponse(String valueToFetech, JSONObject jsonObject) {
		if (jsonObject != null && jsonObject.length() > 0)
			return jsonObject.optString(valueToFetech);
		return JiraRestConfig.Common.EMPTY;
	}

	public boolean isValidJsonObject(JSONObject jsonObj) {
		return (jsonObj != null && jsonObj.length() > 0);
	}

	public boolean isValidJsonArray(JSONArray jsonArr) {
		return (jsonArr != null && jsonArr.length() > 0);
	}
}
