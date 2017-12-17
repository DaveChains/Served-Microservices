package com.serveme.payment.service.impl;

import com.mysema.query.types.expr.BooleanExpression;
import com.serveme.payment.domain.*;
import com.serveme.payment.persistence.RestaurantTabRepository;
import com.serveme.payment.service.RestaurantTabService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;


@Service
public class RestaurantTabServiceImpl implements RestaurantTabService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RestaurantTabServiceImpl.class);

    private final RestaurantTabRepository restaurantTabRepository;

    private final LocalDate startPeriodInclusive;

    private final int periodDays;

    @Inject
    public RestaurantTabServiceImpl(
            RestaurantTabRepository restaurantTabRepository,
            @Value("${restaurantTab.period.startInclusive.day}") int startPeriodDay,
            @Value("${restaurantTab.period.startInclusive.month}") int startPeriodMonth,
            @Value("${restaurantTab.period.startInclusive.year}") int startPeriodYear,
            @Value("${restaurantTab.period.days}") int periodDays){
        this.restaurantTabRepository = restaurantTabRepository;
        this.startPeriodInclusive = LocalDate.of(startPeriodYear, startPeriodMonth, startPeriodDay);
        this.periodDays = periodDays;
    }

    @Override
    public RestaurantTab getCurrentOrStartNew(long restaurantId){
        RestaurantTab tab = getCurrentOpen(restaurantId);
        return tab != null ? tab : startTab(restaurantId);
    }

    @Override
    public RestaurantTab getCurrentOpen(long restaurantId) {
        LocalDate now = LocalDate.now(ZoneId.of("UTC"));
        RestaurantTab tab = this.retrieve(restaurantId, now);
        //makes sure that only returns tabs if the endDate is after today
        return tab != null && tab.getEndPeriodInclusive().isAfter(now) ? tab : null;
    }

    @Override
    public RestaurantTab startTab(long restaurantId) {
        LocalDate startPeriodInclusive = getCurrentPeriodInclusive();
        LocalDate endPeriodExclusive = startPeriodInclusive.plusDays(periodDays);
        return createRestaurantTab(
                restaurantId,
                startPeriodInclusive,
                endPeriodExclusive);
    }


    @Override
    public LocalDate getCurrentPeriodInclusive(){
        return getStartPeriodInclusiveFromDate(LocalDate.now(ZoneId.of("UTC")));
    }

    @Override
    public LocalDate getStartPeriodInclusiveFromDate(LocalDate targetDate){
        long differenceDays = ChronoUnit.DAYS.between(startPeriodInclusive, targetDate);
        return targetDate.minusDays(differenceDays % periodDays);
    }

    private RestaurantTab retrieve(long restaurantId, LocalDate date) {
        return restaurantTabRepository
                .retrieve(restaurantId, date);
    }


    private RestaurantTab createRestaurantTab(
            long restaurantId,
            LocalDate startPeriodInclusive,
            LocalDate endPeriodInclusive) {
        return restaurantTabRepository.save(
                new RestaurantTab(
                        restaurantId,
                        startPeriodInclusive,
                        endPeriodInclusive
                ));
    }

}
