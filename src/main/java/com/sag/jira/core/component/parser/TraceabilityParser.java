package com.sag.jira.core.component.parser;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sag.jira.core.obj.TraceLinks;
import com.sag.jira.core.obj.TraceLinks.TestCase;
import com.sag.jira.util.JiraRestConfig;
import com.sun.jersey.api.client.ClientResponse;

public class TraceabilityParser extends JiraParser {
	private TraceLinks traceLink = new TraceLinks();
	private String itracKey;
	private static final Logger logger = LoggerFactory.getLogger(TraceabilityParser.class);

	public TraceabilityParser(ClientResponse response, String itracKey) throws JSONException {
		this.itracKey = itracKey;
		parseResponse(response);
		findAllTestCases();
	}

	private void findAllTestCases() {
		if (isValidJsonObject(jsonObject)) {
			int totalCount = jsonObject.optInt(JiraRestConfig.TraceLinks.TOTALCOUNT);
			if (totalCount > 0) {
				JSONArray traceLinks = jsonObject.optJSONArray(JiraRestConfig.TraceLinks.TRACELINKS);
				if (isValidJsonArray(traceLinks)) {
					logger.debug("[TEST] Adding test cases for " + itracKey + " found total test cases "
							+ traceLinks.length());
					for (int i = 0; i < traceLinks.length(); i++) {
						JSONObject testcase = traceLinks.optJSONObject(i);
						addTestcase(testcase);
					}
				}
			}
		}
	}

	private void addTestcase(JSONObject tracelinkObj) {
		if (isValidJsonObject(tracelinkObj)) {
			TestCase testcase = traceLink.new TestCase();
			JSONObject testcaseObj = tracelinkObj.optJSONObject(JiraRestConfig.TraceLinks.TESTCASE);
			String tcid = testcaseObj.optString(JiraRestConfig.Common.ID);
			if (!traceLink.isTestCaseAlreadyAdded(tcid)) {
				if (isValidJsonObject(testcaseObj)) {
					testcase.setTestcaseId(tcid);
					testcase.setKey(testcaseObj.optString(JiraRestConfig.TraceLinks.KEY));
					testcase.setName(testcaseObj.optString(JiraRestConfig.Common.NAME));
					testcase.setProjectId(testcaseObj.optString(JiraRestConfig.TraceLinks.PROJECT_ID));
				}
				JSONObject statusobj = testcaseObj.optJSONObject(JiraRestConfig.TraceLinks.STATUS);
				if (isValidJsonObject(statusobj)) {
					testcase.setStatus(statusobj.optString(JiraRestConfig.Common.NAME));
				}
				JSONObject typeObj = tracelinkObj.optJSONObject(JiraRestConfig.TraceLinks.TYPE);
				if (isValidJsonObject(typeObj)) {
					testcase.setType(typeObj.optString(JiraRestConfig.Common.NAME));
				}
				testcase.setTestlinkId(tracelinkObj.optString(JiraRestConfig.Common.ID));
				testcase.setIssueId(tracelinkObj.optString(JiraRestConfig.TraceLinks.ISSUE_ID));
				traceLink.addTestcases(testcase);
			}
		}
	}

	public int getTotalTestCaseCount() {
		return traceLink.getTotalTestCaseCount();
	}

	public int getAutomatedTestCaseCount() {
		return traceLink.getAutomatedTestCaseCount();
	}

}
