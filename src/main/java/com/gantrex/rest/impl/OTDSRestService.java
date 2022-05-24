package com.gantrex.rest.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Optional;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.ex.ConversionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gantrex.config.ConfigurationManager;
import com.gantrex.exceptions.AuthenticationFailedException;
import com.gantrex.exceptions.ConfigNotInitializedException;
import com.gantrex.exceptions.RestRequestFailedException;
import com.gantrex.rest.RestApi;
import com.gantrex.rest.model.response.AuthenticationResponse;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;

/**
 * 
 */

public class OTDSRestService extends RestService {

	// private RestApi restApi = null;
	private static final Logger log = LoggerFactory.getLogger(OTDSRestService.class);
	private static final String emptyString = "";

	public OTDSRestService() throws ConfigNotInitializedException, ConfigurationException {

		this(ConfigurationManager.getInstance().getOTDSBaseURL());

	}

	public OTDSRestService(String baseURL) {

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
		try {
			int level = ConfigurationManager.getInstance().getInterceptorLogLevel();
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
			interceptor.redactHeader("Authorization");
			interceptor.redactHeader("Set-Cookie");
			interceptor.redactHeader("X-CSRF-TOKEN");

		}
		OkHttpClient.Builder builder = new OkHttpClient.Builder();

		builder.addInterceptor(chain -> {
			okhttp3.Response response = chain.proceed(chain.request());
			return response;
		}).addInterceptor(chain -> {
			// this is where we will add whatever we want to our request headers.
			Request original = chain.request();
			Request.Builder requestBuilder = original.newBuilder().header("Accept", "application/json")
					.header("Content-Type", "application/x-www-form-urlencoded");
			Request request = requestBuilder.method(original.method(), original.body()).build();
			return chain.proceed(request);
		}).addInterceptor(interceptor); // .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888)));

		return builder.build();
	}

	public AuthenticationResponse authenticateForEmpower(String userName, String password)
			throws IOException, AuthenticationFailedException, RestRequestFailedException,
			ConfigNotInitializedException, ConfigurationException {

		log.info("Authenticating {} ", userName);

		String clientID = ConfigurationManager.getInstance().getClientID();
		String clientSecret = ConfigurationManager.getInstance().getClientSecret();
		String grantType = ConfigurationManager.getInstance().getGrantType();

		Response<AuthenticationResponse> response;
		try {
			response = this.restApi.athenticate(userName, password, clientID, clientSecret, grantType).execute();
		} catch (IOException e) {
			throw new RestRequestFailedException("Authenticating " + userName + ". Execute threw IO Exception", e);
		}
		if (response != null && response.isSuccessful()) {
			log.debug("Authenticating {}. Response is successful.", userName);
			AuthenticationResponse authResponse = response.body();
			log.debug("Authenticating {}. Token Received : {}", userName, authResponse.getAccessToken());
			return authResponse;
		} else if (response != null) {
			log.debug("Authenticating {}. Response is unsuccessful with code {}", userName, response.message());
			String errorString = Optional.ofNullable(response.errorBody()).map(body -> {
				try {
					return body.string();
				} catch (IOException e) {
					return emptyString;
				}
			}).map(s -> s.length() > 200 ? s.substring(0, 200) : s).orElse(emptyString);
			if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
				throw new AuthenticationFailedException(
						"Authentication Request returned Unauthorized for user " + userName + ". " + errorString);
			} else {
				throw new RestRequestFailedException(
						"Authentication Request returned Unauthorized for user " + userName + ". " + errorString);
			}
		} else {
			log.debug("Authenticating {}. Response object is null", userName);
			throw new RestRequestFailedException("Authenticating " + userName + ". Response object is null");
		}

	}

}
