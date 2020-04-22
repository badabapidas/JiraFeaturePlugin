package com.sag.jira.core.component.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sag.jira.core.obj.Comment;
import com.sag.jira.util.JiraRestConfig;

public class CommentParser extends JiraParser {
	private List<Comment> allCommments = new ArrayList<>();
	protected final Logger log = LoggerFactory.getLogger(getClass());

	public CommentParser(JSONObject jsonObject) throws JSONException {
		if (isValidJsonObject(jsonObject)) {
			JSONArray allComments = jsonObject.optJSONArray(JiraRestConfig.Comment.COMMENT);
			parseJsonArrayForComments(allComments);
		}
	}

	private void parseJsonArrayForComments(JSONArray allComments) {
		if (isValidJsonArray(allComments)) {
			for (int i = 0; i < allComments.length(); i++) {
				Comment comment = new Comment();
				JSONObject commentJson = allComments.optJSONObject(i);
				comment.setComment(commentJson.optString(JiraRestConfig.Commit.BODY));
				comment.setCreateDate(commentJson.optString(JiraRestConfig.Commit.CREATED));
				JSONObject author = commentJson.optJSONObject(JiraRestConfig.Commit.AUTHOR);
				comment.setAlias(author.optString(JiraRestConfig.Common.NAME));
				allCommments.add(comment);
			}
		}
	}

	public void displayAllComments() {
		if (allCommments != null) {
			for (Comment comment : allCommments) {
				log.info("[" + comment.getCreateDate() + "] " + comment.getAlias() + " commented [***"
						+ comment.getComment() + "]");
			}
		}
	}

	public List<String> getAllCommentsFor(String alias) {
		Map<String, List<String>> commenFor = getEachComment();
		if (commenFor.containsKey(alias)) {
			return commenFor.get(alias);
		}
		return new ArrayList<>();
	}

	private Map<String, List<String>> getEachComment() {
		Map<String, List<String>> allComment = new HashMap<>();
		for (Comment comment : allCommments) {
			List<String> comments = new ArrayList<>();
			if (allComment.containsKey(comment.getAlias())) {
				comments = allComment.get(comment.getAlias());
				comments.add(comment.getComment());
			} else {
				comments.add(comment.getComment());
			}
			allComment.put(comment.getAlias(), comments);

		}
		return allComment;
	}

	public List<Comment> getAllComments() {
		return allCommments;
	}

}
