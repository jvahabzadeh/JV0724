package com.jvprogdemo.models;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class RentalAgreement {
	// I can't say I approve of 2-digit year formatting, but...
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yy");
	private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(Locale.US);

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
				.append("Tool code: ").append(toolCode.toUpperCase())
				.append("Tool type: ").append(toolTypeName)
				.append("Tool brand: ").append(toolBrandName)
				.append("Rental days: ").append(rentalDays)
				.append("Check out date: ").append(DATE_FORMAT.format(checkoutDate))
				.append("Due due date: ").append(DATE_FORMAT.format(dueDate))
				.append("Daily rental charge: ").append(CURRENCY_FORMAT.format(dailyRentalCharge))
				.append("Charge days: ").append(chargeDays)
				.append("Pre-discount charge: ").append(CURRENCY_FORMAT.format(preDiscountCharge))
				.append("Discount percent: ").append(discountPercent).append('%')
				.append("Discount amount: ").append(CURRENCY_FORMAT.format(discountAmount))
				.append("Final charge: ").append(CURRENCY_FORMAT.format(finalCharge));
		return builder.toString();
	}
}
