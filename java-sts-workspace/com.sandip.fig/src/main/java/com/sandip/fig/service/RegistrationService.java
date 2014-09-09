package com.sandip.fig.service;

import com.sandip.fig.exception.RegistartionException;
import com.sandip.fig.rest.dtos.RegistrationDto;

public interface RegistrationService {
	public void create(RegistrationDto registrationDto) throws RegistartionException;
	public void update(RegistrationDto registrationDto);
	public void disable(Long id);

}
