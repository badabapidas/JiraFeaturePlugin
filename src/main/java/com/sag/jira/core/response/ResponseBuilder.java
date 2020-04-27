/**
 * Singleton design pattern
 */
package com.sag.jira.core.response;

public class ResponseBuilder {

	public static ItracReviewResponseBuilder getReviewResponseBuilder() {
		return new ItracReviewResponseBuilder();
	}

	public static ItracComitResponseBuilder getCommitResponseBuilder() {
		return ItracComitResponseBuilder.getInstance();
	}

	public static ItracWorkLogResponseBuilder getWorklogResponseBuilder() {
		return ItracWorkLogResponseBuilder.getInstance();
	}
}
