package com.serveme.payment.persistence.converters;


import com.serveme.payment.payment.ChargeResultWrapper;
import com.serveme.payment.util.DataUtil;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.Instant;

@Converter(autoApply = true)
public class ChargeResultWrapperJpaConverter implements AttributeConverter<ChargeResultWrapper, String>{
    @Override
    public String convertToDatabaseColumn(ChargeResultWrapper chargeResult) {

        return chargeResult == null ? null : DataUtil.toJson(chargeResult);
    }

    @Override
    public ChargeResultWrapper convertToEntityAttribute(String json) {
        return json == null ? null : DataUtil.fromJson(json, ChargeResultWrapper.class);
    }
}
