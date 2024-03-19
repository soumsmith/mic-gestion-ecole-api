package com.vieecoles.steph.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

	public static Date getLastTimeFromDate(Date date) {
		LocalDate dt = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
		return asDate(dt.atTime(LocalTime.MAX));
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

	public static int getNumDay(Date date) {
		int jourNum;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		jourNum = calendar.get(Calendar.DAY_OF_WEEK);
		return jourNum;
	}

	/**
	 * Calcule la durée entre deux heures au format hh:mm.
	 * 
	 * @param heureDebut
	 * @param heureFin
	 * @return la durée en minutes
	 */
	public static int calculerDuree(String heureDebut, String heureFin) {
		String[] debutParts = heureDebut.split(":");
		String[] finParts = heureFin.split(":");

		int debutHeures = Integer.parseInt(debutParts[0]);
		int debutMinutes = Integer.parseInt(debutParts[1]);
		int finHeures = Integer.parseInt(finParts[0]);
		int finMinutes = Integer.parseInt(finParts[1]);

		int dureeHeures = finHeures - debutHeures;
		int dureeMinutes = finMinutes - debutMinutes;

		if (dureeMinutes < 0) {
			dureeHeures--;
			dureeMinutes += 60;
		}
		System.out.println(String.format("%02d:%02d soit %02d minutes", dureeHeures, dureeMinutes,
				dureeHeures * 60 + dureeMinutes));
		return dureeHeures * 60 + dureeMinutes;
	}

	/**
	 * Calcule la somme d'une heure au format hh:mm et de minutes additionnelles.
	 * 
	 * @param heure
	 * @param minutes
	 * @return l'heure résultante au format hh:mm
	 */
	public static String ajouterMinutes(String heure, int minutes) {
		String[] heureParts = heure.split(":");

		int heures = Integer.parseInt(heureParts[0]);
		int minutesActuelles = Integer.parseInt(heureParts[1]);

		int totalMinutes = heures * 60 + minutesActuelles + minutes;
		int heuresResultat = totalMinutes / 60;
		int minutesResultat = totalMinutes % 60;

		return String.format("%02d:%02d", heuresResultat, minutesResultat);
	}

	/**
	 * Cette méthode vérifie si l'heure entrée est comprise entre la marge de lheure
	 * actuelle - marge debut et l'heure actuelle + margefin.
	 * 
	 * @param heure
	 * @param margeDebut
	 * @param margeFin
	 * @return un boolean true si l'heure est comprise sinon false
	 */
	public static boolean verifierHeureDansMarge(String heure, int margeDebut, int margeFin) {
		LocalTime heureActuelle = LocalTime.now();
		LocalTime heureParametre = LocalTime.parse(heure, DateTimeFormatter.ofPattern("HH:mm"));

		LocalTime heureDebutMarge = heureActuelle.minusMinutes(margeDebut);
		LocalTime heureFinMarge = heureActuelle.plusMinutes(margeFin);

		return !heureParametre.isBefore(heureDebutMarge) && !heureParametre.isAfter(heureFinMarge);
	}
}
