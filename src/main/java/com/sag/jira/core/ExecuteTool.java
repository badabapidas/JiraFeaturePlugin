package com.sag.jira.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Objects;

import org.codehaus.jettison.json.JSONException;

import com.sag.jira.core.component.iTrac;
import com.sag.jira.exception.ITracNotFoundException;
import com.sag.jira.rule.JiraRuleRunner;

public class ExecuteTool {
	public static void main(String[] args) throws JSONException, ParseException, IOException {
		System.out.println("Welcome to Jira Metrics Tool. This tool will help you to generate itrac effort metrics.\n");
		JiraRuleRunner jiraRuleRunner = new JiraRuleRunner();
		while (true) {
			boolean resultFound = false;
			System.out.println("Please enter iTrac no:");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String readLine = reader.readLine();
			if (readLine != null) {
				String itracNum = readLine.trim();
				if (itracNum != null && !itracNum.isEmpty()) {
					try {
						isValidItrac(itracNum);
						jiraRuleRunner.setItracKey(itracNum);
						System.out.println("Enable Dev Metrics? (y/n)");
//						reader = new BufferedReader(new InputStreamReader(System.in));
						if (getBooleanResponse()) {
							jiraRuleRunner.setEnableDevMetrics(true);
						} else {
							jiraRuleRunner.setEnableDevMetrics(false);
						}
						System.out.println("Enable QA Metrics? (y/n)");
//						reader = new BufferedReader(new InputStreamReader(System.in));
						if (getBooleanResponse()) {
							jiraRuleRunner.setEnableQaMetrics(true);
							System.out.println("Enter defect label");
//							reader = new BufferedReader(new InputStreamReader(System.in));
							String response = getTextResponse();
							if (Objects.nonNull(response) && !response.isEmpty()) {
								jiraRuleRunner.setDefectLabel(response);
							}
							System.out.println("Enter regression label");
//							reader = new BufferedReader(new InputStreamReader(System.in));
							response = getTextResponse();
							if (Objects.nonNull(response) && !response.isEmpty()) {
								jiraRuleRunner.setRegressionLabel(response);
							}
						} else {
							jiraRuleRunner.setEnableQaMetrics(false);
						}

						if (jiraRuleRunner.isEnableDevMetrics() || jiraRuleRunner.isEnableQaMetrics()) {
							System.out.println(
									"Processing your request, depending on the number of child itracs it might take a while...please wait\n");
							jiraRuleRunner.executeRules();
							resultFound = true;
						} else {
							System.out.println("No options selected. Do you want to try again? (y/n)");
							if (getBooleanResponse())
								continue;
							System.exit(0);

						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Incorrect itrac id, do you want to try again? (y/n)");
						if (getBooleanResponse())
							continue;
						System.exit(0);
					}
					if (resultFound) {
						System.out.println(
								"\nProcess completed. Report can be found in the location \"responses/metrics\"");
						System.out.println("Thanks for using the tool. Press any key to exit the tool");
						BufferedReader continueResp = new BufferedReader(new InputStreamReader(System.in));
						continueResp.readLine().trim();
						System.exit(0);

					}
				}
			}

		}
	}

	private static void isValidItrac(String itracNum) throws ITracNotFoundException {
		new iTrac(itracNum, false);
	}

	private static boolean getBooleanResponse() throws IOException {
		BufferedReader continueResp = new BufferedReader(new InputStreamReader(System.in));
		String resp = continueResp.readLine().trim();
		if (resp.equals("y")) {
			return true;
		} else {
//			System.out.println("Hope to see you again. Bye Bye.");
			return false;
		}
	}

	private static String getTextResponse() throws IOException {
		BufferedReader continueResp = new BufferedReader(new InputStreamReader(System.in));
		return continueResp.readLine().trim();

	}
}
