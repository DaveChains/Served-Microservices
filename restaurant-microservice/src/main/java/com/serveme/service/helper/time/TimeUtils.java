package com.serveme.service.helper.time;

import java.sql.Timestamp;
import java.time.*;

public interface TimeUtils {
    LocalDateTime dateTimeNow();

    LocalTime timeNow();

    /**
     * The current date, as relevant for scheduling, billing etc.
     */
    LocalDate getCurrentDate();

    /**
     * Gets the time zone that the business operates in (rather than that of the computer we're
     * running on).
     */
    ZoneId getBusinessTimeZone();

    LocalDateTime fromTimestamp(Timestamp timestamp);

    Timestamp toTimestamp(LocalDateTime dateTime);

    Timestamp toTimestamp(LocalDate date);

    LocalDateTime plus(LocalDateTime dateTime, Duration duration);

    LocalDate plus(LocalDate date, Duration duration);

    LocalDateTime minus(LocalDateTime dateTime, Duration duration);

    LocalDate minus(LocalDate date, Duration duration);

    LocalDateTime atZone(LocalDateTime dateTime);

    Duration durationBetween(LocalDateTime dateTime1, LocalDateTime dateTime2);

    Duration durationBetween(LocalTime dateTime1, LocalTime dateTime2);

    LocalDateTime dateTimeOf(LocalDate date, LocalTime time);

    LocalTime timeDefaultParse(String timeString);

    LocalTime timeParseWithPattern(String timeString, String pattern);
}
