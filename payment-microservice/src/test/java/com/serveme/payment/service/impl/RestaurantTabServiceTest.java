package com.serveme.payment.service.impl;

import com.mysema.query.types.Predicate;
import com.serveme.payment.domain.RestaurantTab;
import com.serveme.payment.persistence.RestaurantTabRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.doReturn;
import java.time.LocalDate;
import java.time.ZoneId;


public class RestaurantTabServiceTest {


    private RestaurantTabRepository rep;

    private RestaurantTabServiceImpl restaurantTabService;


    @Before
    public void before() {
        rep = Mockito.mock(RestaurantTabRepository.class);

        restaurantTabService = new RestaurantTabServiceImpl(rep, 30, 5, 2016, 14);

    }


    @Test
    public void getCurrentOrStartNew_call_getCurrentOpen(){
        RestaurantTabServiceImpl mockTabService = Mockito.mock(RestaurantTabServiceImpl.class);
        Mockito.when(mockTabService.getCurrentOrStartNew(Mockito.anyLong())).thenCallRealMethod();
        mockTabService.getCurrentOrStartNew(1111);
        Mockito.verify(mockTabService, Mockito.times(1)).getCurrentOrStartNew(Mockito.anyLong());


    }

    @Test
    public void getCurrentOpen_dont_return_tab_when_endDate_is_past() {
        doReturn(getTabWithEndDate(
                LocalDate.now(ZoneId.of("UTC")).minusDays(1))
        ).when(rep).retrieve(Mockito.anyLong(), Mockito.any(LocalDate.class));

        RestaurantTab tab = restaurantTabService.getCurrentOpen(1);
        Assert.assertNull(tab);
    }


    @Test
    public void getCurrentOpen_does_return_tab_when_endDate_is_future() {
        doReturn(getTabWithEndDate(
                        LocalDate.now(ZoneId.of("UTC")).plusDays(1))
        ).when(rep).retrieve(Mockito.anyLong(), Mockito.any(LocalDate.class));
        RestaurantTab tab = restaurantTabService.getCurrentOpen(1);
        Assert.assertNotNull(tab);
    }


    private RestaurantTab getTabWithEndDate(LocalDate endDate) {
        RestaurantTab r =  new RestaurantTab(
                1,
                LocalDate.of(2016,05,30),
                endDate
        );

        return r;
    }


}
