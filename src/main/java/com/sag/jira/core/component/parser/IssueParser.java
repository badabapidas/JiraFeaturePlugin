package com.sag.jira.core.component.parser;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sag.jira.util.JiraRestConfig;
import com.sun.jersey.api.client.ClientResponse;

public class IssueParser extends JiraParser {

	public IssueParser(ClientResponse response) throws JSONException {
		parseResponse(response);
	}

	public String getDescription() {
		if (jsonObject != null) {
			String description = jsonObject.optString(JiraRestConfig.Common.DESCRIPTION);
			if (description.isEmpty()) {
				JSONObject updateJsonObject = jsonObject.optJSONObject(JiraRestConfig.Issue.FILEDS);
				return fetchAndValidateResponse(JiraRestConfig.Common.DESCRIPTION, updateJsonObject);
			}

		}
		return JiraRestConfig.Common.EMPTY;
	}

	public String getSummary() {
		if (jsonObject != null) {
			String summary = jsonObject.optString(JiraRestConfig.Issue.SUMMARY);
			if (summary.isEmpty()) {
				JSONObject updateJsonObject = jsonObject.optJSONObject(JiraRestConfig.Issue.FILEDS);
				return fetchAndValidateResponse(JiraRestConfig.Issue.SUMMARY, updateJsonObject);
			}

		}
		return JiraRestConfig.Common.EMPTY;
	}

	public String getKey() {
		if (jsonObject != null) {
			return jsonObject.optString(JiraRestConfig.Issue.KEY);
		}
		return JiraRestConfig.Common.EMPTY;
	}

	public String getGenerateId() {
		if (jsonObject != null) {
			return jsonObject.optString(JiraRestConfig.Issue.ID);
		}
		return JiraRestConfig.Common.EMPTY;
	}
}
