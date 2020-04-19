package com.sag.jira.core.component;

import java.util.Map;

import org.codehaus.jettison.json.JSONException;

import com.sag.jira.core.JiraRestCore;
import com.sag.jira.core.component.parser.ComponentsParser;
import com.sag.jira.util.JiraRestConfig;

public class Component extends JiraRestCore {
	private ComponentsParser parser;

	public Component(String assetId) {
		if (parser == null) {
			get(JiraRestConfig.getProjectUrl(assetId));
			try {
				parser = new ComponentsParser(clientResponse);
			} catch (JSONException e) {
			}
		}
	}

	public Map<String, String> getComponentsWithDescription() {
		return parser.getComponentsWithDescriptions();
	}

	public String getComponents() {
		return parser.getComponents();
	}

}
