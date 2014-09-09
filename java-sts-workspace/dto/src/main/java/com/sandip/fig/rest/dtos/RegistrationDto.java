package com.sandip.fig.rest.dtos;

import java.io.Serializable;

public class RegistrationDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2652212285003388474L;
	
	private String email;
	
	private String username;
	
	private String password;
	private Long id;
	private boolean active = true;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	

}
