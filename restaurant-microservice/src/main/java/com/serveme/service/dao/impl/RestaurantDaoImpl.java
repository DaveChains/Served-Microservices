package com.serveme.service.dao.impl;

import com.serveme.service.api.dto.out.SearchingRestaurantDto;
import com.serveme.service.dao.RestaurantDao;
import com.serveme.service.domain.CategoryDomain;
import com.serveme.service.domain.DishDomain;
import com.serveme.service.domain.RestaurantDomain;
import com.serveme.service.domain.SearchObject;
import com.serveme.service.util.persistence.GenericRowMapperList;
import com.serveme.service.util.persistence.GenericRowMapperMapList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 22/10/15.
 */
@Repository
public class RestaurantDaoImpl implements RestaurantDao {

    Logger logger = Logger.getLogger(RestaurantDaoImpl.class.getName());

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Override
    public long createBasic(String name) {
        logger.log(Level.INFO, "\n\tCreating new RestaurantDomain. Name: " + name);
        String sql = "INSERT INTO RESTAURANTS (name) " +
                "VALUES(?)";


        Object[] params = {name};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.VARCHAR);

        factory.setReturnGeneratedKeys(true);
        KeyHolder keyholder = new GeneratedKeyHolder();

        jdbcTemplate.update(factory.newPreparedStatementCreator(params), keyholder);
        long id = keyholder.getKey().longValue();
        return id;

    }


    @Override
    public void update(RestaurantDomain restaurant) {
        logger.log(Level.INFO, "\n\tUpdating RestaurantDomain. Name: " + restaurant.getName());
        String sql = "UPDATE RESTAURANTS SET name=?, description=?, address=?, postcode=?, city=?, country=?, location_lat=?, location_lon=?, " +
                "email_for_clients=?, email_for_us=?, phone_for_clients=?, phone_for_us=?, update_date=?, photo_url=?, service_charge_perc=?, " +
                "max_people_by_table=? WHERE id=?";

        Timestamp now = new Timestamp(System.currentTimeMillis());
        Object[] params = {
                restaurant.getName(),
                restaurant.getDescription(),
                restaurant.getAddress(),
                restaurant.getPostcode(),
                restaurant.getCity(),
                restaurant.getCountry(),
                restaurant.getLocationLat(),
                restaurant.getLocationLon(),
                restaurant.getEmailForClients(),
                restaurant.getEmailForUs(),
                restaurant.getPhoneForClients(),
                restaurant.getPhoneForUs(),
                now,
                restaurant.getPhotoUrl(),
                restaurant.getServiceChargePerc(),
                restaurant.getMaxPeopleByTable(),
                restaurant.getId()};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql,
                Types.VARCHAR,//name
                Types.VARCHAR,//description
                Types.VARCHAR,//address
                Types.VARCHAR,//postCode
                Types.VARCHAR,//city
                Types.VARCHAR,//country
                Types.DECIMAL,//location latitude
                Types.DECIMAL,//location longitude
                Types.VARCHAR,//email for clients
                Types.VARCHAR,//email for us
                Types.VARCHAR,//phone for clients
                Types.VARCHAR,//phone for us
                Types.TIMESTAMP,//update date
                Types.VARCHAR,//photo url
                Types.VARCHAR,//service charge percentage
                Types.INTEGER,//max people by table
                Types.INTEGER
        );

        if (jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0) {
            throw new RuntimeException("The restaurant " + restaurant.getName() + "(id: " + restaurant.getId() + ") couldn't be updated");
        }
    }


    @Override
    public Map<String, List<DishDomain>> findMenuByRestaurant(long id) {

        logger.log(Level.INFO, "\n\tSelecting Menu  by restaurant id: " + id);

        String sql = "SELECT c.name category_name,d.* " +
                " FROM DISHES d" +
                " JOIN CATEGORIES c ON c.id=d.category_id" +
                " JOIN RESTAURANTS r ON d.restaurant_id=r.id" +
                " WHERE d.restaurant_id=" + id +
                " ORDER BY c.position DESC" +
                " LIMIT 0,1000";

        Map<String, List<DishDomain>> resultMenu = null;
        try {
            resultMenu = (Map<String, List<DishDomain>>) jdbcTemplate.queryForObject(sql, new Object[]{}, new GenericRowMapperMapList(DishDomain.class, "category_name"));

        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
        }
        return resultMenu;
    }

    @Override
    public List<CategoryDomain> getCategoriesByRestaurant(long restaurantId) {
        String sql = "SELECT * FROM CATEGORIES where restaurant_id=?";
        List<CategoryDomain> categoryDomainList = null;
        try {
            categoryDomainList = (List) jdbcTemplate.queryForObject(sql, new Object[]{restaurantId}, new GenericRowMapperList(CategoryDomain.class));
        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
        }
        return categoryDomainList;
    }

    @Override
    public List<DishDomain> getDishesByRestaurant(long restaurantId) {
        String sql = "SELECT * FROM DISHES where restaurant_id=?";
        List<DishDomain> dishDomainList = null;
        try {
            dishDomainList = (List) jdbcTemplate.queryForObject(sql, new Object[]{restaurantId}, new GenericRowMapperList(DishDomain.class));
        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
        }
        return dishDomainList;
    }

    @Override
    public RestaurantDomain findById(long id) {

        logger.log(Level.INFO, "\n\tSelecting RestaurantDomain by id: " + id);
        String sql = "SELECT * FROM RESTAURANTS" +
                " WHERE id=?" +
                " LIMIT 0,1";
        List<RestaurantDomain> userList = null;
        try {
            userList = (List) jdbcTemplate.queryForObject(sql, new Object[]{id}, new GenericRowMapperList(RestaurantDomain.class));

        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
        }

        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<RestaurantDomain> findAllRestaurants() {

        String sql = "SELECT * FROM RESTAURANTS";
        List<RestaurantDomain> restaurantList = null;
        try {
            restaurantList = (List) jdbcTemplate.queryForObject(sql, new Object[]{}, new GenericRowMapperList(RestaurantDomain.class));
        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
        }
        return restaurantList;
    }

    @Override
    public void removeById(long id) {
        logger.log(Level.INFO, "\n\tRemoving RestaurantDomain id: " + id);
        String sql = "DELETE FROM  RESTAURANTS WHERE id=?";

        Object[] params = {id};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER);

        if (jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0) {
            throw new RuntimeException("The restaurant with id" + id + " wasn't deleted");
        }
    }

    @Override
    public void setOnline(long id, boolean online) {
        logger.log(Level.INFO, "\n\tSetting Restaurant " + (online ? "online" : "offline") + ": " + id);
        if (online) {
            discardDisables(id);
            return;
        }
        String sql = "INSERT INTO DISABLES (restaurant_id, day_to_disable, disabled) VALUES (?,?,?)";

        Object[] params = {
                id,
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                true};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql,
                Types.INTEGER,//online
                Types.DATE,
                Types.BOOLEAN
        );

        if (jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0) {
            throw new RuntimeException("The disable for restaurnat " + "(id: " + id + ") couldn't be set");
        }
    }

    private void discardDisables(long id) {
        String sql = "UPDATE DISABLES SET disabled=? WHERE restaurant_id=?";

        Object[] params = {false, id};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.BOOLEAN, Types.INTEGER);

        if (jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0) {
            throw new RuntimeException("The disable for restaurant " + id + " wasn't deleted");
        }
    }

    @Override
    public List<Long> getDisabledRestaurants() {
        String sql = "SELECT restaurant_id FROM DISABLES WHERE disabled=? AND day_to_disable=?";
        List<Long> restaurantList = new ArrayList<>();
        try {
            restaurantList = (List) jdbcTemplate.queryForList(sql,
                    new Object[]{true, new SimpleDateFormat("yyyy-MM-dd").format(new Date())},
                    Long.class);
        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
        }
        return restaurantList;
    }

    @Override
    public boolean isOnline(long restaurantId) {
        String sql = "SELECT count(*) FROM DISABLES WHERE disabled=? AND day_to_disable=? AND restaurant_id=?";
        long disablesForToday = 0;
        //TODO seach a equal method too QueryForLong
//        disablesForToday = jdbcTemplate.queryForLong(sql,
//                new Object[]{true, new SimpleDateFormat("yyyy-MM-dd").format(new Date()), restaurantId});
        return disablesForToday == 0;
    }

    @Override
    public List<SearchingRestaurantDto> search(SearchObject searchObject, boolean testMode, double maxDistance) {
        String live = testMode ? " OR live_restaurant = 0 " : "";
        String sql = "SELECT *, (6371 * acos(cos(radians(?)) * cos(radians(location_lat)) * cos(radians(location_lon) "
                + " - radians(?)) + sin(radians(?)) * sin(radians(location_lat)))) AS distance FROM RESTAURANTS "
                + " HAVING distance < ? " + live
                + " ORDER BY distance";
        try {
            return (List) jdbcTemplate.queryForObject(sql,
                    new Object[]{searchObject.getLocation().getLat(),
                            searchObject.getLocation().getLon(), searchObject.getLocation().getLat(),
                            maxDistance}, new GenericRowMapperList(SearchingRestaurantDto.class));
        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
        }
        return new ArrayList<SearchingRestaurantDto>();
    }
}
