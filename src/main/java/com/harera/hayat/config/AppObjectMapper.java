package com.harera.hayat.config;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AppObjectMapper extends ObjectMapper {

    public AppObjectMapper() {
        this.configure(WRITE_DATES_AS_TIMESTAMPS, false);
    }
}
