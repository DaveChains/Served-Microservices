package com.serveme.service.order.external.service.impl;

import com.google.gson.reflect.TypeToken;
import com.serveme.service.order.api.errors.Errors;
import com.serveme.service.order.domain.PaymentDetail;
import com.serveme.service.order.external.dto.output.UserExDTO;
import com.serveme.service.order.external.service.UserService;
import com.serveme.service.order.util.api.httpExceptions.AuthenticationException;
import com.serveme.service.order.util.api.httpExceptions.InternalServerException;
import com.serveme.service.order.util.http.HttpServiceBase;
import com.serveme.service.order.util.http.HttpServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 20/11/15.
 */
@Service
public class UserServiceImpl implements UserService {


    //SERVICES PATH
    protected static String GET_USER_BY_ACCESSTOKEN = "/user/detail";
    protected static String GET_USER_LIST_BY_PHONE = "/internal/user/search/phone";
    protected static String GET_USER_LIST_BY_ID = "/internal/user/search/id";
    protected static String GET_USER_BY_ID = "/user/detail/id";
    protected static String GET_PAYMENT_BY_ID = "/payment/{id}/card";
    Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
    @Value("${service.user.host}")
    private String host;
    @Value("${service.user.port}")
    private int port;
    @Value("${service.payment.host}")
    private String paymentHost;
    @Value("${service.payment.port}")
    private int paymentPort;

    @Override
    public UserExDTO getUser(String accessToken) {

        try {
            return (UserExDTO) new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.GET)
                    .setPath(GET_USER_BY_ACCESSTOKEN)
                    .setHeader("access-token", accessToken)
                    .setReturnedClass(UserExDTO.class)
                    .call();

        } catch (HttpServiceException ex) {
            if (ex.getStatusCode() == 401) {
                throw new AuthenticationException("Authentication error retrieving the user by token : " + accessToken + "\n" + ex.getMessage(),
                        Errors.AUTHENTICATION.getError());
            } else {
                throw new InternalServerException("Http error connecting to User service\nResposeStatus:" + ex.getStatusCode() + "\nmessage" + ex.getMessage(),
                        Errors.INTERNAL_SERVER_ERROR.getError());
            }
        } catch (Exception ex) {
            throw ex;
        }

    }

    @Override
    public List<UserExDTO> getUserListFromPhone(List<String> phones) {

        try {
            return (List) new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.PUT)
                    .setPath(GET_USER_LIST_BY_PHONE)
                    .setBody(phones)
                    .setReturnedClass(new TypeToken<List<UserExDTO>>() {
                    }.getType())
                    .call();

        } catch (HttpServiceException ex) {
            throw new InternalServerException("Http error connecting to User service\nResposeStatus:" + ex.getStatusCode() + "\nmessage" + ex.getMessage(),
                    Errors.INTERNAL_SERVER_ERROR.getError());
        } catch (Exception ex) {
            throw ex;
        }

    }

    @Override
    public List<UserExDTO> getUserListFromId(List<Long> IDs) {

        try {
            return (List) new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.PUT)
                    .setPath(GET_USER_LIST_BY_ID)
                    .setBody(IDs)
                    .setReturnedClass(new TypeToken<List<UserExDTO>>() {
                    }.getType())
                    .call();

        } catch (HttpServiceException ex) {
            throw new InternalServerException("Http error connecting to User service\nResposeStatus:" + ex.getStatusCode() + "\nmessage" + ex.getMessage(),
                    Errors.INTERNAL_SERVER_ERROR.getError());
        } catch (Exception ex) {
            throw ex;
        }

    }

    @Override
    public UserExDTO getUserFromId(long id) {

        try {
            return (UserExDTO) new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.GET)
                    .setPath(GET_USER_BY_ID)
                    .setHeader("id", String.valueOf(id))
                    .setReturnedClass(UserExDTO.class)
                    .call();

        } catch (HttpServiceException ex) {
            throw new InternalServerException("Http error connecting to User service\nResposeStatus:" + ex.getStatusCode() + "\nmessage" + ex.getMessage(),
                    Errors.INTERNAL_SERVER_ERROR.getError());
        } catch (Exception ex) {
            throw ex;
        }

    }

    @Override
    public PaymentDetail getPaymentMethodForUser(String accessToken, long id) {
        try {
            return (PaymentDetail) new HttpServiceBase()
                    .setUrl("http://" + paymentHost + ":" + paymentPort)
                    .setHttpMethod(HttpMethod.GET)
                    .setPathParam("id", String.valueOf(id))
                    .setPath(GET_PAYMENT_BY_ID)
                    .setHeader("access-token", accessToken)
                    .setReturnedClass(PaymentDetail.class)
                    .call();

        } catch (HttpServiceException ex) {
            if (ex.getStatusCode() == 500)
                throw new InternalServerException("Http error connecting to Payment service\nResposeStatus:" + ex.getStatusCode() + "\nmessage" + ex.getMessage(),
                        Errors.INTERNAL_SERVER_ERROR.getError());
        } catch (Exception ex) {
            throw ex;
        }
        return null;
    }
}
