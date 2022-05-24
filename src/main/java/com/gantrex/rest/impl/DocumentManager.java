package com.gantrex.rest.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gantrex.exceptions.AuthenticationFailedException;
import com.gantrex.exceptions.CSRFNullException;
import com.gantrex.exceptions.ConfigNotInitializedException;
import com.gantrex.exceptions.DocumentManagementException;
import com.gantrex.exceptions.RestRequestFailedException;
import com.gantrex.rest.model.empower.csrf.CSRFResponse;
import com.gantrex.rest.model.empower.document.ImportDocumentResponse;

public class DocumentManager {

	private String csrfToken;
	private EmpowerRestService service;
	private final static Logger log = LoggerFactory.getLogger(DocumentManager.class);

	public void init(String authToken) throws DocumentManagementException {
		try {

			service = new EmpowerRestService();
			CSRFResponse response = service.getCSRFToken(authToken);
			csrfToken = Optional.ofNullable(response).map(r -> r.getBody()).map(body -> body.getCsrfToken())
					.orElse(null);
		} catch (AuthenticationFailedException e) {
			throw new DocumentManagementException(e.getMessage(), e);
		} catch (RestRequestFailedException e) {
			throw new DocumentManagementException(e.getMessage(), e);
		} catch (CSRFNullException e) {
			throw new DocumentManagementException(e.getMessage(), e);
		} catch (ConfigNotInitializedException e) {
			throw new DocumentManagementException(e.getMessage(), e);
		} catch (ConfigurationException e) {
			throw new DocumentManagementException(e.getMessage(), e);
		}
	}

	public void importDocument(File file, String appID, String busDocID, List<String> docTags, List<String> ownerIDs)
			throws DocumentManagementException {
		try {
			if (isAlive() && hasToken()) {
				service.importDocument(file, appID, busDocID, docTags, ownerIDs);
			} else {
				log.error("DocumentManager is not alive of does not have token");
				throw new DocumentManagementException("DocumentManager is not alive or does not have csrf token");
			}
		} catch (IOException e) {
			throw new DocumentManagementException(e.getMessage(), e);
		} catch (RestRequestFailedException e) {
			throw new DocumentManagementException(e.getMessage(), e);
		} catch (AuthenticationFailedException e) {
			throw new DocumentManagementException(e.getMessage(), e);
		}
	}

	public ImportDocumentResponse importDocument(byte[] bytes, String fileName, String appID, String busDocID, List<String> docTags,
			List<String> ownerIDs) throws DocumentManagementException {
		try {
			if (isAlive() && hasToken()) {
				return service.importDocument(bytes, fileName, appID, busDocID, docTags, ownerIDs);
			} else {
				log.error("DocumentManager is not alive of does not have token");
				throw new DocumentManagementException("DocumentManager is not alive or does not have csrf token");
			}
		} catch (IOException e) {
			throw new DocumentManagementException(e.getMessage(), e);
		} catch (RestRequestFailedException e) {
			throw new DocumentManagementException(e.getMessage(), e);
		} catch (AuthenticationFailedException e) {
			throw new DocumentManagementException(e.getMessage(), e);
		}
	}

	public byte[] exportDocument(String docID, Boolean preserveDoc) throws DocumentManagementException {
		try {
			if (isAlive() && hasToken()) {
				return Optional.ofNullable(service.exportDocument(docID, preserveDoc))
						.orElseThrow(() -> new DocumentManagementException("Null Content Received from response body"));
			} else {
				log.error("DocumentManager is not alive or does not have csrf token");
				return null;
			}
		} catch (IOException e) {
			throw new DocumentManagementException(e.getMessage(), e);
		} catch (RestRequestFailedException e) {
			throw new DocumentManagementException(e.getMessage(), e);
		} catch (AuthenticationFailedException e) {
			throw new DocumentManagementException(e.getMessage(), e);
		}
	}

	public void exportAndSaveDocument(String docID, Boolean preserveDoc, String exportPath)
			throws DocumentManagementException {
		if (StringUtils.isEmpty(exportPath)) {
			throw new DocumentManagementException("Export path is empty for " + docID);
		}

		try {

			File file = new File(exportPath);
			if (isAlive() && hasToken()) {
				byte[] bytes = Optional.ofNullable(service.exportDocument(docID, preserveDoc))
						.orElseThrow(() -> new DocumentManagementException("Null Content Received from response body"));
				FileUtils.writeByteArrayToFile(file, bytes);
			} else {
				log.error("DocumentManager is not alive of does not have token");
				throw new DocumentManagementException("DocumentManager is not alive or does not have csrf token");
			}
		} catch (IOException e) {
			throw new DocumentManagementException(e.getMessage(), e);
		} catch (RestRequestFailedException e) {
			throw new DocumentManagementException(e.getMessage(), e);
		} catch (AuthenticationFailedException e) {
			throw new DocumentManagementException(e.getMessage(), e);
		}
	}

	public Boolean isAlive() {
		return service != null;
	}

	public Boolean hasToken() {
		return csrfToken != null;
	}

}
