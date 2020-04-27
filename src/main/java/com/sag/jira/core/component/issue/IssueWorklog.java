package com.sag.jira.core.component.issue;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sag.jira.core.component.iTrac;
import com.sag.jira.core.obj.Worklog;
import com.sag.jira.core.response.ItracWorkLogResponseBuilder;
import com.sag.jira.core.response.ItracWorkLogResponseBuilder.WorklogResponse;
import com.sag.jira.core.response.ResponseBuilder;
import com.sag.jira.util.JiraRestConfig;

public class IssueWorklog extends iTracRoot {
	private List<Worklog> allWorkLogsForOneItrac;
	private iTrac iTrac;
	private ItracWorkLogResponseBuilder response = ResponseBuilder.getWorklogResponseBuilder();

	public IssueWorklog(final iTrac iTrac, final JSONObject jsonObjectForIssue) throws JSONException {
		try {
			this.iTrac = iTrac;
			initialized(iTrac.getKey(), jsonObjectForIssue, JiraRestConfig.ISSUE_WORKLOG_REST_PATH);
			final JSONArray allWorklogsInArray = jsonObject.optJSONArray((JiraRestConfig.Worklog.WORKLOGS));
			readAllWorkLogs(allWorklogsInArray);
			populateWorklogMetrics();
		} catch (final JSONException e) {
		}
	}

	private void populateWorklogMetrics() {
		updateWorklogMetricsForSubtasks();
		updateWorklogMetricsForLinkedIssues();
	}

	public void displayWorklogMetrics() {
		response.displayWorklogMetrics();
	}

	private void updateWorklogMetricsForSubtasks() {
		if (iTrac.isSubtasksAvailable()) {
			iTrac.getAllSubtasks().stream().forEach(p -> {
				iterateForAllChildrens(p);
			});
		}
	}

	private void updateWorklogMetricsForLinkedIssues() {
		if (iTrac.isLinkedItracsAvaiable()) {
			iTrac.getLinkedIssues().stream().forEach(p -> {
				iterateForAllChildrens(p);
			});
		}
	}

	private void iterateForAllChildrens(com.sag.jira.core.component.iTrac p) {
		this.iTrac = p;
		try {
			p.getWorklogHandler();
		} catch (JSONException e) {
		}
	}

//	private void displayAllLogWorks() {
//		if (allWorkLogsForOneItrac != null) {
//			for (final Worklog worklogs : allWorkLogsForOneItrac) {
//				log.info("[" + worklogs.getCreateDate() + "] " + worklogs.getAlias() + " spent <"
//						+ findTotalTime(Integer.parseInt(worklogs.getTimeSpentSeconds())) + "> with comment [***"
//						+ worklogs.getComment() + "]");
//			}
//		}
//	}

	private int getTotalTimeSpentForItrac() {
		int timeInSeconds = 0;
		if (allWorkLogsForOneItrac != null) {
			for (final Worklog worklogs : allWorkLogsForOneItrac) {
				timeInSeconds += Integer.parseInt(worklogs.getTimeSpentSeconds());
			}
		}
		return timeInSeconds;
	}

	private int getTotalWorklogCountForOneItrac() {
		return Integer.parseInt(fetchAndValidateResponse(JiraRestConfig.Worklog.TOTAL));
	}

	private void readAllWorkLogs(final JSONArray allWorklogsInArray) {
		if (allWorklogsInArray != null) {
			allWorkLogsForOneItrac = new ArrayList<>();
			for (int i = 0; i < allWorklogsInArray.length(); i++) {
				try {
					final Worklog worklog = new Worklog();
					final JSONObject worklogJson = allWorklogsInArray.getJSONObject(i);
					worklog.setComment(worklogJson.optString(JiraRestConfig.Worklog.COMMENT));
					worklog.setTimeSpent(worklogJson.optString(JiraRestConfig.Worklog.TIMESPENT));
					worklog.setTimeSpentSeconds(worklogJson.optString(JiraRestConfig.Worklog.TIME_SPENT_SECS));
					worklog.setCreateDate(worklogJson.optString(JiraRestConfig.Worklog.CREATED));
					final JSONObject updateAuthor = new JSONObject(
							worklogJson.optString(JiraRestConfig.Worklog.UPDATE_AUTHOR));
					worklog.setAlias(updateAuthor.optString(JiraRestConfig.Common.NAME));

					allWorkLogsForOneItrac.add(worklog);
				} catch (final JSONException e) {
				}
			}

			// populate response
			response.setItrac(iTrac).setTotalTimeSpent(getTotalTimeSpentForItrac())
					.setWorklogCount(getTotalWorklogCountForOneItrac()).addWorklogs(allWorkLogsForOneItrac).build();
		}
	}

	public WorklogResponse getResponseForItrac(String itracId) {
		return response.getResponseForItrac(itracId);
	}

	public String getTotalTimeSpent() {
		return response.getTotalTimeSpent();
	}

	public int getTotalWorklogCount() {
		return response.getTotalWorklogCount();
	}

}
