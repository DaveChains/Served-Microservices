package com.millennialslab.served.service.api.controller;

import com.millennialslab.served.service.api.dto.input.PaymentInfoDto;
import com.millennialslab.served.service.api.dto.input.CardInputDTO;
import com.millennialslab.served.service.api.dto.input.DeviceInputDTO;
import com.millennialslab.served.service.api.dto.input.UserUpdateDetailInputDTO;
import com.millennialslab.served.service.api.dto.output.AuthenticationOutputDTO;
import com.millennialslab.served.service.domain.AccessTokenDomain;
import com.millennialslab.served.service.domain.UserDomain;
import com.millennialslab.served.service.enums.DeviceType;
import com.millennialslab.served.service.exceptions.AccessTokenException;
import com.millennialslab.served.service.service.PaymentService;
import com.millennialslab.served.service.service.UserService;
import com.millennialslab.served.service.util.BaseController;
import com.millennialslab.served.service.util.GenericHiddenBuilder;
import com.millennialslab.served.service.util.StringUtil;
import com.millennialslab.served.service.util.exception.http.AuthenticationException;
import com.millennialslab.served.service.util.exception.http.BadRequestException;
import com.millennialslab.served.service.util.validation.SimpleValidator;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 22/10/15.
 */
@Controller
public class UserAPI extends BaseController {
    @Inject
    protected UserService userService;

    @Inject
    protected PaymentService paymentService;

    /**
     * TODO add secret key in internal services
     */

    Logger logger = Logger.getLogger(UserAPI.class.getName());

    @ResponseBody
    @RequestMapping(value = "/user/test/{id}", method = RequestMethod.GET, produces = "application/json")
    public UserDomain test(@PathVariable("id") long id) {
        UserDomain user = userService.getById(id).removeNullFields();
        return new GenericHiddenBuilder<>(user).hide(userService.getUserHiddenFields()).build();//it's never null
    }

    @ResponseBody
    @RequestMapping(value = "/user/auth/digits", method = RequestMethod.POST, produces = "application/json")
    public AuthenticationOutputDTO enterWithDigits(
            @RequestHeader("X-Auth-Service-Provider") String serviceProvider,
            @RequestHeader("X-Verify-Credentials-Authorization") String authorization,
            @RequestBody DeviceInputDTO deviceInputDTO) {

        String logging = "\n(*)Authentication via Digits with headers:";
        logging += "\n\tX-Auth-Service-Provider: " + serviceProvider;
        logging += "\n\tX-Verify-Credentials-Authorization: " + authorization;

        logger.log(Level.INFO, logging);

        new SimpleValidator().notNull("deviceType").notNull("deviceId").validate(deviceInputDTO);

        if (!DeviceType.ANDROID.getValue().equals(deviceInputDTO.getDeviceType().toLowerCase()) &&
                !DeviceType.iOS.getValue().equals(deviceInputDTO.getDeviceType().toLowerCase())) {
            throw new BadRequestException(
                    String.format("%s not accepted", deviceInputDTO.getDeviceType()),
                    String.format("%s not accepted", deviceInputDTO.getDeviceType()));
        }

        UserDomain user = null;
        try {
            user = userService.authenticateWithDigits(
                    serviceProvider,
                    authorization,
                    deviceInputDTO.getDeviceType(),
                    deviceInputDTO.getDeviceId())
                    .removeNullFields();

        } catch (Exception ex) {
            throw new AuthenticationException(ex.getMessage(), "Issue authenticating with 3rd party library");
        }
        if (user == null)
            throw new AuthenticationException("ServeMe Authentication error", "ServeMe Authentication error");

        AccessTokenDomain accessToken = userService.createAccessToken(user.getId());
        if (accessToken == null)
            throw new AuthenticationException("ServeMe Authentication error", "ServeMe Authentication error");

        AuthenticationOutputDTO authDto = new AuthenticationOutputDTO();
        UserDomain userWithHidden = new GenericHiddenBuilder<>(user).hide(userService.getUserHiddenFields()).build();
        authDto.setProfile(userWithHidden);
        authDto.setToken(accessToken);
        authDto.setPaymentInfo(paymentService.getCurrentCard(accessToken.getAccessToken(), user.getId()));
        return authDto;
    }

