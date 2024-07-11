package com.jvprogdemo.service;

import com.jvprogdemo.models.RentalRequest;
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
	// TODO - I did the enum and map for clarity, but can probably get rid of the enum and turn the map into a list
	//  or a set
	enum CompanyHolidays {
		INDEPENDENCE_DAY, LABOR_DAY
	}

	private static final Map<CompanyHolidays, Function<Integer, LocalDate>> HOLIDAY_FUNCTIONS_MAP = Map.of(
			CompanyHolidays.INDEPENDENCE_DAY, LocalDateUtils::getObservedIndependenceDayForYear,
			CompanyHolidays.LABOR_DAY, LocalDateUtils::getLaborDayForYear
	);
	private static final ToolTypeService TOOL_TYPE_SERVICE = new ToolTypeService();


	public RentalAgreement checkout(RentalRequest rentalRequest) {
		Tool tool = rentalRequest.getTool();
		ToolType toolType = TOOL_TYPE_SERVICE.getByKey(tool.getTypeName());
		String toolCode = tool.getCode();
		String toolTypeName = toolType.getTypeName();
		String toolBrandName = tool.getBrandName();
		int rentalDays = rentalRequest.getRentalDays();
		LocalDate checkoutDate = rentalRequest.getCheckoutDate();
		LocalDate dueDate = checkoutDate.plusDays(rentalDays);
		BigDecimal dailyRentalCharge = toolType.getDailyCharge();
		int chargeDays = calculateChargeDays(checkoutDate, dueDate, toolType);
		BigDecimal preDiscountCharge = dailyRentalCharge.multiply(new BigDecimal(chargeDays));
		int discountPercent = rentalRequest.getDiscountPercent();
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
		int finalYear = dueDate.getYear();
		// initial calculation below is number of holidays times number of WHOLE YEARS between checkout and due dates
		int numOfFullYears = firstYear == finalYear ? 0 : finalYear - firstYear - 1;
		int numOfHolidays = HOLIDAY_FUNCTIONS_MAP.size() * numOfFullYears;
		// now check for holidays on the first year and last year of the rental period, as those are partial years
		for (Function<Integer, LocalDate> func : HOLIDAY_FUNCTIONS_MAP.values()) {
			LocalDate holidayDate = func.apply(firstYear);
			if (holidayDate.isAfter(checkoutDate)) {
				if (firstYear == finalYear) {
					numOfHolidays += holidayDate.isAfter(dueDate) ? 0 : 1;
				} else {
					numOfHolidays++;
					holidayDate = func.apply(finalYear);
					numOfHolidays += holidayDate.isAfter(dueDate) ? 0 : 1;

				}
			}
		}
		return numOfHolidays;
	}
}
