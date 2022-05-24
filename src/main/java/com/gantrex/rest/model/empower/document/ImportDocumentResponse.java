package com.gantrex.rest.model.empower.document;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gantrex.rest.model.empower.Header;

public class ImportDocumentResponse {
	@JsonProperty("header")
	private Header header;
	@JsonProperty("body")
	private Body body;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("header")
	public Header getHeader() {
		return header;
	}

	@JsonProperty("header")
	public void setHeader(Header header) {
		this.header = header;
	}

	@JsonProperty("body")
	public Body getBody() {
		return body;
	}

	@JsonProperty("body")
	public void setBody(Body body) {
		this.body = body;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
