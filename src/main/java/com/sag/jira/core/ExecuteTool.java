package com.sag.jira.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

import org.codehaus.jettison.json.JSONException;

import com.sag.jira.rule.JiraRuleRunner;

public class ExecuteTool {
	public static void main(String[] args) throws JSONException, ParseException, IOException {
		System.out.println("Welcome to Jira Metrics Tool. This tool will help you to generate itrac effort metrics.\n");
		while (true) {
			boolean resultFound = false;
			System.out.println("Please enter iTrac no:");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String readLine = reader.readLine();
			if (readLine != null) {
				String itracNum = readLine.trim();
				if (itracNum != null && !itracNum.isEmpty()) {
					try {
						System.out.println(
								"Processing your request, depending on the number of child itracs it might take a while...please wait\n");
						new JiraRuleRunner().executeRules(itracNum);
						resultFound = true;
					} catch (Exception e) {
						System.out.println("Incorrect itrac id, do you want to try again? (y/n)");
						if (tryAgain())
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

	private static boolean tryAgain() throws IOException {
		BufferedReader continueResp = new BufferedReader(new InputStreamReader(System.in));
		String resp = continueResp.readLine().trim();
		if (resp.equals("y")) {
			return true;
		} else {
			System.out.println("Hope to see you again. Bye Bye.");
			return false;
		}
	}
}
