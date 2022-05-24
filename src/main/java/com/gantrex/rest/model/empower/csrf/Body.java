package com.gantrex.rest.model.empower.csrf;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Body implements Serializable {

	private static final long serialVersionUID = 3476394899558143357L;

	@JsonProperty("csrfHeader")
	private String csrfHeader;
	@JsonProperty("csrfParameter")
	private String csrfParameter;
	@JsonProperty("csrfToken")
	private String csrfToken;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("csrfHeader")
	public String getCsrfHeader() {
		return csrfHeader;
	}

	@JsonProperty("csrfHeader")
	public void setCsrfHeader(String csrfHeader) {
		this.csrfHeader = csrfHeader;
	}

	@JsonProperty("csrfParameter")
	public String getCsrfParameter() {
		return csrfParameter;
	}

	@JsonProperty("csrfParameter")
	public void setCsrfParameter(String csrfParameter) {
		this.csrfParameter = csrfParameter;
	}

	@JsonProperty("csrfToken")
	public String getCsrfToken() {
		return csrfToken;
	}

	@JsonProperty("csrfToken")
	public void setCsrfToken(String csrfToken) {
		this.csrfToken = csrfToken;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;

	}
}
