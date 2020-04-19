package com.sag.jira.core;

import javax.naming.AuthenticationException;

import com.sag.jira.core.component.Component;
import com.sag.jira.core.component.Issue;
import com.sag.jira.core.component.Project;
import com.sag.jira.core.component.SearchJQL;
import com.sag.jira.core.component.User;

public class JiraRestClient extends JiraRestCore {

	public JiraRestClient() {
	}

	public Component getComponent(final String id) {
		return new Component(id);
	}

	public Issue getIssue(final String id) {
		return new Issue(id);
	}

	public Project getProject(final String id) throws AuthenticationException {
		return new Project(id);
	}

	public User getUserClient(final String alias) {
		return new User(alias);
	}

	public SearchJQL searchJql(final String jqlQuery) {
		return new SearchJQL(jqlQuery);
	}

}
