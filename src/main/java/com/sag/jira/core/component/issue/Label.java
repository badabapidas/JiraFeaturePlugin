package com.sag.jira.core.component.issue;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sag.jira.util.JiraRestConfig;

public class Label extends IssueRoot {
	public Label(String issueId, JSONObject jsonObjectForIssue) throws JSONException {
		try {
			initialized(issueId, jsonObjectForIssue, JiraRestConfig.ISSUE_LABELS_REST_PATH);
		} catch (JSONException e) {
			throw new JSONException("Label not found!");
		}
	}

	public String getLabels() {
		return fetchAndValidateResponse(JiraRestConfig.Label.LABELS);
	}

}
