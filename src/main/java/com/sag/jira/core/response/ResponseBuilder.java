package com.sag.jira.core.response;

public class ResponseBuilder {

	public static ItracReviewResponseBuilder getReviewResponseBuilder() {
		return new ItracReviewResponseBuilder();
	}

	public static ItracComitResponseBuilder getCommitResponseBuilder() {
		return ItracComitResponseBuilder.getInstance();
	}
}
