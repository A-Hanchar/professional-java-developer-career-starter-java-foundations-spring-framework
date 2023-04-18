package com.artsiomhanchar.peopledbweb.web.formatter;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.Locale;

@Component
public class MultipartFileFormatter implements Formatter<MultipartFile> {

    /**
     * Parse a text String to produce a T.
     *
     * @param text   the text string
     * @param locale the current user locale
     * @return an instance of T
     * @throws ParseException           when a parse exception occurs in a java.text parsing library
     * @throws IllegalArgumentException when a parse exception occurs
     */
    @Override
    public MultipartFile parse(String text, Locale locale) throws ParseException {
        return null;
    }

    /**
     * Print the object of type T for display.
     *
     * @param object the instance to print
     * @param locale the current user locale
     * @return the printed text string
     */
    @Override
    public String print(MultipartFile object, Locale locale) {
        return object.getOriginalFilename();
    }
}
