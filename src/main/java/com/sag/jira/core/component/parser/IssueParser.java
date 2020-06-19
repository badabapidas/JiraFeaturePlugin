package com.sag.jira.core.component.parser;

import java.util.HashSet;
import java.util.Set;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sag.jira.core.component.iTrac;
import com.sag.jira.exception.ITracNotFoundException;
import com.sag.jira.util.JiraRestConfig;
import com.sun.jersey.api.client.ClientResponse;

public class IssueParser extends JiraParser {

	private final String IS_PARENT_FEATURE_OF = "is parent Feature of";

	public IssueParser(ClientResponse response) throws JSONException {
		parseResponse(response);
	}

	public Set<iTrac> getAllSubtasks() throws ITracNotFoundException {
		if (isValidJsonObject(jsonObject)) {
			JSONObject fieldObject = jsonObject.optJSONObject(JiraRestConfig.Issue.FILEDS);
			if (isValidJsonObject(fieldObject)) {
				JSONArray subtasks = fieldObject.optJSONArray(JiraRestConfig.Issue.SUBTASKS);
				if (isValidJsonArray(subtasks)) {
					return parseSubtasks(subtasks);
				}
			}
		}
		return new HashSet();
	}

	public Set<iTrac> getAllLInkedIssues() throws ITracNotFoundException {
		if (isValidJsonObject(jsonObject)) {
			JSONObject fieldObject = jsonObject.optJSONObject(JiraRestConfig.Issue.FILEDS);
			if (isValidJsonObject(fieldObject)) {
				JSONArray linkedIssues = fieldObject.optJSONArray(JiraRestConfig.Issue.ISSUELINKS);
				if (isValidJsonArray(linkedIssues)) {
					return parseLinkedIssues(linkedIssues);
				}
			}
		}
		return new HashSet();
	}

	private Set<iTrac> parseLinkedIssues(JSONArray issues) throws ITracNotFoundException {
		Set<iTrac> linkedIssues = new HashSet<>();
		for (int i = 0; i < issues.length(); i++) {
			JSONObject issue = issues.optJSONObject(i);
			if (isValidJsonObject(issue)) {
				JSONObject typeObj = issue.optJSONObject(JiraRestConfig.Issue.TYPE);
				if (isValidJsonObject(typeObj)) {
					String inward = typeObj.optString(JiraRestConfig.Issue.INWARD);
					if (inward.equalsIgnoreCase(IS_PARENT_FEATURE_OF)) {
						JSONObject inwardIssues = issue.optJSONObject(JiraRestConfig.Issue.INWARD_ISSUE);
						if (isValidJsonObject(inwardIssues)) {
							String itracId = inwardIssues.optString(JiraRestConfig.Issue.KEY);
							linkedIssues.add(new iTrac(itracId, true));
						}
					}
				}
			}
		}
		return linkedIssues;
	}

	private Set<iTrac> parseSubtasks(JSONArray subtasks) throws ITracNotFoundException {
		Set<iTrac> subtasksList = new HashSet<>();
		for (int i = 0; i < subtasks.length(); i++) {
			JSONObject subtask = subtasks.optJSONObject(i);
			if (isValidJsonObject(subtask)) {
				String itracId = subtask.optString("key");
				subtasksList.add(new iTrac(itracId, true));
			}
		}
		return subtasksList;
	}

	public String getDescription() {
		if (isValidJsonObject(jsonObject)) {
			String description = jsonObject.optString(JiraRestConfig.Common.DESCRIPTION);
			if (description.isEmpty()) {
				JSONObject updateJsonObject = jsonObject.optJSONObject(JiraRestConfig.Issue.FILEDS);
				return fetchAndValidateResponse(JiraRestConfig.Common.DESCRIPTION, updateJsonObject);
			}
		}
		return JiraRestConfig.Common.EMPTY;
	}

	public String getSummary() {
		if (isValidJsonObject(jsonObject)) {
			String summary = jsonObject.optString(JiraRestConfig.Issue.SUMMARY);
			if (summary.isEmpty()) {
				JSONObject updateJsonObject = jsonObject.optJSONObject(JiraRestConfig.Issue.FILEDS);
				return fetchAndValidateResponse(JiraRestConfig.Issue.SUMMARY, updateJsonObject);
			}
		}
		return JiraRestConfig.Common.EMPTY;
	}

	public String getKey() {
		if (isValidJsonObject(jsonObject)) {
			return jsonObject.optString(JiraRestConfig.Issue.KEY);
		}
		return JiraRestConfig.Common.EMPTY;
	}

	public String getGenerateId() {
		if (isValidJsonObject(jsonObject)) {
			return jsonObject.optString(JiraRestConfig.Common.ID);
		}
		return JiraRestConfig.Common.EMPTY;
	}
}
