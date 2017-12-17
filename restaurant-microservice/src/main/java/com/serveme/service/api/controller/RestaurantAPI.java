package com.serveme.service.api.controller;

import com.serveme.service.api.dto.out.RestaurantOutDTO;
import com.serveme.service.api.dto.out.RestaurantsIdsDTO;
import com.serveme.service.api.dto.out.SearchingRestaurantDto;
import com.serveme.service.domain.*;
import com.serveme.service.exception.NonAuthorizedException;
import com.serveme.service.helper.ScheduleHelper;
import com.serveme.service.service.*;
import com.serveme.service.service.impl.SearchingEngineServiceImpl;
import com.serveme.service.util.BaseController;
import com.serveme.service.util.exception.http.BadRequestException;
import com.serveme.service.util.exception.http.NotFoundException;
import com.serveme.service.util.validation.SimpleValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 22/10/15.
 */
@Controller
public class RestaurantAPI extends BaseController {
    private static final long ANDROID_VERSION = 6;
    private static final long IOS_VERSION = 3;
    private static final long TEST_RESTAURANT = 33;

    /**
     * TODO add secret key in internal services
     * TODO TOO MUCH logic in here, but TOO MUCH!!..it needs to be moved to another layer
     */

    private static final Logger logger = Logger.getLogger(RestaurantAPI.class.getName());


    @Value("${restaurant.order.max-distance}")
    private double orderDistance;


    @Inject
    private ScheduleHelper scheduleHelper;

    @Inject
    protected RestaurantService restaurantService;

    @Inject
    protected UserRestaurantService userRestaurantService;

    @Inject
    protected FoodOptionService foodOptionService;

    @Inject
    protected ExtrasService extrasService;

    @Inject
    protected HolidayService holidayService;

    @Inject
    protected DiscountService discountService;

    @Inject
    protected UserService userService;

    @Inject
    protected OrderService orderService;

    @Inject
    protected CategoryService categoryService;

    @Inject
    protected DishService dishService;

    @Inject
    protected ScheduleService scheduleService;

    @Inject
    protected SearchingEngineServiceImpl searchingEngine;

    @Value("${admin.token}")
    protected String adminToken;


