package com.serveme.service.helper.time;

import javax.inject.Named;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.TimeZone;


@Named(value = "UkTimeUtil")
@Singleton
public class UkTimeUtils implements TimeUtils {
    private static final String SERVEME_TIME_ZONE_NAME = "Europe/London";
    private static final TimeZone SERVEME_TIME_ZONE = TimeZone.getTimeZone(SERVEME_TIME_ZONE_NAME);
    private static final DateTimeFormatter DEFAULT_SERVEME_TIME_FORMATTER = DateTimeFormatter
            .ISO_LOCAL_DATE
            .withZone(SERVEME_TIME_ZONE.toZoneId());

    @Override
    public LocalDateTime dateTimeNow() {
        return Instant.now().atZone(SERVEME_TIME_ZONE.toZoneId()).toLocalDateTime();
    }

    @Override
    public LocalTime timeNow() {
        return dateTimeNow().toLocalTime();
    }

    @Override
    public LocalDate getCurrentDate() {
        return dateTimeNow().toLocalDate();
    }

    @Override
    public ZoneId getBusinessTimeZone() {
        return SERVEME_TIME_ZONE.toZoneId();
    }

    @Override
    public LocalDateTime fromTimestamp(Timestamp timestamp) {
        return timestamp.toInstant().atZone(SERVEME_TIME_ZONE.toZoneId()).toLocalDateTime();
    }

    @Override
    public Timestamp toTimestamp(LocalDateTime dateTime) {
        return Timestamp.from(dateTime.atZone(SERVEME_TIME_ZONE.toZoneId()).toInstant());
    }

    @Override
    public Timestamp toTimestamp(LocalDate date) {
        return toTimestamp(date.atStartOfDay());
    }

    @Override
    public LocalDateTime plus(LocalDateTime dateTime, Duration duration) {
        return dateTime
                .atZone(SERVEME_TIME_ZONE.toZoneId())
                .toLocalDateTime()
                .plus(duration);
    }

    @Override
    public LocalDate plus(LocalDate date, Duration duration) {
        return date.plus(duration);
    }


    @Override
    public LocalDateTime minus(LocalDateTime dateTime, Duration duration) {
        return dateTime
                .atZone(SERVEME_TIME_ZONE.toZoneId())
                .toLocalDateTime()
                .minus(duration);
    }

    @Override
    public LocalDate minus(LocalDate date, Duration duration) {
        return date.minus(duration);
    }

    @Override
    public LocalDateTime atZone(LocalDateTime dateTime) {
        return dateTime
                .atZone(SERVEME_TIME_ZONE.toZoneId())
                .toLocalDateTime();
    }

    @Override
    public Duration durationBetween(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return Duration.between(
                dateTime1,
                dateTime2);
    }

    @Override
    public Duration durationBetween(LocalTime dateTime1, LocalTime dateTime2) {
        return Duration.between(
                dateTime1,
                dateTime2);
    }

    @Override
    public LocalDateTime dateTimeOf(LocalDate date, LocalTime time) {
        return LocalDateTime
                .of(date,time)
                .atZone(SERVEME_TIME_ZONE.toZoneId())
                .toLocalDateTime();
    }

    @Override
    public LocalTime timeDefaultParse(String timeString) {
        return LocalTime.parse(timeString, DEFAULT_SERVEME_TIME_FORMATTER);
    }

    @Override
    public LocalTime timeParseWithPattern(String timeString, String pattern) {
        return LocalTime.parse(
                timeString,
                DateTimeFormatter
                        .ofPattern(pattern)
                        .withZone(SERVEME_TIME_ZONE.toZoneId()));
    }


}
