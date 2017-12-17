package com.serveme.service.order.external.service.impl;

import com.serveme.service.order.api.dto.output.ListOfDevicesForMessagesInputDTO;
import com.serveme.service.order.domain.Mail;
import com.serveme.service.order.external.dto.output.DevicesDTO;
import com.serveme.service.order.external.service.NotificationService;
import com.serveme.service.order.util.http.HttpServiceBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by DavidChains on 11/12/15.
 */
@Service
public class NotificationServiceImpl implements NotificationService {


    //SERVICES PATH
    protected static String ORDER_NOTIFICATION_BASE = "/notification";
    protected static String EMAIL_NOTIFICATION_BASE = "/notification/mail/create";

    Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
    @Value("${service.notification.host}")
    private String host;
    @Value("${service.notification.port}")
    private int port;

    @Override
    public Object notificateOrderAccepted(DevicesDTO devices, String orderId) {
        return sendNotification(devices, orderId, "accepted");
    }

    @Override
    public Object notificateOrderDeclined(DevicesDTO devices, String orderId) {
        return sendNotification(devices, orderId, "declined");
    }

    @Override
    public Object notificateOrderFinished(DevicesDTO devices, String orderId) {
        return sendNotification(devices, orderId, "finished");
    }

    @Override
    public Object notificateOrderNew(DevicesDTO devices, String orderId) {
        return sendNotification(devices, orderId, "new");
    }

    @Override
    public Object notificateOrderAutoCanceled(DevicesDTO userDevices, DevicesDTO restaurantDevices, DevicesDTO adminDevices, String orderId) {
        try {
            sendNotification(restaurantDevices, orderId, "canceled/restaurant");
            sendNotification(adminDevices, orderId, "canceled/restaurant");
        } catch (Exception ignored) {
        }
        return sendNotification(userDevices, orderId, "canceled/user");
    }

    @Override
    public Object notificateOrderCanceled(DevicesDTO userDevices, DevicesDTO restaurantDevices, DevicesDTO adminDevices, String orderId, boolean byUser) {
        try {
            sendNotification(restaurantDevices, orderId, "canceled/restaurant/" + byUser);
            sendNotification(adminDevices, orderId, "canceled/restaurant/" + byUser);
        } catch (Exception ignored) {
        }
        return sendNotification(userDevices, orderId, "canceled/user/" + byUser);
    }

    @Override
    public Object notificateNewSplitBill(DevicesDTO devices, String orderId) {
        return sendNotification(devices, orderId, "new_split_bill");
    }

    @Override
    public Object notificateAcceptedSplitBill(DevicesDTO devices, String orderId) {
        return sendNotification(devices, orderId, "accepted_split_bill");
    }

    @Override
    public Object notificateDeclinedSplitBill(DevicesDTO devices, String orderId) {
        return sendNotification(devices, orderId, "declined_split_bill");
    }

    @Override
    public Object notificateRememberOrder(DevicesDTO devices, String title, String message) {
        ListOfDevicesForMessagesInputDTO messageList = new ListOfDevicesForMessagesInputDTO();
        messageList.setDeviceIds(devices.getDeviceIds());
        messageList.setTitle(title);
        messageList.setMessage(message);
        return sendGeneralNotification(messageList);
    }

    @Override
    public Object registerMail(Mail mail) {
        try {
            return new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.POST)
                    .setPath(EMAIL_NOTIFICATION_BASE)
                    .setBody(mail)
                    .call();
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Email failed to be registered");
            return null;
        }
    }

    private Object sendGeneralNotification(ListOfDevicesForMessagesInputDTO devices) {
        try {
            return new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.POST)
                    .setPath(ORDER_NOTIFICATION_BASE + "/message/user")
                    .setBody(devices)
                    .call();
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Notification failed to be delivered");
            return null;
        }
    }

    private Object sendNotification(DevicesDTO devices, String orderId, String type) {
        try {
            return new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.POST)
                    .setPath(ORDER_NOTIFICATION_BASE + "/" + orderId + "/" + type)
                    .setBody(devices)
                    .call();
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Notification failed to be delivered");
            return null;
        }
    }
}
