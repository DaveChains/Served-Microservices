package com.serveme.service.order.external.service;

import com.serveme.service.order.domain.Mail;
import com.serveme.service.order.external.dto.output.DevicesDTO;

/**
 * Created by DavidChains on 11/12/15.
 */
public interface NotificationService {

    Object notificateOrderAccepted(DevicesDTO devices, String orderId);

    Object notificateOrderDeclined(DevicesDTO devices, String orderId);

    Object notificateOrderFinished(DevicesDTO devices, String orderId);

    Object notificateOrderNew(DevicesDTO devices, String orderId);

    Object notificateOrderAutoCanceled(DevicesDTO userDevices, DevicesDTO restaurantDevices, DevicesDTO adminDevices, String orderId);

    Object notificateOrderCanceled(DevicesDTO userDevices, DevicesDTO restaurantDevices, DevicesDTO adminDevices, String orderId, boolean byUser);

    Object notificateNewSplitBill(DevicesDTO devices, String orderId);

    Object notificateAcceptedSplitBill(DevicesDTO devices, String orderId);

    Object notificateDeclinedSplitBill(DevicesDTO devices, String orderId);

    Object registerMail(Mail mail);

	Object notificateRememberOrder(DevicesDTO devices, String title, String message);
}