    @ResponseBody
    @RequestMapping(value = "/restaurant", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity updateRestaurant(@RequestBody RestaurantDomain restaurant) {

        logger.log(Level.INFO, "PUT : restaurant");
        new SimpleValidator()
                .notNull("id")
                .graterThan("id", 0)
                .validate(restaurant);

        //to avoid accidental override
        if (restaurant.getPhotoUrl() == null) {
            String photoUrl = restaurantService.getById(restaurant.getId()).getPhotoUrl();
            restaurant.setPhotoUrl(photoUrl);
        }
        restaurantService.update(restaurant);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @RequestMapping(value = "/restaurant/{restaurantId}/image", method = RequestMethod.POST)
    public ResponseEntity updateRestaurantImage(@RequestParam("file") MultipartFile file,
                                                @PathVariable("restaurantId") long restaurantId) {

        logger.log(Level.INFO, String.format("/restaurant/%s/image", restaurantId));
        if (!file.isEmpty()) {
            try {
                String url = userRestaurantService.saveImage(file);
                RestaurantDomain restaurant = restaurantService.getById(restaurantId);
                restaurant.setPhotoUrl(url);
                restaurantService.update(restaurant);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                logger.log(Level.INFO, "Failed to upload => " + e.getMessage());
            }
        } else {
            logger.log(Level.INFO, "Upload failed because the file was empty.");
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}", method = RequestMethod.GET, produces = "application/json")
    public RestaurantDomain getRestaurant(@PathVariable("restaurantId") long restaurantId) {

        logger.log(Level.INFO, "GET : /restaurant/" + restaurantId);

        RestaurantDomain restaurant = restaurantService.getById(restaurantId);
        if (restaurant != null) {
            restaurant.setOnline(restaurantService.isOnline(restaurantId));
            return restaurant;
        } else {
            throw new NotFoundException("Restaurant : " + restaurantId + "not found", "Not found " + restaurantId);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/full", method = RequestMethod.GET, produces = "application/json")
    public RestaurantOutDTO getRestaurantFull(@PathVariable("restaurantId") long restaurantId) {

        logger.log(Level.INFO, "GET : /restaurant/" + restaurantId + "/full");
        RestaurantDomain restaurantDomain = restaurantService.getById(restaurantId);

        if (restaurantDomain != null) {
            RestaurantOutDTO restaurantOutDTO = new RestaurantOutDTO(restaurantDomain);
            Map<String, List<DishDomain>> menu = restaurantService.getMenuByRestaurant(restaurantId);
            restaurantOutDTO.setMenu(setFoodOptions(menu, restaurantId));
            return restaurantOutDTO;
        } else {
            throw new NotFoundException("Restaurant : " + restaurantId + "not found", "Not found " + restaurantId);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/menu", method = RequestMethod.GET, produces = "application/json")
    public List<CategoryDomain> getRestaurantMenu(@PathVariable("restaurantId") long restaurantId) {

        logger.log(Level.INFO, "GET : /restaurant/" + restaurantId + "/menu");
        RestaurantDomain restaurantDomain = restaurantService.getById(restaurantId);

        if (restaurantDomain != null) {
            List<CategoryDomain> categories = restaurantService.getCategoriesByRestaurant(restaurantId);
            List<DishDomain> dishes = restaurantService.getDishesByRestaurant(restaurantId);

            categories = setDishes(categories, dishes);

            List<CategoryDomain> menu = setFoodOptions(restaurantId, categories);
            return menu;
        } else {
            throw new NotFoundException("Restaurant : " + restaurantId + "not found", "Not found " + restaurantId);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/app_version/{os}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Long> getCurrentVersion(@PathVariable("os") String os) {

        logger.log(Level.INFO, "GET : /app_version/" + os);
        if (os.equals("android")) {
            return new ResponseEntity<Long>(ANDROID_VERSION, HttpStatus.OK);
        } else if (os.equals("ios")) {
            return new ResponseEntity<Long>(IOS_VERSION, HttpStatus.OK);
        }
        throw new NotFoundException("os", "Unknown o.s in request");
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/full", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Long> putRestaurantFull(@RequestHeader("access-token") String accessToken,
                                                  @RequestBody RestaurantOutDTO restaurant) {
        logger.log(Level.INFO, "PUT : /restaurant/full");
        if (!accessToken.equals(adminToken)) {
            throw new NonAuthorizedException(accessToken);
        }

        //creating restaurant
        long restaurantId = restaurantService.createBasic(restaurant.getName());
        restaurant.setId(restaurantId);

        //adding data
        restaurantService.update(restaurant);

        //adding menu
        Set<String> categories = restaurant.getMenu().keySet();
        int n = 100;
        for (String categoryName : categories) {
            CategoryDomain category = new CategoryDomain();
            category.setRestaurantId(restaurantId);
            category.setName(categoryName);
            category.setPriority(n--);

            long categoryId = categoryService.createCategory(category);
            List<DishDomain> dishes = restaurant.getMenu().get(categoryName);
            for (DishDomain dish : dishes) {
                dish.setPriority(1);
                dish.setCategoryId(categoryId);
                dish.setRestaurantId(restaurantId);
                long dishId = dishService.createDish(dish);

                if (dish.getExtras() != null) {
                    for (ExtraDomain extra : dish.getExtras()) {
                        extra.setDishId(dishId);
                        extra.setCategoryId(categoryId);
                    }
                    extrasService.update(dish.getExtras(), restaurantId);
                }
                if (dish.getOptions() != null) {
                    for (FoodOptionDomain option : dish.getOptions()) {
                        option.setDishId(dishId);
                        option.setRestaurantId(restaurantId);
                        foodOptionService.update(option);
                    }
                }
            }
        }

        //adding holidays
        if (restaurant.getHolidays() != null)
            holidayService.update(restaurant.getHolidays(), restaurantId);

        //adding schedules
        if (restaurant.getSchedules() != null)
            scheduleService.update(restaurant.getSchedules(), restaurantId);

        //adding discounts
        if (restaurant.getDiscounts() != null)
            discountService.update(restaurant.getDiscounts(), restaurantId);

        //update in searching engine
        restaurantService.sendToSearchingEngine(restaurantId);

        return new ResponseEntity<Long>(restaurantId, HttpStatus.OK);
    }

    //TODO awful design...needs a hack to make it works
    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/isopen/{timestamp}", method = RequestMethod.GET, produces = "application/json")
    public Boolean isRestaurantOpen(@PathVariable("restaurantId") long restaurantId, @PathVariable("timestamp") long time) {

        logger.log(Level.INFO, "POST : /restaurant/isopen");
        if (restaurantId <= 0)
            throw new BadRequestException("Wrong id " + restaurantId, "Wrong id " + restaurantId);

        RestaurantDomain restaurantDomain = restaurantService.getById(restaurantId);

        if (restaurantDomain == null)
            throw new NotFoundException("Not found restaurant with id " + restaurantId, null);

        RestaurantOutDTO restaurantOutDTO = new RestaurantOutDTO(restaurantDomain);
        restaurantOutDTO.setSchedules(scheduleService.getByRestaurantId(restaurantOutDTO.getId()));
        restaurantOutDTO.setHolidays(holidayService.getByRestaurantId(restaurantOutDTO.getId()));

        return scheduleHelper.isOpen(restaurantOutDTO);
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/all", method = RequestMethod.GET, produces = "application/json")
    public RestaurantsIdsDTO getAllRestaurantIds() {
        return restaurantService.getAllRestaurantIds();
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/all_restaurants", method = RequestMethod.GET, produces = "application/json")
    public List<RestaurantDomain> getAllRestaurants() {
        //todo authenticate admin
        logger.log(Level.INFO, "GET : /restaurant/all_restaurants");
        return restaurantService.getAllRestaurants();
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/reindex", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity reindexAll(@RequestHeader String accessToken) {
        if (!accessToken.equals(adminToken)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        RestaurantsIdsDTO ids = restaurantService.getAllRestaurantIds();
        for (Long id : ids.getRestaurantIds()) {
            restaurantService.sendToSearchingEngine(id);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/online", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity setOnline(@PathVariable("restaurantId") long restaurantId,
                                    @RequestHeader String accessToken,
                                    @RequestBody Boolean online) {
        if (!accessToken.equals(adminToken)) {
            UserRestaurantDomain restaurantUser = userRestaurantService.authenticate(accessToken);
            if (restaurantUser == null) {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            } else {
                restaurantId = restaurantUser.getRestaurantId();
            }
        }

        restaurantService.setOnline(restaurantId, online);

        return new ResponseEntity(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/dish/{dishId}/available/{status}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity setAvailable(@PathVariable("restaurantId") long restaurantId,
                                       @RequestHeader String accessToken,
                                       @PathVariable("dishId") long dishId,
                                       @PathVariable("status") Boolean available) {

        if (!accessToken.equals(adminToken)) {
            UserRestaurantDomain restaurantUser = userRestaurantService.authenticate(accessToken);
            if (restaurantUser == null) {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
            restaurantId = restaurantUser.getRestaurantId();
        }
        DishDomain dish = dishService.getById(dishId);
        if (dish.getRestaurantId() != restaurantId) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        dishService.setRunoutForToday(dishId, available);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/online", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Boolean> getOnline(@PathVariable("restaurantId") long restaurantId) {
        RestaurantDomain restaurantDomain = restaurantService.getById(restaurantId);

        if (restaurantDomain == null)
            throw new NotFoundException("Not found restaurant with id " + restaurantId, null);

        return new ResponseEntity<Boolean>(restaurantService.isOnline(restaurantId), HttpStatus.OK);
    }


    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/valid_distance", method = RequestMethod.GET)
    public RestaurantDistanceData hasValidDistance(@RequestParam double lat,
                                                   @RequestParam double lon,
                                                   @PathVariable("restaurantId") long restaurantId) {
        if (restaurantId == TEST_RESTAURANT) {
            return RestaurantDistanceData.getTestObject();
        } else {
            RestaurantDomain restaurant = restaurantService.getById(restaurantId);
            return getValidDistance(new Location(lat, lon), restaurant);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/search", method = RequestMethod.POST, produces = "application/json")
    public List<SearchingRestaurantDto> legacySearch(@RequestHeader("access-token") String accessToken,
                                                     @RequestBody SearchObject searchObject) {
        logger.log(Level.INFO, "searching restaurants");
        UserExDTO user = userService.getUser(accessToken);
        return processRestaurants(user, restaurantService.legacySearch(searchObject, user.isTestUser()));
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/v2.0/search", method = RequestMethod.POST, produces = "application/json")
    public List<SearchingRestaurantDto> search(
            @RequestHeader("access-token") String accessToken,
            @RequestBody SearchObject searchObject) {
        logger.log(Level.INFO, "searching restaurants");
        UserExDTO user = userService.getUser(accessToken);
        return processRestaurants(user, restaurantService.search(searchObject, user.isTestUser()));
    }

    private List<CategoryDomain> setDishes(List<CategoryDomain> categories, List<DishDomain> dishes) {
        Map<Long, CategoryDomain> cats = new HashMap<Long, CategoryDomain>();

        for (CategoryDomain category : categories) {
            cats.put(category.getId(), category);
        }

        for (DishDomain dish : dishes) {
            if (cats.get(dish.getCategoryId()) != null) {
                cats.get(dish.getCategoryId()).getDishes().add(dish);
            }
        }

        return categories;
    }

    private List<CategoryDomain> setFoodOptions(long restaurantId, List<CategoryDomain> menu) {
        if (menu == null) return null;

        List<FoodOptionDomain> foodOptions = foodOptionService.getByRestaurant(restaurantId);
        List<ExtraDomain> extras = extrasService.getByRestaurantId(restaurantId);
        Map<Long, DishDomain> dishes = new HashMap<Long, DishDomain>();

        for (CategoryDomain category : menu) {
            for (DishDomain dish : category.getDishes()) {
                dishes.put(dish.getId(), dish);
                dish.setOptions(new ArrayList<>());
                dish.setExtras(new ArrayList<>());
            }
        }

        for (FoodOptionDomain option : foodOptions) {
            if (dishes.get(option.getDishId()) != null && dishes.get(option.getDishId()).getOptions() != null)
                dishes.get(option.getDishId()).getOptions().add(option);
        }
        for (ExtraDomain extraDomain : extras) {
            if (dishes.get(extraDomain.getDishId()) != null && dishes.get(extraDomain.getDishId()).getExtras() != null)
                dishes.get(extraDomain.getDishId()).getExtras().add(extraDomain);
        }
        return menu;
    }

    private Map<String, List<DishDomain>> setFoodOptions(Map<String, List<DishDomain>> menu, long restaurantId) {
        if (menu == null) return null;

        List<FoodOptionDomain> foodOptions = foodOptionService.getByRestaurant(restaurantId);
        List<ExtraDomain> extras = extrasService.getByRestaurantId(restaurantId);
        Map<Long, DishDomain> dishes = new HashMap<Long, DishDomain>();

        for (List<DishDomain> categories : menu.values()) {
            if (categories != null)
                for (DishDomain dish : categories) {
                    dish.setOptions(new ArrayList<>());
                    dish.setExtras(new ArrayList<>());
                    dishes.put(dish.getId(), dish);
                }
        }

        for (FoodOptionDomain option : foodOptions) {
            if (dishes.get(option.getDishId()) != null && dishes.get(option.getDishId()).getOptions() != null)
                dishes.get(option.getDishId()).getOptions().add(option);
        }
        for (ExtraDomain extraDomain : extras) {
            if (dishes.get(extraDomain.getDishId()) != null && dishes.get(extraDomain.getDishId()).getExtras() != null)
                dishes.get(extraDomain.getDishId()).getExtras().add(extraDomain);
        }
        return menu;
    }



    //TODO this logic should be in some helper
    private RestaurantDistanceData getValidDistance(Location location, RestaurantDomain restaurant) {
        double distance = getDistance(location.getLat(), location.getLon(),
                restaurant.getLocationLat().doubleValue(), restaurant.getLocationLon().doubleValue());

        return new RestaurantDistanceData(distance, orderDistance, distance < orderDistance);
    }

    private List<SearchingRestaurantDto> processRestaurants(UserExDTO user, List<SearchingRestaurantDto> restaurants) {
        List<Long> restaurantIds = new ArrayList<Long>();

        //get offline restaurants
        List<Long> disabledRestaurants = restaurantService.getDisabledRestaurants();
        for (SearchingRestaurantDto restaurant : restaurants) {
            Long restaurantId = restaurant.getId();
            restaurant.setOnline(!disabledRestaurants.contains(restaurantId));
            restaurantIds.add(restaurant.getId());
        }

        for (SearchingRestaurantDto restaurant : restaurants) {
            restaurant.setSchedules(scheduleService.getByRestaurantId(restaurant.getId()));
            restaurant.setHolidays(holidayService.getByRestaurantId(restaurant.getId()));
        }

        //calc discount
        List<List<Long>> countOrders = orderService.countOrdersByUser(user.getId(), restaurantIds);
        for (int i = 0; i < restaurants.size(); i++) {
            restaurants.get(i).setNumberOfOrders(countOrders.get(i).size());
            BigDecimal discount = DiscountDomain.calcDiscountPercentage(countOrders.get(i), discountService.getByRestaurantId(restaurants.get(i).getId()));
            restaurants.get(i).setDiscount(discount);
        }

        //determinating state
        restaurants.stream()
                .filter(scheduleHelper::isOpen)
                .forEach(r -> r.setOpen(true));
        restaurants
                .stream()
                .filter(SearchingRestaurantDto::isOpen)
                .forEach(scheduleHelper::setLastBookingTimes);
        restaurants.sort(SearchingRestaurantDto.comparator);
        return restaurants;
    }

    /**
     * @return distance in kilometers
     */

    public double getDistance(double initialLat, double initialLong,
                              double finalLat, double finalLong) {
        int R = 6371;
        double dLat = toRadians(finalLat - initialLat);
        double dLon = toRadians(finalLong - initialLong);
        initialLat = toRadians(initialLat);
        finalLat = toRadians(finalLat);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(initialLat) * Math.cos(finalLat);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    private double toRadians(double degrees) {
        return degrees * (Math.PI / 180);
    }
}
