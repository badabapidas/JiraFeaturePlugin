/**
 * Used builder pattern
 */
package com.sag.jira.core.response;

import java.util.HashMap;
import java.util.Map;

import com.sag.jira.core.response.ItracReviewResponseBuilder.ReviewResponse.STATUS;

public class ItracReviewResponseBuilder {
	protected ReviewResponse response = ReviewResponse.getDefaultResponse();

	public ItracReviewResponseBuilder setItracId(String itracId) {
		response.itracId = itracId;
		return this;
	}

	public ItracReviewResponseBuilder setTotalReviewCounts(int totalReviewCounts) {
		response.totalReviewCounts = totalReviewCounts;
		return this;
	}

	public ItracReviewResponseBuilder setReviewMetrics(Map<String, Map<String, Object>> allReviewHistory) {
		response.setAllReviewHistory(allReviewHistory);
		return this;
	}

	public ReviewResponse build() {
		if (response.getAllReviewHistory().size() > 0) {
			response.setStatus(STATUS.AVAILABLE);
		}
		return response;
	}

	public static class ReviewResponse {
		enum STATUS {
			AVAILABLE, NOT_AVAILABLE
		}

		protected String itracId;
		protected int totalReviewCounts;
		protected Map<String, Map<String, Object>> allReviewHistory = new HashMap<>();
		protected STATUS default_status = STATUS.NOT_AVAILABLE;

		public ReviewResponse() {
		}

		public String getItracId() {
			return itracId;
		}

		public int getTotalReviewCounts() {
			return totalReviewCounts;
		}

		public Map<String, Map<String, Object>> getAllReviewHistory() {
			return allReviewHistory;
		}

		public void setAllReviewHistory(Map<String, Map<String, Object>> allReviewHistory) {
			this.allReviewHistory = allReviewHistory;
		}

		public STATUS getStatus() {
			return default_status;
		}

		public void setStatus(STATUS default_status) {
			this.default_status = default_status;
		}

		@Override
		public String toString() {
			return "Response [itracId=" + itracId + ", totalReviewCounts=" + totalReviewCounts + ", allReviewHistory="
					+ allReviewHistory + "]";
		}

		public static ReviewResponse getDefaultResponse() {
			return new ReviewResponse();
		}
		

	}
}
