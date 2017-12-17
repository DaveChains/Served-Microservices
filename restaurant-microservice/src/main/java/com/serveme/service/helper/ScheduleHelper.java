package com.serveme.service.helper;

import com.serveme.service.api.dto.out.SearchingRestaurantDto;
import com.serveme.service.domain.Scheduled;
import com.serveme.service.helper.time.TimeUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
public class ScheduleHelper {


    private final static String TIME_PATTERN = "HH:mm:ss";

    private final TimeUtils timeUtils;

    private static final Logger logger = Logger.getLogger(ScheduleHelper.class.getName());

    @Inject
    public ScheduleHelper(TimeUtils timeUtils) {
        this.timeUtils = timeUtils;
    }


    public boolean isOpen(final Scheduled restaurant) {
        final LocalDate today = timeUtils.getCurrentDate();
        final LocalTime timeNow = timeUtils.timeNow();

        return !this.isRestaurantInHolidays(restaurant)
                && restaurant
                .getSchedules()
                .stream()
                .filter(schedule -> schedule.getDayOfTheWeek() == today.getDayOfWeek().getValue())
                .anyMatch(schedule -> {
                    final LocalTime startTime = timeUtils
                            .timeParseWithPattern(schedule.getInitHour(), TIME_PATTERN);
                    final LocalTime endTime = timeUtils
                            .timeParseWithPattern(schedule.getEndHour(), TIME_PATTERN);
                    return  startTime.isBefore(timeNow)
                            && endTime.isAfter(timeNow);
                });
    }


    public void setLastBookingTimes(SearchingRestaurantDto restaurant) {
        restaurant.setMinutesRemainingToLast30minBooking(getLastBookTime(restaurant, 30, 30));
        restaurant.setMinutesRemainingToLast45minBooking(getLastBookTime(restaurant, 45, 30));
        restaurant.setMinutesRemainingToLast60minBooking(getLastBookTime(restaurant, 60, 30));
        if (restaurant.getMinutesRemainingToLast30minBooking() > 0) {
            restaurant.setMinutesRemainingToClose(restaurant
                    .getMinutesRemainingToLast30minBooking() + 60);
        } else {
            restaurant.setMinutesRemainingToClose(0);
        }
    }

    /**
     *
     * @param restaurant
     * @param bookingTimeMinutes
     * @param timeToEatMinutes
     * @return minutes until the booking becomes unavailable
     */
    public long getLastBookTime(
            Scheduled restaurant,
            int bookingTimeMinutes,
            int timeToEatMinutes) {
        if(isRestaurantInHolidays(restaurant) || restaurant.getSchedules() == null) {
            return 0;
        }

        try {
            final LocalDate today = timeUtils.getCurrentDate();
            final LocalTime timeNow = timeUtils.timeNow();

            Optional<LocalTime> remainingTime = restaurant
                    .getSchedules()
                    .stream()
                    .filter(schedule -> schedule.getDayOfTheWeek() == today.getDayOfWeek().getValue())
                    .map(schedule -> {
                        final LocalTime startTime = timeUtils
                                .timeParseWithPattern(schedule.getInitHour(), TIME_PATTERN);
                        final LocalTime endTime = timeUtils
                                .timeParseWithPattern(schedule.getEndHour(), TIME_PATTERN);
                        return  (startTime.isBefore(timeNow) && endTime.isAfter(timeNow))
                                ?endTime.minusMinutes(bookingTimeMinutes + timeToEatMinutes)
                                :null;
                    })
                    .filter(d-> d != null)
                    .filter(time -> time.isAfter(timeNow))
                    .findFirst();

            return !remainingTime.isPresent()
                    ? 0
                    : timeUtils
                    .durationBetween(timeNow, remainingTime.get())
                    .toMinutes();
        }catch(Exception ex) {
            logger.log(Level.SEVERE, "Error with restaurant "+restaurant.toString(), ex);
            return 0;
        }
    }


    private boolean isRestaurantInHolidays(final Scheduled restaurant) {
        if(restaurant.getHolidays() == null) {
            return false;
        }
        final LocalDateTime now = timeUtils.dateTimeNow();
        return restaurant
                .getHolidays()
                .stream()
                .anyMatch(holiday -> {
                    final LocalDateTime startTime = timeUtils.fromTimestamp(holiday.getInitHour());
                    final LocalDateTime endTime = timeUtils.fromTimestamp(holiday.getEndHour());
                    return startTime.isBefore(now) && endTime.isAfter(endTime);
                });
    }
}
