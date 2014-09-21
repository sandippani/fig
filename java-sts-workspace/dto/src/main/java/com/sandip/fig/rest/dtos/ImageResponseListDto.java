package com.sandip.fig.rest.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImageResponseListDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 284213025044761179L;
	
	private List<ImageResponseDto> list = new ArrayList<ImageResponseDto>();
	
	private int nextPageId;
	
	public ImageResponseListDto() {
	}

	public ImageResponseListDto(List<ImageResponseDto> content) {
		list.addAll(content);
	}

	public List<ImageResponseDto> getList() {
		return list;
	}

	public void setList(List<ImageResponseDto> list) {
		this.list = list;
	}

	public int getNextPageId() {
		return nextPageId;
	}

	public void setNextPageId(int nextPageId) {
		this.nextPageId = nextPageId;
	}
	
	
	
	

}
