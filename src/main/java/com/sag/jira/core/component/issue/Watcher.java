package com.sag.jira.core.component.issue;

import org.codehaus.jettison.json.JSONException;

import com.sag.jira.core.JiraRestCore;
import com.sag.jira.core.component.parser.WatcherParser;
import com.sag.jira.util.JiraRestConfig;

public class Watcher extends JiraRestCore {

	private String issueId;
	private WatcherParser parser;

	public Watcher(String issueId) throws JSONException {
		this.issueId = issueId;
		if (parser == null) {
			get(JiraRestConfig.getWatcherUrl(issueId));
			parser = new WatcherParser(clientResponse);
		}
	}

	public String getWatchCount() {
		return parser.getWatchCount();
	}

	public boolean isWatching() {
		return parser.isWatching();
	}

	public String getWatcherNames() {
		return parser.getWatcherNames();
	}

}
