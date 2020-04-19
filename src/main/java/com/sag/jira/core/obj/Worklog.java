package com.sag.jira.core.obj;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Worklog {

  private String alias;

  private String comment;
  private String timeSpent;
  private String timeSpentSeconds;
  private String createDate;

  public Worklog() {}

  public String getAlias() {
    return alias;
  }

  public String getComment() {
    return comment;
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

  public String getTimeSpent() {
    return timeSpent;
  }

  public String getTimeSpentSeconds() {
    return timeSpentSeconds;
  }

  public void setAlias(final String alias) {
    this.alias = alias;
  }

  public void setComment(final String comment) {
    this.comment = comment;
  }

  public void setCreateDate(final String createDate) {
    this.createDate = createDate;
  }

  public void setTimeSpent(final String timeSpent) {
    this.timeSpent = timeSpent;
  }

  public void setTimeSpentSeconds(final String timeSpentSeconds) {
    this.timeSpentSeconds = timeSpentSeconds;
  }

}
