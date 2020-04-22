package com.sag.jira.core.component.issue;

import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sag.jira.core.component.parser.CommentParser;
import com.sag.jira.util.JiraRestConfig;

public class Comment extends iTracRoot {
	private CommentParser parser;

	public Comment(String issueId, JSONObject jsonObjectForIssue) throws JSONException {
		try {
			initialized(issueId, jsonObjectForIssue, JiraRestConfig.ISSUE_COMMENT_REST_PATH);
			parser = new CommentParser(jsonObject);
		} catch (JSONException e) {
		}
	}

	public String totalComment() {
		return fetchAndValidateResponse(JiraRestConfig.Comment.TOTAL);
	}

	public List<String> getAllCommentsFor(String alias) {
		return parser.getAllCommentsFor(alias);
	}

	public void displayAllComments() {
		parser.displayAllComments();
	}
}