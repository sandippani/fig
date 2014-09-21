package com.example.fig;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.example.fig.ImageEndlessListView.ImageEndlessListener;
import com.sandip.fig.rest.dtos.ImageResponseDto;
import com.sandip.fig.rest.dtos.ImageResponseListDto;

public class ImageViewList extends Activity implements ImageEndlessListener,
		HttpClientListener {
	private final static int ITEM_PER_REQUEST = 1;
	ImageEndlessListView lv;

	int mult = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_view_list);
		List<ImageResponseDto> l = new ArrayList<ImageResponseDto>();
		lv = (ImageEndlessListView) findViewById(R.id.imageendlesslistview);
		ImageEndlessAdapter adp = new ImageEndlessAdapter(this, l,
				R.layout.row_layout);
		// lv.setLoadingView(R.layout.loading_layout);
		lv.setAdapter(adp);
		lv.setListener(this);
		loadData(1);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public void loadData(int page) {
		if (page < 1) {
			Toast.makeText(this, "No more images in gallery.",
					Toast.LENGTH_LONG).show();
		} else {
			HttpAsyncTask<Object> httpAsyncTask = new HttpAsyncTask<Object>(
					"list/image/" + page + "/" + ITEM_PER_REQUEST, this);
			httpAsyncTask.setReturnType(ImageResponseListDto.class);
			httpAsyncTask.execute(new Void[] {});
		}
	}

	@Override
	public void processResponse(Object obj, Object extraParameters) {
		ImageResponseListDto imageResponseListDto = (ImageResponseListDto) obj;
		lv.addNewData(imageResponseListDto.getList(),
				imageResponseListDto.getNextPageId());
	}

}
