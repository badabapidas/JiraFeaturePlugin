package com.sag.jira.core;

import org.codehaus.jettison.json.JSONObject;

import com.sag.base.core.BaseClient;
import com.sag.jira.util.JiraRestConfig;
import com.sag.jira.util.PropertyUtil;

public class JiraRestCore extends BaseClient {

	protected JiraRestCore() {
	}

	public static String getBaseTargetUri() {
		return PropertyUtil.getJiraurl();
	}

	public static String getRestTargetUri() {
		StringBuilder sb = new StringBuilder();
		return sb.append(getBaseTargetUri()).append(JiraRestConfig.REST_API_ENDPOINT).toString();
	}

	protected String fetchAndValidateResponse(String valueToFetech, JSONObject issueResponse) {
		if (issueResponse != null)
			return issueResponse.optString(valueToFetech);
		return JiraRestConfig.Common.EMPTY;
	}

}
