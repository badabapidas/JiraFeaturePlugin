package com.sag.jira.core.component;

import org.codehaus.jettison.json.JSONException;

import com.sag.jira.core.JiraRestCore;
import com.sag.jira.core.component.parser.UserParser;
import com.sag.jira.util.JiraRestConfig;

public class User extends JiraRestCore {

	private String alias;
	private UserParser parser;

	public User(String alias) {
		this.alias = alias;
		if (parser == null) {
			get(JiraRestConfig.getUserUrl(alias));
			try {
				parser = new UserParser(clientResponse);
			} catch (JSONException e) {
			}
		}
	}

	public String getAlias() {
		return parser.getAlias();
	}

	public String getDisplayName() {
		return parser.getDisplayName();
	}

	public String getEmail() {
		return parser.getEmail();
	}

	public String getRestUrl() {
		return parser.getRestUrl();
	}

}
