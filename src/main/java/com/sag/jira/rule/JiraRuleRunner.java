package com.sag.jira.rule;

import java.io.IOException;
import java.text.ParseException;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JiraRuleRunner {
	protected final Logger log = LoggerFactory.getLogger(getClass());

	public JiraRuleRunner() {
	}

	public void executeRules() throws JSONException, ParseException, IOException {
		executeItracMetricsRule();
	}

	private void executeItracMetricsRule() throws JSONException, ParseException, IOException {
		String itracKey = "UHM-1620";
		log.info("\n************* Runnings itrac Effort Metrics for " + itracKey);
		ItracReportSummaryAnalyzer itracSummery = new ItracReportSummaryAnalyzer(itracKey);
		itracSummery.executeRuleAndGenerateHtmlReport();
	}

	public static void main(String[] args) throws JSONException, ParseException, IOException {
		new JiraRuleRunner().executeItracMetricsRule();
	}
}
