package com.millennialslab.served.service.api.controller;

import com.millennialslab.served.service.domain.UserDomain;
import com.millennialslab.served.service.service.UserService;
import com.millennialslab.served.service.util.BaseController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 15/11/15.
 */
@RestController
@RequestMapping(value = "/internal")
public class InternalUserAPI extends BaseController {
    @Inject
    protected UserService userService;
    /**
     * TODO add secret key in internal services
     */

    Logger logger = Logger.getLogger(UserAPI.class.getName());

    @ResponseBody
    @RequestMapping(value = "/user/search/phone", method = RequestMethod.PUT, produces = "application/json")
    public List<UserDomain> usersByPhone(@RequestBody List<String> phones) {
        return userService.getUserByPhone(phones);
    }

    @ResponseBody
    @Deprecated
    @RequestMapping(value = "/user/search/id", method = RequestMethod.PUT, produces = "application/json")
    public List<UserDomain> usersById(@RequestBody List<Long> idList) {
        return userService.getUserByIds(idList);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = "application/json")
    public UserDomain usersById(@PathVariable("id") long id) {
        return userService.getById(id);
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/user/_list", method = RequestMethod.GET, produces = "application/json")
    public List<UserDomain> usersByIdList(@QueryParam("id") Long[] id) {
        List<UserDomain> result = userService.getUserByIds(Arrays.asList(id));
        return result;
    }



}
