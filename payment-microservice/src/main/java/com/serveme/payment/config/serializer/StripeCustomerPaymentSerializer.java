package com.serveme.payment.config.serializer;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.serveme.payment.domain.PaymentDetail;

import java.io.IOException;


/**
 * This serializer hides stripeCustomerId field
 */
public class StripeCustomerPaymentSerializer extends JsonSerializer<PaymentDetail> {

    @Override
    public void serialize(PaymentDetail value, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeObjectField("createdAt", value.getCreatedAt());
        jgen.writeObjectField("updatedAt",value.getUpdatedAt());
        jgen.writeNumberField("userId",value.getUserId());
        jgen.writeStringField("cardType",value.getCardType());
        jgen.writeStringField("cardLastDigits",value.getCardLastDigits());
        jgen.writeEndObject();
    }
}
