package com.jvprogdemo.models;

import com.jvprogdemo.models.entity.Tool;
import com.jvprogdemo.service.entity.ToolService;

import java.time.LocalDate;

public class CheckoutRequest {
	private static final ToolService TOOL_SERVICE = new ToolService();

	private Tool tool;
	private int rentalDays;
	private int discountPercent;
	private LocalDate checkOutDate;

	public CheckoutRequest(String toolCode, int rentalDays, int discountPercent, LocalDate checkOutDate) {
		validateToolCode(toolCode);
		if (rentalDays < 1) {
			// TODO - possibly make this a non-runtime exception?
			throw new IllegalArgumentException("Rental period must be at least 1 day.");
		}
		if (discountPercent < 0 || discountPercent > 100) {
			// TODO - possibly make this a non-runtime exception?
			throw new IllegalArgumentException("Discount percent cannot be less than 0 or greater than 100.");
		}
		if (checkOutDate == null) {
			// TODO - possibly make this a non-runtime exception?
			throw new IllegalArgumentException("A Check Out date is required.");
		}
	}

	private void validateToolCode(String toolCode) {
		if (toolCode == null || toolCode.isBlank()) {
			throw new IllegalArgumentException("A Checkout Request requires a Tool Code.");
		}
		tool = TOOL_SERVICE.getByKey(toolCode);
		if (tool == null) {
			throw new IllegalArgumentException("There is no Tool with a Code of: " + toolCode);
		}
	}

	public Tool getTool() {
		return tool;
	}

	public int getRentalDays() {
		return rentalDays;
	}

	public int getDiscountPercent() {
		return discountPercent;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}
}
