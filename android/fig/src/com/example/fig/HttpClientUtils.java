package com.example.fig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class HttpClientUtils {

	private static HttpClient httpclient = new DefaultHttpClient();

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static HttpClient getHttpClient() {
		return httpclient;
	}

	public static void closeClient() {
		httpclient = null;
	}

	public static String convertToJsonString(Serializable object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			throw new JsonException(e.getMessage(), e);
		} catch (JsonMappingException e) {
			throw new JsonException(e.getMessage(), e);
		} catch (IOException e) {
			throw new JsonException(e.getMessage(), e);
		}
	}

	public static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null) {
			result += line;
		}

		inputStream.close();
		return result;
	}

	public static <T> T convertInputStreamToResponseObject(
			InputStream inputStream, Class<T> valueType) {
		try {
			return objectMapper.readValue(inputStream, valueType);
		} catch (JsonGenerationException e) {
			throw new JsonException(e.getMessage(), e);
		} catch (JsonMappingException e) {
			throw new JsonException(e.getMessage(), e);
		} catch (IOException e) {
			throw new JsonException(e.getMessage(), e);
		}
	}

	public static <T> T convertjsonToResponseObject(String value,
			Class<T> valueType) {
		try {
			return objectMapper.readValue(value, valueType);
		} catch (JsonGenerationException e) {
			throw new JsonException(e.getMessage(), e);
		} catch (JsonMappingException e) {
			throw new JsonException(e.getMessage(), e);
		} catch (IOException e) {
			throw new JsonException(e.getMessage(), e);
		}
	}

}
