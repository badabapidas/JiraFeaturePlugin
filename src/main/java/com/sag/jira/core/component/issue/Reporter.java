package com.sag.jira.core.component.issue;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sag.jira.util.JiraRestConfig;

public class Reporter extends iTracRoot {

	public Reporter(String id, JSONObject jsonObjectForIssue) throws JSONException {

		try {
			initialized(id, jsonObjectForIssue, JiraRestConfig.ISSUE_REPORTER_REST_PATH);
		} catch (JSONException e) {
		}
	}

	public String getAlias() {
		return fetchAndValidateResponse(JiraRestConfig.Common.NAME);
	}

	public String getDisplayName() {
		return fetchAndValidateResponse(JiraRestConfig.User.DISPLAY_NAME);
	}

	public String getEmail() {
		return fetchAndValidateResponse(JiraRestConfig.User.EMAIL_ADDRESS);
	}

}
