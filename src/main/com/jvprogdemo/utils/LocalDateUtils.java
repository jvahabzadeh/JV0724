package com.jvprogdemo.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

public class LocalDateUtils {
	public static int getNumOfWeekendDays(LocalDate startDateExclusive, int numOfDays) {
		// TODO - REALLY need to look over this implementation more carefully
		int weekendDays = 0;
		if (numOfDays > 0) {
			int numOfFullWeeks = numOfDays / 7;
			weekendDays = 2 * numOfFullWeeks;
			int leftoverDays = numOfDays % 7;
			// if leftover days are 0, we're done.
			if (leftoverDays > 0) {
				DayOfWeek dayOfWeek = startDateExclusive.getDayOfWeek();
				DayOfWeek endDayOfWeek = dayOfWeek.plus(leftoverDays);
				do {
					dayOfWeek = dayOfWeek.plus(1);
					switch (dayOfWeek) {
						case SATURDAY:
						case SUNDAY:
							weekendDays++;
							break;
					}
				} while (!dayOfWeek.equals(endDayOfWeek));
			}
		}
		return weekendDays;
	}

	public static LocalDate getObservedIndependenceDayForYear(int year) {
		LocalDate independenceDay = LocalDate.of(year, Month.JULY, 4);
		if (independenceDay.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
			independenceDay = independenceDay.minusDays(1);
		}
		if (independenceDay.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
			independenceDay = independenceDay.plusDays(1);
		}
		return independenceDay;
	}

	public static LocalDate getLaborDayForYear(int year) {
		LocalDate laborDay = LocalDate.of(year, Month.SEPTEMBER, 1);
		int dayValue = laborDay.getDayOfWeek().getValue();
		if (dayValue > 1) {
			laborDay = laborDay.plusDays(8 - dayValue);
		}
		return laborDay;
	}
}
