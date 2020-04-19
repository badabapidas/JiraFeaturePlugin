package com.sag.jira.core.component;

import java.util.HashSet;
import java.util.Set;

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
	private Set<Issue> subtasks = new HashSet<>();
	private Set<Issue> issueLinks = new HashSet<>();

	public Issue(String id) {
		this.id = id;
		if (parser == null) {
			try {
				get(JiraRestConfig.getIssueUrl(id));
				parser = new IssueParser(clientResponse);
				jsonResponse = parser.getJsonResponse();
				populateChildren();
			} catch (JSONException e) {
				logger.error("Error in Issue component " + e);
			}
		}
	}

	private void populateChildren() {
		populateSubtasks();
		populateIssueLinks();
	}

	private void populateIssueLinks() {
		issueLinks = parser.getAllLInkedIssues();
		System.out.println("Linked issues counts for itrac:" + id + " - " + issueLinks.size() + "\n");
	}

	private void populateSubtasks() {
		subtasks = parser.getAllSubtasks();
		System.out.println("Sub Task counts for itrac:" + id + " - " + subtasks.size());
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

	public Assignee getAsigneeHandler() throws JSONException {
		return new Assignee(id, jsonResponse);

	}

	public Comment getCommentHandler() throws JSONException {
		return new Comment(id, jsonResponse);
	}

	public Component getComponentsHandler() throws JSONException {
		return new Component(id, jsonResponse);
	}

	public FixVersion getFixVersionHandler() throws JSONException {
		return new FixVersion(id, jsonResponse);
	}

	public Type getTypeHander() throws JSONException {
		return new Type(id, jsonResponse);
	}

	public Label getLabelsHandler() throws JSONException {
		return new Label(id, jsonResponse);
	}

	public Priority getPriorityHandler() throws JSONException {
		return new Priority(id, jsonResponse);
	}

	public Project getProjectHandler() throws JSONException {
		return new Project(id, jsonResponse);
	}

	public Reporter getReporterHandler() throws JSONException {
		return new Reporter(id, jsonResponse);
	}

	public Resolution getResolutionHandler() throws JSONException {
		return new Resolution(id, jsonResponse);
	}

	public Status getStatusHandler() throws JSONException {
		return new Status(id, jsonResponse);
	}

	public TimeTracker getTimeTrackerHandler() throws JSONException {
		return new TimeTracker(id, jsonResponse);
	}

	public Watcher getWatcherHandler() throws JSONException {
		return new Watcher(id);
	}

	public IssueWorklog getWorklogHandler() throws JSONException {
		return new IssueWorklog(id, jsonResponse);
	}

	public void removeLabel(final String labelToRemove) {
		if (null != labelToRemove) {
			final String editIssueData = "{ \"update\": { \"labels\": [ {\"remove\": \"" + labelToRemove + "\"} ] } }";
			put(JiraRestConfig.getIssueUrl(id), editIssueData);
		}
	}

	public Commit getCommitHandler() throws JSONException {
		Review.setJsonResponse(jsonResponse);
		return new Commit(this.getGenerateId(), id);
	}
}
