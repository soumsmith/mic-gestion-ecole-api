package com.vieecoles.steph.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	/**
	 * Obtenir la date a partir d une chaine de caractères sous le format yyyy-MM-dd
	 *
	 * @param date
	 * @return
	 */
	public static LocalDate getDateWithString(String date) {
		LocalDate dt = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		return dt;
	}

	public static Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date asDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate asLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static LocalDateTime asLocalDateTime(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	// Ajouter un nombre de jours à une date
	
	public static Date addDays(Date baseDate, Integer nombreJours) {
		LocalDate initDate = asLocalDate(baseDate);	
		LocalDate targetDate = initDate.plusDays(nombreJours);
		
		return asDate(targetDate);
	}
	
	public static Date getDateAtStartDay(Date date) {
		LocalDate localDate = asLocalDate(date);
		return asDate(localDate.atStartOfDay());
	}
}
