package com.gantrex.rest.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.configuration2.ex.ConfigurationException;

import com.gantrex.exceptions.AuthenticationFailedException;
import com.gantrex.exceptions.ConfigNotInitializedException;
import com.gantrex.exceptions.RestRequestFailedException;
import com.gantrex.rest.model.response.AuthenticationResponse;

public class Authenticator {

	private String user;
	private String pass;
	private String authToken;
	private String tokenType;
	private Date expirationDate;

	public static void authenticate() {

	}

	public Authenticator(String user, String pass) {
		this.user = user;
		this.pass = pass;
	}

	public void init() throws AuthenticationFailedException {

		OTDSRestService service;
		try {
			service = new OTDSRestService();
			AuthenticationResponse response = service.authenticateForEmpower(user, pass);
			authToken = null;

			authToken = Optional.ofNullable(response).map(r -> r.getAccessToken()).orElse(null);
			if (hasToken()) {
				tokenType = response.getTokenType();
				expirationDate = new Date(System.currentTimeMillis() + (response.getExpiresIn() * 1000));
			}
		} catch (ConfigNotInitializedException e) {
			throw new AuthenticationFailedException(e.getMessage(), e);
		} catch (ConfigurationException e) {
			throw new AuthenticationFailedException(e.getMessage(), e);
		} catch (IOException e) {
			throw new AuthenticationFailedException(e.getMessage(), e);
		} catch (RestRequestFailedException e) {
			throw new AuthenticationFailedException(e.getMessage(), e);
		}

	}

	public String getAuthToken() {
		return (authToken == null ? "" : authToken);
	}

	public Boolean hasToken() {
		return authToken != null;
	}

	public Date getExpDate() {
		return expirationDate;
	}

	public String getTokenType() {
		return tokenType;
	}

}
