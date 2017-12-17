package com.serveme.service.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.serveme.service.builder.TokenBuilder;
import com.serveme.service.dao.UserRestaurantDao;
import com.serveme.service.dao.UserRestaurantsTokenDao;
import com.serveme.service.domain.UserRestaurantDomain;
import com.serveme.service.domain.UserRestaurantTokenDomain;
import com.serveme.service.service.RestaurantService;
import com.serveme.service.service.SearchingEngineService;
import com.serveme.service.service.UserRestaurantService;
import com.serveme.service.util.SecurityUtil;
import com.serveme.service.util.StringUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 22/10/15.
 */
@Service
public class UserRestaurantServiceImpl implements UserRestaurantService {


    Logger logger = Logger.getLogger(UserRestaurantServiceImpl.class.getName());

    @Inject
    protected UserRestaurantDao userRestaurantDao;

    @Inject
    protected RestaurantService restaurantService;

    @Inject
    protected UserRestaurantsTokenDao userRestaurantsTokenDao;

    @Inject
    protected SearchingEngineService searchingEngineService;
    
    @Value("${cloudinary.name}")
    protected String cloudinaryName;
    
    @Value("${cloudinary.api}")
    protected String cloudinaryApi;
    
    @Value("${cloudinary.secret}")
    protected String cloudinarySecret;

    @Override
    //Implement with mongo. If not, mysql + transaction
    //handle transactions
    public long createUserRestaurant(String restaurantName, UserRestaurantDomain userRestaurantDomain) {

        long restaurantId = restaurantService.createBasic(restaurantName);
        if (restaurantId > 0) {
            if (StringUtil.nullEmpty(userRestaurantDomain.getName())) {
                String email = userRestaurantDomain.getEmail();
                String[] emailArr = email.split("@");
                userRestaurantDomain.setName(emailArr[0]);
            }


            userRestaurantDomain.setRestaurantId(restaurantId);
            userRestaurantDomain.setCreated(new Timestamp(System.currentTimeMillis()));
            userRestaurantDomain.setPassword(SecurityUtil.encryptString(userRestaurantDomain.getPassword()));

            //Sending restaurant to searching-engine-service
            restaurantService.sendToSearchingEngine(restaurantId);

            //creating user
            return userRestaurantDao.create(userRestaurantDomain);
        } else {
            throw new RuntimeException("RestaurantDomain with name " + restaurantName + " couldn't be created");
        }
    }


    @Override
    public long createUserRestaurant(long restaurantId, UserRestaurantDomain userRestaurantDomain) {
        if (restaurantService.getById(restaurantId) != null) {
            userRestaurantDomain.setRestaurantId(restaurantId);
            userRestaurantDomain.setCreated(new Timestamp(System.currentTimeMillis()));
            userRestaurantDomain.setPassword(SecurityUtil.encryptString(userRestaurantDomain.getPassword()));
            return userRestaurantDao.create(userRestaurantDomain);
        } else {
            //TODO replace for Badrequest
            throw new RuntimeException("RestaurantDomain not found for id " + userRestaurantDomain.getRestaurantId());
        }
    }


    @Override
    public void updateUserRestaurant(UserRestaurantDomain userRestaurantDomain) {

        userRestaurantDomain.setUpdated(new Timestamp(System.currentTimeMillis()));
        userRestaurantDao.update(userRestaurantDomain);
    }

    @Override
    public UserRestaurantDomain getById(long id) {
        return userRestaurantDao.findById(id);

    }

    @Override
    public UserRestaurantDomain getByRestaurantId(long id) {
        return userRestaurantDao.findByRestaurantId(id);

    }


    @Override
    public void remove(long id) {
        userRestaurantDao.removeById(id);

    }

    @Override
    @Transactional
    public UserRestaurantTokenDomain signIn(String email, String password) {
        UserRestaurantDomain userRestaurantDomain = userRestaurantDao.findByEmail(email);
        if (userRestaurantDomain != null) {
            if (SecurityUtil.checkEncrypted(password, userRestaurantDomain.getPassword())) {
                UserRestaurantTokenDomain userRestaurantTokenDomain = new TokenBuilder()
                        .setUserRestaurant(userRestaurantDomain)
                        .build();

                resetRestaurantTokensAndLastDeviceIds(
                        userRestaurantDomain.getRestaurantId(),
                        userRestaurantTokenDomain);
                return userRestaurantTokenDomain;
            } else {
                //TODO replace for Badrequest
                throw new RuntimeException("Wrong credentials");
            }
        } else {
            //TODO replace for Badrequest
            throw new RuntimeException("User not found");
        }

    }

    private void resetRestaurantTokensAndLastDeviceIds(long restaurantId, UserRestaurantTokenDomain userRestaurantTokenDomain) {
        userRestaurantsTokenDao.deleteTokensByRestaurant(restaurantId);
        userRestaurantDao.invalidateSessionDeviceByRestaurant(restaurantId);
        userRestaurantsTokenDao.saveToken(userRestaurantTokenDomain);
    }


    @Override
    public UserRestaurantDomain authenticate(String token) {
        return userRestaurantDao.findByAccessToken(token);

    }

    @Override
    public List<UserRestaurantDomain> getAdminUsers() {
        return userRestaurantDao.findAdminUsers();
    }

    @Override
    public UserRestaurantTokenDomain adminSignIn(String adminToken, long adminRestaurantId) {
        return new TokenBuilder().buildAdminToken(adminToken, adminRestaurantId);
    }
    
    @Override
    public String saveImage(MultipartFile fileMultipart) throws IOException {
    	
    	File file = multipartToFile(fileMultipart);
    	Cloudinary cloud = initiateCloudinary();
    	Map uploadResult = cloud.uploader().upload(file, ObjectUtils.emptyMap());
    	String url = (String) uploadResult.get("url");
        return url;
    }
    
    private Cloudinary initiateCloudinary() {
    	Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
    			  "cloud_name", cloudinaryName,
    			  "api_key", cloudinaryApi,
    			  "api_secret", cloudinarySecret));
    return cloudinary;
    }
    
    public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException 
    {
            File convFile = new File( multipart.getOriginalFilename());
            multipart.transferTo(convFile);
            return convFile;
    }
}
