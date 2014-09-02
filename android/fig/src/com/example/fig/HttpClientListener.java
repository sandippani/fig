package com.example.fig;

import com.sandip.fig.rest.dtos.ResponseDto;

public interface HttpClientListener {
	
	void processResponse(ResponseDto responseDto,Object extraParameters);

}
