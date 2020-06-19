package com.sag.jira.core.component.parser;

import org.codehaus.jettison.json.JSONException;

import com.sag.jira.util.JiraRestConfig;
import com.sun.jersey.api.client.ClientResponse;

public class ProjectParser extends JiraParser {
	public ProjectParser(ClientResponse response) throws JSONException {
		parseResponse(response);
	}

	public String getKey() {
		return fetchAndValidateResponse(JiraRestConfig.Project.KEY);
	}

	public String getName() {
		return fetchAndValidateResponse(JiraRestConfig.Common.NAME);
	}

	public String getDescription() {
		return fetchAndValidateResponse(JiraRestConfig.Common.DESCRIPTION);
	}

}
