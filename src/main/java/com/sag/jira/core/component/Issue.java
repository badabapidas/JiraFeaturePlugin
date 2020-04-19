package com.sag.jira.core.component;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sag.jira.core.JiraRestCore;
import com.sag.jira.core.component.issue.Assignee;
import com.sag.jira.core.component.issue.Comment;
import com.sag.jira.core.component.issue.Component;
import com.sag.jira.core.component.issue.FixVersion;
import com.sag.jira.core.component.issue.IssueWorklog;
import com.sag.jira.core.component.issue.Label;
import com.sag.jira.core.component.issue.Priority;
import com.sag.jira.core.component.issue.Reporter;
import com.sag.jira.core.component.issue.Resolution;
import com.sag.jira.core.component.issue.Status;
import com.sag.jira.core.component.issue.TimeTracker;
import com.sag.jira.core.component.issue.Type;
import com.sag.jira.core.component.issue.Watcher;
import com.sag.jira.core.component.parser.IssueParser;
import com.sag.jira.core.obj.Review;
import com.sag.jira.util.JiraRestConfig;

public class Issue extends JiraRestCore {
	private final String id;
	private IssueParser parser;
	private JSONObject jsonResponse;

	public Issue(String id) {
		this.id = id;
		if (parser == null) {
			try {
				get(JiraRestConfig.getIssueUrl(id));
				parser = new IssueParser(clientResponse);
				jsonResponse = parser.getJsonResponse();
			} catch (JSONException e) {
				logger.error("Error in Issue component " + e);
			}
		}
	}

	public String getDescription() {
		return parser.getDescription();
	}

	public String getKey() {
		return parser.getKey();
	}

	public String getGenerateId() throws JSONException {
		return parser.getGenerateId();
	}

	public String getSummary() {
		return parser.getSummary();
	}

	public IssueParser getParser() {
		return parser;
	}

	public Assignee getAsignee() throws JSONException {
		return new Assignee(id, jsonResponse);

	}

	public Comment getComment() throws JSONException {
		return new Comment(id, jsonResponse);
	}

	public Component getComponents() throws JSONException {
		return new Component(id, jsonResponse);
	}

	public FixVersion getFixVersion() throws JSONException {
		return new FixVersion(id, jsonResponse);
	}

	public Type getIssueType() throws JSONException {
		return new Type(id, jsonResponse);
	}

	public Label getLabels() throws JSONException {
		return new Label(id, jsonResponse);
	}

	public Priority getPriority() throws JSONException {
		return new Priority(id, jsonResponse);
	}

	public Project getProject() throws JSONException {
		return new Project(id, jsonResponse);
	}

	public Reporter getReporter() throws JSONException {
		return new Reporter(id, jsonResponse);
	}

	public Resolution getResolution() throws JSONException {
		return new Resolution(id, jsonResponse);
	}

	public Status getStatus() throws JSONException {
		return new Status(id, jsonResponse);
	}

	public TimeTracker getTimeTracker() throws JSONException {
		return new TimeTracker(id, jsonResponse);
	}

	public Watcher getWatcher() throws JSONException {
		return new Watcher(id);
	}

	public IssueWorklog getWorklog() throws JSONException {
		return new IssueWorklog(id, jsonResponse);
	}

	public void removeLabel(final String labelToRemove) {
		if (null != labelToRemove) {
			final String editIssueData = "{ \"update\": { \"labels\": [ {\"remove\": \"" + labelToRemove + "\"} ] } }";
			put(JiraRestConfig.getIssueUrl(id), editIssueData);
		}
	}

	public Commit getCommits() throws JSONException {
		Review.setJsonResponse(jsonResponse);
		return new Commit(this.getGenerateId(), id);
	}
}
