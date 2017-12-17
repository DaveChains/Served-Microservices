package com.serveme.service.notification.dao;
import java.util.List;

import com.serveme.service.notification.domain.Mail;

public interface MailDao {
	
	public long create(Mail mail);
	public void update(Mail mail);
	public List<Mail> getAllNotSended();
}
