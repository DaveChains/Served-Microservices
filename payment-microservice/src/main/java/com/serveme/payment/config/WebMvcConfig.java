package com.serveme.payment.config;

/**
 * Created by Davids-iMac on 26/05/15.
 */

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.serveme.payment.config.serializer.LocalDateTimeSerializer;
import com.serveme.payment.domain.PaymentDetail;
import com.serveme.payment.config.serializer.StripeCustomerPaymentSerializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        MappingJackson2HttpMessageConverter mapper = new MappingJackson2HttpMessageConverter();
        SimpleModule module = new SimpleModule();
        module.addSerializer(PaymentDetail.class, new StripeCustomerPaymentSerializer());
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());

        mapper.getObjectMapper().registerModule(module);
        mapper.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        converters.add(mapper);
    }

}
