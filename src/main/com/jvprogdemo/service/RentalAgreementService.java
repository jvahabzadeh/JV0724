package com.jvprogdemo.service;

import com.jvprogdemo.models.CheckoutRequest;
import com.jvprogdemo.models.RentalAgreement;
import com.jvprogdemo.models.entity.Tool;
import com.jvprogdemo.models.entity.ToolType;
import com.jvprogdemo.service.entity.ToolTypeService;
import com.jvprogdemo.utils.LocalDateUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.function.Function;

public class RentalAgreementService {
	enum CompanyHolidays {
		INDEPENDENCE_DAY, LABOR_DAY
	}

	private static final Map<CompanyHolidays, Function<Integer, LocalDate>> HOLIDAY_FUNCTIONS_MAP = Map.of(
			CompanyHolidays.INDEPENDENCE_DAY, LocalDateUtils::getObservedIndependenceDayForYear,
			CompanyHolidays.LABOR_DAY, LocalDateUtils::getLaborDayForYear
	);
	private static final ToolTypeService TOOL_TYPE_SERVICE = new ToolTypeService();


	public RentalAgreement checkout(CheckoutRequest checkoutRequest) {
		Tool tool = checkoutRequest.getTool();
		ToolType toolType = TOOL_TYPE_SERVICE.getByKey(tool.getTypeName());
		String toolCode = tool.getCode();
		String toolTypeName = toolType.getTypeName();
		String toolBrandName = tool.getBrandName();
		int rentalDays = checkoutRequest.getRentalDays();
		LocalDate checkoutDate = checkoutRequest.getCheckOutDate();
		LocalDate dueDate = checkoutDate.plusDays(rentalDays);
		BigDecimal dailyRentalCharge = toolType.getDailyCharge();
		int chargeDays = calculateChargeDays(checkoutDate, dueDate, toolType);
		BigDecimal preDiscountCharge = dailyRentalCharge.multiply(new BigDecimal(chargeDays));
		int discountPercent = checkoutRequest.getDiscountPercent();
		BigDecimal discountAmount = calculateDiscountAmount(preDiscountCharge, discountPercent);
		BigDecimal finalCharge = preDiscountCharge.subtract(discountAmount);
		return new RentalAgreement(toolCode, toolTypeName, toolBrandName, rentalDays, checkoutDate, dueDate,
				dailyRentalCharge, chargeDays, preDiscountCharge, discountPercent, discountAmount, finalCharge);
	}

	private BigDecimal calculateDiscountAmount(BigDecimal originalCharge, int discountPercent) {
		return originalCharge
				.multiply(new BigDecimal(discountPercent))
				.divide(new BigDecimal(100), RoundingMode.HALF_UP);
	}

	private int calculateChargeDays(LocalDate checkoutDate, LocalDate dueDate, ToolType toolType) {
		// typecasting to INT is safe because Integer.MAX_VALUE in days comes out to almost 5.9 million years.  I
		// don't think the company has any sort of policy in place for long term tool rentals of THAT duration.  That
		// is, unless a DeLorean with a Flux Capacitor is involved.
		int chargeDays = (int) ChronoUnit.DAYS.between(checkoutDate, dueDate);
		int numOfWeekendDays = LocalDateUtils.getNumOfWeekendDays(checkoutDate, chargeDays);
		int numOfWeekdays = chargeDays - numOfWeekendDays;
		if(!toolType.isWeekdayCharge()) {
			chargeDays -= numOfWeekdays;
		} else if(!toolType.isHolidayCharge()) {
			// we do the else for holidays only if we know we ARE charging for weekdays because all holidays are on
			// weekdays.
			// if we removed the weekdays because we don't charge on weekdays, then the holidays have already been
			// omitted by this point in the code.
			chargeDays -= numberOfHolidaysIn(checkoutDate, dueDate);
		}
		if(!toolType.isWeekendCharge()) {
			chargeDays -= numOfWeekendDays;
		}

		return chargeDays;
	}

	private int numberOfHolidaysIn(LocalDate checkoutDate, LocalDate dueDate) {
		int firstYear = checkoutDate.getYear();
		int lastYear = dueDate.getYear();
		// initial calculation below is number of holidays times number of WHOLE YEARS between checkout and due dates
		int numOfHolidays = HOLIDAY_FUNCTIONS_MAP.size() * (firstYear == lastYear ? 0 : lastYear - firstYear - 1);
		// now check for holidays on the first year and last year of the rental period
		for (Function<Integer, LocalDate> func : HOLIDAY_FUNCTIONS_MAP.values()) {
			if (func.apply(firstYear).isAfter(checkoutDate)) {
				numOfHolidays++;
			}
			if (!func.apply(lastYear).isAfter(dueDate)) {
				numOfHolidays++;
			}
		}
		return numOfHolidays;
	}
}
