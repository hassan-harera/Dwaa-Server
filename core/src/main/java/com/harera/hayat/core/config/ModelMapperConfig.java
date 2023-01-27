package com.harera.hayat.core.config;

import static org.modelmapper.Conditions.isNotNull;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ModelMapperConfig {

    @Bean
    @Primary
    public ModelMapper modelMapper() {
        com.harera.hayat.core.config.NotNullableMapper modelMapper =
                        new com.harera.hayat.core.config.NotNullableMapper();
        modelMapper.getConfiguration().setPropertyCondition(isNotNull());
        return modelMapper;
    }
}
