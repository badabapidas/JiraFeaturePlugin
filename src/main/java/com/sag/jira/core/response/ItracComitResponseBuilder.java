/**
 * Used builder pattern
 */
package com.sag.jira.core.response;

import java.util.HashMap;
import java.util.Map;

import com.sag.jira.core.component.iTrac;
import com.sag.jira.core.component.issue.Status;
import com.sag.jira.core.response.ItracReviewResponseBuilder.ReviewResponse.STATUS;

public class ItracComitResponseBuilder {
	protected static ItracComitResponseBuilder INSTANCE = null;
	protected CommitResponse response = new CommitResponse();
	protected static int totalNoOfLinesAdded = 0;
	protected static int totalNoOfLinesRemoved = 0;
	protected static int totalReviewCommentCounts = 0;
	private iTrac itrac;
	protected static Map<iTrac, CommitResponse> commitMetrics = new HashMap();

	public ItracComitResponseBuilder addTotalNoOfLInesAdded(int numOfLines) {
		response.setTotalNoOfLinesAdded(numOfLines);
		totalNoOfLinesAdded += numOfLines;
		return this;
	}

	public ItracComitResponseBuilder addTotalNoOfLinesRemoved(int numOfLines) {
		response.setTotalNoOfLinesRemoved(numOfLines);
		totalNoOfLinesRemoved += numOfLines;
		return this;
	}

	public ItracComitResponseBuilder addTotalReviewCommentCounts(int reviewCommentCounts) {
		response.setTotalReviewCommentCounts(reviewCommentCounts);
		totalReviewCommentCounts += reviewCommentCounts;
		return this;
	}

	public static ItracComitResponseBuilder getInstance() {
		if (INSTANCE == null) {
			return new ItracComitResponseBuilder();
		}
		return INSTANCE;
	}

	public int getTotalNoOfLinesAdded() {
		return totalNoOfLinesAdded;
	}

	public int getTotalNoOfLinesRemoved() {
		return totalNoOfLinesRemoved;
	}

	public int getTotalReviewCommentCounts() {
		return totalReviewCommentCounts;
	}

	public CommitResponse getResponseForItrac(String itracKey) {
		for (Map.Entry<iTrac, CommitResponse> respone : commitMetrics.entrySet()) {
			if (respone.getKey().getKey().equalsIgnoreCase(itracKey)) {
				return respone.getValue();
			}
		}
		return new CommitResponse();
	}

	public void build() {
		if (!commitMetrics.containsKey(itrac)) {
			response.setStatus(response.responseStatus.AVAILABLE);
			commitMetrics.put(itrac, response);
			response = new CommitResponse();
		}
	}

	private ItracComitResponseBuilder() {
	}

	public ItracComitResponseBuilder setItrac(iTrac itrac) {
		this.itrac = itrac;
		return this;
	}

	public static class CommitResponse {
		enum STATUS {
			AVAILABLE, NOT_AVAILABLE
		}

		protected STATUS responseStatus = STATUS.NOT_AVAILABLE;
		protected int totalNoOfLinesAdded = 0;
		protected int totalNoOfLinesRemoved = 0;
		protected int totalReviewCommentCounts = 0;

		protected CommitResponse() {
		}

		public STATUS getStatus() {
			return responseStatus;
		}

		public void setStatus(STATUS default_status) {
			this.responseStatus = default_status;
		}

		public int getTotalNoOfLinesAdded() {
			return totalNoOfLinesAdded;
		}

		public void setTotalNoOfLinesAdded(int totalNoOfLinesAdded) {
			this.totalNoOfLinesAdded = totalNoOfLinesAdded;
		}

		public int getTotalNoOfLinesRemoved() {
			return totalNoOfLinesRemoved;
		}

		public void setTotalNoOfLinesRemoved(int totalNoOfLinesRemoved) {
			this.totalNoOfLinesRemoved = totalNoOfLinesRemoved;
		}

		public int getTotalReviewCommentCounts() {
			return totalReviewCommentCounts;
		}

		public void setTotalReviewCommentCounts(int totalReviewCommentCounts) {
			this.totalReviewCommentCounts = totalReviewCommentCounts;
		}

	}
}
