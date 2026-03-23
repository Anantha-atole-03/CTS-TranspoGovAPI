package com.cts.transpogov.dtos;

import java.time.LocalDateTime;

/**
 * ApiResponse<T> - Preserves your existing fields and constructors. - Adds
 * static factory methods ok(...) and created(...) to match controller usage.
 */
public class ApiResponse<T> {

	private String message;
	private LocalDateTime time;
	private int statusCode; // renamed from stateCode
	private T data;

	public ApiResponse() {
		this.time = LocalDateTime.now();
	}

	public ApiResponse(String message, int statusCode) {
		this.message = message;
		this.statusCode = statusCode;
		this.time = LocalDateTime.now();
	}

	public ApiResponse(String message, int statusCode, T data) {
		this.message = message;
		this.statusCode = statusCode;
		this.data = data;
		this.time = LocalDateTime.now();
	}

	/*
	 * --------------------------------------------------------- Static factory
	 * helpers (added to support your controller)
	 * ---------------------------------------------------------
	 */

	/** 200 OK with data */
	public static <T> ApiResponse<T> ok(T data) {
		return new ApiResponse<>("OK", 200, data);
	}

	/** 200 OK with custom message */
	public static <T> ApiResponse<T> ok(String message, T data) {

		return new ApiResponse<>(message, 200, data);
	}

	/** 201 Created with data */
	public static <T> ApiResponse<T> created(T data) {
//        ApiResponse<T> res = new ApiResponse<>("CREATED", 201, data);
		return new ApiResponse<>("CREATED", 201, data);

	}

	/** 201 Created with custom message */
	public static <T> ApiResponse<T> created(String message, T data) {

		return new ApiResponse<>(message, 201, data);
	}

	/** Generic error helper (not required by your controller, but handy) */
	public static <T> ApiResponse<T> error(String message, int statusCode) {

		return new ApiResponse<>(message, statusCode, null);
	}

	/*
	 * ------------------- Getters & Setters -------------------
	 */
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}