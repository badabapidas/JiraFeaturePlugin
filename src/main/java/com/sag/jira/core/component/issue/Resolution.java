package com.sag.jira.core.component.issue;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sag.jira.util.JiraRestConfig;

public class Resolution extends iTracRoot {

	public Resolution(String issueId, JSONObject jsonObjectForIssue) throws JSONException {
		try {
			initialized(issueId, jsonObjectForIssue, JiraRestConfig.ISSUE_RESOLUTION_REST_PATH);
		} catch (JSONException e) {
		}
	}

	public String getStatus() {
		return fetchAndValidateResponse(JiraRestConfig.Common.NAME);
	}

	public String getDescription() {
		return fetchAndValidateResponse(JiraRestConfig.Common.DESCRIPTION);
	}

}
