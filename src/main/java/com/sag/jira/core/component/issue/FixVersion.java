package com.sag.jira.core.component.issue;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sag.jira.core.obj.FixVersions;
import com.sag.jira.util.JiraRestConfig;

public class FixVersion extends iTracRoot {
	private List<FixVersions> fixVersionList;

	public FixVersion(String issueId, JSONObject jsonObjectForIssue) throws JSONException {
		try {
			initialized(issueId, jsonObjectForIssue, JiraRestConfig.ISSUE_FIXVERSIONS_REST_PATH);
			readFixVersions();
		} catch (JSONException e) {
			throw new JSONException("Fix version not found!");
		}
	}

	public String getFixVersions() {
		StringBuilder fixversions = new StringBuilder();
		if (fixVersionList != null) {
			for (int i = 0; i < fixVersionList.size(); i++) {
				FixVersions fixVersionsHandler = fixVersionList.get(i);
				fixversions.append(fixVersionsHandler.getVersion());
				if (i < fixVersionList.size() - 1) {
					fixversions.append(",");

				}
			}
		}
		return fixversions.toString().isEmpty() ? JiraRestConfig.Common.NOT_AVAILABLE : fixversions.toString();
	}

	private void readFixVersions() throws JSONException {

		fixVersionList = new ArrayList<>();
		JSONArray optJSONArray = jsonObject.optJSONArray("fixVersions");
		if (optJSONArray != null) {

			for (int j = 0; j < optJSONArray.length(); j++) {
				FixVersions handler = new FixVersions();
				JSONObject version = optJSONArray.getJSONObject(j);
				handler.setDescription(version.optString("description"));
				handler.setVersion(version.optString("name"));
				handler.setReleasedDate(version.optString("releaseDate"));
				fixVersionList.add(handler);
			}
		}
	}

}
