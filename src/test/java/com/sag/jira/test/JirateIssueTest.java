package com.sag.jira.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sag.jira.core.JiraRestClient;
import com.sag.jira.core.component.Commit;
import com.sag.jira.core.component.iTrac;
import com.sag.jira.core.component.issue.IssueWorklog;
import com.sag.jira.core.response.ItracComitResponseBuilder;
import com.sag.jira.core.response.ItracComitResponseBuilder.CommitResponse;
import com.sag.jira.util.DumpResponse;
import com.sag.jira.util.JiraRestConfig;

public class JirateIssueTest {

	public static void main(String[] args) {
		String filePath = "Response.txt";
		DumpResponse write = new DumpResponse(filePath);
		localDebug();
//		JiraRestClient jira = JiraRestClient.getInstance();

		try {

			// PROJECT
//			write.dump("***  Project Details:");
//			Project project = jira.getProject(PropertyUtil.getJiraProjectId());
//			write.dump("Project Name: " + project.getName());
//			write.dump("Project Description: " + project.getDescription());
//			write.dump("\n\n");

			// Feature details
			write.dump("***  Feature Details:");
			iTrac issue = new iTrac("UHM-674");// UHM-674, UHM-1466, CLTF-636
//			write.dump("Feature key: " + issue.getKey());
//			write.dump("issue type: " + issue.getTypeHander().getIssueType());
//			write.dump("Is it a subtask?: " + issue.getTypeHander().isASubtask());
//			write.dump("Is subtask avaialble? " + issue.isSubtasksAvailable() + ", count: " + issue.getSubtasksCount());
//			write.dump("Is there any linked itracs? " + issue.isLinkedItracsAvaiable() + ", count: "
//					+ issue.getLinkedItracsCount());

//			write.dump("Feature Title: " + issue.getSummary());
//			write.dump("Feature GenerateId: " + issue.getGenerateId());
//			write.dump("Feature Description: " + issue.getDescription());
			write.dump("\n\n");

			// ISSUE Commit
			write.dump("----------- Commit --------------");
			Commit commit = issue.getCommitHandler();
//			write.dump("commitDone?: " + commit.isCommitDone());
//			Map<iTrac, CommitResponse> response = commit.getResponseMetircsBuilder();
//			CommitResponse commitResponse = response.get(issue);
//			write.dump("Commit response for " + issue.getKey());
//			write.dump("Lines added:" + commitResponse.getTotalNoOfLinesAdded());
//			write.dump("Lines removed:" + commitResponse.getTotalNoOfLinesRemoved());
//			write.dump("Review Count:" + commitResponse.getTotalReviewCommentCounts());
//			write.dump("Overall riveiw comment:" + commit.getTotalNoOfLinesAdded());
//			write.dump("\n\n");

//			write.dump("Total lines added: " + commit.getTotalNoOfLinesAdded());
//			write.dump("Total lines Removed: " + commit.getTotalNoOfLinesRemoved());
//			write.dump("Total Review Comments: " + commit.getTotalReviewCommentCounts());
//			write.dump("Review Response:" + commit.getReviewResponseMetrics().getStatus());

//			write.dump("ireviewUrl: " + commit.getIReviewUrlForRecentCommit());
//			write.dump("isReviewDone: " + commit.isReviewDoneForRecentCommit());
//			write.dump("changeLogURL: " + commit.getRecentCommitId());
//			write.dump("commitId: " + commit.getRecentCommitId());
//			write.dump("\n\n");

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
//			IssueWorklog worklog = issue.getWorklogHandler();
			// worklog.displayAllLogWorks();
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

	private static void localDebug() {
		try {
			JSONObject jsonObject = readLocalResponse();
			JSONObject fieldObject = jsonObject.optJSONObject(JiraRestConfig.Issue.FILEDS);
			JSONObject optJSONObject = fieldObject.optJSONObject("subtasks");
			JSONArray optJSONArray = fieldObject.optJSONArray("subtasks");
			System.out.println();
		} catch (JSONException e1) {
		}
	}

	public static JSONObject readLocalResponse() throws JSONException {
		String response = readFile("/UHM-1466.json").trim();
		return new JSONObject(response);
	}

	private static String readFile(String filename) {
		String result = "";
		try {

			InputStream propStream = JiraRestClient.class.getResourceAsStream(filename);

			BufferedReader br = new BufferedReader(new InputStreamReader(propStream));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
