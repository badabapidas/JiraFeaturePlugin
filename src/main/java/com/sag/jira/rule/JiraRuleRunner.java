package com.sag.jira.rule;

import java.io.IOException;
import java.text.ParseException;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sag.jira.exception.ITracNotFoundException;
import com.sag.jira.util.JiraRestConfig;

public class JiraRuleRunner {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	private String itracKey = JiraRestConfig.Common.EMPTY;
	private boolean enableQaMetrics = true;
	private boolean enableDevMetrics = false;
	private String defect_label = itracKey + "-Testing";
	private String regression_label = itracKey + "_Regression";

	public JiraRuleRunner() {
	}

	public static void main(String[] args) throws JSONException, ParseException, IOException, ITracNotFoundException {
		new JiraRuleRunner().executeRules();
	}

	public void executeRules() throws JSONException, ParseException, IOException, ITracNotFoundException {
		executeItracDevMetricsRule(itracKey);
		executeItracQAMetricsRule(itracKey);
	}

	private void executeItracDevMetricsRule(String itracKey)
			throws JSONException, ParseException, IOException, ITracNotFoundException {
		if (enableDevMetrics) {
			ItracDevReportSummaryAnalyzer reportAnalyzer = new ItracDevReportSummaryAnalyzer(itracKey);
			reportAnalyzer.executeRuleAndGenerateHtmlReport();
		}
	}

	private void executeItracQAMetricsRule(String itracKey)
			throws ITracNotFoundException, JSONException, ParseException, IOException {
		if (enableQaMetrics) {
			ItracQaReportSummaryAnalyzer reportAnalyzer = new ItracQaReportSummaryAnalyzer(itracKey, defect_label,
					regression_label);
			reportAnalyzer.executeRuleAndGenerateHtmlReport();
		}
	}

	public JiraRuleRunner setItracKey(String itracKey) {
		this.itracKey = itracKey;
		return this;
	}

	public JiraRuleRunner setEnableQaMetrics(boolean enableQaMetrics) {
		this.enableQaMetrics = enableQaMetrics;
		return this;
	}

	public JiraRuleRunner setDefect_label(String defect_label) {
		this.defect_label = defect_label;
		return this;
	}

	public JiraRuleRunner setRegression_label(String regression_label) {
		this.regression_label = regression_label;
		return this;
	}

	public JiraRuleRunner setEnableDevMetrics(boolean enableDevMetrics) {
		this.enableDevMetrics = enableDevMetrics;
		return this;
	}

}
