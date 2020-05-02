package com.sag.jira.core.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sag.jira.core.component.iTrac;
import com.sag.jira.core.obj.Worklog;
import com.sag.jira.util.JiraRestConfig;
import com.sag.jira.util.JiraUtils;

public class ItracWorkLogResponseBuilder {
	private static final Logger logger = LoggerFactory.getLogger(ItracWorkLogResponseBuilder.class);
	protected static ItracWorkLogResponseBuilder INSTANCE = null;
	protected WorklogResponse response = new WorklogResponse();
	private iTrac itrac;
	protected static Map<iTrac, WorklogResponse> worklogMetrics = new HashMap();
	protected static int totalTimeSpent = 0;
	protected static int totalWorklogCount = 0;

	public ItracWorkLogResponseBuilder setWorklogCount(int worklogCount) {
		totalWorklogCount += worklogCount;
		response.setWorklogCount(worklogCount);
		return this;
	}

	public ItracWorkLogResponseBuilder addWorklogs(List<Worklog> allWorkLogsForOneItrac) {
		response.setAllWorkLogsForOneItrac(allWorkLogsForOneItrac);
		return this;
	}

	public ItracWorkLogResponseBuilder setTotalTimeSpent(int totalTimeSpent) {
		this.totalTimeSpent += totalTimeSpent;
		response.setTotalTimeSpent(response.findTotalTime(totalTimeSpent));
		return this;
	}

	public ItracWorkLogResponseBuilder setWhoWorkLogged(Map<String, String> whoWorkLogged) {
		response.setWhoWorkLogged(whoWorkLogged);
		return this;
	}

	public void displayWorklogMetrics() {
		for (Map.Entry<iTrac, WorklogResponse> respone : worklogMetrics.entrySet()) {
			logger.debug(
					"Itrac: " + respone.getKey().getKey() + " - Timespent:" + respone.getValue().getTotalTimeSpent());
		}
	}

	public void build() {
		if (response.getWorklogCount() > 0) {
			response.setStatus(response.responseStatus.AVAILABLE);
			response.setWhoWorkLogged(response.getWhoWorkLogged());
			worklogMetrics.put(itrac, response);
			response = new WorklogResponse();
		}
	}

	public WorklogResponse getResponseForItrac(String itracKey) {
		for (Map.Entry<iTrac, WorklogResponse> respone : worklogMetrics.entrySet()) {
			if (respone.getKey().getKey().equalsIgnoreCase(itracKey)) {
				return respone.getValue();
			}
		}
		return new WorklogResponse();
	}

	public Map<iTrac, WorklogResponse> getAllResponses() {
		return worklogMetrics;
	}

	public String getTotalTimeSpent() {
		return response.findTotalTime(totalTimeSpent);
	}

	public int getTotalWorklogCount() {
		return totalWorklogCount;
	}

	public static ItracWorkLogResponseBuilder getInstance() {
		if (INSTANCE == null) {
			return new ItracWorkLogResponseBuilder();
		}
		return INSTANCE;
	}

	public ItracWorkLogResponseBuilder setItrac(iTrac iTrac) {
		this.itrac = iTrac;
		return this;
	}

	public static class WorklogResponse {
		enum STATUS {
			AVAILABLE, NOT_AVAILABLE
		}

		protected STATUS responseStatus = STATUS.NOT_AVAILABLE;
		protected String totalTimeSpent = JiraRestConfig.Common.EMPTY;
		protected int worklogCount = 0;
		protected Map<String, String> whoWorkLogged = new HashMap<>();
		protected List<Worklog> allWorkLogsForOneItrac = new ArrayList<>();

		public String getTotalTimeSpent() {
			return totalTimeSpent;
		}

		public boolean isWorkLogDone() {
			return getTotalWorklogCount() > 0 ? true : false;
		}

		public int getTotalWorklogCount() {
			return allWorkLogsForOneItrac.size();
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
			if (allWorkLogsForOneItrac != null) {
				for (final Worklog worklogs : allWorkLogsForOneItrac) {
					if (JiraUtils.isDateMatched(worklogs.getCreateDate(), date)
							&& worklogs.getAlias().equalsIgnoreCase(user)) {
						return true;
					}
				}
			}
			return false;
		}

		public String getWorkLogComment(final String date, final String user) {
			if (!JiraUtils.isValidDateFormate(date)) {
				return "Date not supported";
			}
			if (allWorkLogsForOneItrac != null) {
				for (final Worklog worklogs : allWorkLogsForOneItrac) {
					if (JiraUtils.isDateMatched(worklogs.getCreateDate(), date)
							&& worklogs.getAlias().equalsIgnoreCase(user)) {
						return worklogs.getComment();
					}
				}
			}
			return JiraRestConfig.Common.EMPTY;
		}

		public String getWorkLogFor(final String alias) {
			int seconds = 0;
			final Map<String, Integer> whoWorkLogged = getWhoWorkLoggedIndividually();
			if (whoWorkLogged.containsKey(alias)) {
				seconds = whoWorkLogged.get(alias);
			}
			return findTotalTime(seconds);
		}

		public String getTotalTimeSpentOnDay(final String user, final String date) {
			if (!JiraUtils.isValidDateFormate(date)) {
				return "Date format not supported";
			}
			if (allWorkLogsForOneItrac != null) {
				int timeInSeconds = 0;
				for (final Worklog worklogs : allWorkLogsForOneItrac) {
					if (JiraUtils.isDateMatched(worklogs.getCreateDate(), date)
							&& worklogs.getAlias().equalsIgnoreCase(user)) {
						timeInSeconds += Integer.parseInt(worklogs.getTimeSpentSeconds());
					}
				}
				return findTotalTime(timeInSeconds);

			}
			return JiraRestConfig.DEFAULT_TIME_FORMAT;
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
			for (final Worklog worklogs : allWorkLogsForOneItrac) {
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

		public STATUS getStatus() {
			return responseStatus;
		}

		public void setStatus(STATUS default_status) {
			this.responseStatus = default_status;
		}

		public void setTotalTimeSpent(String totalTimeSpent) {
			this.totalTimeSpent = totalTimeSpent;
		}

		public int getWorklogCount() {
			return worklogCount;
		}

		public void setWorklogCount(int worklogCount) {
			this.worklogCount = worklogCount;
		}

		public void setWhoWorkLogged(Map<String, String> whoWorkLogged) {
			this.whoWorkLogged = whoWorkLogged;
		}

		public void setAllWorkLogsForOneItrac(List<Worklog> allWorkLogsForOneItrac) {
			this.allWorkLogsForOneItrac = allWorkLogsForOneItrac;
		}

	}

}
