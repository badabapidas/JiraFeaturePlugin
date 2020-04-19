package com.sag.jira.core.component;

import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sag.jira.core.JiraRestCore;
import com.sag.jira.core.component.parser.CommitParser;
import com.sag.jira.core.response.ItracReviewResponseBuilder.Response;
import com.sag.jira.util.JiraRestConfig;

public class Commit extends JiraRestCore {

	private final String issueGenerateId;
	private final String itracId;
	private JSONObject jsonResponse;
	private CommitParser parser;

	public Commit(String issueGenerateId, String itracId) {
		this.issueGenerateId = issueGenerateId;
		this.itracId = itracId;
		if (parser == null) {
			get(JiraRestConfig.getCommitUrl(issueGenerateId));
			try {
				parser = new CommitParser(clientResponse, issueGenerateId, itracId);
				jsonResponse = parser.getJsonResponse();
			} catch (final JSONException e) {
			}
		}
	}

	public boolean addReviewTask(String issusId) {
		if (isCommitDone() && !isReviewDoneForRecentCommit()) {
			logger.info("Commit done but review still pending so creating review tasks for " + issusId);
			return true;

		}
		return false;
	}

	public boolean isReviewDoneForRecentCommit() {
		return parser.isReviewDoneForRecentCommit();
	}

	public String getIReviewUrlForRecentCommit() {
		return parser.getIReviewUrlForRecentCommit();
	}

	public boolean isCommitDone() {
		return parser.isCommitDone();
	}

	public String getChangelogUrlForRecentCommit() {
		return parser.getChangelogUrlForRecentCommit();
	}

	public String getAuthorForRecentCommit() {
		return parser.getAuthorForRecentCommit();
	}

	public String getRecentCommitId() {
		return parser.getRecentCommitId();
	}

	public int getTotalNoOfLinesAdded() {
		return parser.getTotalNoOfLinesAdded();
	}

	public int getTotalNoOfLinesRemoved() {
		return parser.getTotalNoOfLinesRemoved();
	}

	public int getTotalReviewCommentCounts() {
		return parser.getTotalReviewComments();
	}


	public Response getReviewResponseMetrics() {
		return parser.getReviewMetrics();
	}

}
