package com.sandip.fig.rest.dtos;

import java.io.Serializable;

public class ResponseDto implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -439732031747253624L;
	
	private Object returnObject;
	
	private ResponseStatus responseStatus;
	
	

	public ResponseDto() {
	}

	public ResponseDto(Object returnObject, ResponseStatus responseStatus) {
		super();
		this.returnObject = returnObject;
		this.responseStatus = responseStatus;
	}

	public Object getReturnObject() {
		return returnObject;
	}

	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	
}
