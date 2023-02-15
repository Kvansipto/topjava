package ru.javawebinar.topjava.util;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalDateTime lt, LocalDate startDate, LocalTime startTime,
                                            LocalDate endDate, LocalTime endTime) {
        LocalDate localDate = lt.toLocalDate();
        LocalTime localTime = lt.toLocalTime();
        return (localDate.compareTo(startDate) >= 0 && localDate.compareTo(endDate) <= 0)
                && (localTime.compareTo(startTime) >= 0 && localTime.compareTo(endTime) < 0);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate toDate(String date) {
        return StringUtils.hasLength(date) ? LocalDate.parse(date) : null;
    }

    public static LocalTime toTime(String time) {
        return StringUtils.hasLength(time) ? LocalTime.parse(time) : null;
    }
}

