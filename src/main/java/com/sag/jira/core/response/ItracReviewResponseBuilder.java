/**
 * Used builder pattern
 */
package com.sag.jira.core.response;

import java.util.HashMap;
import java.util.Map;

public class ItracReviewResponseBuilder {
	protected Response response = new Response();

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

	public Response build() {
		return response;
	}

	public class Response {
		protected String itracId;
		protected int totalReviewCounts;
		protected Map<String, Map<String, Object>> allReviewHistory = new HashMap<>();

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

		@Override
		public String toString() {
			return "Response [itracId=" + itracId + ", totalReviewCounts=" + totalReviewCounts + ", allReviewHistory="
					+ allReviewHistory + "]";
		}

	}
}
