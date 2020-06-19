package com.sag.jira.core.component.parser;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sag.jira.core.response.ItracReviewResponseBuilder;
import com.sag.jira.core.response.ItracReviewResponseBuilder.ReviewResponse;
import com.sag.jira.core.response.ResponseBuilder;
import com.sag.jira.util.JiraRestConfig;
import com.sun.jersey.api.client.ClientResponse;

public class ReviewParser extends JiraParser {

//	private Map<String, Integer> reviewCommitCountHistory = new HashMap<>();
	private int reviewCommentCounts = 0;
	private ReviewResponse response = null;
	private String itracId = null;
	private Map<String, Map<String, Object>> allReviewHistory = new HashMap<>();

	public ReviewParser(ClientResponse response, String itracId) throws JSONException {
		this.itracId = itracId;
		this.response = null;
		parseResponse(response);
		if (isValidJsonObject(jsonObject)) {
			findReviewDetails();
			populateResponse();
		}
	}

	private void populateResponse() {
		ItracReviewResponseBuilder reviewResponseBuilder = ResponseBuilder.getReviewResponseBuilder();
		response = reviewResponseBuilder.setReviewMetrics(allReviewHistory).setTotalReviewCounts(reviewCommentCounts)
				.setItracId(itracId).build();
	}

	private void findReviewDetails() {
		JSONArray details = jsonObject.optJSONArray(JiraRestConfig.Review.DETAIL);
		if (isValidJsonArray(details)) {
			for (int i = 0; i < details.length(); i++) {
				JSONObject detail = details.optJSONObject(i);
				findReviews(detail);
			}
		}
	}

	private void findReviews(JSONObject detail) {
		JSONArray reviews = detail.optJSONArray(JiraRestConfig.Review.REVIEWS);
		if (isValidJsonArray(reviews)) {
			for (int i = 0; i < reviews.length(); i++) {
				Map<String, Object> reviewDetails = new HashMap<>();
				JSONObject review = reviews.optJSONObject(i);
				String cr_id = review.optString(JiraRestConfig.Common.ID);
				int reviewCount = review.optInt(JiraRestConfig.Review.COMMENT_COUNT);
				reviewCommentCounts += review.optInt(JiraRestConfig.Review.COMMENT_COUNT);

				JSONArray reviewers = review.optJSONArray(JiraRestConfig.Review.REVIEWERS);
				StringBuilder sb = new StringBuilder();
				if (isValidJsonArray(reviewers)) {
					for (int j = 0; j < reviewers.length(); j++) {
						sb.append(reviewers.optJSONObject(j).optString(JiraRestConfig.Common.NAME));
						if (j < reviewers.length() - 1) {
							sb.append(",");
						}
					}
				}
				reviewDetails.put(JiraRestConfig.Review.COMMENT_COUNT, reviewCount);
//				reviewDetails.put(JiraRestConfig.Review.STATE, state);
//				reviewDetails.put(JiraRestConfig.Commit.AUTHOR,
//						review.optJSONObject(JiraRestConfig.Commit.AUTHOR).optString(JiraRestConfig.Common.NAME));
//				reviewDetails.put(JiraRestConfig.Review.REVIEWERS,
//						sb.toString().isEmpty() ? "NA" : sb.toString());
				allReviewHistory.put(cr_id, reviewDetails);
			}
		}
	}

	public ReviewResponse populateReviewResponse() {
		return response;
	}

}