    @ResponseBody
    @RequestMapping(value = "/user/detail", method = RequestMethod.GET, produces = "application/json")
    public UserDomain getUserDetail(@RequestHeader("access-token") String accessToken) {
        UserDomain user = userService.getByToken(accessToken).removeNullFields();
        return new GenericHiddenBuilder<>(user).hide(userService.getUserHiddenFields()).build();//it's never null
    }


    @ResponseBody
    @RequestMapping(value = "/user/detail/id", method = RequestMethod.GET, produces = "application/json")
    public UserDomain getUserDetailById(@RequestHeader("id") long id) {
        UserDomain user = userService.getById(id).removeNullFields();
        return new GenericHiddenBuilder<>(user).hide(userService.getUserHiddenFields()).build();//it's never null
    }

    @ResponseBody
    @RequestMapping(value = "/user/signup", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity createUserFromWebPanel(@RequestBody UserUpdateDetailInputDTO webUser) {

        try {

            if (StringUtil.nullEmpty(webUser.getEmail()))
                return new ResponseEntity("Email required", HttpStatus.BAD_REQUEST);
            long id = userService.createUserFromWebPanel(webUser);
            logger.log(Level.INFO, "Web user created with id " + id);
            return new ResponseEntity(id, HttpStatus.CREATED);

        } catch (DuplicateKeyException ex) {
            logger.log(Level.INFO, "USER ALREADY CREATED : \n" + webUser.toString());
            return new ResponseEntity(HttpStatus.ACCEPTED);

        } catch (Exception ex) {

            logger.log(Level.SEVERE, "USER NOT CREATED : \n" + webUser.toString(), ex);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @ResponseBody
    @RequestMapping(value = "/user/detail", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity updateUserDetail(
            @RequestHeader("access-token") String accessToken,
            @RequestBody UserUpdateDetailInputDTO userProfile) {

        UserDomain user = userService.getByToken(accessToken);
        user.setFirstName(userProfile.getFirstName());
        user.setSurname(userProfile.getSurname());
        user.setEmail(userProfile.getEmail());
        userService.updateUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/user/card/register", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity registerCard(
            @RequestHeader("access-token") String accessToken,
            @RequestBody CardInputDTO cardForm) {
        logger.log(Level.INFO, "Card register starting");
        UserDomain user = userService.getByToken(accessToken);
        logger.log(Level.INFO, "Card registering for user " + user.getId());
//        userService.registerCard(userSM, cardForm);
        if (paymentService.addPaymentCard(
                accessToken,
                user.getId(),
                cardForm.getCardToken(),
                cardForm.getCardType(),
                cardForm.getCardLastDigits())) {
            logger.log(Level.INFO, "Card successfully created for user " + user.getId());
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/user/card/current", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<PaymentInfoDto> getCardInfo(
            @RequestHeader("access-token") String accessToken,
            @RequestBody CardInputDTO cardForm) {

        UserDomain user = userService.getByToken(accessToken);
        PaymentInfoDto paymentInfoDto = paymentService.getCurrentCard(accessToken, user.getId());
        if (paymentInfoDto != null)
            return new ResponseEntity<PaymentInfoDto>(paymentInfoDto, HttpStatus.OK);
        else
            return new ResponseEntity<PaymentInfoDto>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessTokenException.class)
    public ResponseEntity<String> handleAccessTokenException(AccessTokenException ex) {
        logger.log(Level.SEVERE, ex.getMessage());
        return new ResponseEntity("Authentication error", HttpStatus.UNAUTHORIZED);
    }
}
