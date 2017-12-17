package com.serveme.service.service.impl;

import com.serveme.service.api.dto.out.RestaurantOutDTO;
import com.serveme.service.api.dto.out.RestaurantsIdsDTO;
import com.serveme.service.api.dto.out.SearchingRestaurantDto;
import com.serveme.service.dao.RestaurantDao;
import com.serveme.service.domain.*;
import com.serveme.service.service.*;
import com.serveme.service.transfer.dto.LocationDTO;
import com.serveme.service.transfer.dto.PostcodesIOResponse;
import com.serveme.service.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by Davids-iMac on 22/10/15.
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {

    Logger logger = Logger.getLogger(RestaurantServiceImpl.class.getName());

    @Value("${restaurant.order.max-distance}")
    private double orderDistance;

    @Value("${restaurant.search.max-distance}")
    private double searchDistance;

    @Inject
    protected RestaurantDao restaurantDao;

    @Inject
    protected SearchingEngineService searchingEngineService;

    @Inject
    protected ScheduleService scheduleService;

    @Inject
    protected DiscountService discountService;

    @Inject
    protected HolidayService holidayService;

    @Inject
    protected GeolocationService geolocationService;

    @Override
    //Implement with mongo. If not, mysql + transaction
    //handle transactions
    public long createBasic(String restaurantName) {

        long restaurantId = restaurantDao.createBasic(restaurantName);
        if (restaurantId < 0) {
            throw new RuntimeException("RestaurantDomain with name " + restaurantName + " couldn't be created");
        }
        scheduleService.update(generateDefaultSchedule(), restaurantId);
        return restaurantId;
    }

    private List<ScheduleDomain> generateDefaultSchedule() {
        List<ScheduleDomain> list = new ArrayList<ScheduleDomain>();
        for (int i = 1; i <= 7; i++) {
            list.add(new ScheduleDomain(i, "12:00", "22:00"));
        }
        return list;
    }

    @Override
    public RestaurantDomain getById(long restaurantId) {
        return restaurantDao.findById(restaurantId);
    }

    @Override
    public void update(RestaurantDomain restaurant) {
        PostcodesIOResponse postcodeIOResponse = null;

        if (!StringUtil.nullEmpty(restaurant.getPostcode())) {
            String postcode = restaurant.getPostcode().replace(" ", "");
            postcodeIOResponse = (PostcodesIOResponse) geolocationService.getLocationByPostcode(postcode);

            if (postcodeIOResponse != null && postcodeIOResponse.getStatus() == 200 && postcodeIOResponse.getResult() != null) {
                LocationDTO location = postcodeIOResponse.getResult();
                restaurant.setCity(location.getRegion());
                restaurant.setCountry(location.getCountry());
                restaurant.setLocationLon(location.getLongitude());
                restaurant.setLocationLat(location.getLatitude());
            } else {
                logger.log(Level.INFO, "postcode " + restaurant.getPostcode() + " NOT FOUND");
            }

        } else {
            logger.log(Level.INFO, "Error connecting to API to retrieve location with postcode " + restaurant.getPostcode());
        }

        restaurantDao.update(restaurant);
        sendToSearchingEngine(restaurant);
    }

    @Override
    public void sendToSearchingEngine(RestaurantDomain restaurant) {
        RestaurantOutDTO restaurantOutDTO = new RestaurantOutDTO(restaurant);
        Map menu = getMenuByRestaurant(restaurant.getId());
        restaurantOutDTO.setMenu(menu);
        restaurantOutDTO.setSchedules(scheduleService.getByRestaurantId(restaurantOutDTO.getId()));
        restaurantOutDTO.setHolidays(holidayService.getByRestaurantId(restaurantOutDTO.getId()));
        restaurantOutDTO.setDiscounts(discountService.getByRestaurantId(restaurantOutDTO.getId()));
        //searchingEngineService.sendRestaurant(restaurantOutDTO);
    }

    @Override
    public void sendToSearchingEngine(long restaurantId) {
        sendToSearchingEngine(getById(restaurantId));
    }

    @Override
    public RestaurantsIdsDTO getAllRestaurantIds() {
        List<Long> restaurantsIds = new ArrayList<Long>();
        List<RestaurantDomain> restaurants = restaurantDao.findAllRestaurants();
        for (int i = 0; i < restaurants.size(); i++) {
            restaurantsIds.add(restaurants.get(i).getId());
        }
        RestaurantsIdsDTO restaurantsDTO = new RestaurantsIdsDTO();
        restaurantsDTO.setRestaurantIds(restaurantsIds);
        return restaurantsDTO;
    }

    @Override
    public List<RestaurantDomain> getAllRestaurants() {
        return restaurantDao.findAllRestaurants();
    }

    @Override
    public Map<String, List<DishDomain>> getMenuByRestaurant(long restaurantId) {
        return restaurantDao.findMenuByRestaurant(restaurantId);
    }

    @Override
    public List<CategoryDomain> getCategoriesByRestaurant(long restaurantId) {
        return restaurantDao.getCategoriesByRestaurant(restaurantId);
    }

    @Override
    public List<DishDomain> getDishesByRestaurant(long restaurantId) {
        return restaurantDao.getDishesByRestaurant(restaurantId);
    }

    @Override
    public void setOnline(long id, boolean online) {
        restaurantDao.setOnline(id, online);
    }

    @Override
    public List<Long> getDisabledRestaurants() {
        return restaurantDao.getDisabledRestaurants();
    }

    @Override
    public boolean isOnline(long restaurantId) {
        return restaurantDao.isOnline(restaurantId);
    }

    @Override
    public List<SearchingRestaurantDto> search(SearchObject searchObject, boolean showTestRestaurants) {
        List<SearchingRestaurantDto> restaurants = restaurantDao.search(searchObject, showTestRestaurants, searchDistance);
        return restaurants == null
                ? new ArrayList<>()
                :filterRestaurants(showTestRestaurants, restaurants);
    }

    @Override
    public List<SearchingRestaurantDto> legacySearch(SearchObject searchObject, boolean showTestRestaurants) {
        List<SearchingRestaurantDto> restaurants = restaurantDao.search(searchObject, showTestRestaurants, orderDistance);
        return restaurants == null
                ? new ArrayList<>()
                :filterRestaurants(showTestRestaurants, restaurants);
    }

    private List<SearchingRestaurantDto> filterRestaurants(boolean showTestRestaurants, List<SearchingRestaurantDto> restaurants) {
        if (showTestRestaurants) {
            return restaurants;
        } else {
            return restaurants
                    .stream()
                    .filter(SearchingRestaurantDto::isLiveRestaurant)
                    .collect(Collectors.toList());

        }
    }

}
