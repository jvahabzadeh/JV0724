package com.jvprogdemo.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class ToolType extends AbstractEntity {
	private final String type;
	private final BigDecimal dailyCharge;
	private final boolean weekdayCharge;
	private final boolean weekendCharge;
	private final boolean holidayCharge;

	public ToolType(String type, BigDecimal dailyCharge, boolean weekdayCharge, boolean weekendCharge,
					boolean holidayCharge) {
		if (type == null || type.isBlank()) {
			throw new IllegalArgumentException("Tool Type may not be NULL or blank.");
		}
		if (dailyCharge == null) {
			throw new IllegalArgumentException("Daily Charge for a tool type may not be NULL.");
		}
		if (dailyCharge.scale() != 2) {
			throw new IllegalArgumentException("Daily Charge must have a scale of 2 for US currency.");
		}
		this.type = type;
		this.dailyCharge = dailyCharge;
		this.weekdayCharge = weekdayCharge;
		this.weekendCharge = weekendCharge;
		this.holidayCharge = holidayCharge;
	}

	public String getType() {
		return type;
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ToolType toolType = (ToolType) o;
		return Objects.equals(type, toolType.type);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(type);
	}
}
