package com.serveme.service.notification.api.controller;import com.serveme.service.notification.api.dto.input.ListOfDevicesForMessagesInputDTO;import com.serveme.service.notification.api.dto.input.ListOfDevicesInputDTO;import com.serveme.service.notification.domain.DeviceInformation;import com.serveme.service.notification.enums.DeviceType;import com.serveme.service.notification.exceptions.NotificationCannotBeSentException;import com.serveme.service.notification.external.dto.input.DataClauseParseDTO;import com.serveme.service.notification.external.dto.input.ParseDTO;import com.serveme.service.notification.external.dto.input.WhereClauseParseDTO;import com.serveme.service.notification.external.dto.output.GcmResponseDTO;import com.serveme.service.notification.external.dto.output.ResponseDTO;import com.serveme.service.notification.external.service.GCMService;import com.serveme.service.notification.external.service.ParseService;import org.springframework.http.HttpStatus;import org.springframework.http.ResponseEntity;import org.springframework.stereotype.Controller;import org.springframework.web.bind.annotation.*;import javax.inject.Inject;import java.util.logging.Level;import java.util.logging.Logger;/** * Created by DavidChains on 7/12/15. */@Controllerpublic class PushNotificationAPI extends NotificationAPIBase {    @Inject    protected ParseService parseService;    @Inject    protected GCMService gcmService;    public PushNotificationAPI() {        logger = Logger.getLogger(PushNotificationAPI.class.getName());    }    @ResponseBody    @RequestMapping(value = "/notification/test", method = RequestMethod.GET, produces = "application/json")    public ResponseEntity<String> isRestaurantOpen() {        return ResponseEntity.ok("Todo OK");    }    @ResponseBody    @RequestMapping(value = "/notification/{orderId}/accepted", method = RequestMethod.POST, produces = "application/json")    public ResponseEntity acceptOrder(            @PathVariable("orderId") String orderId,            @RequestBody ListOfDevicesInputDTO listOfDevicesInputDTO) {        logger.log(Level.INFO, "/notification/" + orderId + "/accepted");        logger.log(Level.INFO, "Devices : " + listOfDevicesInputDTO.toString());        try {            sendNotifications(orderId, listOfDevicesInputDTO, "accepted", false);        } catch (Exception ignored) {        }        return sendNotifications(orderId, listOfDevicesInputDTO, "accepted", true);    }    @ResponseBody    @RequestMapping(value = "/notification/{orderId}/finished", method = RequestMethod.POST, produces = "application/json")    public ResponseEntity finishOrder(            @PathVariable("orderId") String orderId,            @RequestBody ListOfDevicesInputDTO listOfDevicesInputDTO) {        try {            sendNotifications(orderId, listOfDevicesInputDTO, "finished", false);        } catch (Exception ignored) {        }        return sendNotifications(orderId, listOfDevicesInputDTO, "finished", true);    }    @ResponseBody    @RequestMapping(value = "/notification/{orderId}/declined", method = RequestMethod.POST, produces = "application/json")    public ResponseEntity declineOrder(            @PathVariable("orderId") String orderId,            @RequestBody ListOfDevicesInputDTO listOfDevicesInputDTO) {        try {            sendNotifications(orderId, listOfDevicesInputDTO, "declined", false);        } catch (Exception ignored) {        }        return sendNotifications(orderId, listOfDevicesInputDTO, "declined", true);    }    @ResponseBody    @RequestMapping(value = "/notification/{orderId}/canceled/user", method = RequestMethod.POST, produces = "application/json")    public ResponseEntity autoCancelOrderForUser(            @PathVariable("orderId") String orderId,            @RequestBody ListOfDevicesInputDTO listOfDevicesInputDTO) {        return sendNotifications(orderId, listOfDevicesInputDTO, "canceled", true);    }    @ResponseBody    @RequestMapping(value = "/notification/{orderId}/canceled/restaurant", method = RequestMethod.POST, produces = "application/json")    public ResponseEntity autoCancelOrderForRestaurant(            @PathVariable("orderId") String orderId,            @RequestBody ListOfDevicesInputDTO listOfDevicesInputDTO) {        return sendNotifications(orderId, listOfDevicesInputDTO, "canceled", false);    }    @ResponseBody    @RequestMapping(value = "/notification/{orderId}/canceled/user/{canceledByUser}", method = RequestMethod.POST, produces = "application/json")    public ResponseEntity cancelOrderForUser(            @PathVariable("orderId") String orderId, @PathVariable("canceledByUser") boolean canceledByUser,            @RequestBody ListOfDevicesInputDTO listOfDevicesInputDTO) {        return sendNotifications(orderId, listOfDevicesInputDTO, canceledByUser ? "user_canceled" : "admin_canceled", true);    }    @ResponseBody    @RequestMapping(value = "/notification/{orderId}/canceled/restaurant/{canceledByUser}", method = RequestMethod.POST, produces = "application/json")    public ResponseEntity cancelOrderForRestaurant(            @PathVariable("orderId") String orderId, @PathVariable("canceledByUser") boolean canceledByUser,            @RequestBody ListOfDevicesInputDTO listOfDevicesInputDTO) {        return sendNotifications(orderId, listOfDevicesInputDTO, canceledByUser ? "user_canceled" : "admin_canceled", false);    }    @ResponseBody    @RequestMapping(value = "/notification/message/user", method = RequestMethod.POST, produces = "application/json")    public ResponseEntity sendAdMessageForUsers(            @RequestBody ListOfDevicesForMessagesInputDTO list) {        return sendNotifications(list, "neutral", true);    }    @ResponseBody    @RequestMapping(value = "/notification/message/restaurant", method = RequestMethod.POST, produces = "application/json")    public ResponseEntity sendAdMessageForRestaurants(            @RequestBody ListOfDevicesForMessagesInputDTO list) {        return sendNotifications(list, "neutral", false);    }    @ResponseBody    @RequestMapping(value = "/notification/{orderId}/new", method = RequestMethod.POST, produces = "application/json")    public ResponseEntity newOrder(            @PathVariable("orderId") String orderId,            @RequestBody ListOfDevicesInputDTO listOfDevicesInputDTO) {        return sendNotifications(orderId, listOfDevicesInputDTO, "new", false);    }    @ResponseBody    @RequestMapping(value = "/notification/{orderId}/new_split_bill", method = RequestMethod.POST, produces = "application/json")    public ResponseEntity newSplitBill(            @PathVariable("orderId") String orderId,            @RequestBody ListOfDevicesInputDTO listOfDevicesInputDTO) {        return sendNotifications(orderId, listOfDevicesInputDTO, "new_split_bill", true);    }    @ResponseBody    @RequestMapping(value = "/notification/{orderId}/accepted_split_bill", method = RequestMethod.POST, produces = "application/json")    public ResponseEntity acceptedSplitBill(            @PathVariable("orderId") String orderId,            @RequestBody ListOfDevicesInputDTO listOfDevicesInputDTO) {        return sendNotifications(orderId, listOfDevicesInputDTO, "accepted_split_bill", true);    }    @ResponseBody    @RequestMapping(value = "/notification/{orderId}/declined_split_bill", method = RequestMethod.POST, produces = "application/json")    public ResponseEntity declinedSplitBill(            @PathVariable("orderId") String orderId,            @RequestBody ListOfDevicesInputDTO listOfDevicesInputDTO) {        return sendNotifications(orderId, listOfDevicesInputDTO, "declined_split_bill", true);    }    @ResponseBody    @RequestMapping(value = "/notification/expired/restaurant", method = RequestMethod.POST, produces = "application/json")    public ResponseEntity sessionExpired(            @RequestBody DeviceInformation deviceInformation) {        logger.log(Level.INFO, "Devices : " + deviceInformation.toString());        ListOfDevicesInputDTO listOfDevicesInputDTO = new ListOfDevicesInputDTO();        listOfDevicesInputDTO.addDeviceInformation(deviceInformation);        return sendNotifications("", listOfDevicesInputDTO, "session_expired", false);    }    @ResponseBody    @RequestMapping(value = "/notification/expired/user", method = RequestMethod.POST, produces = "application/json")    public ResponseEntity sessionExpiredUser(            @RequestBody DeviceInformation deviceInformation) {        logger.log(Level.INFO, "Devices : " + deviceInformation.toString());        ListOfDevicesInputDTO listOfDevicesInputDTO = new ListOfDevicesInputDTO();        listOfDevicesInputDTO.addDeviceInformation(deviceInformation);        return sendNotifications("", listOfDevicesInputDTO, "session_expired", true);    }    /*******************************************************     * PRIVATE METHODS     *******************************************************/    private ResponseEntity sendNotifications(String orderId, ListOfDevicesInputDTO listOfDevicesInputDTO,                                             String type, boolean forUserApp) {        int success = 0;        StringBuilder failedDevices = new StringBuilder();        for (DeviceInformation device : listOfDevicesInputDTO.getDeviceIds()) {            if (device.getDeviceType() == DeviceType.IOS) {                logger.log(Level.INFO, "IOS notification");                ParseDTO notificationData = buildNotificationData(device, orderId, type, listOfDevicesInputDTO.getRestaurantName());                logger.log(Level.INFO, "Parse notification: " + notificationData.toString());                ResponseDTO parseResponse = forUserApp ? parseService.sendUserPushNotification(notificationData)                        : parseService.sendRestaurantPushNotification(notificationData);                logger.log(Level.INFO, "Parse Response : " + parseResponse.toString());                if (parseResponse.isResult()) {                    success++;                } else {                    failedDevices.append(device.getDeviceId());                    failedDevices.append(" ");                }            } else if (device.getDeviceType() == DeviceType.ANDROID) {                logger.log(Level.INFO, "ANDROID notification");//1                GcmResponseDTO gcmResponse = forUserApp ? gcmService.sendUserPushNotification(device.getDeviceId(), orderId, type, listOfDevicesInputDTO.getRestaurantName(), listOfDevicesInputDTO.getArrivalAt())                        : gcmService.sendRestaurantPushNotification(device.getDeviceId(), orderId, type);//2                if (gcmResponse.isSuccessful()) {                    success++;                } else {                    failedDevices.append(device.getDeviceId());                    failedDevices.append(" ");                }            }        }        if (success == listOfDevicesInputDTO.getDeviceIds().size()) {            return new ResponseEntity(HttpStatus.OK);        } else {            throw new NotificationCannotBeSentException(failedDevices.toString().trim());        }    }    private ResponseEntity sendNotifications(ListOfDevicesForMessagesInputDTO list, String type, boolean forUserApp) {        int success = 0;        StringBuilder failedDevices = new StringBuilder();        for (DeviceInformation device : list.getDeviceIds()) {            if (device.getDeviceType() == DeviceType.IOS) {                logger.log(Level.INFO, "IOS notification");                ParseDTO notificationData = buildNotificationDataGeneralMessage(device, list.getMessage());                logger.log(Level.INFO, "Parse notification: " + notificationData.toString());                ResponseDTO parseResponse = forUserApp ? parseService.sendUserPushNotification(notificationData)                        : parseService.sendRestaurantPushNotification(notificationData);                logger.log(Level.INFO, "Parse Response : " + parseResponse.toString());                if (parseResponse.isResult()) {                    success++;                } else {                    failedDevices.append(device.getDeviceId());                    failedDevices.append(" ");                }            } else if (device.getDeviceType() == DeviceType.ANDROID) {                logger.log(Level.INFO, "ANDROID notification");//3                GcmResponseDTO gcmResponse = forUserApp ? gcmService.sendUserPushNotification(device.getDeviceId(), list.getTitle(), list.getMessage(), type)                        //4                        : gcmService.sendRestaurantPushNotification(device.getDeviceId(), list.getTitle(), list.getMessage(), type);                if (gcmResponse.isSuccessful()) {                    success++;                } else {                    failedDevices.append(device.getDeviceId());                    failedDevices.append(" ");                }            }        }        if (success == list.getDeviceIds().size()) {            return new ResponseEntity(HttpStatus.OK);        } else {            throw new NotificationCannotBeSentException(failedDevices.toString().trim());        }    }    private ParseDTO buildNotificationData(DeviceInformation device, String orderId, String orderState, String restaurantName) {        WhereClauseParseDTO whereClause = new WhereClauseParseDTO();        whereClause.setDeviceToken(device.getDeviceId());        DataClauseParseDTO dataClause = new DataClauseParseDTO();        dataClause.setOrderState(orderState);        dataClause.setOrderId(orderId);        if (orderState.equals("new")) {            dataClause.setAlert("There is a new order for your restaurant.");        } else {            if (orderState.equals("accepted")) {                dataClause.setAlert(restaurantName + " got your order and is already working on it!!!");            } else if (orderState.equals("finished")) {                dataClause.setAlert("Great!! Your bill at " + restaurantName + " has been closed");            } else {                dataClause.setAlert("An order has been " + orderState + ".");            }        }        dataClause.setSound("default");        ParseDTO notificationData = new ParseDTO();        notificationData.setWhere(whereClause);        notificationData.setData(dataClause);        return notificationData;    }    private ParseDTO buildNotificationDataGeneralMessage(DeviceInformation device, String message) {        WhereClauseParseDTO whereClause = new WhereClauseParseDTO();        whereClause.setDeviceToken(device.getDeviceId());        DataClauseParseDTO dataClause = new DataClauseParseDTO();        dataClause.setAlert(message);        dataClause.setSound("default");        ParseDTO notificationData = new ParseDTO();        notificationData.setWhere(whereClause);        notificationData.setData(dataClause);        return notificationData;    }}