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
import com.sag.jira.core.component.issue.Severity;
import com.sag.jira.core.component.issue.Status;
import com.sag.jira.core.component.issue.TimeTracker;
import com.sag.jira.core.component.issue.Type;
import com.sag.jira.core.component.issue.Watcher;
import com.sag.jira.core.component.parser.IssueParser;
import com.sag.jira.core.obj.Review;
import com.sag.jira.exception.ITracNotFoundException;
import com.sag.jira.util.JiraRestConfig;

public class iTrac extends JiraRestCore {
	private final String id;
	private IssueParser parser;
	private JSONObject jsonResponse;
	private Set<iTrac> subtasks = new HashSet<>();
	private Set<iTrac> linkedIssues = new HashSet<>();

	public iTrac(String id, boolean gotThroughChildren) throws ITracNotFoundException {
		this.id = id.trim();
		if (parser == null) {
			try {
				get(JiraRestConfig.getIssueUrl(this.id));
				parser = new IssueParser(clientResponse);
				jsonResponse = parser.getJsonResponse();
				if (jsonResponse == null) {
					throw new ITracNotFoundException("Not a valid Itrac Id");
				}
				System.out.println("Processing metrics for " + id + "...");
				if (gotThroughChildren) {
					populateChildren();
				}
			} catch (JSONException e) {
				logger.error("Error in Issue component " + e);
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		iTrac other = (iTrac) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	private void populateChildren() throws ITracNotFoundException {
		populateSubtasks();
		populateLInkedItracs();
	}

	private void populateLInkedItracs() throws ITracNotFoundException {
		linkedIssues = parser.getAllLInkedIssues();
		logger.debug("Itrac:" + id + " Linked Issues <" + linkedIssues.size() + ">");
	}

	private void populateSubtasks() throws ITracNotFoundException {
		subtasks = parser.getAllSubtasks();
		logger.debug("Itrac:" + id + " Subtasks <" + subtasks.size() + ">");
	}

	public Set<iTrac> getAllSubtasks() {
		return subtasks;
	}

	public Set<iTrac> getLinkedIssues() {
		return linkedIssues;
	}

	public int getLinkedItracsCount() {
		return linkedIssues.size();
	}

	public int getSubtasksCount() {
		return subtasks.size();
	}

	public boolean isSubtasksAvailable() {
		return subtasks.size() > 0;
	}

	public boolean isLinkedItracsAvaiable() {
		return linkedIssues.size() > 0;
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

	public Label getLabelHandler() throws JSONException {
		return new Label(id, jsonResponse);
	}

	public Priority getPriorityHandler() throws JSONException {
		return new Priority(id, jsonResponse);
	}

	public Severity getSeverityHandler() throws JSONException {
		return new Severity(id, jsonResponse);
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
		return new IssueWorklog(this, jsonResponse);
	}

	public void removeLabel(final String labelToRemove) {
		if (null != labelToRemove) {
			final String editIssueData = "{ \"update\": { \"labels\": [ {\"remove\": \"" + labelToRemove + "\"} ] } }";
			put(JiraRestConfig.getIssueUrl(id), editIssueData);
		}
	}

	public Commit getCommitHandler() throws JSONException {
		Review.setJsonResponse(jsonResponse);
		return new Commit(this.getGenerateId(), this);
	}

	public Traceability getTraceabilityHandler() throws JSONException {
		return new Traceability(this.getGenerateId(), this);
	}

}
