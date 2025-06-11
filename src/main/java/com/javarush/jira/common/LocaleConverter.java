package com.javarush.jira.common;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Locale;

@Converter
public class LocaleConverter implements AttributeConverter<Locale, String> {
    @Override
    public String convertToDatabaseColumn(Locale locale) {
        return locale != null ? locale.toLanguageTag() : null;
    }

    @Override
    public Locale convertToEntityAttribute(String dbData) {
        return Locale.forLanguageTag(dbData);
    }
}
