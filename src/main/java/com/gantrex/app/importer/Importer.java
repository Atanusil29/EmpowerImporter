package com.gantrex.app.importer;

import java.util.List;

import com.gantrex.exceptions.TaskException;
import com.gantrex.rest.model.empower.document.ImportDocumentResponse;

public interface Importer {

	Boolean isTimedOut();

	void refresh() throws TaskException;

	ImportDocumentResponse uploadDocument(byte[] bytes, String fileName, String appID) throws TaskException;

	ImportDocumentResponse uploadDocument(byte[] bytes, String fileName, String appID, String busDocID)
			throws TaskException;

	ImportDocumentResponse uploadDocument(byte[] bytes, String fileName, String appID, String busDocID,
			List<String> docTags) throws TaskException;

	ImportDocumentResponse uploadDocument(byte[] bytes, String fileName, String appID, String busDocID,
			List<String> docTags, List<String> ownerIDs) throws TaskException;

}
