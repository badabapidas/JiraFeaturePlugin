package com.sag.jira.util;

import java.util.ArrayList;

import com.sag.jira.core.JiraRestCore;

public final class JiraRestConfig {

	public static final String LABEL_FOR_BACKLOG = "MBTCNL-2017-Backlogs";
	public static final String LABEL_FOR_PORTING_BETA_TO_STAGING = "MBTCNL-PortingFromTrunkToStaging";
	public static final String LABEL_FOR_PORTING_STAGING_TO_STABLE = "MBTCNL-PortingFromStagingToStable";

	public static final String ISSUE_PRIORITY_REST_PATH = "priority";
	public static final String ISSUE_ASSIGNEE_REST_PATH = "assignee";
	public static final String ISSUE_REPORTER_REST_PATH = "reporter";
	public static final String ISSUE_RESOLUTION_REST_PATH = "resolution";
	public static final String ISSUE_STATUS_REST_PATH = "status";
	public static final String ISSUE_WORKLOG_REST_PATH = "worklog";
	public static final String ISSUE_LABELS_REST_PATH = "labels";
	public static final String ISSUE_FIXVERSIONS_REST_PATH = "fixVersions";
	public static final String ISSUE_TYPE_REST_PATH = "issuetype";
	public static final String ISSUE_PROJECT_REST_PATH = "project";
	public static final String ISSUE_TIME_TRACKER_REST_PATH = "timetracking";
	public static final String ISSUE_COMMENT_REST_PATH = "comment";

	public static final String ISSUE_WATCHERS_REST_PATH = "/watchers";
	private static final String project_rest_url = "2/project/";
	private static final String issue_rest_url = "2/issue/";
	private static final String user_rest_url = "2/user?username=";
	private static final String search_jql_rest_url = "2/search?jql=";
	private static final String commit_rest_utl_pre = "/rest/dev-status/1.0/issue/detail?issueId=";
	private static final String commit_rest_utl_post = "&applicationType=fecru&dataType=repository";

	public static final String DEFAULT_TIME_FORMAT = "00:00:00:00";

	// Rules
	public static final String WORKLOG_REPORT_FILE_NAME = "WorkLogReport.html";
	public static final String RULE_ESTIMATE_FOR_SPRINT = "SprintItracsEstimateResult";
	public static final String RULE_ESTIMATE_FOR_USER = "UserItracsEstimateResult";

	public static ArrayList<String> ALL_MEMBERS_FOR_ANALYSIS = new ArrayList<String>();

	// IREVIEW urls
	private static String reviewUsersUrl = "http://ireview.eur.ad.sag/rest-service/users-v1";
	private static String reviewBaseUrl = "http://ireview.eur.ad.sag/rest-service/reviews-v1";

	// public static final JiraRestClient ITRAC_REST_CLIENT =
	// new JiraRestClient("https://itrac.eur.ad.sag", "itracsust", "tsuscarti");

	static {
		ALL_MEMBERS_FOR_ANALYSIS.add("indgo");
		ALL_MEMBERS_FOR_ANALYSIS.add("bada");
	}

	public static String getIssueUrl(final String id) {
		return JiraRestCore.getRestTargetUri() + issue_rest_url + id;
	}

	public static String getCommitUrl(final String id) {
		return JiraRestCore.getBaseTargetUri() + commit_rest_utl_pre + id + commit_rest_utl_post;
	}

	public static String getProjectUrl(final String id) {
		return JiraRestCore.getRestTargetUri() + project_rest_url + id;
	}

	public static String getSearchJqlUrl(final String searchJql, final int startAt, final int maxResults) {
		return JiraRestCore.getRestTargetUri() + search_jql_rest_url + searchJql + "&startAt=" + startAt
				+ "&maxResults=" + maxResults;
	}

	public static String getUserUrl(final String username) {
		return JiraRestCore.getRestTargetUri() + user_rest_url + username;
	}

	public static String getWatcherUrl(final String id) {
		return getIssueUrl(id) + ISSUE_WATCHERS_REST_PATH;
	}

	public static String getReviewUserUrl() {
		return reviewUsersUrl;
	}

