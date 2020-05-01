package com.sag.jira.rule.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.sag.jira.core.component.iTrac;
import com.sag.jira.util.JiraRestConfig;

public class ReportBuilder {
	protected StringBuilder builder = new StringBuilder();
	protected String rulename = JiraRestConfig.Common.EMPTY;
	protected iTrac itrac = null;

	protected final static String H1_TAG_START = "<h1>";
	protected final static String H1_TAG_END = "</h1>";
	protected final static String TD_TAG_START = "<td>";
	protected final static String TD_TAG_END = "</td>";
	protected final static String BOLD_TAG_START = "<b>";
	protected final static String BOLD_TAG_END = "</b>";
	protected final static String TR_TAG_START = "<tr>";
	protected final static String TR_TAG_END = "</tr>";
	protected final static String ANCHOR_TAG_START = "<a>";
	protected final static String ANCHOR_TAG_END = "</a>";
	protected final static String TABLE_TAG_END = "</table>";

	protected final static String HEADER_KEY_ISSUE_ID = "Issue ID";
	protected final static String HEADER_KEY_ISSUE_SUMMARY = "Summary";
	protected final static String HEADER_KEY_ISSUE_TYPE = "Type";
	protected final static String HEADER_KEY_ISSUE_COMPONENT = "Component";
	protected final static String HEADER_KEY_LINES_ADDED = "Lines Added";
	protected final static String HEADER_KEY_LINES_REMOVED = "Lines Removed";
	protected final static String HEADER_KEY_REVIEW_COMMENTS_COUNT = "Review Comments";
	protected final static String HEADER_KEY_ISSUE_STATUS = "Status";
	protected final static String HEADER_KEY_ESTIMATED_TIME = "Estimated Time";
	protected final static String HEADER_KEY_WORKLOG_COUNT = "Worklog Counts";
	protected final static String HEADER_KEY_TOTAL_TIME_SPENT = "Time Spent";
	protected final static String HEADER_KEY_WHO_LOGGED = "Who logged";

	public static ItracMetricsReportBuilder getItracMetricBuilder() {
		return new ItracMetricsReportBuilder();
	}

	public StringBuilder getBuilder() {
		return builder;
	}

	public ReportBuilder setItrackey(iTrac itrac) {
		this.itrac = itrac;
		return this;
	}

	public static void writeToFile(final String fileContent, final String fileName) throws IOException {
		final String projectPath = System.getProperty("user.dir");
		final String tempFile = projectPath + File.separator + fileName;
		final File file = new File(tempFile);
		if (file.exists()) {
			file.delete();
		} else {
			file.getParentFile().mkdirs();
		}
		file.createNewFile();
		final OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
		final Writer writer = new OutputStreamWriter(outputStream);
		writer.write(fileContent);
		writer.close();

	}

}
