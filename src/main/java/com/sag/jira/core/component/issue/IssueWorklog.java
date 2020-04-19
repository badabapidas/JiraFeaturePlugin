package com.sag.jira.core.component.issue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sag.jira.core.obj.Worklog;
import com.sag.jira.util.JiraRestConfig;
import com.sag.jira.util.JiraUtils;

public class IssueWorklog extends IssueRoot {
	private List<Worklog> allWorkLogs;

	public IssueWorklog(final String issueId, final JSONObject jsonObjectForIssue) throws JSONException {
		try {
			initialized(issueId, jsonObjectForIssue, JiraRestConfig.ISSUE_WORKLOG_REST_PATH);
			final JSONArray allWorklogsInArray = jsonObject.optJSONArray((JiraRestConfig.Worklog.WORKLOGS));
			readAllWorkLogs(allWorklogsInArray);
		} catch (final JSONException e) {
			throw new JSONException("Worklog not found!");
		}
	}

	public void displayAllLogWorks() throws NumberFormatException, ParseException {
		if (allWorkLogs != null) {
			for (final Worklog worklogs : allWorkLogs) {
				log.info("[" + worklogs.getCreateDate() + "] " + worklogs.getAlias() + " spent <"
						+ findTotalTime(Integer.parseInt(worklogs.getTimeSpentSeconds())) + "> with comment [***"
						+ worklogs.getComment() + "]");
			}
		}
	}

	private String findTotalTime(final int seconds) {

		final int numberOfDays = seconds / 28800;
		final int numberOfHours = (seconds % 28800) / 3600;
		final int numberOfMinutes = ((seconds % 28800) % 3600) / 60;
		final int numberOfSeconds = ((seconds % 28800) % 3600) % 60;

		return formateSpentTime(numberOfDays, numberOfHours, numberOfMinutes, numberOfSeconds);
	}

	private String formateSpentTime(final int nod, final int noh, final int nom, final int nos) {
		final StringBuilder totaltime = new StringBuilder();

		return totaltime.append(String.valueOf(nod)).append("d:").append(noh).append("h:").append(nom).append("m:")
				.append(nos).append("s").toString();

	}

	public List<Worklog> getAllLogWorks() {
		return allWorkLogs;
	}

	public String getTotalTimeSpentOnDay(final String user, final String date) {
		if (!JiraUtils.isValidDateFormate(date)) {
			return "Date format not supported";
		}

		if (allWorkLogs != null) {
			int timeInSeconds = 0;
			for (final Worklog worklogs : allWorkLogs) {
				if (JiraUtils.isDateMatched(worklogs.getCreateDate(), date)
						&& worklogs.getAlias().equalsIgnoreCase(user)) {
					timeInSeconds += Integer.parseInt(worklogs.getTimeSpentSeconds());
				}
			}
			return findTotalTime(timeInSeconds);

		}
		return JiraRestConfig.DEFAULT_TIME_FORMAT;
	}

	public int getTotalWorklogCount() {
		return Integer.parseInt(fetchAndValidateResponse(JiraRestConfig.Worklog.TOTAL));
	}

	public Map<String, String> getWhoWorkLogged() {
		final Map<String, Integer> totalLogworked = getWhoWorkLoggedIndividually();
		final Map<String, String> spentTimeFormat = new HashMap<>();
		final Iterator it = totalLogworked.entrySet().iterator();
		while (it.hasNext()) {
			final Map.Entry pair = (Map.Entry) it.next();
			spentTimeFormat.put(pair.getKey().toString(), findTotalTime((Integer) pair.getValue()));
			it.remove();
		}
		return spentTimeFormat;
	}

	private Map<String, Integer> getWhoWorkLoggedIndividually() {
		final Map<String, Integer> totalLogworked = new HashMap<>();
		for (final Worklog worklogs : allWorkLogs) {
			if (totalLogworked.containsKey(worklogs.getAlias())) {
				final Integer alreadySpentTimes = totalLogworked.get(worklogs.getAlias());
				totalLogworked.put(worklogs.getAlias(),
						alreadySpentTimes + Integer.parseInt(worklogs.getTimeSpentSeconds()));
			} else {
				totalLogworked.put(worklogs.getAlias(), Integer.parseInt(worklogs.getTimeSpentSeconds()));
			}

		}
		return totalLogworked;
	}

	public String getWorkLogComment(final String date, final String user) {
		if (!JiraUtils.isValidDateFormate(date)) {
			return "Date not supported";
		}
		if (allWorkLogs != null) {
			for (final Worklog worklogs : allWorkLogs) {
				if (JiraUtils.isDateMatched(worklogs.getCreateDate(), date)
						&& worklogs.getAlias().equalsIgnoreCase(user)) {
					return worklogs.getComment();
				}
			}
		}
		return "";
	}

	public String getWorkLogFor(final String alias) {
		int seconds = 0;
		final Map<String, Integer> whoWorkLogged = getWhoWorkLoggedIndividually();
		if (whoWorkLogged.containsKey(alias)) {
			seconds = whoWorkLogged.get(alias);
		}
		return findTotalTime(seconds);
	}

	public boolean isWorkLogDone() {
		return getTotalWorklogCount() > 0 ? true : false;
	}

	/**
	 * 
	 * @param date (only supported 2017-06-07) -> 07 june,2017
	 * @param user
	 * @return
	 */
	public boolean isWorklogDoneForDate(final String date, final String user) {
		if (!JiraUtils.isValidDateFormate(date)) {
			return false;
		}
		if (allWorkLogs != null) {
			for (final Worklog worklogs : allWorkLogs) {
				if (JiraUtils.isDateMatched(worklogs.getCreateDate(), date)
						&& worklogs.getAlias().equalsIgnoreCase(user)) {
					return true;
				}
			}
		}
		return false;
	}

	private void readAllWorkLogs(final JSONArray allWorklogsInArray) {
		if (allWorklogsInArray != null) {
			allWorkLogs = new ArrayList<>();
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

					allWorkLogs.add(worklog);
				} catch (final JSONException e) {
				}
			}
		}
	}

	public String totalTimeSpent() {
		int timeSpentInSeconds = 0;
		for (final Worklog worklogHandlers : allWorkLogs) {
			timeSpentInSeconds += Integer.parseInt(worklogHandlers.getTimeSpentSeconds());
		}
		return findTotalTime(timeSpentInSeconds);
	}

}
