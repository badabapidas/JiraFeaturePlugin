package com.sag.jira.core.component.issue;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sag.jira.util.JiraRestConfig;

public class Type extends IssueRoot {
	public Type(String issueId, JSONObject jsonObjectForIssue) throws JSONException {
		try {
			initialized(issueId, jsonObjectForIssue, JiraRestConfig.ISSUE_TYPE_REST_PATH);
		} catch (JSONException e) {
			throw new JSONException("Issue type not found!");
		}
	}

	public String getIssueType() {
		return fetchAndValidateResponse(JiraRestConfig.Common.NAME);
	}

	public boolean isSubtasksAvailable() {
		return Boolean.parseBoolean(fetchAndValidateResponse(JiraRestConfig.Issue.SUBTASK));
	}

	public String getIssueTypeDescription() {
		return fetchAndValidateResponse(JiraRestConfig.Common.DESCRIPTION);
	}
}
