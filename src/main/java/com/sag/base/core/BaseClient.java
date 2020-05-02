package com.sag.base.core;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sag.jira.util.PropertyUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.core.util.Base64;

public class BaseClient {

	private String username;
	private String password;
	private static Client client;
	protected ClientResponse clientResponse;
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected BaseClient() {

		if (client == null) {
			client = Client.create();
		}
		this.username = PropertyUtil.getJiraUsername();
		this.password = PropertyUtil.getJiraUserPassword();
	}

	protected static Client getClient() {
		return client;
	}

	private Builder getAuthenticated(final WebResource webresource) {
		final String auth = new String(Base64.encode(username + ":" + password));
		return webresource.header("Authorization", "Basic " + auth);
	}

	private String encodeUrl(final String requestUrl) throws URISyntaxException, MalformedURLException {
		URL url = new URL(requestUrl);
		final URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(),
				url.getQuery(), url.getRef());
		url = uri.toURL();
		return url.toString();
	}

	protected void get(String url) {
		try {
			url = encodeUrl(url);
			final WebResource resource = client.resource(url);
			final Builder authenticated = getAuthenticated(resource);
			final ClientResponse response = authenticated.type("application/json").accept("application/json")
					.get(ClientResponse.class);
			final int status = response.getStatus();
			if (status == 200) {
				clientResponse = response;
			} else {
//				logger.error("Response status:" + status);
			}
		} catch (MalformedURLException | URISyntaxException e) {
			logger.error("Response status:" + e.getMessage());
			clientResponse = null;
		}

	}

	protected void put(String url, final String data) {
		try {
			url = encodeUrl(url);
			final WebResource resource = client.resource(url);
			final Builder authenticated = getAuthenticated(resource);
			final ClientResponse response = authenticated.type("application/json").accept("application/json")
					.put(ClientResponse.class, data);
			final int status = response.getStatus();
			if (status == 200) {
				clientResponse = response;
			} else {
				logger.error("Response status:" + status);
			}
		} catch (MalformedURLException | URISyntaxException e) {
			logger.error("Response status:" + e.getMessage());
			clientResponse = null;
		}
	}

	protected ClientResponse post(String url, final String data) {
		try {
			url = encodeUrl(url);
			final WebResource resource = client.resource(url);
			final Builder authenticated = getAuthenticated(resource);
			final ClientResponse response = authenticated.type("application/x-www-form-urlencoded")
					.accept("application/json").post(ClientResponse.class, data);
			final int status = response.getStatus();
			if (status == 200) {
				clientResponse = response;
			} else {
				logger.error("Response status:" + status);
			}
		} catch (MalformedURLException | URISyntaxException e) {
			logger.error("Response status:" + e.getMessage());
			clientResponse = null;
		}
		return clientResponse;
	}

}
