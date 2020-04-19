package com.sag.jira.core.component.issue;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sag.jira.util.JiraRestConfig;

public class TimeTracker extends IssueRoot {
	public TimeTracker(String issueId, JSONObject jsonObjectForIssue) throws JSONException {
		try {
			initialized(issueId, jsonObjectForIssue, JiraRestConfig.ISSUE_TIME_TRACKER_REST_PATH);
		} catch (JSONException e) {
			throw new JSONException("Time Tracker not found!");
		}
	}

	public String getOriginalEstimate() {
		return fetchAndValidateResponse(JiraRestConfig.TimTracker.ESTIMATE_ORGINAL);
	}

	public boolean isEstimateDone() {
		return getOriginalEstimate().isEmpty() ? false : true;
	}

	public String getRemainingEstimate() {
		return fetchAndValidateResponse(JiraRestConfig.TimTracker.ESTIMATE_REMAINING);
	}

	public String getTimeSpent() {
		return fetchAndValidateResponse(JiraRestConfig.TimTracker.TIMESPENT);
	}

	public String getOriginalEstimateInSecs() {
		return fetchAndValidateResponse(JiraRestConfig.TimTracker.ESTIMATE_ORGINAL_SECS);
	}

	public String getRemainingEstimateInSecs() {
		return fetchAndValidateResponse(JiraRestConfig.TimTracker.ESTIMATE_REMAINING_SECS);
	}

	public String getTimeSpentInSecs() {
		return fetchAndValidateResponse(JiraRestConfig.TimTracker.TIMESPENT_SECS);
	}

}