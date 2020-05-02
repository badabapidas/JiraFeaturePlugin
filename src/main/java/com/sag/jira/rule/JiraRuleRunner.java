package com.sag.jira.rule;

import java.io.IOException;
import java.text.ParseException;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sag.jira.exception.ITracNotFoundException;

public class JiraRuleRunner {
	protected final Logger log = LoggerFactory.getLogger(getClass());

	public JiraRuleRunner() {
	}

	public void executeRules(String itracKey)
			throws JSONException, ParseException, IOException, ITracNotFoundException {
		executeItracMetricsRule(itracKey);
	}

	private void executeItracMetricsRule(String itracKey)
			throws JSONException, ParseException, IOException, ITracNotFoundException {
//		String itracKey = "UHM-674";
//		log.info("\n************* Runnings itrac Effort Metrics for " + itracKey);
		ItracReportSummaryAnalyzer itracSummery = new ItracReportSummaryAnalyzer(itracKey);
		itracSummery.executeRuleAndGenerateHtmlReport();
	}

//	public static void main(String[] args) throws JSONException, ParseException, IOException, ITracNotFoundException {
//		new JiraRuleRunner().executeItracMetricsRule();
//	}
}
