package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static <T extends Temporal> boolean isBetweenHalfOpen(LocalDateTime lt, T startTime, T endTime) {
        if (startTime instanceof LocalDate && endTime instanceof LocalDate) {
            return lt.compareTo(((LocalDate) startTime).atStartOfDay()) >= 0
                    && lt.compareTo(((LocalDate) endTime).atStartOfDay()) < 0;
        } else if (startTime instanceof LocalTime && endTime instanceof LocalTime) {
            return lt.toLocalTime().compareTo(((LocalTime) startTime)) >= 0
                    && lt.toLocalTime().compareTo(((LocalTime) endTime)) < 0;
        } else if (startTime instanceof LocalDateTime && endTime instanceof LocalDateTime) {
            return lt.compareTo((LocalDateTime) startTime) >= 0 && lt.compareTo((LocalDateTime) endTime) < 0;
        }
        return false;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate toDate(String date) {
        return date == null || date.equals("") ? null : LocalDate.parse(date, DATE_FORMATTER);
    }

    public static LocalTime toTime(String time) {
        return time == null || time.equals("") ? null : LocalTime.parse(time, TIME_FORMATTER);
    }
}

