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

class CheckoutRequestTest {
	AbstractEntityService<Tool> toolService = new ToolService();

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void createCheckoutRequest() throws Exception {
		CheckoutRequest checkoutRequest;
		// valid checkout request
		LocalDate goodCheckOutDate = LocalDate.of(2024, Month.FEBRUARY, 7);
		checkoutRequest = CheckoutRequest.createCheckoutRequest("ChNs", 16, 10, goodCheckOutDate);
		assertNotNull(checkoutRequest);
		assertEquals(toolService.getByKey("chns"), checkoutRequest.getTool());
		assertEquals(16, checkoutRequest.getRentalDays());
		assertEquals(10, checkoutRequest.getDiscountPercent());
		assertEquals(goodCheckOutDate, checkoutRequest.getCheckoutDate());
		// null checkout date
		assertThrowsExactly(IllegalArgumentException.class, () -> CheckoutRequest.createCheckoutRequest("ChNs", 16,
				10, null));
		// good tool codes
		assertEquals("CHNS",
				CheckoutRequest.createCheckoutRequest("ChNs", 3, 10, goodCheckOutDate).getTool().getCode());
		assertEquals("LADW",
				CheckoutRequest.createCheckoutRequest("ladw", 3, 10, goodCheckOutDate).getTool().getCode());
		assertEquals("JAKD",
				CheckoutRequest.createCheckoutRequest("JAKD", 3, 10, goodCheckOutDate).getTool().getCode());
		assertEquals("JAKR",
				CheckoutRequest.createCheckoutRequest("jaKR", 3, 10, goodCheckOutDate).getTool().getCode());
		// bad tool codes
		assertThrowsExactly(IllegalArgumentException.class, () ->  CheckoutRequest.createCheckoutRequest("", 16, 10,
				goodCheckOutDate));
		assertThrowsExactly(IllegalArgumentException.class, () ->  CheckoutRequest.createCheckoutRequest("  ", 16, 10,
				goodCheckOutDate));
		assertThrowsExactly(IllegalArgumentException.class, () ->  CheckoutRequest.createCheckoutRequest(null, 16, 10,
				goodCheckOutDate));
		assertThrowsExactly(NoSuchToolException.class, () ->  CheckoutRequest.createCheckoutRequest("fake-code", 16, 10,
				goodCheckOutDate));
		// good rental days values
		assertEquals(3,
				CheckoutRequest.createCheckoutRequest("ChNs", 3, 10, goodCheckOutDate).getRentalDays());
		assertEquals(16,
				CheckoutRequest.createCheckoutRequest("ChNs", 16, 10, goodCheckOutDate).getRentalDays());
		assertEquals(4736,
				CheckoutRequest.createCheckoutRequest("ChNs", 4736, 10, goodCheckOutDate).getRentalDays());
		// bad rental days values
		assertThrowsExactly(IllegalArgumentException.class, () ->  CheckoutRequest.createCheckoutRequest("", 0, 10,
				goodCheckOutDate));
		assertThrowsExactly(IllegalArgumentException.class, () ->  CheckoutRequest.createCheckoutRequest("", -7, 10,
				goodCheckOutDate));
		// good discount values
		assertEquals(0,
				CheckoutRequest.createCheckoutRequest("ChNs", 3, 0, goodCheckOutDate).getDiscountPercent());
		assertEquals(2,
				CheckoutRequest.createCheckoutRequest("ChNs", 3, 2, goodCheckOutDate).getDiscountPercent());
		assertEquals(15,
				CheckoutRequest.createCheckoutRequest("ChNs", 3, 15, goodCheckOutDate).getDiscountPercent());
		assertEquals(37,
				CheckoutRequest.createCheckoutRequest("ChNs", 3, 37, goodCheckOutDate).getDiscountPercent());
		assertEquals(100,
				CheckoutRequest.createCheckoutRequest("ChNs", 3, 100, goodCheckOutDate).getDiscountPercent());
		// bad discount values
		assertThrowsExactly(IllegalArgumentException.class, () -> CheckoutRequest.createCheckoutRequest("ChNs", 3, -7,
				goodCheckOutDate));
		assertThrowsExactly(IllegalArgumentException.class, () -> CheckoutRequest.createCheckoutRequest("ChNs", 3, -93,
				goodCheckOutDate));
		assertThrowsExactly(IllegalArgumentException.class, () -> CheckoutRequest.createCheckoutRequest("ChNs", 3, -752,
				goodCheckOutDate));
	}
}