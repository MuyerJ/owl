package com.owl.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements Converter<String, Date> {
    private String dateFormat;

    public DateConverter(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public Date convert(String convertParam) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        try {
            return format.parse(convertParam);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
