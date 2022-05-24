package com.gantrex.app.importer;

import java.util.Date;
import java.util.List;

import com.gantrex.config.ConfigurationManager;
import com.gantrex.exceptions.AuthenticationFailedException;
import com.gantrex.exceptions.ConfigNotInitializedException;
import com.gantrex.exceptions.DocumentManagementException;
import com.gantrex.exceptions.TaskException;
import com.gantrex.rest.impl.Authenticator;
import com.gantrex.rest.impl.DocumentManager;
import com.gantrex.rest.model.empower.document.ImportDocumentResponse;
import com.gantrex.utils.Utils;

public class ImporterImpl implements Importer {

	private Date expirationDate;
	private String authToken;
	private final static Integer shift = 5;
	private DocumentManager docMan;

	@Override
	public Boolean isTimedOut() {
		return (null == expirationDate) || expirationDate.compareTo(Utils.dateWithShift(shift)) < 0;
	}

	@Override
	public void refresh() throws TaskException {
		init();
	}

	private void init() throws TaskException {
		Authenticator authenticator;

		try {
			String user = ConfigurationManager.getInstance().getEmpowerUser();
			String password = ConfigurationManager.getInstance().getEmpowerUserPassword();
			authenticator = new Authenticator(user, password);
			authenticator.init();
			if (authenticator.hasToken()) {
				expirationDate = authenticator.getExpDate();
				authToken = authenticator.getAuthToken();
				docMan = new DocumentManager();
				docMan.init(authToken);
			}
		} catch (AuthenticationFailedException e) {
			throw new TaskException(e.getMessage(), e);
		} catch (ConfigNotInitializedException e) {
			throw new TaskException(e.getMessage(), e);
		} catch (DocumentManagementException e) {
			throw new TaskException(e.getMessage(), e);
		}
	}

	@Override
	public ImportDocumentResponse uploadDocument(byte[] bytes, String fileName, String appID) throws TaskException {
		return uploadDocument(bytes, fileName, appID, null);

	}

	@Override
	public ImportDocumentResponse uploadDocument(byte[] bytes, String fileName, String appID, String busDocID)
			throws TaskException {
		return uploadDocument(bytes, fileName, appID, busDocID, null);

	}

	@Override
	public ImportDocumentResponse uploadDocument(byte[] bytes, String fileName, String appID, String busDocID,
			List<String> docTags) throws TaskException {
		return uploadDocument(bytes, fileName, appID, busDocID, docTags, null);

	}

	@Override
	public ImportDocumentResponse uploadDocument(byte[] bytes, String fileName, String appID, String busDocID,
			List<String> docTags, List<String> ownerIDs) throws TaskException {

		try {
			return docMan.importDocument(bytes, fileName, appID, busDocID, docTags, ownerIDs);
		} catch (DocumentManagementException e) {
			throw new TaskException("Failed To Upload file " + fileName, e);
		}

	}

}
