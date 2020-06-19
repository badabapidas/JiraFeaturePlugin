package com.sag.jira.core.response;

public class ItracTraceabilityResponseBuilder {
	protected static ItracTraceabilityResponseBuilder INSTANCE = null;
	public static ItracTraceabilityResponseBuilder getInstance() {
		if (INSTANCE == null) {
			return new ItracTraceabilityResponseBuilder();
		}
		return INSTANCE;
	}

}
