package com.eshop.inventory.vo;

public class Response {

	public static final String SUCCESS = "success";
	public static final String FAILURE = "failure";
	
	public static final Response success(String message) {
		return new Response(SUCCESS,message);
	}
	public static final Response success() {
		return new Response(SUCCESS);
	}
	
	public static final Response failure(String message) {
		return new Response(FAILURE,message);
	}
	public static final Response failure() {
		return new Response(FAILURE);
	}
	
	private String status;
	private String message;
	
	public Response(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	public Response() {
		super();
	}
	public Response(String status) {
		super();
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
