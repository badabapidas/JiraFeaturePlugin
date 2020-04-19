package com.sag.jira.core.component.issue;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sag.jira.util.JiraRestConfig;

public class Assignee extends IssueRoot {

	public Assignee(String issueId, JSONObject jsonObjectForIssue) throws JSONException {
		try {
			initialized(issueId, jsonObjectForIssue, JiraRestConfig.ISSUE_ASSIGNEE_REST_PATH);
		} catch (JSONException e) {
			throw new JSONException("Assignee not found! " + e.getMessage());
		}
	}

	public String getAlias() {
		return fetchAndValidateResponse(JiraRestConfig.Common.NAME);
	}

	public String getEmail() {
		return fetchAndValidateResponse(JiraRestConfig.User.EMAIL_ADDRESS);
	}

	public String getDisplayName() {
		return fetchAndValidateResponse(JiraRestConfig.User.DISPLAY_NAME);
	}

}
