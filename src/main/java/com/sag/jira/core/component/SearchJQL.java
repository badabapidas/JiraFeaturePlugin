package com.sag.jira.core.component;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.codehaus.jettison.json.JSONException;

import com.sag.jira.core.JiraRestCore;
import com.sag.jira.core.component.parser.SearchJqlParser;
import com.sag.jira.util.JiraRestConfig;
import com.sag.jira.util.JiraUtils;

public class SearchJQL extends JiraRestCore {

	private int maxThreadCount = 0;
	private ExecutorService executor;
	private final String searchJql;
	private SearchJqlParser parser;
	private int startAt = 2;
	private final int maxResults = 10;

	private class SearchRunnable implements Runnable {
		private final String searchJql;
		private final int startAt;

		public SearchRunnable(final String searchJql, final int startAt) {
			this.searchJql = searchJql;
			this.startAt = startAt;
		}

		@Override
		public void run() {
			if (parser == null) {
				get(JiraRestConfig.getSearchJqlUrl(searchJql, startAt, maxResults));
				try {
					parser = new SearchJqlParser(clientResponse, true);
				} catch (final JSONException e) {
				}
			}
		}
	}

	public SearchJQL(final String searchJql) {
		this.searchJql = searchJql;
		logger.info("Search process has started, depending on the number of querie results, it might take time ...");
		// To get the total count one request has to be made first so we are fetching
		// only 2 records
		get(JiraRestConfig.getSearchJqlUrl(searchJql, 0, 2));
		try {
			parser = new SearchJqlParser(clientResponse, false);
			doThreadSearch();
		} catch (final JSONException e) {
		}
	}

	private void doThreadSearch() {
		// if found more then 2 records start a thread pool
		if (getTotalSearchCount() > 2) {
			final long currentTimeMillis = System.currentTimeMillis();
			logger.info("Start time: " + JiraUtils.getCurrentTime());
			initializeThreadCount();
			if (null != executor) {
				for (int i = 0; i < maxThreadCount; i++) {

					final Runnable worker = new SearchRunnable(searchJql, startAt);
					updateStartingPoint();
					executor.execute(worker);
				}
				executor.shutdown();
				while (!executor.isTerminated()) {
				}
				logger.info("Finish time: " + JiraUtils.getCurrentTime());
				logger.info("Total time taken to perform the search :"
						+ ((System.currentTimeMillis() - currentTimeMillis) / 1000) + " secs");
				logger.info("Finished all threads for " + parser.getAllIssuesSize() + " results.");
			}
		}
	}

	public Set<String> getAllItracsById() {
		return parser.getAllItracsById();
	}

	public int getMaxSearchCount() {
		return Integer.parseInt(parser.getMaxSearchCount());
	}

	public Set<Issue> getSearchResults() {
		return parser.getAllIssues();
	}

	public int getTotalSearchCount() {
		return Integer.parseInt(parser.getTotal());
	}

	private void initializeThreadCount() {
		final int total = getTotalSearchCount();
		logger.info("Total search results found: " + total);
		if (total > maxResults) {
			maxThreadCount = (total / maxResults) + 1;
			logger.info("Thread spawning: " + maxThreadCount + " to make search faster");
			executor = Executors.newFixedThreadPool(maxThreadCount);
		} else if (total <= maxResults) {
			logger.info("No thread spawning as the no of result is only " + total);
			get(JiraRestConfig.getSearchJqlUrl(searchJql, startAt, total));
			try {
				parser = new SearchJqlParser(clientResponse, true);
			} catch (final JSONException e) {
			}
		}
	}

	private void updateStartingPoint() {
		if (startAt < getTotalSearchCount()) {
			startAt += maxResults;
		}
	}
}
