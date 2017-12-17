package com.serveme.service.notification.service;

import com.serveme.service.notification.domain.Mail;

public interface MailService {
	public void registerNewEmail(Mail mail);
	public void sendPendingEmails();
}
