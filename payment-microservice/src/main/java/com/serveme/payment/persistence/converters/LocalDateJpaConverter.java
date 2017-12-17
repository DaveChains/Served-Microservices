package com.serveme.payment.persistence.converters;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Converter(autoApply = true)
public class LocalDateJpaConverter implements AttributeConverter<LocalDate, Date>{
    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {

        return localDate == null ? null : Date.valueOf(localDate);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {
        return date == null ? null : date.toLocalDate();
    }
}
