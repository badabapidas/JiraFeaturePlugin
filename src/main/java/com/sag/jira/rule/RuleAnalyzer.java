package com.sag.jira.rule;

import java.io.IOException;
import java.text.ParseException;
import java.util.Set;

import org.codehaus.jettison.json.JSONException;

import com.sag.jira.core.component.SearchJQL;
import com.sag.jira.core.component.iTrac;
import com.sag.jira.exception.ITracNotFoundException;

public abstract class RuleAnalyzer {
	private SearchJQL searchQLEngine;

	public abstract String executeRuleAndGenerateHtmlReport() throws JSONException, ParseException, IOException;

	protected abstract String executeRuleAndGetHtmlReportString() throws JSONException, ParseException, IOException;

	public static class Rules {
		public static final String RULE_ITRAC_DEV_METRICS = "Itrac Developmnet Metrics";
	}

	protected Set<iTrac> executeQueryAndGetResults(final String searchJql)
			throws JSONException, ITracNotFoundException {
		searchQLEngine = new SearchJQL(searchJql);
		return searchQLEngine.getSearchResults();
	}

	protected boolean isSearchIsGoingOn() {
		return searchQLEngine.isSearchOnGoing();
	}

}
