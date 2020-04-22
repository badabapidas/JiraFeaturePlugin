package com.sag.jira.core.component;

import java.util.Map;

import org.codehaus.jettison.json.JSONException;

import com.sag.jira.core.JiraRestCore;
import com.sag.jira.core.component.parser.CommitParser;
import com.sag.jira.core.response.ItracComitResponseBuilder;
import com.sag.jira.core.response.ItracComitResponseBuilder.CommitResponse;
import com.sag.jira.core.response.ItracReviewResponseBuilder.ReviewResponse;
import com.sag.jira.core.response.ResponseBuilder;
import com.sag.jira.util.JiraRestConfig;

public class Commit extends JiraRestCore {

	private iTrac iTrac;
	private CommitParser parser;
	private ItracComitResponseBuilder commitResponse = ResponseBuilder.getCommitResponseBuilder();

	public Commit(String issueGenerateId, iTrac iTrac) {
		this.iTrac = iTrac;
		if (parser == null) {
			get(JiraRestConfig.getCommitUrl(issueGenerateId));
			try {
				parser = new CommitParser(clientResponse, issueGenerateId, iTrac.getKey());
				updateCommitMetrics();
			} catch (final JSONException e) {
			}
		}
	}

	private void updateCommitMetrics() {
		updateCommitMetricsForItrac(this);
		updateCommitMetricsForSubtasks();
		updateCommitMetricsForLinkedIssues();
	}

	private void updateCommitMetricsForLinkedIssues() {
		if (iTrac.isLinkedItracsAvaiable()) {
			iTrac.getLinkedIssues().stream().forEach(p -> {
				iterateForAllChildrens(p);
			});
		}
	}

	private void updateCommitMetricsForSubtasks() {
		if (iTrac.isSubtasksAvailable()) {
			iTrac.getAllSubtasks().stream().forEach(p -> {
				iterateForAllChildrens(p);
			});
		}
	}

	private void iterateForAllChildrens(com.sag.jira.core.component.iTrac p) {
		try {
			this.iTrac = p;
			p.getCommitHandler();
		} catch (JSONException e) {
		}
	}

	private void updateCommitMetricsForItrac(Commit c) {
		int totalNoOfLinesRemoved = c.getTotalNoOfLinesRemovedP();
		int totalNoOfLinesAdded = c.getTotalNoOfLinesAddedP();
		int totalReviewCommentCounts = c.getTotalReviewCommentCountsP();
		logger.debug(
				iTrac.getKey() + ":" + " totalNoOfLinesRemoved: " + totalNoOfLinesRemoved + ", totalNoOfLinesAdded: "
						+ totalNoOfLinesAdded + ", totalReviewCommentCounts:" + totalReviewCommentCounts);
		commitResponse.setItrac(iTrac).addTotalNoOfLInesAdded(totalNoOfLinesAdded)
				.addTotalNoOfLinesRemoved(totalNoOfLinesRemoved).addTotalReviewCommentCounts(totalReviewCommentCounts)
				.build();
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

	private int getTotalNoOfLinesAddedP() {
		return parser.getTotalNoOfLinesAdded();
	}

	private int getTotalNoOfLinesRemovedP() {
		return parser.getTotalNoOfLinesRemoved();
	}

	private int getTotalReviewCommentCountsP() {
		return parser.getTotalReviewCommentsCounts();
	}

	public ReviewResponse getReviewResponseMetrics() {
		return parser.getReviewMetrics();
	}

	public CommitResponse getResponseForItrac(String itracKey) {
		return commitResponse.getResponseForItrac(itracKey);
	}

	public int getTotalNoOfLinesAdded() {
		return commitResponse.getTotalNoOfLinesAdded();
	}

	public int getTotalNoOfLinesRemoved() {
		return commitResponse.getTotalNoOfLinesRemoved();
	}

	public int getTotalReviewCommentCounts() {
		return commitResponse.getTotalReviewCommentCounts();
	}

}