	public static String getReviewBaseUrl() {
		return reviewBaseUrl;
	}

	public static String getReviewIdUrl(String reviewId) {
		return reviewBaseUrl + "/" + reviewId + "/reviewers";
	}

	public static String getReviewUrlFromIssue(String issueGenaratedId) {
		return JiraRestCore.getBaseTargetUri() + "/rest/dev-status/1.0/issue/detail?issueId=" + issueGenaratedId
				+ "&applicationType=fecru&dataType=review";
	}

	public static final class Issue {
		public static final String FILEDS = "fields";
		public static final String SUMMARY = "summary";
		public static final String KEY = "key";
		public static final String ID = "id";
		public static final String ISSUES = "issues";
		public static final String TOTAL = "total";
		public static final String MAXRESULTS = "maxResults";
		public static final String SUBTASK = "subtask";
	}

	public static final class Label {
		public static final String LABELS = "labels";
	}

	public static final class TimTracker {
		public static final String ESTIMATE_ORGINAL = "originalEstimate";
		public static final String ESTIMATE_REMAINING = "remainingEstimate";
		public static final String TIMESPENT = "timeSpent";
		public static final String ESTIMATE_ORGINAL_SECS = "originalEstimateSeconds";
		public static final String ESTIMATE_REMAINING_SECS = "remainingEstimateSeconds";
		public static final String TIMESPENT_SECS = "timeSpentSeconds";
	}

	public static final class SearchJQL {
		public static final String ISSUES = "issues";
		public static final String TOTAL = "total";
		public static final String MAXRESULTS = "maxResults";
	}

	public static final class Commit {
		public static final String DETAIL = "detail";
		public static final String REPOSITORIES = "repositories";
		public static final String URL = "url";
		public static final String COMMITS = "commits";
		public static final String ID = "id";
		public static final String FILECOUNT = "fileCount";
		public static final String MESSAGE = "message";
		public static final String AUTHOR = "author";
		public static final String CREATE_REVIEW_URL = "createReviewUrl";
		public static final String REVIEWS = "reviews";
		public static final String CREATED = "created";
		public static final String BODY = "body";
		public static final String FILES = "files";
	}

	public static final class Comment {
		public static final String COMMENT = "comments";
		public static final String TOTAL = "total";
	}

	public static final class Component {
		public static final String COMPONENTS = "components";
	}

	public static final class Worklog {
		public static final String WORKLOGS = "worklogs";
		public static final String TOTAL = "total";
		public static final String COMMENT = "comment";
		public static final String TIMESPENT = "timeSpent";
		public static final String TIME_SPENT_SECS = "timeSpentSeconds";
		public static final String CREATED = "created";
		public static final String UPDATE_AUTHOR = "updateAuthor";

	}

	public static final class Watcher {
		public static final String WATCHERS = "watchers";
		public static final String DISPLAY_NAME = "displayName";
		public static final String WATCH_COUNT = "watchCount";
		public static final String IS_WATCHING = "isWatching";
	}

	public static final class File {
		public static final String PATH = "path";
		public static final String URL = "url";
		public static final String CHANGETYPE = "changeType";
		public static final String LINESADDED = "linesAdded";
		public static final String LINESREMOVED = "linesRemoved";
	}

	public static final class Review {
		public static final String TOTAL_COUNT = "totalCount";
		public static final String URL = "url";
		public static final String REVIEWS = "reviews";
		public static final String REVIEWERS = "reviewers";
		public static final String ID = "id";
		public static final String STATE = "state";
		public static final String TITLE = "title";
		public static final String DETAIL = "detail";
		public static final String COMMENT_COUNT = "commentCount";
	}

	public static final class Project {
		public static final String KEY = "key";
		public static final String NAME = "name";
	}

	public static final class User {
		public static final String DISPLAY_NAME = "displayName";
		public static final String EMAIL_ADDRESS = "emailAddress";
	}

	public static final class Common {
		public static final String EMPTY = "";
		public static final String SELF = "self";
		public static final String NAME = "name";
		public static final String DESCRIPTION = "description";
	}

}
