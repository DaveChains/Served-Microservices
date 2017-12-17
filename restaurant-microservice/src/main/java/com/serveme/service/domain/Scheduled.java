package com.serveme.service.domain;

import java.util.List;

public interface Scheduled {

    List<HolidayDomain> getHolidays();

    List<ScheduleDomain> getSchedules();

}
