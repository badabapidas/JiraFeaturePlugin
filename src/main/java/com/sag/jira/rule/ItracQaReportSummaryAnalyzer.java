package com.sag.jira.rule;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sag.jira.core.component.Traceability;
import com.sag.jira.core.component.iTrac;
import com.sag.jira.exception.ITracNotFoundException;
import com.sag.jira.rule.report.ItracQaMetricsReportBuilder;
import com.sag.jira.rule.report.ReportBuilder;
import com.sag.jira.util.JiraRestConfig;

public class ItracQaReportSummaryAnalyzer extends RuleAnalyzer {
	private ItracQaMetricsReportBuilder report = ReportBuilder.getItracQaMetricBuilder();
	private iTrac itrac = null;
	private Traceability traceabilityHandler;
	private Set<iTrac> allDefects = new HashSet<>();
	private Set<iTrac> regressions = new HashSet<>();
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private int totalDefectCounts = 0;
	private int totalRegressionDefectCounts = 0;

	public ItracQaReportSummaryAnalyzer(String itracKey, String defect_label, String regression_label)
			throws ITracNotFoundException, JSONException {
		itrac = new iTrac(itracKey, true);
		traceabilityHandler = itrac.getTraceabilityHandler();

		Set<iTrac> defects = new HashSet<>();
		defects.addAll(executeQueryAndGetResults("labels in (" + defect_label + ")"));
		totalDefectCounts = defects.size();

		Set<iTrac> regressionDefects = new HashSet<>();
		regressionDefects.addAll(executeQueryAndGetResults("labels in (" + regression_label + ")"));
		totalRegressionDefectCounts = regressionDefects.size();

		allDefects.addAll(defects);
		allDefects.addAll(regressionDefects);

		logger.debug("@@@@@@@@ Defect count: " + totalDefectCounts);
		logger.debug("@@@@@@@@ Regression count:" + totalRegressionDefectCounts);
		logger.debug("@@@@@@@@ All Defect count: " + allDefects.size());
	}

	@Override
	public String executeRuleAndGenerateHtmlReport() throws JSONException, ParseException, IOException {
		report.setItrackey(itrac);
		final String htmlReportContent = executeRuleAndGetHtmlReportString();
		ReportBuilder.writeToFile(htmlReportContent,
				JiraRestConfig.OUTPUT_METRICS + itrac.getKey() + "_QA_Metrics_Report.html");
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
		int totalTestCount = traceabilityHandler.getTotalTestCaseCount();
		int automatedCount = traceabilityHandler.getAutomatedTestCaseCount();
		report.addSummary(totalTestCount, automatedCount, totalDefectCounts, totalRegressionDefectCounts);
	}

	private void updateRowData() throws JSONException {
		updateMetrics(allDefects);
		updateMetrics(regressions);
	}

	private void updateMetrics(Set<iTrac> itracs) throws JSONException {

		for (Iterator iterator = itracs.iterator(); iterator.hasNext();) {
			iTrac fetched_itrac = (iTrac) iterator.next();
			report.addRow(fetched_itrac.getKey(), fetched_itrac.getSummary(),
					fetched_itrac.getTypeHander().getIssueType(), fetched_itrac.getLabelHandler().getLabels(),
					fetched_itrac.getComponentsHandler().getComponents(),
					fetched_itrac.getSeverityHandler().getStatus(),
					fetched_itrac.getWorklogHandler().getResponseForItrac(fetched_itrac.getKey()).getTotalTimeSpent(),
					fetched_itrac.getStatusHandler().getStatus());
		}
	}
}
