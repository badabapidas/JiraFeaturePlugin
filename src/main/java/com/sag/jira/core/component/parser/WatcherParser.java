package com.sag.jira.core.component.parser;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sag.jira.core.obj.Watcher;
import com.sag.jira.util.JiraRestConfig;
import com.sun.jersey.api.client.ClientResponse;

public class WatcherParser extends JiraParser {

	private List<Watcher> watcherList = new ArrayList<>();

	public WatcherParser(ClientResponse response) throws JSONException {
		parseResponse(response);
		readWatchers();
	}

	private void readWatchers() {
		JSONArray watcherArray = jsonObject.optJSONArray(JiraRestConfig.Watcher.WATCHERS);
		if (watcherArray != null) {
			for (int i = 0; i < watcherArray.length(); i++) {
				Watcher watcher = new Watcher();
				JSONObject watcherJson = watcherArray.optJSONObject(i);
				watcher.setAlias(watcherJson.optString(JiraRestConfig.Common.NAME));
				watcher.setDisplayName(watcherJson.optString(JiraRestConfig.Watcher.DISPLAY_NAME));
				watcher.setRestUrl(watcherJson.optString(JiraRestConfig.Common.SELF));
				watcherList.add(watcher);
			}
		}
	}

	public List<Watcher> getWatchers() {
		return watcherList;
	}

	public String getWatchCount() {
		return fetchAndValidateResponse(JiraRestConfig.Watcher.WATCH_COUNT);
	}

	public boolean isWatching() {
		return Boolean.parseBoolean(fetchAndValidateResponse(JiraRestConfig.Watcher.IS_WATCHING));
	}

	public String getWatcherNames() {
		StringBuilder names = new StringBuilder();
		if (watcherList != null) {
			for (int i = 0; i < watcherList.size(); i++) {
				Watcher watcherHandler = watcherList.get(i);
				names.append(watcherHandler.getDisplayName());
				if (i < watcherList.size() - 1) {
					names.append(",");
				}
			}
		}
		return names.toString();
	}
}
