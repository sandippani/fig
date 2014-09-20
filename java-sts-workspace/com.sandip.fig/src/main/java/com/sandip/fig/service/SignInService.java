package com.sandip.fig.service;

import com.sandip.fig.exception.SignInException;

public interface SignInService {
	public Long signin(String email, String password) throws SignInException;

}
