package com.sag.jira.core.component.issue;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sag.jira.util.JiraRestConfig;

public abstract class IssueRoot {
	protected String issueId;
	protected JSONObject jsonObject;
	protected final Logger log = LoggerFactory.getLogger(getClass());

	public void initialized(String issueId, JSONObject jsonObjectForIssue, String restAssetPath) throws JSONException {
		this.issueId = issueId;
		this.jsonObject = jsonObjectForIssue.getJSONObject(JiraRestConfig.Issue.FILEDS).optJSONObject(restAssetPath);
		if (this.jsonObject == null)
			this.jsonObject = jsonObjectForIssue.getJSONObject(JiraRestConfig.Issue.FILEDS);
	}

	public String fetchAndValidateResponse(String valueToFetech) {
		if (jsonObject != null)
			return jsonObject.optString(valueToFetech);
		return JiraRestConfig.Common.EMPTY;
	}

	public String getRestUrl() {
		return fetchAndValidateResponse(JiraRestConfig.Common.SELF);
	}

}
