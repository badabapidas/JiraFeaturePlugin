package com.sag.jira.rule;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jettison.json.JSONException;

import com.sag.jira.core.component.Commit;
import com.sag.jira.core.component.iTrac;
import com.sag.jira.core.component.issue.IssueWorklog;
import com.sag.jira.core.component.issue.TimeTracker;
import com.sag.jira.core.response.ItracComitResponseBuilder.CommitResponse;
import com.sag.jira.core.response.ItracWorkLogResponseBuilder.WorklogResponse;
import com.sag.jira.exception.ITracNotFoundException;
import com.sag.jira.rule.report.ItracMetricsReportBuilder;
import com.sag.jira.rule.report.ReportBuilder;
import com.sag.jira.util.JiraRestConfig;

public class ItracReportSummaryAnalyzer extends RuleAnalyzer {

	private ItracMetricsReportBuilder report = ReportBuilder.getItracMetricBuilder();
	private iTrac itrac = null;
	private Map<iTrac, CommitResponse> allCommitResponses = new HashMap<>();
	private IssueWorklog issueWorklogHandler = null;
	private Commit commitHandler = null;

	public ItracReportSummaryAnalyzer(String itracKey) throws JSONException, ITracNotFoundException {
		itrac = new iTrac(itracKey);
		commitHandler = itrac.getCommitHandler();
		allCommitResponses = commitHandler.getAllResponses();
		issueWorklogHandler = itrac.getWorklogHandler();
	}

	@Override
	public String executeRuleAndGenerateHtmlReport() throws JSONException, ParseException, IOException {
		report.setItrackey(itrac);
		final String htmlReportContent = executeRuleAndGetHtmlReportString();
		ReportBuilder.writeToFile(htmlReportContent,
				JiraRestConfig.OUTPUT_METRICS + itrac.getKey() + "_Metrics_Summary.html");
		return report.getBuilder().toString();
	}

	@Override
	protected String executeRuleAndGetHtmlReportString() throws JSONException, ParseException, IOException {
		addMetricsSummary();
		report.addHeader();
		updateRowData();
		report.addFooter();
		return report.getBuilder().toString();
	}

	private void addMetricsSummary() throws JSONException {
		String totalTimeSpent = issueWorklogHandler.getTotalTimeSpent();
		int totalLinesAdded = commitHandler.getTotalNoOfLinesAdded();
		int totalLinesRemoved = commitHandler.getTotalNoOfLinesRemoved();
		int totalReviewComments = commitHandler.getTotalReviewCommentCounts();
		int worklogCount = issueWorklogHandler.getTotalWorklogCount();

		report.addSummary(totalTimeSpent, totalLinesAdded, totalLinesRemoved, totalReviewComments, worklogCount);
	}

	private void updateRowData() throws JSONException {
		for (Entry<iTrac, CommitResponse> commitResponseMap : allCommitResponses.entrySet()) {
			iTrac fetchedItrac = commitResponseMap.getKey();
			CommitResponse commitResponse = commitResponseMap.getValue();
			TimeTracker timeTracker = fetchedItrac.getTimeTrackerHandler();
			WorklogResponse worklogResponse = fetchedItrac.getWorklogHandler()
					.getResponseForItrac(fetchedItrac.getKey());

			report.addRow(fetchedItrac.getKey(), fetchedItrac.getSummary(), fetchedItrac.getTypeHander().getIssueType(),
					fetchedItrac.getComponentsHandler().getComponents(), commitResponse.getTotalNoOfLinesAdded(),
					commitResponse.getTotalNoOfLinesRemoved(), commitResponse.getTotalReviewCommentCounts(),
					timeTracker.getOriginalEstimate(), worklogResponse.getTotalWorklogCount(),
					worklogResponse.getTotalTimeSpent(), worklogResponse.getWhoWorkLogged(),
					fetchedItrac.getStatusHandler().getStatus());
		}
	}
}
