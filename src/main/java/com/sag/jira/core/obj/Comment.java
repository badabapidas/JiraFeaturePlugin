package com.sag.jira.core.obj;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Comment {

	private String alias;
	private String comment;
	private String createDate;

	public Comment() {
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreateDate() {
		final String regex = "[0-9-]+";

		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(createDate);

		while (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}
