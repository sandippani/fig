package com.example.fig;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import android.os.AsyncTask;

import com.sandip.fig.rest.dtos.ResponseDto;

public class HttpAsyncTask extends AsyncTask<Void, Void, ResponseDto> {
	
	private Serializable serverObject;
	
	private HttpRequestType httpRequestType;
	
	private String targetUrl;
	
	private HttpClientListener httpClientListener;
	
	private Object extraInputParameters;

	public HttpAsyncTask(String requestedUrl) {
		super();
		if(requestedUrl == null || "".equals(requestedUrl)){
			throw new IllegalArgumentException("Server Request Url cannot be null");
		}
		this.httpRequestType = HttpRequestType.GET;
		targetUrl = SystemConstants.SERVER_URL+requestedUrl;
	}
	
	public HttpAsyncTask(String requestedUrl,Object extraInputParameters) {
		this(requestedUrl);
		this.extraInputParameters = extraInputParameters;
	}
	
	public HttpAsyncTask(Serializable serverObject,
			HttpRequestType httpRequestType, String requestedUrl) {
		this(requestedUrl);
		this.serverObject = serverObject;
		this.httpRequestType = httpRequestType;
	}


	public HttpAsyncTask(Serializable serverObject,
			HttpRequestType httpRequestType, String requestedUrl,
			HttpClientListener httpClientListener, Object extraInputParameters) {
		this(requestedUrl);
		this.serverObject = serverObject;
		this.httpRequestType = httpRequestType;
		this.httpClientListener = httpClientListener;
		this.extraInputParameters = extraInputParameters;
	}

	public Object getExtraInputParameters() {
		return extraInputParameters;
	}

	public void setHttpClientListener(HttpClientListener httpClientListener) {
		this.httpClientListener = httpClientListener;
	}

	@Override
	protected ResponseDto doInBackground(Void... params) {
		ResponseDto responseDto = null;
		switch (httpRequestType) {
		case GET:
			doWithGetRequest();
			break;
		case POST:
			responseDto = doWithPostRequest();
			break;

		default:
			break;
		}
		return responseDto;
	}

	private ResponseDto doWithPostRequest() {

		InputStream inputStream = null;
		HttpPost httpPost = new HttpPost(targetUrl);
		ResponseDto responseDto = null;
		 try {
			 StringEntity entity = null;
			 String json = null;
			 if(serverObject != null){
				 json= HttpClientUtils.convertToJsonString(serverObject);
				 entity = new StringEntity(json);
				 httpPost.setEntity(entity);
			 }
			 httpPost.setHeader("Accept", "application/json");
			 httpPost.setHeader("Content-type", "application/json");
			 HttpResponse httpResponse = HttpClientUtils.getHttpClient().execute(httpPost);
			 inputStream = httpResponse.getEntity().getContent();
			 json = HttpClientUtils.convertInputStreamToString(inputStream);
			 responseDto = HttpClientUtils.convertjsonToResponseObject(json, ResponseDto.class);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return responseDto;
		
	}

	private void doWithGetRequest() {}

	@Override
	protected void onPostExecute(ResponseDto result) {
		if(httpClientListener != null){
			httpClientListener.processResponse(result,extraInputParameters);
		}
	}
	
	
	

	

}
