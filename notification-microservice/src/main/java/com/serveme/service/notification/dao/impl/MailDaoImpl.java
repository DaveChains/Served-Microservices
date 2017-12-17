package com.serveme.service.notification.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.serveme.service.notification.dao.MailDao;
import com.serveme.service.notification.domain.Mail;
import com.serveme.service.notification.util.persistence.GenericRowMapperList;

import javax.inject.Inject;
import java.sql.Types;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class MailDaoImpl implements MailDao {

    Logger logger = Logger.getLogger(MailDaoImpl.class.getName());

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Override
    public long create(Mail mail){
        logger.log(Level.INFO, "\n\tCreating new mail. ID: "+mail.getId());
        String sql = "INSERT INTO MAILS(message_type, service_from, receiver, email_subject, content, tries) " +
                "VALUES(?, ?, ?, ?, ?, ?)";

        Object[] params = {mail.getType(), mail.getFrom(), mail.getReceiver(), mail.getSubject(), mail.getContent(), mail.getTries()};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER);

        factory.setReturnGeneratedKeys(true);
        KeyHolder keyholder = new GeneratedKeyHolder();

        jdbcTemplate.update(factory.newPreparedStatementCreator(params), keyholder);
        long id = keyholder.getKey().longValue();
        return id;
    }

    @Override
    public void update(Mail mail){
        String sql = "UPDATE MAILS SET message_type=?, service_from=?, receiver=?, email_subject=?, content=?, tries=?, lastTryAt=?, sent=?" +
                " WHERE id=?";


        Object[] params = {mail.getType(), mail.getFrom(), mail.getReceiver(), mail.getSubject(), mail.getContent(), mail.getTries(), mail.getLastTryAt(), mail.isSent(), mail.getId()};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.TIMESTAMP, Types.INTEGER, Types.INTEGER);

        if(jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0){
            throw new RuntimeException("The email "+mail.getId()+" wasn't updated");
        }

    }

    @Override
    public List<Mail> getAllNotSended(){

        String sql = "SELECT * FROM MAILS" +
                " WHERE sent=?";
        List<Mail> mailList = null;
        try{
        	mailList = (List)jdbcTemplate.queryForObject(sql, new Object[]{0}, new GenericRowMapperList(Mail.class));
        }catch(Exception ex) {
        }

        return mailList;  
    }


}
