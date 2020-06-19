/**
 * This class will help to fetch the test cases attach to any feature 
 */
package com.sag.jira.core.component;

import org.codehaus.jettison.json.JSONException;

import com.sag.jira.core.JiraRestCore;
import com.sag.jira.core.component.parser.TraceabilityParser;
import com.sag.jira.util.JiraRestConfig;

public class Traceability extends JiraRestCore {
	private iTrac iTrac;
	private TraceabilityParser parser;

	public Traceability(String issueGenerateId, iTrac iTrac) {
		this.iTrac = iTrac;
		if (parser == null) {
			try {
				get(JiraRestConfig.getTCMUrlForTrace(issueGenerateId));
				parser = new TraceabilityParser(clientResponse, iTrac.getKey());
				fetchDetailsFromChildren();
			} catch (final JSONException e) {
			}
		}
	}

	private void fetchDetailsFromChildren() {
		findDetailsFromSubtasks();
		findDetailsFromLinkedIssues();
	}

	private void findDetailsFromSubtasks() {
		if (iTrac.isSubtasksAvailable()) {
			iTrac.getAllSubtasks().stream().forEach(p -> {
				iterateForAllChildrens(p);
			});
		}
	}

	private void findDetailsFromLinkedIssues() {
		if (iTrac.isLinkedItracsAvaiable()) {
			iTrac.getLinkedIssues().stream().forEach(p -> {
				iterateForAllChildrens(p);
			});
		}
	}

	private void iterateForAllChildrens(com.sag.jira.core.component.iTrac p) {
		this.iTrac = p;
		try {
			p.getTraceabilityHandler();
		} catch (JSONException e) {
		}
	}

	public int getTotalTestCaseCount() {
		return parser.getTotalTestCaseCount();
	}

	public int getAutomatedTestCaseCount() {
		return parser.getAutomatedTestCaseCount();
	}
}
