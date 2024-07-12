package com.jvprogdemo.service;

import com.jvprogdemo.models.RentalRequest;
import com.jvprogdemo.models.RentalAgreement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class RentalAgreementServiceTest {
	RentalAgreementService rentalAgreementService = new RentalAgreementService();
	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	private void commonTestAssertions(String toolCode, int rentalDays, int discountPct, LocalDate ckoutDt,
									  BigDecimal dailyCharge, String expectedToolName, String expectedBrandName,
									  int expectedChargeDays) throws Exception {
		RentalRequest rentalRequest = RentalRequest.createRequest(toolCode, rentalDays, discountPct, ckoutDt);
		assertNotNull(rentalRequest);
		RentalAgreement rentalAgreement = rentalAgreementService.checkout(rentalRequest);
		assertNotNull(rentalAgreement);
		BigDecimal preDiscountChg = dailyCharge.multiply(new BigDecimal(rentalAgreement.getChargeDays()));
		BigDecimal discountAmt = preDiscountChg.multiply(new BigDecimal(discountPct)).divide(new BigDecimal(100),
				RoundingMode.HALF_UP);
		assertEquals(toolCode, rentalAgreement.getToolCode());
		assertEquals(expectedToolName, rentalAgreement.getToolTypeName());
		assertEquals(expectedBrandName, rentalAgreement.getToolBrandName());
		assertEquals(rentalDays, rentalAgreement.getRentalDays());
		assertEquals(ckoutDt, rentalAgreement.getCheckoutDate());
		assertEquals(ckoutDt.plusDays(rentalDays), rentalAgreement.getDueDate());
		assertEquals(dailyCharge, rentalAgreement.getDailyRentalCharge());
		assertEquals(expectedChargeDays, rentalAgreement.getChargeDays());
		assertEquals(preDiscountChg, rentalAgreement.getPreDiscountCharge());
		assertEquals(discountPct, rentalAgreement.getDiscountPercent());
		assertEquals(discountAmt, rentalAgreement.getDiscountAmount());
		assertEquals(preDiscountChg.subtract(discountAmt), rentalAgreement.getFinalCharge());

//		System.out.println(rentalAgreement);
//		System.out.println("-------------------------------------------------");
	}

	@Test
	void checkout() throws Exception {
		// TESTS 2 through 6 FROM DOCUMENT
		String toolCode;
		int rentalDays;
		int discountPct;
		LocalDate ckoutDt;
		// TEST 2
		toolCode = "LADW";
		rentalDays = 3;
		discountPct = 10;
		ckoutDt = LocalDate.of(2020, Month.JULY, 2);
		commonTestAssertions(toolCode, rentalDays, discountPct, ckoutDt, new BigDecimal("1.99"), "Ladder", "Werner", 2);

		// TEST 3
		toolCode = "CHNS";
		rentalDays = 5;
		discountPct = 25;
		ckoutDt = LocalDate.of(2015, Month.JULY, 2);
		commonTestAssertions(toolCode, rentalDays, discountPct, ckoutDt, new BigDecimal("1.49"), "Chainsaw", "Stihl", 3);

		// TEST 4
		toolCode = "JAKD";
		rentalDays = 6;
		discountPct = 0;
		ckoutDt = LocalDate.of(2015, Month.SEPTEMBER, 3);
		commonTestAssertions(toolCode, rentalDays, discountPct, ckoutDt, new BigDecimal("2.99"), "Jackhammer", "DeWalt",
				3);

		// TEST 5
		toolCode = "JAKR";
		rentalDays = 9;
		ckoutDt = LocalDate.of(2015, Month.JULY, 2);
		commonTestAssertions(toolCode, rentalDays, discountPct, ckoutDt, new BigDecimal("2.99"), "Jackhammer", "Ridgid",
				5);

		// TEST 6
		toolCode = "JAKR";
		rentalDays = 4;
		discountPct = 50;
		ckoutDt = LocalDate.of(2020, Month.JULY, 2);
		commonTestAssertions(toolCode, rentalDays, discountPct, ckoutDt, new BigDecimal("2.99"), "Jackhammer", "Ridgid",
				1);
	}

	@Test
	void checkoutAdditionalTests() throws Exception {
		String toolCode;
		int rentalDays;
		int discountPct;
		LocalDate ckoutDt;
		toolCode = "CHNS";
		rentalDays = 1400; // 200 weeks
		discountPct = 15;
		ckoutDt = LocalDate.of(2024, Month.JANUARY, 1);
		// 200 weeks chainsaw test - only weekdays, so rental days should be 1,000 charge days
		// ergo, $1,490.00 charge, and 15% discount is $223.50, so final is $1,266.50
		commonTestAssertions(toolCode, rentalDays, discountPct, ckoutDt, new BigDecimal("1.49"), "Chainsaw", "Stihl",
				1000);

		// same rental days and discount pct as above, but jackhammer, which also skips holidays.
		// 8 weeks less than 4 full years, so hits Independence Day and Labor Day 4 times each.
		// Thus, 992 charge days, $2,966.24, and 15% discount is $444.936 -> $444.94, so final is $2,521.30
		toolCode = "JAKR";
		commonTestAssertions(toolCode, rentalDays, discountPct, ckoutDt, new BigDecimal("2.99"), "Jackhammer", "Ridgid",
				992);

		// ladder, only skips holidays. 6 full years, so 2 leap years (2024 and 2028).  2 holidays/year = 12 holidays.
		// Thus, 2,180 charge days, $4,334.22, 10% discount = $433.422 -> $433.42, so final is $3,400.80
		toolCode = "LADW";
		rentalDays = (int)ChronoUnit.DAYS.between(ckoutDt, ckoutDt.plusYears(6));
		discountPct = 10;
		commonTestAssertions(toolCode, rentalDays, discountPct, ckoutDt, new BigDecimal("1.99"), "Ladder", "Werner",
				2180);
	}


}