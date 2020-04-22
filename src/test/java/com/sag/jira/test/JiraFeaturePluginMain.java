package com.sag.jira.test;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sag.jira.core.component.Commit;
import com.sag.jira.core.component.iTrac;
import com.sag.jira.core.component.issue.Assignee;
import com.sag.jira.core.component.issue.Comment;
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
import com.sag.jira.core.response.ItracComitResponseBuilder.CommitResponse;

public class JiraFeaturePluginMain {
	private static final Logger logger = LoggerFactory.getLogger(JiraFeaturePluginMain.class);
	private static final String ITRAC_ID = "CLTF-636"; // UHM-1466, UHM-674
	private static final String ITRAC_ID_Metric = "UHM-1104";
	private static final String newLine = "\n";

	public static void main(String[] args) throws JSONException {
		logger.info("Fetching details for " + ITRAC_ID);
		final iTrac itrac = new iTrac(ITRAC_ID);

		logger.info("============================");

		// TYPE HANDLER
		Type typeHander = itrac.getTypeHander();
		logger.info("Summary: " + itrac.getSummary());
		logger.info("iTrac Type: " + typeHander.getIssueType());
		logger.info("Is this a Subtask? " + typeHander.isASubtask() + newLine);

		// ASSIGNEE HANDLER
		Assignee asignee = itrac.getAsigneeHandler();
		logger.info("Assignee: " + asignee.getDisplayName());

		// REPORTER HANDLER
		Reporter reporterHandler = itrac.getReporterHandler();
		logger.info("Reporter: " + reporterHandler.getDisplayName());

		// STATUS HANDLER
		Status statusHandler = itrac.getStatusHandler();
		logger.info("Status: " + statusHandler.getStatus());

		// RESOLUTION HANDLER
		Resolution resolutionHandler = itrac.getResolutionHandler();
		logger.info("Resoultion: " + resolutionHandler.getStatus());

		// PRIORITY HANDLER
		Priority priorityHandler = itrac.getPriorityHandler();
		logger.info("Priority: " + priorityHandler.getStatus());

		// LABEL HANDLER
		Label labelsHandler = itrac.getLabelHandler();
		logger.info("Labels: " + labelsHandler.getLabels());

		// FIX HANDLER
		FixVersion fixVersionHandler = itrac.getFixVersionHandler();
		logger.info("Fix version: " + fixVersionHandler.getFixVersions());

		// WATCHER HANDLER
		Watcher watcherHandler = itrac.getWatcherHandler();
		logger.info("Wachers: " + watcherHandler.getWatcherNames() + newLine);

		logger.info("iTrac children details");
		logger.info("============================");
		logger.info("Subtask? " + itrac.isSubtasksAvailable() + ", count: " + itrac.getSubtasksCount());
		logger.info("Linked Itracs? " + itrac.isLinkedItracsAvaiable() + ", count: " + itrac.getLinkedItracsCount()
				+ newLine);

		// COMMENT HNADLER
		logger.info("Comments details");
		logger.info("============================");
		Comment commentHandler = itrac.getCommentHandler();
		logger.info("Total:" + commentHandler.totalComment());
		logger.info("Comments by bada:" + newLine + commentHandler.getAllCommentsFor("bada") + newLine);

		// TIMETRACKER HANDLER
		logger.info("Time Spent details");
		logger.info("============================");
		TimeTracker timeTrackerHandler = itrac.getTimeTrackerHandler();
		logger.info("Estimate done? " + timeTrackerHandler.isEstimateDone());
		logger.info("Original Estimated Time:" + timeTrackerHandler.getOriginalEstimate());
		logger.info("Remaining Estimated Time:" + timeTrackerHandler.getRemainingEstimate());
		logger.info("Total time spent:" + timeTrackerHandler.getTimeSpent() + newLine);

		// COMMIT HANDLER
		logger.info("Overall Commit Details");
		logger.info("============================");
		Commit commitHandler = itrac.getCommitHandler();
		logger.info("Lines added: " + commitHandler.getTotalNoOfLinesAdded());
		logger.info("Lines removed: " + commitHandler.getTotalNoOfLinesRemoved());
		logger.info("Review Comments Count: " + commitHandler.getTotalReviewCommentCounts() + newLine);

		logger.info("Commit response for " + ITRAC_ID_Metric);
		CommitResponse commitResponse = commitHandler.getResponseForItrac(ITRAC_ID_Metric);
		logger.info("Response Satus: " + commitResponse.getStatus());
		logger.info("-> Lines added: " + commitResponse.getTotalNoOfLinesAdded());
		logger.info("-> Lines removed: " + commitResponse.getTotalNoOfLinesRemoved());
		logger.info("-> Review Count: " + commitResponse.getTotalReviewCommentCounts() + newLine);

		// WORKLOG HANDLER
		logger.info("Worklog details");
		logger.info("============================");
		IssueWorklog worklogHandler = itrac.getWorklogHandler();
		logger.info("Total worklog counts: " + worklogHandler.getTotalWorklogCount());
		logger.info("Worklog for bada:" + worklogHandler.getWorkLogFor("bada"));
		logger.info("Who all work logged?" + worklogHandler.getWhoWorkLogged());
		logger.info("All work logs:");
		worklogHandler.displayAllLogWorks();
		logger.info("Total time spent:" + worklogHandler.getTotalTimeSpent());
	}
}