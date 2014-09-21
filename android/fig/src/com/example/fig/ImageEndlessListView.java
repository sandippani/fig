package com.example.fig;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.sandip.fig.rest.dtos.ImageResponseDto;

public class ImageEndlessListView extends ListView implements OnScrollListener {

	private View footer;
	private boolean isLoading;
	private ImageEndlessListener listener;
	private ImageEndlessAdapter adapter;

	private int pageId = 1;

	public ImageEndlessListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		this.setOnScrollListener(this);
	}

	public ImageEndlessListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOnScrollListener(this);
	}

	public ImageEndlessListView(Context context) {
		super(context);
		this.setOnScrollListener(this);
	}

	public void setListener(ImageEndlessListener listener) {
		this.listener = listener;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	/*
	 * public void setLoadingView(int resId) { LayoutInflater inflater =
	 * (LayoutInflater) super.getContext()
	 * .getSystemService(Context.LAYOUT_INFLATER_SERVICE); footer =
	 * inflater.inflate(resId, null); this.addFooterView(footer);
	 * 
	 * }
	 */

	public void setAdapter(ImageEndlessAdapter adapter) {
		super.setAdapter(adapter);
		this.adapter = adapter;
		// this.removeFooterView(footer);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

		if (getAdapter() == null) {
			return;
		}

		if (getAdapter().getCount() == 0) {
			return;
		}

		int l = visibleItemCount + firstVisibleItem;
		if (l >= totalItemCount && !isLoading) {
			// It is time to add new data. We call the listener
			// this.addFooterView(footer);
			isLoading = true;
			listener.loadData(pageId);
		}
		// TODO Auto-generated method stub

	}

	public void addNewData(List<ImageResponseDto> data, int page) {

		// this.removeFooterView(footer);

		adapter.addAll(data);
		adapter.notifyDataSetChanged();
		isLoading = false;
		this.pageId = page;
	}

	public ImageEndlessListener setListener() {
		return listener;
	}

	public static interface ImageEndlessListener {
		public void loadData(int page);
	}
}
