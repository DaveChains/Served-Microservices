package com.serveme.service.service;

import com.serveme.service.domain.UserRestaurantDomain;
import com.serveme.service.domain.UserRestaurantTokenDomain;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by Davids-iMac on 22/10/15.
 */
public interface UserRestaurantService {

    long createUserRestaurant(String restaurantName, UserRestaurantDomain userRestaurantDomain);

    long createUserRestaurant(long restaurantId, UserRestaurantDomain userRestaurantDomain);

    void updateUserRestaurant(UserRestaurantDomain userRestaurantDomain);

    UserRestaurantDomain getById(long id);

    UserRestaurantDomain getByRestaurantId(long restaurantId);

    void remove(long id);

    UserRestaurantTokenDomain signIn(String email, String password);

    UserRestaurantDomain authenticate(String token);

    UserRestaurantTokenDomain adminSignIn(String adminToken, long adminRestaurantId);

    List<UserRestaurantDomain> getAdminUsers();

    String saveImage(MultipartFile file) throws IOException;
}
