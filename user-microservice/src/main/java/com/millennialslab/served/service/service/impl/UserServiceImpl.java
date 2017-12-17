package com.millennialslab.served.service.service.impl;

import com.millennialslab.served.service.api.dto.input.UserUpdateDetailInputDTO;
import com.millennialslab.served.service.dao.impl.AccessTokenDAOImpl;
import com.millennialslab.served.service.domain.AccessTokenDomain;
import com.millennialslab.served.service.domain.UserDomain;
import com.millennialslab.served.service.exceptions.AccessTokenException;
import com.millennialslab.served.service.exceptions.DigitsAuthenticationException;
import com.millennialslab.served.service.exceptions.PaymentCardCreateException;
import com.millennialslab.served.service.external.Digits3P;
import com.millennialslab.served.service.service.DigitsExternalService;
import com.millennialslab.served.service.service.NotificationService;
import com.millennialslab.served.service.service.UserService;
import com.millennialslab.served.service.util.StringUtil;
import com.millennialslab.served.service.dao.UserDAO;
import com.stripe.Stripe;
import com.stripe.model.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 22/10/15.
 */
@Service
public class UserServiceImpl implements UserService {

    @Inject
    protected DigitsExternalService digitsService;

    @Inject
    protected NotificationService notificationService;

    @Inject
    protected UserDAO userDao;

    @Inject
    protected AccessTokenDAOImpl accessTokenDao;

    @Value("${stripe.apiKey}")
    protected String apiKey;
    Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    //Stripe initialization
    {
        Stripe.apiKey = apiKey;
    }

    @Override
    public String[] getUserHiddenFields() {
        return new String[]{"digitsId", "lastDeviceId", "lastDeviceType"};
    }

    @Override
    public UserDomain authenticateWithDigits(String serviceProvider, String authorizationHeader, String deviceType, String deviceId) {

        Digits3P digitsResponse = digitsService.authenticate(serviceProvider, authorizationHeader);
        if (digitsResponse != null) {
            UserDomain user = userDao.findByDigitsId(digitsResponse.getIdStr());
            if (user == null) {
                user = new UserDomain();
                user.setDigitsId(digitsResponse.getIdStr());
                user.setPhoneNumber(digitsResponse.getPhoneNumber());
                user.setLastDeviceId(deviceId);
                user.setLastDeviceType(deviceType);
                user.setPromoCode(this.getPromoCode(user));
                long id = userDao.insert(user);
                user.setId(id);

            } else {
                boolean needsUpdate = false;
                notificationService.sendExpiredSession(user.getLastDeviceId(), user.getLastDeviceType(), deviceId, deviceType);
                if (user.getLastDeviceId() == null || !user.getLastDeviceId().equals(deviceId)) {
                    user.setLastDeviceId(deviceId);
                    needsUpdate = true;
                }

                if (user.getLastDeviceType() == null || !user.getLastDeviceType().equals(deviceType)) {
                    user.setLastDeviceType(deviceType);
                    needsUpdate = true;
                }

                if (needsUpdate) {
                    userDao.update(user);
                }
            }
            return user;

        } else {
            throw new DigitsAuthenticationException();
        }

    }

    @Override
    public UserDomain getByToken(String accessToken) {
        UserDomain user = userDao.findByToken(accessToken);
        if (user == null) {
            throw new AccessTokenException();
        }
        return user;
    }

    @Override
    public boolean updateUser(UserDomain user) {
        return userDao.update(user);
    }

    @Override
    public AccessTokenDomain createAccessToken(long userId) {

        accessTokenDao.delete(userId);

        AccessTokenDomain accessToken = new AccessTokenDomain();
        accessToken.setUserId(userId);
        accessToken.setAccessToken(StringUtil.generateAccessToken());
        accessToken.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        accessTokenDao.insert(accessToken);
        return accessToken;
    }

    @Override
    public List<UserDomain> getUserByIds(List<Long> ids) {
        return userDao.findByIds(ids);
    }

    @Override
    public UserDomain getById(long id) {
        return userDao.findById(id);
    }

    @Override
    public List<UserDomain> getUserByPhone(List<String> phones) {
        List<UserDomain> userList = userDao.findByPhone(phones);
        return userList;
    }

    @Override
    public long createUserFromWebPanel(UserUpdateDetailInputDTO userDomain) {

        return userDao.insertWebUser(userDomain.getFirstName(), userDomain.getSurname(), userDomain.getEmail());
    }

    protected String getPromoCode(UserDomain user) {
        return "promo_" + user.getPhoneNumber();
    }

    private String makeStripeCustomer(long userId, String token) {
        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("description", "user id: " + userId);
        customerParams.put("source", token);
        Stripe.apiKey = apiKey;
        Customer customer;
        try {
            customer = Customer.create(customerParams);
            return customer.getId();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new PaymentCardCreateException();
        }
    }
}
