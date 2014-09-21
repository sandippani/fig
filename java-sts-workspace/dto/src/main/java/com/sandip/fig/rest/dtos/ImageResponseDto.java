package com.sandip.fig.rest.dtos;

import java.io.Serializable;

public class ImageResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -843465021352536693L;
	
	private String imageUrl;
	
	private String comment;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	

}
