package com.serveme.service.notification.external.service;

import com.serveme.service.notification.domain.Mail;

/**
 * Created by DavidChains on 12/02/16.
 */
public interface MailGunService {
	public boolean sendEmail(Mail email);
}
