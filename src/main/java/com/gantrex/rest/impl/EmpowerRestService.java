package com.gantrex.rest.impl;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.ex.ConversionException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gantrex.config.ConfigurationManager;
import com.gantrex.exceptions.AppIDExceedsMaxLengthException;
import com.gantrex.exceptions.AuthenticationFailedException;
import com.gantrex.exceptions.CSRFNullException;
import com.gantrex.exceptions.ConfigNotInitializedException;
import com.gantrex.exceptions.DocumentSizeExceedException;
import com.gantrex.exceptions.RestRequestFailedException;
import com.gantrex.rest.RestApi;
import com.gantrex.rest.model.empower.csrf.CSRFResponse;
import com.gantrex.rest.model.empower.document.ImportDocumentResponse;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;

/**
 * 
 */

public class EmpowerRestService extends RestService {

	// private RestApi restApi;
	private String csrfToken;
	private static final Logger log = LoggerFactory.getLogger(OTDSRestService.class);
	private static final String emptyString = "";
	private static final long maxFileSize = 200 * 1024 * 1024;
	HashSet<String> cookies = new HashSet<String>();

	public EmpowerRestService() throws ConfigNotInitializedException, ConfigurationException {

		this(ConfigurationManager.getInstance().getEmpowerBaseURL());

	}

	public EmpowerRestService(String baseURL) {

		super(baseURL);
	}

	/**
	 * Method to return the API interface.
	 *
	 * @return RestApi
	 */
	public RestApi getAPI() {

		return this.restApi;
	}

	/**
	 * Method to build and return an OkHttpClient so we can set/get headers quickly
	 * and efficiently.
	 *
	 * @return OkHttpClient
	 */

	protected OkHttpClient buildClient() {

		HttpLoggingInterceptor.Level interceptorLevel = HttpLoggingInterceptor.Level.NONE;
		int level = 0;
		try {
			level = ConfigurationManager.getInstance().getInterceptorLogLevel();
			switch (level) {
			case 0:
				break;
			case 1:
				interceptorLevel = HttpLoggingInterceptor.Level.BASIC;
				break;
			case 2:
				interceptorLevel = HttpLoggingInterceptor.Level.HEADERS;
				break;
			case 3:
				interceptorLevel = HttpLoggingInterceptor.Level.BODY;
				break;
			default:
				break;
			}

		} catch (ConfigNotInitializedException e) {
			log.warn("Configuration is not initialized yet");
		} catch (ConversionException e) {
			log.warn("Failed to convert Rest Log Level to integer", e);
		}

		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {

			@Override
			public void log(String message) {
				log.info(message);
			}
		});
		interceptor.setLevel(interceptorLevel);
		if (true) {
			interceptor.redactHeader("Cookie");
			interceptor.redactHeader("Set-Cookie");
			interceptor.redactHeader("Authorization");
			interceptor.redactHeader("X-CSRF-TOKEN");
		}

		OkHttpClient.Builder builder = new OkHttpClient.Builder();

		builder.addInterceptor(chain -> {

			okhttp3.Response originalResponse = chain.proceed(chain.request());
			if (!originalResponse.headers("Set-Cookie").isEmpty()) {
				if (cookies.isEmpty()) {
					for (String header : originalResponse.headers("Set-Cookie")) {
						cookies.add(header);
					}
				} else {
					log.debug("Skipping the retrieval of cookies from response");
				}
			}
			return originalResponse;
		}).addInterceptor(chain -> {
			// this is where we will add whatever we want to our request headers.
			Request original = chain.request();
			Request.Builder requestBuilder = original.newBuilder();// .header("Accept", "application/json");
			if (csrfToken != null) {
				requestBuilder.header("X-CSRF-TOKEN", csrfToken);
				// requestBuilder.header("Cookie", "XSRF-TOKEN="+csrfToken+";");
			}
			Request request = requestBuilder.method(original.method(), original.body()).build();
			return chain.proceed(request);
		}).addInterceptor(chain -> {
			Request original = chain.request();
			Request.Builder requestBuilder = original.newBuilder();
			for (String cookie : cookies) {
				requestBuilder.addHeader("Cookie", cookie);
			}
			Request request = requestBuilder.method(original.method(), original.body()).build();
			return chain.proceed(request);

		}).addInterceptor(interceptor); // .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888)));

