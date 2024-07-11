package com.jvprogdemo.models;

import com.jvprogdemo.exceptions.NoSuchToolException;
import com.jvprogdemo.models.entity.Tool;
import com.jvprogdemo.service.entity.AbstractEntityService;
import com.jvprogdemo.service.entity.ToolService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class RentalRequestTest {
	AbstractEntityService<Tool> toolService = new ToolService();

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void createRequest() throws Exception {
		// TEST 1 FROM DOCUMENT
		assertThrowsExactly(IllegalArgumentException.class, () -> RentalRequest.createRequest("JAKR", 5, 101,
						LocalDate.of(2015, Month.SEPTEMBER, 3)),
				"Expected Illegal Argument Exception expected for discountPct value of 101 did not occur.");

		// ADDITIONAL TESTS
		RentalRequest rentalRequest;
		// valid checkout request
		LocalDate goodCheckOutDate = LocalDate.of(2024, Month.FEBRUARY, 7);
		rentalRequest = RentalRequest.createRequest("ChNs", 16, 10, goodCheckOutDate);
		assertNotNull(rentalRequest);
		assertEquals(toolService.getByKey("chns"), rentalRequest.getTool());
		assertEquals(16, rentalRequest.getRentalDays());
		assertEquals(10, rentalRequest.getDiscountPercent());
		assertEquals(goodCheckOutDate, rentalRequest.getCheckoutDate());
		// null checkout date
		assertThrowsExactly(IllegalArgumentException.class, () -> RentalRequest.createRequest("ChNs", 16,
				10, null), "Expected Illegal Argument Exception for a NULL checkout date did not occur.");
		// good tool codes
		assertEquals("CHNS",
				RentalRequest.createRequest("ChNs", 3, 10, goodCheckOutDate).getTool().getCode());
		assertEquals("LADW",
				RentalRequest.createRequest("ladw", 3, 10, goodCheckOutDate).getTool().getCode());
		assertEquals("JAKD",
				RentalRequest.createRequest("JAKD", 3, 10, goodCheckOutDate).getTool().getCode());
		assertEquals("JAKR",
				RentalRequest.createRequest("jaKR", 3, 10, goodCheckOutDate).getTool().getCode());
		// bad tool codes
		assertThrowsExactly(IllegalArgumentException.class, () -> RentalRequest.createRequest("", 16, 10,
				goodCheckOutDate), "Expected Illegal Argument Exception for an empty Tool Code did not occur.");
		assertThrowsExactly(IllegalArgumentException.class, () -> RentalRequest.createRequest("  ", 16, 10,
				goodCheckOutDate), "Expected Illegal Argument Exception for a blank Tool Code did not occur.");
		assertThrowsExactly(IllegalArgumentException.class, () -> RentalRequest.createRequest(null, 16, 10,
				goodCheckOutDate), "Expected Illegal Argument Exception for a NULL Tool Code did not occur.");
		assertThrowsExactly(NoSuchToolException.class, () -> RentalRequest.createRequest("fake-code", 16, 10,
				goodCheckOutDate), "Expected No Such Tool Exception for a non-existent Tool Code did not occur.");
		// good rental days values
		assertEquals(3,
				RentalRequest.createRequest("ChNs", 3, 10, goodCheckOutDate).getRentalDays());
		assertEquals(16,
				RentalRequest.createRequest("ChNs", 16, 10, goodCheckOutDate).getRentalDays());
		assertEquals(4736,
				RentalRequest.createRequest("ChNs", 4736, 10, goodCheckOutDate).getRentalDays());
		// bad rental days values
		assertThrowsExactly(IllegalArgumentException.class, () -> RentalRequest.createRequest("ChNs", 0, 10,
				goodCheckOutDate), "Expected Illegal Argument Exception for 0 rental days did not occur.");
		assertThrowsExactly(IllegalArgumentException.class, () -> RentalRequest.createRequest("ChNs", -7, 10,
				goodCheckOutDate),
				"Expected Illegal Argument Exception for a negative number of rental days did not occur.");
		// good discount values
		assertEquals(0,
				RentalRequest.createRequest("ChNs", 3, 0, goodCheckOutDate).getDiscountPercent());
		assertEquals(2,
				RentalRequest.createRequest("ChNs", 3, 2, goodCheckOutDate).getDiscountPercent());
		assertEquals(15,
				RentalRequest.createRequest("ChNs", 3, 15, goodCheckOutDate).getDiscountPercent());
		assertEquals(37,
				RentalRequest.createRequest("ChNs", 3, 37, goodCheckOutDate).getDiscountPercent());
		assertEquals(100,
				RentalRequest.createRequest("ChNs", 3, 100, goodCheckOutDate).getDiscountPercent());
		// bad discount values
		assertThrowsExactly(IllegalArgumentException.class, () -> RentalRequest.createRequest("ChNs", 3, -7,
				goodCheckOutDate), "Expected Illegal Argument Exception for a negative discount value did not occur.");
		assertThrowsExactly(IllegalArgumentException.class, () -> RentalRequest.createRequest("ChNs", 3, -93,
				goodCheckOutDate), "Expected Illegal Argument Exception for a negative discount value did not occur.");
		assertThrowsExactly(IllegalArgumentException.class, () -> RentalRequest.createRequest("ChNs", 3, -752,
				goodCheckOutDate), "Expected Illegal Argument Exception for a negative discount value did not occur.");
		assertThrowsExactly(IllegalArgumentException.class, () -> RentalRequest.createRequest("ChNs", 3, 153,
				goodCheckOutDate),
				"Expected Illegal Argument Exception for a discount value greater than 100 did not occur.");
	}
}