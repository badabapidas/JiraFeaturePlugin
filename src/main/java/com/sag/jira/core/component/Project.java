
package com.sag.jira.core.component;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sag.jira.core.JiraRestCore;
import com.sag.jira.core.component.parser.ProjectParser;
import com.sag.jira.util.JiraRestConfig;

public class Project extends JiraRestCore {

	private String projectId;
	private ProjectParser parser;
	protected String issueId;
	protected JSONObject issueResponse;

	public Project(String id) {
		this.projectId = id;
		if (parser == null) {
			get(JiraRestConfig.getProjectUrl(id));
			try {
				parser = new ProjectParser(clientResponse);
			} catch (JSONException e) {
			}
		}
	}

	public Project(String issueId, JSONObject issueResponse) throws JSONException {
		try {
			initialized(issueId, issueResponse, JiraRestConfig.ISSUE_PROJECT_REST_PATH);
		} catch (JSONException e) {
			throw new JSONException("Project type not found!");
		}
	}

	private void initialized(String issueId, JSONObject issueResponse, String restAssetPath) throws JSONException {
		this.issueId = issueId;
		this.issueResponse = issueResponse.getJSONObject("fields").optJSONObject(restAssetPath);
		if (this.issueResponse == null)
			this.issueResponse = issueResponse.getJSONObject("fields");

	}

	public String getKey() {
		if (null != issueResponse)
			return fetchAndValidateResponse(JiraRestConfig.Project.KEY, issueResponse);
		return parser.getKey();
	}

	public String getName() {
		if (issueResponse != null)
			return fetchAndValidateResponse(JiraRestConfig.Project.NAME, issueResponse);
		return parser.getName();
	}

	public Issue getIssue(String id) {
		return new Issue(id);
	}

	public String getDescription() {
		return parser.getDescription();
	}

	public Component getComponents() {
		return new Component(projectId);
	}

	public ProjectParser getParser() {
		return parser;
	}

}
