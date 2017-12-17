package com.serveme.service.notification.external.service.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.serveme.service.notification.domain.Mail;
import com.serveme.service.notification.external.service.MailGunService;
import com.serveme.service.notification.temp.EmailContent;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * Created by DavidChains on 06/04/15.
 */
@Service
public class MailGunServiceImpl implements MailGunService {

    Logger logger = Logger.getLogger(MailGunServiceImpl.class.getName());


    @Value("${api.mailgun.url}")
    protected String url;

    @Value("${api.mailgun.api-key}")
    protected String apiKey;
    
    @Value("${api.mailgun.from}")
    protected String from;

    private final static String API_FIELD        = "api";
    private final static String FROM_FIELD       = "from";
    private final static String TO_FIELD         = "to";
    private final static String SUBJECT_FIELD    = "subject";
    private final static String CONTENT_FIELD    = "text";
    private final static String HTML_FIELD      = "html";

    public boolean sendEmail(Mail email){
        logger.log(Level.INFO, "Sending email: "+email.getId());

        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(API_FIELD, apiKey));
        WebResource webResource =client.resource(url);
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add(FROM_FIELD, from);
        formData.add(TO_FIELD, email.getReceiver());
        formData.add(SUBJECT_FIELD, email.getSubject());
        formData.add(CONTENT_FIELD, "DOESNT SUPPORT HTML");

        formData.add(HTML_FIELD, email.getContent());

        ClientResponse response= webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);

        if(response.getStatus() <200 || response.getStatus() >=300){
            String errorMsg = "ERROR sending email :"+email.getId()+"\n" +
                    "Status: "+response.getStatus()+"\n" +
                    "Cause:\n"+response.toString();
            logger.log(Level.WARNING, errorMsg);
        }else{
            String infoMsg = "Successfully sent email :"+email.getId()+"\n" ;
            logger.log(Level.INFO, infoMsg);
            return true;
        }

        return false;
    }
}
