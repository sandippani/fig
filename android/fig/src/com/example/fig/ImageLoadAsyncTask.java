package com.example.fig;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandip.fig.rest.dtos.ImageResponseDto;

public class ImageLoadAsyncTask extends AsyncTask<String, Void, Bitmap> {

	private final TextView tv;

	private final ImageView image;

	private final ImageResponseDto imageResponseDto;

	public ImageLoadAsyncTask(TextView tv, ImageView image,
			ImageResponseDto imageResponseDto) {
		super();
		this.tv = tv;
		this.image = image;
		this.imageResponseDto = imageResponseDto;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		HttpClient httpClient = HttpClientUtils.getHttpClient();
		String targetUrl = SystemConstants.SERVER_URL
				+ imageResponseDto.getImageUrl();
		HttpGet httpGet = new HttpGet(targetUrl);
		Bitmap bitmap = null;
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			InputStream is = httpResponse.getEntity().getContent();
			bitmap = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
		return bitmap;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		this.image.setImageBitmap(result);
		this.tv.setText(imageResponseDto.getComment());
	}

}
