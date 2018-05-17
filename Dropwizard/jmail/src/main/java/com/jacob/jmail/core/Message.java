package com.jacob.jmail.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown= true)
public class Message {
	
	public int id;	
	public String token;
	public String recipient;
	public String subject;
	public String message;

}
