//package com.harera.hayat.config;
//
//import java.io.IOException;
//import java.time.OffsetDateTime;
//import java.time.format.DateTimeFormatter;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//
//@Configuration
//public class SerializationConfig {
//
//    @Bean
//    @Primary
//    //    @ConditionalOnMissingBean(ObjectMapper.class)
//    public ObjectMapper serializingObjectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(OffsetDateTime.class, new JsonSerializer<>() {
//            @Override
//            public void serialize(OffsetDateTime offsetDateTime,
//                            JsonGenerator jsonGenerator,
//                            SerializerProvider serializerProvider) throws IOException {
//                jsonGenerator.writeString(DateTimeFormatter.ISO_OFFSET_DATE_TIME
//                                .format(offsetDateTime));
//            }
//        });
//        objectMapper.registerModule(simpleModule);
//        return objectMapper;
//    }
//}
