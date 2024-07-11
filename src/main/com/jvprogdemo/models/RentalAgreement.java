package com.jvprogdemo.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.jvprogdemo.utils.FormatUtils.CURRENCY_FORMAT;
import static com.jvprogdemo.utils.FormatUtils.DATE_FORMAT;

public class RentalAgreement {

	private final String toolCode;
	private final String toolTypeName;
	private final String toolBrandName;
	private final int rentalDays;
	private final LocalDate checkoutDate;
	private final LocalDate dueDate;
	private final BigDecimal dailyRentalCharge;
	private final int chargeDays;
	private final BigDecimal preDiscountCharge;
	private final int discountPercent;
	private final BigDecimal discountAmount;
	private final BigDecimal finalCharge;

	public RentalAgreement(String toolCode, String toolTypeName, String toolBrandName, int rentalDays,
						   LocalDate checkoutDate, LocalDate dueDate, BigDecimal dailyRentalCharge, int chargeDays,
						   BigDecimal preDiscountCharge, int discountPercent, BigDecimal discountAmount,
						   BigDecimal finalCharge) {
		this.toolCode = toolCode;
		this.toolTypeName = toolTypeName;
		this.toolBrandName = toolBrandName;
		this.rentalDays = rentalDays;
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
		this.dailyRentalCharge = dailyRentalCharge;
		this.chargeDays = chargeDays;
		this.preDiscountCharge = preDiscountCharge;
		this.discountPercent = discountPercent;
		this.discountAmount = discountAmount;
		this.finalCharge = finalCharge;
	}

	// maybe a separate method would be better rather than overriding toString
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder()
				.append("Rental Agreement:\n")
				.append('\t').append("Tool code: ").append(toolCode.toUpperCase()).append('\n')
				.append('\t').append("Tool type: ").append(toolTypeName).append('\n')
				.append('\t').append("Tool brand: ").append(toolBrandName).append('\n')
				.append('\t').append("Rental days: ").append(rentalDays).append('\n')
				.append('\t').append("Check out date: ").append(DATE_FORMAT.format(checkoutDate)).append('\n')
				.append('\t').append("Due due date: ").append(DATE_FORMAT.format(dueDate)).append('\n')
				.append('\t').append("Daily rental charge: ").append(CURRENCY_FORMAT.format(dailyRentalCharge)).append('\n')
				.append('\t').append("Charge days: ").append(chargeDays).append('\n')
				.append('\t').append("Pre-discount charge: ").append(CURRENCY_FORMAT.format(preDiscountCharge)).append('\n')
				.append('\t').append("Discount percent: ").append(discountPercent).append('%').append('\n')
				.append('\t').append("Discount amount: ").append(CURRENCY_FORMAT.format(discountAmount)).append('\n')
				.append('\t').append("Final charge: ").append(CURRENCY_FORMAT.format(finalCharge)).append('\n');
		return builder.toString();
	}
}
