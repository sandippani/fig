package com.example.fig;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.sandip.fig.rest.dtos.ResponseDto;

public class HttpAsyncTask<T> extends AsyncTask<Void, Void, Object> {

	private Serializable serverObject;

	private HttpRequestType httpRequestType;

	private final String targetUrl;

	private HttpClientListener httpClientListener;

	private Object extraInputParameters;

	private Class<?> returnType = ResponseDto.class;

	private ProgressDialog pd;

	public HttpAsyncTask(String requestedUrl,
			HttpClientListener httpClientListener) {
		super();
		if (requestedUrl == null || "".equals(requestedUrl)) {
			throw new IllegalArgumentException(
					"Server Request Url cannot be null");
		}
		if (httpClientListener != null && httpClientListener instanceof Context) {
			pd = new ProgressDialog((Context) httpClientListener);
		}
		this.httpRequestType = HttpRequestType.GET;
		targetUrl = SystemConstants.SERVER_URL + requestedUrl;
		this.httpClientListener = httpClientListener;
	}

	public HttpAsyncTask(String requestedUrl, Object extraInputParameters) {
		this(requestedUrl, null);
		this.extraInputParameters = extraInputParameters;
	}

	public HttpAsyncTask(Serializable serverObject,
			HttpRequestType httpRequestType, String requestedUrl) {
		this(requestedUrl, null);
		this.serverObject = serverObject;
		this.httpRequestType = httpRequestType;
	}

	public HttpAsyncTask(Serializable serverObject,
			HttpRequestType httpRequestType, String requestedUrl,
			HttpClientListener httpClientListener, Object extraInputParameters) {
		this(requestedUrl, httpClientListener);
		this.serverObject = serverObject;
		this.httpRequestType = httpRequestType;
		this.httpClientListener = httpClientListener;
		this.extraInputParameters = extraInputParameters;
	}

	public void setReturnType(Class<?> returnType) {
		this.returnType = returnType;
	}

	public Object getExtraInputParameters() {
		return extraInputParameters;
	}

	public void setHttpClientListener(HttpClientListener httpClientListener) {
		this.httpClientListener = httpClientListener;
	}

	@Override
	protected void onPreExecute() {
		if (pd != null) {
			pd.setTitle("Processing...");
			pd.setMessage("Please wait.");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}
	}

	@Override
	protected Object doInBackground(Void... params) {
		Object responseDto = null;

		switch (httpRequestType) {
		case GET:
			responseDto = doWithGetRequest();
			break;
		case POST:
			responseDto = doWithPostRequest();
			break;

		default:
			break;
		}
		return responseDto;
	}

	private Object doWithPostRequest() {

		InputStream inputStream = null;
		HttpPost httpPost = new HttpPost(targetUrl);
		Object responseDto = null;
		try {
			StringEntity entity = null;
			String json = null;
			if (serverObject != null) {
				json = HttpClientUtils.convertToJsonString(serverObject);
				entity = new StringEntity(json);
				httpPost.setEntity(entity);
			}
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			HttpResponse httpResponse = HttpClientUtils.getHttpClient()
					.execute(httpPost);
			inputStream = httpResponse.getEntity().getContent();
			json = HttpClientUtils.convertInputStreamToString(inputStream);
			responseDto = HttpClientUtils.convertjsonToResponseObject(json,
					returnType);
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

	private Object doWithGetRequest() {
		InputStream inputStream = null;
		HttpGet httpGet = new HttpGet(targetUrl);
		Object responseDto = null;
		try {
			String json = null;
			httpGet.setHeader("Accept", "application/json");
			httpGet.setHeader("Content-type", "application/json");
			HttpResponse httpResponse = HttpClientUtils.getHttpClient()
					.execute(httpGet);
			inputStream = httpResponse.getEntity().getContent();
			json = HttpClientUtils.convertInputStreamToString(inputStream);
			responseDto = HttpClientUtils.convertjsonToResponseObject(json,
					returnType);
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

	@Override
	protected void onPostExecute(Object result) {
		if (pd != null) {
			pd.dismiss();
		}
		if (httpClientListener != null) {
			httpClientListener.processResponse(result, extraInputParameters);
		}
	}

}
