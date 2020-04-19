package com.sag.jira.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.sag.jira.core.JiraRestCore;

public class PropertyUtil {
	private static Properties prop = new Properties();

	private final static String jiraUrl = "https://itrac.eur.ad.sag";
	private static String username = null;
	private static String password = null;
	private static String projectName = null;
	private static String projectId = null;

	private static String jiraIssueId = null;
	private static String jiraUserNameForUsage = null;
	static {
		initialize();
	}

	public static void initialize() {

		InputStream input = null;
		try {

			InputStream propStream = JiraRestCore.class.getResourceAsStream("/config.properties");
			prop.load(propStream);
			username = fetchValue("jira.username", "itracoptimize");
			password = fetchValue("jira.password", "optitrac");
			projectName = fetchValue("jira.project.name", "webMethods.io End-To-End Monitoring");
			projectId = fetchValue("jira.project.id", "UHM");

			jiraIssueId = prop.getProperty("jira.issue.id");
			jiraUserNameForUsage = prop.getProperty("jira.user.usage");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	private static String fetchValue(String key, String defaultValue) {
		if (System.getProperty(key.trim()) != null) {
			return System.getProperty(key);
		}
		if (prop.getProperty(key) != null && !prop.getProperty(key).isEmpty()) {
			return prop.getProperty(key);
		}
		return defaultValue.trim();
	}

	public static String getJiraUsername() {
		return username;
	}

	public static String getJiraProjectId() {
		return projectId;
	}

	public static String getJiraIssueId() {
		return jiraIssueId;
	}

	public static String getJiraUserNameForUsage() {
		return jiraUserNameForUsage;
	}

	public static String getJiraUserPassword() {
		return password;
	}

	public static String getProjectName() {
		return projectName;
	}

	public static String getJiraurl() {
		return jiraUrl;
	}

}