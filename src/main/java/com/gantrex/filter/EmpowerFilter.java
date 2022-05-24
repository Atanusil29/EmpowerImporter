package com.gantrex.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gantrex.app.importer.Importer;
import com.gantrex.app.importer.ImporterImpl;
import com.gantrex.config.ConfigurationManager;
import com.gantrex.exceptions.ConfigNotInitializedException;
import com.gantrex.exceptions.EmpowerFilterException;
import com.gantrex.exceptions.TaskException;
import com.gantrex.rest.model.empower.document.ImportDocumentResponse;

import streamserve.context.Context;
import streamserve.context.ContextFactory;
import streamserve.context.LogService;
import streamserve.context.VariablesCollection;
import streamserve.filter.Execute;

public class EmpowerFilter extends Filter implements Execute {

	private static final Logger log = LoggerFactory.getLogger(EmpowerFilter.class);
	private static Properties props;

	public void invoke(InputStream input, OutputStream output) {

		Context context = null;
		String filter = "EmpowerFilter";
		LogService exstreamLog = null;

		try {
			context = ContextFactory.acquireContext(ContextFactory.ServiceContextType);
			exstreamLog = context.getInterface(LogService.class);
			exstreamLog.log(filter, "Starting Empower Java Filter", streamserve.context.LogLevel.info);
			log.info("Starting Empower Java Filter");
			byte[] bytes = uploadContent(input, exstreamLog, filter, context);
			output.write(bytes);
			exstreamLog.log(filter, "Exiting the Empower Java filter", streamserve.context.LogLevel.info);			
			
		} catch (EmpowerFilterException e) {
			log.error(e.getMessage(), e);
			if (exstreamLog != null) {
				exstreamLog.log(filter, e.getMessage(), streamserve.context.LogLevel.error);
			}
			throw new RuntimeException(e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (exstreamLog != null) {
				exstreamLog.log(filter, e.getMessage(), streamserve.context.LogLevel.error);
			}
			throw new RuntimeException(e);
		} finally {
			if (context != null)
				ContextFactory.releaseContext(context);
			log.error("Exiting the Empower Java filter");
		}

	}

	private byte[] uploadContent(InputStream contentStream, LogService exstreamLog, String filter, Context context)
			throws EmpowerFilterException {
		byte[] bytes = this.readAll(contentStream, exstreamLog, filter);

		Importer importer = new ImporterImpl();
		try {

			VariablesCollection variables = context.getInterface(streamserve.context.VariablesCollection.class);

			String filename = Optional.ofNullable(variables.getVariable("$Filename"))
					.map(value -> value.getValue(String.class)).orElseThrow(() -> new EmpowerFilterException(
							"Business Document ($Filename) variable is not defined or value not set"));
			String busDocID = FilenameUtils.getBaseName(filename);
			String appID = ConfigurationManager.getInstance().getApplicationID();
			exstreamLog.log(filter, "Business Doc ID :" + busDocID + ", appID : " + appID,
					streamserve.context.LogLevel.debug);
			log.debug("Upload Content :: App ID : {}, BUsiness Doc ID : {}, FileName :{}", appID, busDocID, filename);

			if (importer.isTimedOut()) {
				exstreamLog.log(filter, "Authenticating OTDS/Empower", streamserve.context.LogLevel.debug);
				log.debug("Authenticating OTDS/Empower");
				importer.refresh();
			}
			ImportDocumentResponse importDocument = importer.uploadDocument(bytes, filename, appID, busDocID);
			String docID = Optional.ofNullable(importDocument).map(response -> response.getBody())
					.map(body -> body.getDocument()).map(doc -> doc.getDocId())
					.orElseThrow(() -> (new EmpowerFilterException("Doc ID is not retrievable from response")));
			exstreamLog.log(filter, "Document Imported with ID :" + docID, streamserve.context.LogLevel.info);
			log.debug("Document Imported with ID {}", docID);
			variables.setVariable("$Empower_DocID", docID);
		} catch (TaskException e) {
			throw new EmpowerFilterException("Failed to complete task", e);
		} catch (ConfigNotInitializedException e) {
			throw new EmpowerFilterException(e.getMessage(), e);
		}
		return bytes;

	}

	public static Properties getProperties() {
		return props;
	}

	public static void main(String[] args) {

		try {
			InputStream is = new FileInputStream(
					new File("C:\\Users\\islamm\\blg\\connector.empower\\src\\main\\resources\\config.properties"));
			Properties props = new Properties();
			props.load(is);
			ConfigurationManager.getInstance();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
