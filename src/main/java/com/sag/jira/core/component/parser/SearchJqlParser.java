package com.sag.jira.core.component.parser;

import java.util.HashSet;
import java.util.Set;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sag.jira.core.component.Issue;
import com.sag.jira.util.JiraRestConfig;
import com.sun.jersey.api.client.ClientResponse;

public class SearchJqlParser extends JiraParser {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	private static Set<Issue> totalIssues = new HashSet<Issue>();
	private static boolean isReading = false;

	public SearchJqlParser(ClientResponse response, boolean donotClear) throws JSONException {
		if (!donotClear) {
			clearCache();
		}
		parseResponse(response);
		readJql();
	}

	private void readJql() {
		if (jsonObject != null && !isReading) {
			isReading = true;
			JSONArray issueArrays = jsonObject.optJSONArray(JiraRestConfig.SearchJQL.ISSUES);
			for (int i = 0; i < issueArrays.length(); i++) {
				JSONObject issueJson = issueArrays.optJSONObject(i);
				Issue issue = new Issue(issueJson.optString(JiraRestConfig.Issue.KEY));
				synchronized (issue) {
					totalIssues.add(issue);
					isReading = false;
				}
			}
		}
	}

	public Set<Issue> getAllIssues() {
		return totalIssues;
	}

	public Set<String> getAllItracsById() {
		Set<String> itracIds = new HashSet<String>();
		if (totalIssues != null) {
			for (Issue issue : totalIssues) {
				itracIds.add(issue.getKey());
			}
		}
		log.info("Total issues found: " + itracIds.size());
		return itracIds;
	}

	public int getAllIssuesSize() {
		return totalIssues.size();
	}

	public String getTotal() {
		return fetchAndValidateResponse(JiraRestConfig.SearchJQL.TOTAL);
	}

	public String getMaxSearchCount() {
		return fetchAndValidateResponse(JiraRestConfig.SearchJQL.MAXRESULTS);
	}

	public void clearCache() {
		totalIssues.clear();
	}

}
