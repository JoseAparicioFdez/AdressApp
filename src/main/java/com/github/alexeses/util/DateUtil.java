package com.github.alexeses.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {

    /** Gestion del formato de fecha */
    private static final String DATE_PATTERN = "dd.MM.yyyy";
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * Devuelve la fecha como un String formateado. El formato de la fecha es definido por
     * la constante DATE_PATTERN.
     *
     * @param date La fecha a devolver como String
     * @return String formateado
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * Convierte un String en un objeto LocalDate (si el String es válido).
     *
     * @param dateString El String a convertir en una fecha
     * @return El objeto LocalDate o null si no es una fecha válida
     */
    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Comprueba si el String es una fecha válida.
     *
     * @param dateString
     * @return true si es una fecha válida
     */
    public static boolean validDate(String dateString) {
        // Try to parse the String.
        return DateUtil.parse(dateString) != null;
    }
}
