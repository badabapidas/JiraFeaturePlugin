package com.sag.jira.core.component.parser;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sag.jira.util.JiraRestConfig;
import com.sun.jersey.api.client.ClientResponse;

public class ComponentsParser extends JiraParser {

	JSONArray componentArray;

	public ComponentsParser(ClientResponse response) throws JSONException {
		parseResponse(response);
		if (jsonObject != null) {
			componentArray = jsonObject.optJSONArray(JiraRestConfig.Component.COMPONENTS);
		}
	}

	public Map<String, String> getComponentsWithDescriptions() {
		HashMap<String, String> components = new HashMap<>();
		if (componentArray != null) {
			for (int i = 0; i < componentArray.length(); i++) {
				JSONObject jsonObject;
				try {
					jsonObject = componentArray.getJSONObject(i);
					final String name = jsonObject.optString(JiraRestConfig.Common.NAME);
					final String description = jsonObject.optString(JiraRestConfig.Common.DESCRIPTION);
					components.put(name, description);
				} catch (JSONException e) {
				}

			}
		}
		return components;
	}

	public String getComponents() {
		StringBuilder components = new StringBuilder();
		if (componentArray != null) {
			for (int i = 0; i < componentArray.length(); i++) {
				JSONObject jsonObject;
				try {
					jsonObject = componentArray.getJSONObject(i);
					final String name = jsonObject.optString(JiraRestConfig.Common.NAME);
					components.append(name);
					if (i < componentArray.length() - 1)
						components.append(",");
				} catch (JSONException e) {
				}

			}
		}
		return components.toString();
	}
}
