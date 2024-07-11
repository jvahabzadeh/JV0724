package com.jvprogdemo.models;

import com.jvprogdemo.exceptions.NoSuchToolException;
import com.jvprogdemo.models.entity.Tool;
import com.jvprogdemo.service.entity.ToolService;

import java.time.LocalDate;

import static com.jvprogdemo.utils.FormatUtils.DATE_FORMAT;

public class RentalRequest {
	private static final ToolService TOOL_SERVICE = new ToolService();

	private final Tool tool;
	private final int rentalDays;
	private final int discountPercent;
	private final LocalDate checkoutDate;

	private RentalRequest(Tool tool, int rentalDays, int discountPercent, LocalDate checkoutDate) {
		this.tool = tool;
		this.rentalDays = rentalDays;
		this.discountPercent = discountPercent;
		this.checkoutDate = checkoutDate;
	}

	public static RentalRequest createRequest(String toolCode, int rentalDays, int discountPercent,
											  LocalDate checkOutDate) throws NoSuchToolException {
		Tool tmpTool = retrieveTool(toolCode);
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
		return new RentalRequest(tmpTool, rentalDays, discountPercent, checkOutDate);
	}

	private static Tool retrieveTool(String toolCode) throws NoSuchToolException {
		if (toolCode == null || toolCode.isBlank()) {
			throw new IllegalArgumentException("A Checkout Request requires a Tool Code.");
		}
		Tool tmpTool = TOOL_SERVICE.getByKey(toolCode);
		if (tmpTool == null) {
			throw new NoSuchToolException("There is no Tool with a Code of: " + toolCode);
		}
		return tmpTool;
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

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder()
				.append("Checkout Request:\n")
				.append('\t').append("Tool code: ").append(tool.getCode().toUpperCase()).append('\n')
				.append('\t').append("Rental days: ").append(rentalDays).append('\n')
				.append('\t').append("Discount percent: ").append(discountPercent).append('%').append('\n')
				.append('\t').append("Check out date: ").append(DATE_FORMAT.format(checkoutDate)).append('\n')
				;
		return builder.toString();
	}
}
