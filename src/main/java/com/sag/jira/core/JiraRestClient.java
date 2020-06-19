package com.sag.jira.core;

import javax.naming.AuthenticationException;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sag.jira.core.component.Component;
import com.sag.jira.core.component.iTrac;
import com.sag.jira.exception.ITracNotFoundException;
import com.sag.jira.core.component.Project;
import com.sag.jira.core.component.SearchJQL;
import com.sag.jira.core.component.User;

public class JiraRestClient extends JiraRestCore {

	private static JiraRestClient INSTANCE = null;
	private static final Logger logger = LoggerFactory.getLogger(JiraRestClient.class);

	public static JiraRestClient getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new JiraRestClient();
			logger.debug("Jira rest client initiated");
		}
		return INSTANCE;
	}

	private JiraRestClient() {
	}

	public Component getComponent(final String id) {
		return new Component(id);
	}

	public iTrac getIssue(final String id) throws ITracNotFoundException {
		return new iTrac(id, true);
	}

	public Project getProject(final String id) throws AuthenticationException {
		return new Project(id);
	}

	public User getUserClient(final String alias) {
		return new User(alias);
	}

	public SearchJQL searchJql(final String jqlQuery) throws JSONException, ITracNotFoundException {
		return new SearchJQL(jqlQuery);
	}

}
