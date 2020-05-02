package com.sag.jira.core.component.issue;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sag.jira.util.JiraRestConfig;

public abstract class iTracRoot {
	protected String issueId;
	protected JSONObject jsonObject;
	protected final Logger log = LoggerFactory.getLogger(getClass());

	protected void initialized(String issueId, JSONObject jsonObjectForIssue, String restAssetPath)
			throws JSONException {
		this.issueId = issueId;
		this.jsonObject = jsonObjectForIssue.getJSONObject(JiraRestConfig.Issue.FILEDS).optJSONObject(restAssetPath);
		if (this.jsonObject == null)
			this.jsonObject = jsonObjectForIssue.getJSONObject(JiraRestConfig.Issue.FILEDS);
	}

	public boolean isValidJsonObject(JSONObject jsonObj) {
		return (jsonObj != null && jsonObj.length() > 0);
	}

	protected String fetchAndValidateResponse(String valueToFetech) {
		String optString = null;
		if (jsonObject != null) {
			optString = jsonObject.optString(valueToFetech);
		}
		return (optString == null || optString.isEmpty()) ? JiraRestConfig.Common.NOT_AVAILABLE : optString;
	}

	protected String getRestUrl() {
		return fetchAndValidateResponse(JiraRestConfig.Common.SELF);
	}

}
