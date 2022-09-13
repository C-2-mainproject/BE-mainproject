package com.wolves.mainproject.type.convertor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashMap;

@Converter
public class StatusTypeConvertor implements AttributeConverter<String, Integer> {

    public StatusTypeConvertor(){
    }

    @Override
    public Integer convertToDatabaseColumn(String attribute) {
        return null;
    }

    @Override
    public String convertToEntityAttribute(Integer dbData) {
        return null;
    }
}
