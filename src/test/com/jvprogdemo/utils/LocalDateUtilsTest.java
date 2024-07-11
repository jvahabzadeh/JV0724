package com.jvprogdemo.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalDateUtilsTest {

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void getObservedIndependenceDayForYear() {
		assertEquals(DayOfWeek.THURSDAY, LocalDateUtils.getObservedIndependenceDayForYear(2030).getDayOfWeek());
		assertEquals(DayOfWeek.WEDNESDAY, LocalDateUtils.getObservedIndependenceDayForYear(2029).getDayOfWeek());
		assertEquals(DayOfWeek.TUESDAY, LocalDateUtils.getObservedIndependenceDayForYear(2028).getDayOfWeek());
		assertEquals(DayOfWeek.MONDAY, LocalDateUtils.getObservedIndependenceDayForYear(2027).getDayOfWeek()); // SUN
		assertEquals(DayOfWeek.FRIDAY, LocalDateUtils.getObservedIndependenceDayForYear(2026).getDayOfWeek()); // SAT
		assertEquals(DayOfWeek.FRIDAY, LocalDateUtils.getObservedIndependenceDayForYear(2025).getDayOfWeek());
		assertEquals(DayOfWeek.THURSDAY, LocalDateUtils.getObservedIndependenceDayForYear(2024).getDayOfWeek());
		assertEquals(DayOfWeek.TUESDAY, LocalDateUtils.getObservedIndependenceDayForYear(2023).getDayOfWeek());
		assertEquals(DayOfWeek.MONDAY, LocalDateUtils.getObservedIndependenceDayForYear(2022).getDayOfWeek());
		assertEquals(DayOfWeek.MONDAY, LocalDateUtils.getObservedIndependenceDayForYear(2021).getDayOfWeek()); // SUN
		assertEquals(DayOfWeek.FRIDAY, LocalDateUtils.getObservedIndependenceDayForYear(2020).getDayOfWeek()); // SAT
		assertEquals(DayOfWeek.THURSDAY, LocalDateUtils.getObservedIndependenceDayForYear(2019).getDayOfWeek());
		assertEquals(DayOfWeek.WEDNESDAY, LocalDateUtils.getObservedIndependenceDayForYear(2018).getDayOfWeek());
		assertEquals(DayOfWeek.TUESDAY, LocalDateUtils.getObservedIndependenceDayForYear(2017).getDayOfWeek());
		assertEquals(DayOfWeek.MONDAY, LocalDateUtils.getObservedIndependenceDayForYear(2016).getDayOfWeek());
		assertEquals(DayOfWeek.FRIDAY, LocalDateUtils.getObservedIndependenceDayForYear(2015).getDayOfWeek()); // SAT
		assertEquals(DayOfWeek.FRIDAY, LocalDateUtils.getObservedIndependenceDayForYear(2014).getDayOfWeek());
		assertEquals(DayOfWeek.THURSDAY, LocalDateUtils.getObservedIndependenceDayForYear(2013).getDayOfWeek());

	}

	@Test
	void getLaborDayForYear() {
		for (int year = 2020; year < 2026; year++) {
			assertEquals(4, LocalDateUtils.getLaborDayForYear(2028).getDayOfMonth());
			assertEquals(6, LocalDateUtils.getLaborDayForYear(2027).getDayOfMonth());
			assertEquals(7, LocalDateUtils.getLaborDayForYear(2026).getDayOfMonth());
			assertEquals(1, LocalDateUtils.getLaborDayForYear(2025).getDayOfMonth());
			assertEquals(2, LocalDateUtils.getLaborDayForYear(2024).getDayOfMonth());
			// 2024 was a leap year, thus the jump
			assertEquals(4, LocalDateUtils.getLaborDayForYear(2023).getDayOfMonth());
			assertEquals(5, LocalDateUtils.getLaborDayForYear(2022).getDayOfMonth());
			assertEquals(6, LocalDateUtils.getLaborDayForYear(2021).getDayOfMonth());
			assertEquals(7, LocalDateUtils.getLaborDayForYear(2020).getDayOfMonth());
			// 2020 was a leap year, thus the jump
			assertEquals(2, LocalDateUtils.getLaborDayForYear(2019).getDayOfMonth());
			assertEquals(3, LocalDateUtils.getLaborDayForYear(2018).getDayOfMonth());
			assertEquals(4, LocalDateUtils.getLaborDayForYear(2017).getDayOfMonth());
			assertEquals(5, LocalDateUtils.getLaborDayForYear(2016).getDayOfMonth());
			// 2016 was a leap year, thus the jump
			assertEquals(7, LocalDateUtils.getLaborDayForYear(2015).getDayOfMonth());
			assertEquals(1, LocalDateUtils.getLaborDayForYear(2014).getDayOfMonth());
			assertEquals(2, LocalDateUtils.getLaborDayForYear(2013).getDayOfMonth());
		}
	}

	@Test
	void getNumOfWeekendDays() {
		// TODO - REALLY need to look over this unit test more thoroughly, and add more test cases
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMMM d, yyyy : EEEE");
		LocalDate startDate = LocalDate.of(2024, Month.JULY, 1); // a Monday, so first rental day is Tuesday
		for (int days = 0; days < 100; days++) {
			LocalDate endDate = startDate.plusDays(days);
			int weekendDays = LocalDateUtils.getNumOfWeekendDays(startDate, days);
			int expected = 2 * (days / 7);
			if (days > 0) {
				if (endDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
					expected++;
				}
				if (endDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
					expected += 2;
				}
			}
			System.out.println("START: " + fmt.format(startDate) + " -- END: " + fmt.format(endDate) + " -- Weekend " +
					"Days expected: " + expected + "   actual: " + weekendDays);
			assertEquals(expected, weekendDays);
		}

		startDate = LocalDate.of(2015, Month.AUGUST, 8); // a Saturday, so first rental day is Sunday
		for (int days = 0; days < 100; days++) {
			LocalDate endDate = startDate.plusDays(days);
			int weekendDays = LocalDateUtils.getNumOfWeekendDays(startDate, days);
			int expected = 2 * (days / 7);
			if (days > 0) {
				if (!endDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
					expected++;
				}
			}
			System.out.println("START: " + fmt.format(startDate) + " -- END: " + fmt.format(endDate) + " -- Weekend " +
					"Days expected: " + expected + "   actual: " + weekendDays);
			assertEquals(expected, weekendDays);
		}
	}
}