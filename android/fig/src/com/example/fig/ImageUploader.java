package com.example.fig;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class ImageUploader extends AsyncTask<String, Void, String> {
	
	private String result="FAILED";
	
	private ImageUploadListener imageUploadListener;
	

	public ImageUploader() {
	}

	public ImageUploader(ImageUploadListener imageUploadListener) {
		super();
		this.imageUploadListener = imageUploadListener;
	}
	
	

	public void setImageUploadListener(ImageUploadListener imageUploadListener) {
		this.imageUploadListener = imageUploadListener;
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String imageFile= params[0];
		String comment= params[1];
		String imagePostUrl = SystemConstants.SERVER_URL+"upload/image";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPostRequest = new HttpPost(imagePostUrl);
		
		try {
			FileBody bin = new FileBody(new File(imageFile));
			MultipartEntityBuilder multiPartEntityBuilder = MultipartEntityBuilder.create();
			multiPartEntityBuilder.addPart("image", bin);
			multiPartEntityBuilder.addTextBody("comments", comment);
			multiPartEntityBuilder.addTextBody("createdBy", SystemGlobalVariable.getCurrentUserId().toString());
			httpPostRequest.setEntity(multiPartEntityBuilder.build());

			// Execute POST request to the given URL
			HttpResponse httpResponse = null;
			httpResponse = httpClient.execute(httpPostRequest);

			// receive response as inputStream
			InputStream inputStream = null;
			inputStream = httpResponse.getEntity().getContent();

			if (inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	@Override
	protected void onPreExecute() {
		
	}

	@Override
	protected void onPostExecute(String result) {
		if(imageUploadListener != null){
			imageUploadListener.onImageUploadResult(result);
		}
	}

	private String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}

}
