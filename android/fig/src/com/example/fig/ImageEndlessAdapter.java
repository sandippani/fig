package com.example.fig;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandip.fig.rest.dtos.ImageResponseDto;

public class ImageEndlessAdapter extends ArrayAdapter<ImageResponseDto> {

	private final List<ImageResponseDto> itemList;
	private final Context ctx;
	private final int layoutId;

	public ImageEndlessAdapter(Context ctx, List<ImageResponseDto> itemList,
			int layoutId) {
		super(ctx, layoutId, itemList);
		this.itemList = itemList;
		this.ctx = ctx;
		this.layoutId = layoutId;
	}

	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public ImageResponseDto getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return itemList.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View result = convertView;
		if (CollectionUtils.isEmpty(itemList)) {
			return result;
		}
		if (result == null) {
			LayoutInflater inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			result = inflater.inflate(layoutId, parent, false);
		}

		// String imageUrl =
		// "http://www.small-world.net/_borders/small_world_logo.gif";
		// We should use class holder pattern
		TextView tv = (TextView) result.findViewById(R.id.txt_comments);
		ImageView image = (ImageView) result.findViewById(R.id.server_image);
		ImageLoadAsyncTask imageLoadAsyncTask = new ImageLoadAsyncTask(tv,
				image, itemList.get(position));
		imageLoadAsyncTask.execute(new String[] {});
		// Drawable drw = new Drawable();
		// drw. LoadImageFromWebOperations(imageUrl);
		// image.setImageResource(R.drawable.roadicon);
		// tv.setText(itemList.get(position).getComment());

		return result;

	}

}
