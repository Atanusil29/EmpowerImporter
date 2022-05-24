package com.gantrex.rest.impl;

import com.gantrex.rest.IRestService;
import com.gantrex.rest.RestApi;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * 
 */

public abstract class RestService implements IRestService {

	protected Retrofit retrofit = null;
	protected RestApi restApi = null;
	protected OkHttpClient okHttpClient = null;

	public RestService(String baseURL) {

		this.okHttpClient = buildClient();

		this.retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(JacksonConverterFactory.create())
				.client(this.okHttpClient).build();

		this.restApi = this.retrofit.create(RestApi.class);
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

	protected abstract OkHttpClient buildClient();

}
