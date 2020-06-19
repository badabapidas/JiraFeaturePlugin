package com.sag.jira.core.obj;

import java.util.HashMap;
import java.util.Map;

import com.sag.jira.util.JiraRestConfig;

public class TraceLinks {

	private static Map<String, TestCase> testcases = new HashMap();
	private static int automatedTestCaseCount = 0;

	public int getTotalTestCaseCount() {
		return testcases.size();
	}

	public Map<String, TestCase> getTestcases() {
		return testcases;
	}

	public void addTestcases(TestCase testcase) {
		testcases.put(testcase.getTestcaseId(), testcase);
		if (testcase.isAutomated()) {
			automatedTestCaseCount++;
		}
	}

	public boolean isTestCaseAlreadyAdded(String tcid) {
		if (testcases.containsKey(tcid))
			return true;
		return false;
	}

	public int getAutomatedTestCaseCount() {
		return automatedTestCaseCount;
	}

	public class TestCase {
		private String testcase_id = JiraRestConfig.Common.EMPTY;
		private String testlink_id = JiraRestConfig.Common.EMPTY;
		private String issueId = JiraRestConfig.Common.EMPTY;
		private String key = JiraRestConfig.Common.EMPTY;
		private String name = JiraRestConfig.Common.EMPTY;
		private String projectId = JiraRestConfig.Common.EMPTY;
		private String status = JiraRestConfig.Common.EMPTY;
		private String type = JiraRestConfig.Common.EMPTY;

		private static final String STATUS_AUTOMATED = "Automated";

		public boolean isAutomated() {
			return this.status.equalsIgnoreCase(STATUS_AUTOMATED);
		}

		public String getTestcaseId() {
			return testcase_id;
		}

		public void setTestcaseId(String testcase_id) {
			this.testcase_id = testcase_id;
		}

		public String getTestlinkId() {
			return testlink_id;
		}

		public void setTestlinkId(String testlink_id) {
			this.testlink_id = testlink_id;
		}

		public String getIssueId() {
			return issueId;
		}

		public void setIssueId(String issueId) {
			this.issueId = issueId;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getProjectId() {
			return projectId;
		}

		public void setProjectId(String projectId) {
			this.projectId = projectId;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

	}

}
