package com.sag.jira.rule.report;

import java.util.Map;

import com.sag.jira.util.JiraRestConfig;

public class ItracMetricsReportBuilder extends ReportBuilder {
	private final static String TABLE_TAG_START = "<table border=\"2\" bordercolor=\"#000000\">";
	private final static String TR_TAG_CUST_START = "<tr style=\"background:lightgrey\" class=\"center\">";
	private final static String ANCHORE_CUST_TAG_START = "<a href=\"https://itrac.eur.ad.sag/browse/";
	private final static String TD_CUST_ISSUE_TD_START = "<td style=\"background-color: antiquewhite;\" class=\"center\">";
	private final static String CUSTOM_CSS = "<style>" + ".common{ border: 1px solid black; width: 200px;} "
			+ ".broad{width: 20%;}" + ".center{text-align: center;}"
			+ "#summary {font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;border-collapse: collapse;font-style: italic;}"
			+ "#summary td, #summary th { border: 1px solid black; padding-left: 15px;padding-right: 15px} #summary tr:nth-child(even){background-color: #f2f2f2;} #summary tr:hover {background-color: #ddd;}"

			+ "</style>";
	protected final static String TD_TAG_COMMON_START = "<td class=\"common\">";
	protected final static String TD_TAG_BROAD_START = "<td class=\"broad\">";
	protected final static String TD_TAG_START_CENTER = "<td class=\"center\">";
	protected final static String TD_TAG_START_WORKLOG_CENTER = "<td class=\"center\">";
	protected final static String HR_TAG = "<hr style=\"width:50%;text-align:left;margin-left:0\">";
	protected final static String BR_TAG = "<br>";
	protected final static String TD_TAG_SUMMARY_START = "<td style= \"text-align: center\";>";
	protected final static String TD_TAG_NOT_AVAIALABLE_START = "<td style=\"color: red;\"  class=\"center\">";

	protected ItracMetricsReportBuilder() {
	}

	public void addSummary(String totalTimeSpent, int totalLinesAdded, int totalLinesRemoved, int totalReviewComments,
			int worklogCount) {

		builder.append(CUSTOM_CSS);
		builder.append(H1_TAG_START).append("Effort Metrics for ").append(ANCHORE_CUST_TAG_START).append(itrac.getKey())
				.append("\">").append(itrac.getKey()).append(ANCHOR_TAG_END).append(H1_TAG_END);

		builder.append(HR_TAG);

		builder.append("<table id=\"summary\">").append(TR_TAG_START);
		builder.append(TD_TAG_START).append(BOLD_TAG_START).append("Total Time Spent").append(BOLD_TAG_END)
				.append(TD_TAG_END);
		builder.append(TD_TAG_SUMMARY_START).append(BOLD_TAG_START).append(totalTimeSpent).append(BOLD_TAG_END)
				.append(TD_TAG_END);
		builder.append(TR_TAG_END).append(TR_TAG_START);

		builder.append(TD_TAG_START).append(BOLD_TAG_START).append("Total Lines Added").append(BOLD_TAG_END)
				.append(TD_TAG_END);
		builder.append(TD_TAG_SUMMARY_START).append(BOLD_TAG_START).append(totalLinesAdded).append(BOLD_TAG_END)
				.append(TD_TAG_END);
		builder.append(TR_TAG_END).append(TR_TAG_START);

		builder.append(TD_TAG_START).append(BOLD_TAG_START).append("Total Lines Removed").append(BOLD_TAG_END)
				.append(TD_TAG_END);
		builder.append(TD_TAG_SUMMARY_START).append(BOLD_TAG_START).append(totalLinesRemoved).append(BOLD_TAG_END)
				.append(TD_TAG_END);

		builder.append(TR_TAG_END).append(TR_TAG_START);
		builder.append(TD_TAG_START).append(BOLD_TAG_START).append("Total Review Comments").append(BOLD_TAG_END)
				.append(TD_TAG_END);
		builder.append(TD_TAG_SUMMARY_START).append(BOLD_TAG_START).append(totalReviewComments).append(BOLD_TAG_END)
				.append(TD_TAG_END);

		builder.append(TR_TAG_END).append(TR_TAG_START);
		builder.append(TD_TAG_START).append(BOLD_TAG_START).append("Total Worklog Counts").append(BOLD_TAG_END)
				.append(TD_TAG_END);
		builder.append(TD_TAG_SUMMARY_START).append(BOLD_TAG_START).append(worklogCount).append(BOLD_TAG_END)
				.append(TD_TAG_END);

		builder.append(TR_TAG_END).append(TABLE_TAG_END).append(HR_TAG).append(BR_TAG);
	}

