package com.sag.jira.core.component.parser;

import org.codehaus.jettison.json.JSONException;

import com.sag.jira.util.JiraRestConfig;
import com.sun.jersey.api.client.ClientResponse;

public class UserParser extends JiraParser {

	public UserParser(ClientResponse response) throws JSONException {
		parseResponse(response);
	}

	public String getAlias() {
		return fetchAndValidateResponse(JiraRestConfig.Common.NAME);
	}

	public String getDisplayName() {
		return fetchAndValidateResponse(JiraRestConfig.User.DISPLAY_NAME);
	}

	public String getEmail() {
		return fetchAndValidateResponse(JiraRestConfig.User.EMAIL_ADDRESS);
	}

	public String getRestUrl() {
		return fetchAndValidateResponse(JiraRestConfig.Common.SELF);
	}

}
