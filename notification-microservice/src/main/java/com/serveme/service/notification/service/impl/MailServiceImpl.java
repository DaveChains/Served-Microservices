package com.serveme.service.notification.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.serveme.service.notification.dao.MailDao;
import com.serveme.service.notification.domain.Mail;
import com.serveme.service.notification.external.service.MailGunService;
import com.serveme.service.notification.service.MailService;

@Service
public class MailServiceImpl implements MailService {

    Logger logger = Logger.getLogger(MailServiceImpl.class.getName());
    
    @Inject
    protected MailDao mailDao;
    
    @Inject
    protected MailGunService mailGunService;

   public void registerNewEmail(Mail mail) {
	   mail.setSent(false);
	   mailDao.create(mail);
   }
   
   @Override
   @Scheduled(fixedDelay=300000)
   public void sendPendingEmails() {
   	logger.log(Level.INFO, "Sending emails...");
   	List<Mail> mails = mailDao.getAllNotSended();
   	if (mails == null) {
   		return;
   	}
   	for (int i=0; i<mails.size(); i++) {
   		for (int j=0; j<2; j++) {
   			mails.get(i).setLastTryAt(new Timestamp(new Date().getTime()));
   			mails.get(i).setTries(mails.get(i).getTries()+1);
   			boolean result = mailGunService.sendEmail(mails.get(i));
   			if (result) {
   				mails.get(i).setSent(true);
   				mailDao.update(mails.get(i));
   				break;
   			}
   			mailDao.update(mails.get(i));
   		}
   	}
   }
}
