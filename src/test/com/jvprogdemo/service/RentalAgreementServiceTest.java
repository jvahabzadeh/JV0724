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

import static org.junit.jupiter.api.Assertions.*;

class RentalAgreementServiceTest {
	RentalAgreementService rentalAgreementService = new RentalAgreementService();
	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	private void commonTestAssertions(BigDecimal dailyCharge, int rentalDays, int discountPct, LocalDate ckoutDt,
									  String toolCode, String toolName, String brandName, int chargeDays,
									  RentalAgreement rentalAgreement) {
		BigDecimal preDiscountChg = dailyCharge.multiply(new BigDecimal(rentalAgreement.getChargeDays()));
		BigDecimal discountAmt = preDiscountChg.multiply(new BigDecimal(discountPct)).divide(new BigDecimal(100),
				RoundingMode.HALF_UP);
		assertEquals(toolCode, rentalAgreement.getToolCode());
		assertEquals(toolName, rentalAgreement.getToolTypeName());
		assertEquals(brandName, rentalAgreement.getToolBrandName());
		assertEquals(rentalDays, rentalAgreement.getRentalDays());
		assertEquals(ckoutDt, rentalAgreement.getCheckoutDate());
		assertEquals(ckoutDt.plusDays(rentalDays), rentalAgreement.getDueDate());
		assertEquals(dailyCharge, rentalAgreement.getDailyRentalCharge());
		assertEquals(chargeDays, rentalAgreement.getChargeDays());
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
		RentalRequest rentalRequest;
		RentalAgreement rentalAgreement;
		String toolCode;
		int rentalDays;
		int discountPct;
		LocalDate ckoutDt;
		// TEST 2
		toolCode = "LADW";
		rentalDays = 3;
		discountPct = 10;
		ckoutDt = LocalDate.of(2020, Month.JULY, 2);
		rentalRequest = RentalRequest.createRequest(toolCode, rentalDays, discountPct, ckoutDt);
		assertNotNull(rentalRequest);
		rentalAgreement = rentalAgreementService.checkout(rentalRequest);
		assertNotNull(rentalAgreement);
		commonTestAssertions(new BigDecimal("1.99"), rentalDays, discountPct, ckoutDt,
				toolCode, "Ladder", "Werner", 2, rentalAgreement);

		// TEST 3
		toolCode = "CHNS";
		rentalDays = 5;
		discountPct = 25;
		ckoutDt = LocalDate.of(2015, Month.JULY, 2);
		rentalRequest = RentalRequest.createRequest(toolCode, rentalDays, discountPct, ckoutDt);
		assertNotNull(rentalRequest);
		rentalAgreement = rentalAgreementService.checkout(rentalRequest);
		assertNotNull(rentalAgreement);
		commonTestAssertions(new BigDecimal("1.49"), rentalDays, discountPct, ckoutDt,
				toolCode, "Chainsaw", "Stihl", 3, rentalAgreement);

		// TEST 4
		toolCode = "JAKD";
		rentalDays = 6;
		discountPct = 0;
		ckoutDt = LocalDate.of(2015, Month.SEPTEMBER, 3);
		rentalRequest = RentalRequest.createRequest(toolCode, rentalDays, discountPct, ckoutDt);
		assertNotNull(rentalRequest);
		rentalAgreement = rentalAgreementService.checkout(rentalRequest);
		assertNotNull(rentalAgreement);
		commonTestAssertions(new BigDecimal("2.99"), rentalDays, discountPct, ckoutDt,
				toolCode, "Jackhammer", "DeWalt", 3, rentalAgreement);

		// TEST 5
		toolCode = "JAKR";
		rentalDays = 9;
		discountPct = 0;
		ckoutDt = LocalDate.of(2015, Month.JULY, 2);
		rentalRequest = RentalRequest.createRequest(toolCode, rentalDays, discountPct, ckoutDt);
		assertNotNull(rentalRequest);
		rentalAgreement = rentalAgreementService.checkout(rentalRequest);
		assertNotNull(rentalAgreement);
		commonTestAssertions(new BigDecimal("2.99"), rentalDays, discountPct, ckoutDt,
				toolCode, "Jackhammer", "Ridgid", 5, rentalAgreement);

		// TEST 6
		toolCode = "JAKR";
		rentalDays = 4;
		discountPct = 50;
		ckoutDt = LocalDate.of(2020, Month.JULY, 2);
		rentalRequest = RentalRequest.createRequest(toolCode, rentalDays, discountPct, ckoutDt);
		assertNotNull(rentalRequest);
		rentalAgreement = rentalAgreementService.checkout(rentalRequest);
		assertNotNull(rentalAgreement);
		commonTestAssertions(new BigDecimal("2.99"), rentalDays, discountPct, ckoutDt,
				toolCode, "Jackhammer", "Ridgid", 1, rentalAgreement);
	}
}