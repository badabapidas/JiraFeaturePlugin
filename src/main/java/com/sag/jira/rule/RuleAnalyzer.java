package com.sag.jira.rule;

import java.io.IOException;
import java.text.ParseException;

import org.codehaus.jettison.json.JSONException;

public abstract class RuleAnalyzer {

	public abstract String executeRuleAndGenerateHtmlReport() throws JSONException, ParseException, IOException;

	protected abstract String executeRuleAndGetHtmlReportString() throws JSONException, ParseException, IOException;

	public static class Rules {
		public static final String RULE_ITRAC_DEV_METRICS = "Itrac Developmnet Metrics";
	}
}
