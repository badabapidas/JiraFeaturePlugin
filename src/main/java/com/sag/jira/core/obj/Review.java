package com.sag.jira.core.obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONObject;

import com.sag.jira.core.IReviewRestCore;
import com.sag.jira.core.component.parser.ReviewParser;
import com.sag.jira.core.response.ItracReviewResponseBuilder.ReviewResponse;
import com.sag.jira.util.JiraRestConfig;

public class Review extends IReviewRestCore {

	private String id = JiraRestConfig.Common.EMPTY;
	private String url = JiraRestConfig.Common.EMPTY;
	private String state = JiraRestConfig.Common.EMPTY;
	private String title = JiraRestConfig.Common.EMPTY;
	private String issueGenaratedId = JiraRestConfig.Common.EMPTY;
	private static JSONObject jsonResponse;
	private ReviewParser parser;

	public ReviewResponse getReviewMetrics(String itracId) {
		initialize(itracId);
		return parser.populateReviewResponse();
	}

	private void initialize(String itracId) {
		if (parser == null) {
			get(JiraRestConfig.getReviewUrlFromIssue(issueGenaratedId));
			try {
				parser = new ReviewParser(clientResponse, itracId);
				jsonResponse = parser.getJsonResponse();
			} catch (Exception e) {
			}
		}
	}

	public int getReviewCommentsCount(String itracId) {
		ReviewResponse reviewMetrics = getReviewMetrics(itracId);
		return (reviewMetrics != null) ? reviewMetrics.getTotalReviewCounts() : -1;
	}

	public Map<String, Map<String, Object>> getAllReviewMetrics(String itracId) {
		ReviewResponse reviewMetrics = getReviewMetrics(itracId);
		return (reviewMetrics != null) ? reviewMetrics.getAllReviewHistory()
				: new HashMap<String, Map<String, Object>>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIssueGenaratedId() {
		return issueGenaratedId;
	}

	public void setIssueGenaratedId(String issueGenaratedId) {
		this.issueGenaratedId = issueGenaratedId;
	}

	public static void setJsonResponse(JSONObject jsonResponse) {
		Review.jsonResponse = jsonResponse;
	}

	public class Ireview {

		private int totalCount = 0;
		private String url = "";

		List<Review> reviews = new ArrayList();

		public int getTotalCount() {
			return totalCount;
		}

		public void setTotalCount(int totalCount) {
			this.totalCount = totalCount;
		}

		public List<Review> getReviews() {
			return reviews;
		}

		public void addReview(Review review) {
			this.reviews.add(review);
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public boolean isReviewDone() {
			return (getTotalCount() > 0) ? true : false;
		}
	}

}
