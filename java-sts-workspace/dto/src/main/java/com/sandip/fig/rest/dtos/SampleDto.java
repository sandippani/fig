package com.sandip.fig.rest.dtos;

import java.io.Serializable;

public class SampleDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2722649713908706463L;
	
	private String name;
	
	private int age;
	
	

	public SampleDto() {
	}

	public SampleDto(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
}
