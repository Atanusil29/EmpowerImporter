package com.gantrex.rest.model.empower.document;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Document {
	@JsonProperty("abandonedDate")
	private Object abandonedDate;
	@JsonProperty("accessGroupNames")
	private List<String> accessGroupNames = null;
	@JsonProperty("appId")
	private String appId;
	@JsonProperty("applicationName")
	private String applicationName;
	@JsonProperty("busDocId")
	private Object busDocId;
	@JsonProperty("creationDate")
	private Date creationDate;
	@JsonProperty("deleted")
	private Boolean deleted;
	@JsonProperty("docId")
	private String docId;
	@JsonProperty("docTags")
	private List<String> docTags;
	@JsonProperty("documentVersion")
	private String documentVersion;
	@JsonProperty("editorVersion")
	private String editorVersion;
	@JsonProperty("engineVersion")
	private String engineVersion;
	@JsonProperty("exceedsTotal")
	private Boolean exceedsTotal;
	@JsonProperty("exportDate")
	private Date exportDate;
	@JsonProperty("fileName")
	private String fileName;
	@JsonProperty("importDate")
	private Date importDate;
	@JsonProperty("lastEditDate")
	private Date lastEditDate;
	@JsonProperty("lastSaveDate")
	private Date lastSaveDate;
	@JsonProperty("limit")
	private Long limit;
	@JsonProperty("offset")
	private Long offset;
	@JsonProperty("ownerIds")
	private Object ownerIds;
	@JsonProperty("packageFileName")
	private String packageFileName;
	@JsonProperty("packageVersion")
	private String packageVersion;
	@JsonProperty("previewPubFile")
	private String previewPubFile;
	@JsonProperty("serviceName")
	private Object serviceName;
	@JsonProperty("serviceVersion")
	private Object serviceVersion;
	@JsonProperty("total")
	private Long total;
	@JsonProperty("userId")
	private String userId;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("abandonedDate")
	public Object getAbandonedDate() {
		return abandonedDate;
	}

	@JsonProperty("abandonedDate")
	public void setAbandonedDate(Object abandonedDate) {
		this.abandonedDate = abandonedDate;
	}

	@JsonProperty("accessGroupNames")
	public List<String> getAccessGroupNames() {
		return accessGroupNames;
	}

	@JsonProperty("accessGroupNames")
	public void setAccessGroupNames(List<String> accessGroupNames) {
		this.accessGroupNames = accessGroupNames;
	}

	@JsonProperty("appId")
	public String getAppId() {
		return appId;
	}

	@JsonProperty("appId")
	public void setAppId(String appId) {
		this.appId = appId;
	}

	@JsonProperty("applicationName")
	public String getApplicationName() {
		return applicationName;
	}

	@JsonProperty("applicationName")
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	@JsonProperty("busDocId")
	public Object getBusDocId() {
		return busDocId;
	}

	@JsonProperty("busDocId")
	public void setBusDocId(Object busDocId) {
		this.busDocId = busDocId;
	}

	@JsonProperty("creationDate")
	public Date getCreationDate() {
		return creationDate;
	}

	@JsonProperty("creationDate")
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@JsonProperty("deleted")
	public Boolean getDeleted() {
		return deleted;
	}

	@JsonProperty("deleted")
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@JsonProperty("docId")
	public String getDocId() {
		return docId;
	}

	@JsonProperty("docId")
	public void setDocId(String docId) {
		this.docId = docId;
	}

	@JsonProperty("docTags")
	public List<String> getDocTags() {
		return docTags;
	}

	@JsonProperty("docTags")
	public void setDocTags(List<String> docTags) {
		this.docTags = docTags;
	}

	@JsonProperty("documentVersion")
	public String getDocumentVersion() {
		return documentVersion;
	}

	@JsonProperty("documentVersion")
	public void setDocumentVersion(String documentVersion) {
		this.documentVersion = documentVersion;
	}

	@JsonProperty("editorVersion")
	public String getEditorVersion() {
		return editorVersion;
	}

	@JsonProperty("editorVersion")
	public void setEditorVersion(String editorVersion) {
		this.editorVersion = editorVersion;
	}

	@JsonProperty("engineVersion")
	public String getEngineVersion() {
		return engineVersion;
	}

	@JsonProperty("engineVersion")
	public void setEngineVersion(String engineVersion) {
		this.engineVersion = engineVersion;
	}

	@JsonProperty("exceedsTotal")
	public Boolean getExceedsTotal() {
		return exceedsTotal;
	}

	@JsonProperty("exceedsTotal")
	public void setExceedsTotal(Boolean exceedsTotal) {
		this.exceedsTotal = exceedsTotal;
	}

	@JsonProperty("exportDate")
	public Date getExportDate() {
		return exportDate;
	}

	@JsonProperty("exportDate")
	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}

	@JsonProperty("fileName")
	public String getFileName() {
		return fileName;
	}

	@JsonProperty("fileName")
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@JsonProperty("importDate")
	public Date getImportDate() {
		return importDate;
	}

	@JsonProperty("importDate")
	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	@JsonProperty("lastEditDate")
	public Date getLastEditDate() {
		return lastEditDate;
	}

	@JsonProperty("lastEditDate")
	public void setLastEditDate(Date lastEditDate) {
		this.lastEditDate = lastEditDate;
	}

	@JsonProperty("lastSaveDate")
	public Date getLastSaveDate() {
		return lastSaveDate;
	}

	@JsonProperty("lastSaveDate")
	public void setLastSaveDate(Date lastSaveDate) {
		this.lastSaveDate = lastSaveDate;
	}

	@JsonProperty("limit")
	public Long getLimit() {
		return limit;
	}

	@JsonProperty("limit")
	public void setLimit(Long limit) {
		this.limit = limit;
	}

	@JsonProperty("offset")
	public Long getOffset() {
		return offset;
	}

	@JsonProperty("offset")
	public void setOffset(Long offset) {
		this.offset = offset;
	}

	@JsonProperty("ownerIds")
	public Object getOwnerIds() {
		return ownerIds;
	}

	@JsonProperty("ownerIds")
	public void setOwnerIds(Object ownerIds) {
		this.ownerIds = ownerIds;
	}

	@JsonProperty("packageFileName")
	public String getPackageFileName() {
		return packageFileName;
	}

	@JsonProperty("packageFileName")
	public void setPackageFileName(String packageFileName) {
		this.packageFileName = packageFileName;
	}

	@JsonProperty("packageVersion")
	public String getPackageVersion() {
		return packageVersion;
	}

	@JsonProperty("packageVersion")
	public void setPackageVersion(String packageVersion) {
		this.packageVersion = packageVersion;
	}

	@JsonProperty("previewPubFile")
	public String getPreviewPubFile() {
		return previewPubFile;
	}

	@JsonProperty("previewPubFile")
	public void setPreviewPubFile(String previewPubFile) {
		this.previewPubFile = previewPubFile;
	}

	@JsonProperty("serviceName")
	public Object getServiceName() {
		return serviceName;
	}

	@JsonProperty("serviceName")
	public void setServiceName(Object serviceName) {
		this.serviceName = serviceName;
	}

	@JsonProperty("serviceVersion")
	public Object getServiceVersion() {
		return serviceVersion;
	}

	@JsonProperty("serviceVersion")
	public void setServiceVersion(Object serviceVersion) {
		this.serviceVersion = serviceVersion;
	}

	@JsonProperty("total")
	public Long getTotal() {
		return total;
	}

	@JsonProperty("total")
	public void setTotal(Long total) {
		this.total = total;
	}

	@JsonProperty("userId")
	public String getUserId() {
		return userId;
	}

	@JsonProperty("userId")
	public void setUserId(String userId) {
		this.userId = userId;
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
