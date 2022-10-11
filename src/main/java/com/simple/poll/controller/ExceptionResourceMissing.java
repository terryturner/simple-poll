package com.simple.poll.controller;

public class ExceptionResourceMissing extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ExceptionResourceMissing(String message) {
		super(message);
	}
}
