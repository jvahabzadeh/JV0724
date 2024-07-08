package com.jvprogdemo.models;

import java.time.LocalDate;

public class CheckoutRequest {
	private String toolCode;
	private int rentalDays;
	private int discountPercent;
	private LocalDate checkOutDate;

	public CheckoutRequest(String toolCode, int rentalDays, int discountPercent, LocalDate checkOutDate) {
		if(toolCode == null || toolCode.isBlank()) {

		}
	}
}
