package com.jvprogdemo.models.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class ToolType extends AbstractEntity {
	private final String typeName;
	private final BigDecimal dailyCharge;
	private final boolean weekdayCharge;
	private final boolean weekendCharge;
	private final boolean holidayCharge;

	public ToolType(String typeName, BigDecimal dailyCharge, boolean weekdayCharge, boolean weekendCharge,
					boolean holidayCharge) {
		if (typeName == null || typeName.isBlank()) {
			throw new IllegalArgumentException("Type Name may not be NULL or blank.");
		}
		if (dailyCharge == null) {
			throw new IllegalArgumentException("Daily Charge may not be NULL.");
		}
		if (dailyCharge.scale() != 2) {
			throw new IllegalArgumentException("Daily Charge must have a scale of 2 for US currency.");
		}
		this.typeName = typeName;
		this.dailyCharge = dailyCharge;
		this.weekdayCharge = weekdayCharge;
		this.weekendCharge = weekendCharge;
		this.holidayCharge = holidayCharge;
	}

	public String getTypeName() {
		return typeName;
	}

	public BigDecimal getDailyCharge() {
		return dailyCharge;
	}

	public boolean isWeekdayCharge() {
		return weekdayCharge;
	}

	public boolean isWeekendCharge() {
		return weekendCharge;
	}

	public boolean isHolidayCharge() {
		return holidayCharge;
	}

	@Override
	public String getKey() {
		return getTypeName();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ToolType toolType = (ToolType) o;
		return Objects.equals(typeName, toolType.typeName);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(typeName);
	}
}