	public void addHeader() {

		builder.append(TABLE_TAG_START);
		builder.append(TR_TAG_CUST_START);
		builder.append(TD_TAG_COMMON_START).append(BOLD_TAG_START).append(HEADER_KEY_ISSUE_ID).append(BOLD_TAG_END)
				.append(TD_TAG_END);
		builder.append(TD_TAG_BROAD_START).append(BOLD_TAG_START).append(HEADER_KEY_ISSUE_SUMMARY).append(BOLD_TAG_END)
				.append(TD_TAG_END);
		builder.append(TD_TAG_COMMON_START).append(BOLD_TAG_START).append(HEADER_KEY_ISSUE_TYPE).append(BOLD_TAG_END)
				.append(TD_TAG_END);
		builder.append(TD_TAG_COMMON_START).append(BOLD_TAG_START).append(HEADER_KEY_ISSUE_COMPONENT)
				.append(BOLD_TAG_END).append(TD_TAG_END);
		builder.append(TD_TAG_COMMON_START).append(BOLD_TAG_START).append(HEADER_KEY_LINES_ADDED).append(BOLD_TAG_END)
				.append(TD_TAG_END);
		builder.append(TD_TAG_COMMON_START).append(BOLD_TAG_START).append(HEADER_KEY_LINES_REMOVED).append(BOLD_TAG_END)
				.append(TD_TAG_END);
		builder.append(TD_TAG_COMMON_START).append(BOLD_TAG_START).append(HEADER_KEY_REVIEW_COMMENTS_COUNT)
				.append(BOLD_TAG_END).append(TD_TAG_END);
		builder.append(TD_TAG_COMMON_START).append(BOLD_TAG_START).append(HEADER_KEY_ESTIMATED_TIME)
				.append(BOLD_TAG_END).append(TD_TAG_END);
		builder.append(TD_TAG_COMMON_START).append(BOLD_TAG_START).append(HEADER_KEY_WORKLOG_COUNT).append(BOLD_TAG_END)
				.append(TD_TAG_END);
		builder.append(TD_TAG_COMMON_START).append(BOLD_TAG_START).append(HEADER_KEY_TOTAL_TIME_SPENT)
				.append(BOLD_TAG_END).append(TD_TAG_END);
		builder.append(TD_TAG_COMMON_START).append(BOLD_TAG_START).append(HEADER_KEY_WHO_LOGGED).append(BOLD_TAG_END)
				.append(TD_TAG_END);
		builder.append(TD_TAG_COMMON_START).append(BOLD_TAG_START).append(HEADER_KEY_ISSUE_STATUS).append(BOLD_TAG_END)
				.append(TD_TAG_END);
		builder.append(TR_TAG_END);
	}

	public void addRow(String issueId, String isueSummary, String issueType, String components, int linesAdded,
			int linesRemoved, int reviewCommentCount, String estimatedTime, int worklogCount, String totalTimeSPent,
			Map<String, String> whoLogged, String status) {
		// start a row
		builder.append(TR_TAG_START);

		// update itrac id column
		builder.append(TD_CUST_ISSUE_TD_START).append(ANCHORE_CUST_TAG_START).append(issueId).append("\">")
				.append(issueId).append(ANCHOR_TAG_END).append(TD_TAG_END);

		builder.append(TD_TAG_START).append(isueSummary).append(TD_TAG_END);
		updateColumn(issueType, false);
		updateComponentColumn(components);
		updateColumn(linesAdded, true);
		updateColumn(linesRemoved, true);
		updateColumn(reviewCommentCount, true);
		updateEstimateColumn(estimatedTime);
		updateColumn(worklogCount, true);
		updateColumn(totalTimeSPent, false);
		updateWhoLoggedWork(whoLogged);
		updateStatusColumn(status);
		builder.append(TR_TAG_END);
	}

	private void updateComponentColumn(String components) {
		if (!components.isEmpty() && components != null) {
			builder.append(TD_TAG_START_CENTER).append(components).append(TD_TAG_END);
		} else {
			updateDataNotAvailable();
		}
	}

	private void updateWhoLoggedWork(Map<String, String> whoLogged) {
		StringBuilder result = new StringBuilder();
		boolean logWorkDone = false;
		for (Map.Entry<String, String> entry : whoLogged.entrySet()) {
			String who = entry.getKey();
			String howMuch = entry.getValue();
			result.append(who).append(" [").append(howMuch).append("]").append("\n");
			logWorkDone = true;
		}
		if (logWorkDone) {
			builder.append(TD_TAG_START_CENTER).append(result).append(TD_TAG_END);
		} else {
			updateDataNotAvailable();
		}
	}

	private void updateStatusColumn(String status) {
		if (status.equalsIgnoreCase("Closed") || status.equalsIgnoreCase("Completed") || status.equalsIgnoreCase("Done")
				|| status.equalsIgnoreCase("Implemented")) {
			builder.append("<td style=\"background-color: palegreen;\" class=\"center\">");
		} else if (status.contentEquals("In Progress") || status.contentEquals("Implementing")
				|| status.contentEquals("Open")) {
			builder.append("<td style=\"background-color: yellow;\"  class=\"center\">");
		} else {
			builder.append(TD_TAG_START_CENTER);
		}
		builder.append(status).append(TD_TAG_END);
	}

	private void updateEstimateColumn(String estimatedTime) {
		if (estimatedTime.equalsIgnoreCase(JiraRestConfig.Common.NOT_AVAILABLE)) {
			updateDataNotAvailable();
		} else {
			builder.append(TD_TAG_START_CENTER);
			builder.append(estimatedTime).append(TD_TAG_END);
		}
	}

	private void updateColumn(Object value, boolean isInteger) {
		if (isInteger) {
			int nValue = (Integer) value;
			if (nValue > 0) {
				builder.append(TD_TAG_START_CENTER).append(BOLD_TAG_START).append(value).append(BOLD_TAG_END)
						.append(TD_TAG_END);
			} else {
				builder.append(TD_TAG_START_CENTER).append(value).append(TD_TAG_END);
			}
		} else {
			String v = (String) value;
			if (v.isEmpty() || v == null) {
				updateDataNotAvailable();
			} else {
				builder.append(TD_TAG_START_CENTER).append(value).append(TD_TAG_END);
			}
		}
	}

	private void updateDataNotAvailable() {
		builder.append(TD_TAG_NOT_AVAIALABLE_START).append(BOLD_TAG_START).append(JiraRestConfig.Common.NOT_AVAILABLE)
				.append(BOLD_TAG_END).append(TD_TAG_END);
	}

	public void addFooter() {
		builder.append(TABLE_TAG_END);
	}
}
