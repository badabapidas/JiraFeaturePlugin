package com.sag.jira.test;

import java.util.Iterator;
import java.util.Set;

import com.sag.jira.core.JiraRestClient;
import com.sag.jira.core.component.Commit;
import com.sag.jira.core.component.Component;
import com.sag.jira.core.component.Issue;
import com.sag.jira.core.component.Project;
import com.sag.jira.core.component.SearchJQL;
import com.sag.jira.core.component.User;
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
import com.sag.jira.core.obj.Worklog;
import com.sag.jira.util.DumpResponse;
import com.sag.jira.util.PropertyUtil;

public class JirateIssueTest {

	public static void main(String[] args) {
		String filePath = "Response.txt";
		DumpResponse write = new DumpResponse(filePath);
		JiraRestClient jira = new JiraRestClient();

		try {

			// PROJECT
			write.dump("***  Project Details:");
			Project project = jira.getProject(PropertyUtil.getJiraProjectId());
			write.dump("Project Name: " + project.getName());
			write.dump("Project Description: " + project.getDescription());
			write.dump("\n\n");

			// Feature details
			write.dump("***  Feature Details:");
			Issue issue = new Issue("UHM-1466");// UHM-674
			write.dump("Feature key: " + issue.getKey());
			write.dump("Feature Title: " + issue.getSummary());
			write.dump("Feature GenerateId: " + issue.getGenerateId());
			write.dump("Feature Description: " + issue.getDescription());
			write.dump("\n\n");

			// ISSUE Commit
			write.dump("----------- Commit --------------");
			Commit commit = issue.getCommits();
			write.dump("commitDone?: " + commit.isCommitDone());
			write.dump("Total lines added: " + commit.getTotalNoOfLinesAdded());
			write.dump("Total lines Removed: " + commit.getTotalNoOfLinesRemoved());
//			write.dump("Total Review Comments: " + commit.getTotalReviewCommentCounts());
			write.dump("Review Response:" + commit.getReviewResponseMetrics());

//			write.dump("ireviewUrl: " + commit.getIReviewUrlForRecentCommit());
//			write.dump("isReviewDone: " + commit.isReviewDoneForRecentCommit());
//			write.dump("changeLogURL: " + commit.getRecentCommitId());
//			write.dump("commitId: " + commit.getRecentCommitId());
			write.dump("\n\n");

//			String jql = "assignee = bada AND resolution = Unresolved order by updated DESC";
//			SearchJQL searchJql = jira.searchJql(jql);
//			Set<Issue> searchResults = searchJql.getSearchResults();
//			write.dump("***Total result found:" + searchResults.size());
//			for (Iterator iterator = searchResults.iterator(); iterator.hasNext();) {
//				Issue nissue = (Issue) iterator.next();
//				write.dump(nissue.getKey());
//			}
//			write.dump("\n\n");

//			write.dump("*** Project Components");
//			Component components = project.getComponents();
//			write.dump("Component: " + components.getComponents());
//			write.dump("Component with Desc: " + components.getComponentsWithDescription().toString());
//			write.dump("\n\n");
//
//			// USER details
//			write.dump("*** User Details");
//			User user = new User("bada");
//			write.dump("alias: " + user.getAlias());
//			write.dump("name: " + user.getDisplayName());
//			write.dump("email: " + user.getEmail());
//			write.dump("rest url: " + user.getRestUrl());
//			write.dump("\n\n");
//
//			// COMPONENTS
//			write.dump("--- Access Components directly with project ID");
//			Component pcomponent = new Component(PropertyUtil.getJiraProjectId());
//			write.dump("Component: " + pcomponent.getComponents());
//			write.dump("Component with Desc: " + pcomponent.getComponentsWithDescription());
//			write.dump("\n\n");
//
//			write.dump("----------- Issue details --------------");
//			write.dump("Key: " + issue.getKey());
//			write.dump("Summary: " + issue.getSummary());
//			write.dump("Description: " + issue.getDescription());
//			write.dump("\n\n");
//
//			// ISSUE COMPONENT
//			write.dump("----------- Issue Component --------------");
//			com.sag.jira.core.component.issue.Component issueComponents = issue.getComponents();
//			write.dump("Component: " + issueComponents.getComponents());
//			write.dump("\n\n");
//
//			// ISSUE ASIGNEE
//			Assignee asignee = issue.getAsignee();
//			write.dump("----------- Assignee --------------");
//			write.dump("alias:" + asignee.getAlias());
//			write.dump("email:" + asignee.getEmail());
//			write.dump("display name:" + asignee.getDisplayName());
//			write.dump("asignee url:" + asignee.getRestUrl());
//			write.dump("\n\n");
//
//			// ISSUE Reporter
//			Reporter reporter = issue.getReporter();
//			write.dump("----------- Reporter --------------");
//			write.dump("alias:" + reporter.getAlias());
//			write.dump("email: " + reporter.getEmail());
//			write.dump("Display Name:" + reporter.getDisplayName());
//			write.dump("URL: " + reporter.getRestUrl());
//			write.dump("\n\n");
//
//			// ISSUE Status
//			Status status = issue.getStatus();
//			write.dump("----------- Status --------------");
//			write.dump("Status: " + status.getStatus());
//			write.dump("Description: " + status.getDescription());
//			write.dump("url:" + status.getRestUrl());
//			write.dump("\n\n");
//
//			// ISSUE Resolution
//			Resolution resolution = issue.getResolution();
//			write.dump("----------- Resolution --------------");
//			write.dump("Status: " + resolution.getStatus());
//			write.dump("Description: " + resolution.getDescription());
//			write.dump("url:" + resolution.getRestUrl());
//			write.dump("\n\n");
//
//			// ISSUE Priority
//			Priority priority = issue.getPriority();
//			write.dump("----------- Priority --------------");
//			write.dump("Status: " + priority.getStatus());
//			write.dump("Description: " + priority.getDescription());
//			write.dump("url: " + priority.getRestUrl());
//			write.dump("\n\n");
//
//			// ISSUE Label
//			write.dump("----------- Labels --------------");
//			Label labels = issue.getLabels();
//			write.dump("Labels:" + labels.getLabels());
//			write.dump("\n\n");
//
//			// ISSUE Worklog
//			write.dump("----------- Worklog --------------");
//			IssueWorklog worklog = issue.getWorklog();
//			// worklog.displayAllLogWorks();
//			write.dump("total: " + worklog.totalTimeSpent());
//			write.dump("count: " + worklog.getTotalWorklogCount());
//			// worklog.getWhoWorkLogged();
//			write.dump("worklog for bada:" + worklog.getWorkLogFor("bada"));
//			write.dump("is WorkloadDone ever: " + worklog.isWorkLogDone());
//			write.dump("is Work load done on that day for user bada:"
//					+ worklog.isWorklogDoneForDate("2019-04-14", "bada"));
//			write.dump("\n\n");
//
//			// ISSUES FixVersion
//			write.dump("----------- FixVersion --------------");
//			FixVersion fixVersions = issue.getFixVersion();
//			write.dump("Fix versions: " + fixVersions.getFixVersions());
//			write.dump("\n\n");
//
//			// ISSUE Type
//			write.dump("----------- Issue Type --------------");
//			Type issueType = issue.getIssueType();
//			write.dump("Issue Type: " + issueType.getIssueType());
//			write.dump("Is any subtask: " + issueType.isSubtasksAvailable());
//			write.dump("rest url: " + issueType.getRestUrl());
//			write.dump("\n\n");
//
//			// ISSUE Project
//			write.dump("----------- Project details from Issue --------------");
//			Project projectFromIssue = issue.getProject();
//			write.dump("Key: " + projectFromIssue.getKey());
//			write.dump("Name: " + projectFromIssue.getName());
//			write.dump("\n\n");
//
//			Watcher watcher = issue.getWatcher();
//			write.dump("----------- Watcher --------------");
//			write.dump("watch count: " + watcher.getWatchCount());
//			write.dump("is watching:" + watcher.isWatching());
//			write.dump("Watcher list: " + watcher.getWatcherNames());
//			write.dump("\n\n");
//
//			// ISSUE TimeTracker
//			TimeTracker timeTracker = issue.getTimeTracker();
//			write.dump("----------- Time Tracker --------------");
//			write.dump("Total: " + timeTracker.getOriginalEstimate());
//			write.dump("Remaining: " + timeTracker.getRemainingEstimate());
//			write.dump("Spent: " + timeTracker.getTimeSpent());
//			write.dump("\n\n");
//
//			// ISSUE Comment
//			Comment comment = issue.getComment();
//			write.dump("----------- Issue comments --------------");
//			write.dump("total: " + comment.totalComment());
//			write.dump("comments from bada: " + comment.getAllCommentsFor("bada"));
////			comment.displayAllComments();
//			write.dump("\n\n");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			write.close();
			System.out.println("Output redirected to " + filePath);
		}
	}
}