		return builder.build();
	}

	public ImportDocumentResponse importDocument(File file, String appID, String busDocID, List<String> docTag,
			List<String> ownerID) throws IOException, RestRequestFailedException, AuthenticationFailedException {

		if (file == null) {
			throw new NullPointerException("Import Document :: File is null");
		}
		byte[] bytes = FileUtils.readFileToByteArray(file);
		String fileName = file.getName();
		return importDocument(bytes, fileName, appID, busDocID, docTag, ownerID);

	}

	public ImportDocumentResponse importDocument(byte[] bytes, String fileName, String appID, String busDocID,
			List<String> docTag, List<String> ownerID)
			throws IOException, RestRequestFailedException, AuthenticationFailedException {

		if (bytes == null) {
			throw new NullPointerException("Import Document :: byte array is null");
		}

		log.info("Import file::  File : {}, appID : {}, busDocID : {}, docTag : {}, ownerID : {}", fileName, appID,
				busDocID,
				Optional.ofNullable(docTag).orElse(new ArrayList<String>()).stream().collect(Collectors.joining(",")),
				Optional.ofNullable(ownerID).orElse(new ArrayList<String>()).stream().collect(Collectors.joining(",")));

		long fileSize = bytes.length;
		if (fileSize > maxFileSize) {
			throw new DocumentSizeExceedException(String.format("File Size is %s which exceeds max allowed limit of %s",
					FileUtils.byteCountToDisplaySize(fileSize), FileUtils.byteCountToDisplaySize(maxFileSize)));
		}

		// Builder formBodyBuilder = new
		// MultipartBody.Builder().setType(MultipartBody.FORM);
		Map<String, RequestBody> formPart = new HashMap<String, RequestBody>();
		Optional.ofNullable(appID).map(s -> {
			if (s.length() > 43) {
				throw new AppIDExceedsMaxLengthException("App ID " + appID + " exceeds max allowed length of 43 chars");
			}
			return s;
		}).ifPresent(s -> formPart.put("appId", createPartFromString(s)));
		Optional.ofNullable(busDocID).map(s -> {
			if (s.length() > 250) {
				log.warn("Bussiness Doc ID {} exceeds max allowed length of 250 chars", busDocID);
				s = s.substring(0, 250);
			}
			return s;
		}).ifPresent(s -> formPart.put("busDocId", createPartFromString(s)));
		Optional.ofNullable(docTag).ifPresent(list -> {
			list.stream().map(s -> {
				if (s.length() > 250) {
					log.warn("Doc Tag {} exceeds max allowed length of 250 chars", s);
					s = s.substring(0, 250);
				}
				return s;
			}).forEach(s -> formPart.put("docTag", createPartFromString(s)));
		});
		Optional.ofNullable(docTag).ifPresent(list -> {
			list.stream().map(s -> {
				if (s.length() > 250) {
					log.warn("Owner ID {} exceeds max allowed length of 250 chars", s);
					s = s.substring(0, 250);
				}
				return s;
			}).forEach(s -> formPart.put("ownerId", createPartFromString(s)));
		});

		// MultipartBody formBody = formBodyBuilder.build();

		MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
		String mimeType = fileTypeMap.getContentType(fileName);
		RequestBody fileBody = RequestBody.create(MediaType.get(mimeType), bytes);
		MultipartBody filePart = new MultipartBody.Builder().addFormDataPart("file", fileName, fileBody).build();

		Response<ImportDocumentResponse> response;
		try {
			response = this.restApi.importDocument(formPart, filePart.part(0)).execute();
		} catch (IOException e) {
			throw new RestRequestFailedException("Get CSRF Token. Execute threw IO Exception", e);
		}
		if (response != null && response.isSuccessful()) {
			log.debug("Import Document {}. Response is successful.", fileName);
			return response.body();
		} else if (response != null) {
			log.debug("Import Document {}. Response is unsuccessful with code {}, {}", fileName, response.code(),
					response.message());
			String errorString = Optional.ofNullable(response.errorBody()).map(body -> {
				try {
					return body.string();
				} catch (IOException e) {
					return emptyString;
				}
			}).map(s -> s.length() > 200 ? s.substring(0, 200) : s).orElse(emptyString);

			if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
				throw new AuthenticationFailedException(
						"Import Document " + fileName + " returned Unauthorized. " + errorString);
			} else {
				throw new RestRequestFailedException(
						"Import Document " + fileName + " returned " + response.message() + ". " + errorString);
			}
		} else {
			log.debug("Import Document {}. Response object is null", fileName);
			throw new RestRequestFailedException("Import Document " + fileName + ". Response object is null");
		}

	}

	public byte[] exportDocument(String docID, Boolean preserveDoc)
			throws IOException, RestRequestFailedException, AuthenticationFailedException {

		if (StringUtils.isBlank(docID)) {
			throw new NullPointerException("Export Document :: DocID is blank");
		}

		log.info("Export file::  DocID : {}, preserveDoc : {}", docID, preserveDoc);

		Response<ResponseBody> response;
		try {
			response = this.restApi.exportDocument(docID, preserveDoc).execute();
		} catch (IOException e) {
			throw new RestRequestFailedException("Get CSRF Token. Execute threw IO Exception", e);
		}
		if (response != null && response.isSuccessful()) {
			log.debug("Export Document {}. Response is successful.", docID);
			return Optional.ofNullable(response.body()).map(body -> {
				try {
					return body.bytes();
				} catch (IOException e) {
					log.error("Error Reading response body while exporting document " + docID, e);
					return null;
				}
			}).orElse(null);
		} else if (response != null) {
			log.debug("Export Document {}. Response is unsuccessful with code {} , {}", docID, response.code(),
					response.message());
			String errorString = Optional.ofNullable(response.errorBody()).map(body -> {
				try {
					return body.string();
				} catch (IOException e) {
					return emptyString;
				}
			}).map(s -> s.length() > 200 ? s.substring(0, 200) : s).orElse(emptyString);

			if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
				throw new AuthenticationFailedException(
						"Export Document " + docID + " returned Unauthorized. " + errorString);
			} else {
				throw new RestRequestFailedException(
						"Export Document " + docID + " returned " + response.message() + ". " + errorString);
			}
		} else {
			log.debug("Export Document {}. Response object is null", docID);
			throw new RestRequestFailedException("Export Document " + docID + ". Response object is null");
		}

	}

	public CSRFResponse getCSRFToken(String authToken)
			throws AuthenticationFailedException, RestRequestFailedException, CSRFNullException {
		log.info("Get CSRF Token called");
		ObjectMapper mapper = new ObjectMapper();
		Response<CSRFResponse> response;
		try {
			response = this.restApi.getCSRFToken("Bearer " + authToken).execute();
		} catch (IOException e) {
			throw new RestRequestFailedException("Get CSRF Token. Execute threw IO Exception", e);
		}
		if (response != null && response.isSuccessful()) {
			log.debug("Get CSRF Token. Response is successful.");
			CSRFResponse csrfResponse = response.body();
			csrfToken = Optional.ofNullable(csrfResponse).map(csrf -> csrf.getBody()).map(body -> body.getCsrfToken())
					.orElseThrow(() -> {
						try {
							return new CSRFNullException("Failed to get CSRF Token from server. "
									+ mapper.writeValueAsString(response.body()));
						} catch (JsonProcessingException e) {
							return new CSRFNullException(
									"Failed to get CSRF Token from server and failed to serialize body", e);
						}
					});
			log.debug("Get CSRF Token. "
					+ (csrfToken != null ? "Token Received from server" : "Failed to get CSRF Token from Server"));
			return csrfResponse;
		} else if (response != null) {
			log.debug("Get CSRF Token. Response is unsuccessful with code {}", response.message());
			String errorString = Optional.ofNullable(response.errorBody()).map(body -> {
				try {
					return body.string();
				} catch (IOException e) {
					return emptyString;
				}
			}).map(s -> s.length() > 200 ? s.substring(0, 200) : s).orElse(emptyString);

			if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
				throw new AuthenticationFailedException("CSRF Token Request returned Unauthorized. " + errorString);
			} else {
				throw new RestRequestFailedException(
						"CSRF Token Request returned " + response.message() + ". " + errorString);
			}
		} else {
			log.debug("Get CSRF Token. Response object is null");
			throw new RestRequestFailedException("Get CSRF Token. Response object is null");
		}

	}

	private RequestBody createPartFromString(String body) {
		return RequestBody.create(MediaType.parse("multipart/form-data"), body);
	}
}
