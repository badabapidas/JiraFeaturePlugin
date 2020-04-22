package com.sag.jira.core.component.issue;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sag.jira.util.JiraRestConfig;

public class Component extends iTracRoot {
	private JSONArray componentArray;

	public Component(String issueId, JSONObject jsonObjectForIssue) throws JSONException {
		try {
			initialized(issueId, jsonObjectForIssue, JiraRestConfig.getIssueUrl(issueId));
			componentArray = jsonObject.optJSONArray(JiraRestConfig.Component.COMPONENTS);
		} catch (JSONException e) {
			throw new JSONException("Component not found! " + e.getMessage());
		}
	}

	public String getComponents() {
		StringBuilder componentSB = new StringBuilder();
		if (componentArray != null) {
			for (int i = 0; i < componentArray.length(); i++) {
				JSONObject component;
				try {
					component = componentArray.getJSONObject(i);
					final String name = component.optString(JiraRestConfig.Common.NAME);
					componentSB.append(name);
					if (i < componentArray.length() - 1)
						componentSB.append(",");
				} catch (JSONException e) {
				}
			}
		}
		return componentSB.toString();
	}

}
