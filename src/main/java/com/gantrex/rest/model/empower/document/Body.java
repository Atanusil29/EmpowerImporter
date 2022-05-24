package com.gantrex.rest.model.empower.document;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Body implements Serializable {

	private static final long serialVersionUID = 2920794492538206803L;

	@JsonProperty("document")
	private Document document;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("document")
	public Document getDocument() {
		return document;
	}

	@JsonProperty("document")
	public void setDocument(Document document) {
		this.document = document;
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
