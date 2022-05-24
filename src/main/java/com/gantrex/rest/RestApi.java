package com.gantrex.rest;

import java.util.Map;

import com.gantrex.rest.model.empower.csrf.CSRFResponse;
import com.gantrex.rest.model.empower.document.ImportDocumentResponse;
import com.gantrex.rest.model.response.AuthenticationResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

/**
 * 
 *
 *
 */

public interface RestApi {

	@FormUrlEncoded
	@POST("login")
	Call<AuthenticationResponse> athenticate(@Field("username") String userName, @Field("password") String password,
			@Field("client_id") String clientID, @Field("client_secret") String clientSecret,
			@Field("grant_type") String grantType);

	@GET("resource/GetToken")
	@Headers("Accept: application/json")
	Call<CSRFResponse> getCSRFToken(@Header("Authorization") String authHeader);

	@Multipart
	@POST("resource/documents/import")
	@Headers("Accept: application/json")
	Call<ImportDocumentResponse> importDocument(@PartMap Map<String, RequestBody> formPart, @Part MultipartBody.Part filePart);

	@FormUrlEncoded
	@POST("resource/documents/{docID}/export")
	Call<ResponseBody> exportDocument(@Path("docID") String docID, @Field("preserveDoc") Boolean preserveDoc);

}
