package com.harera.hayat.common.config;

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
        NotNullableMapper modelMapper = new NotNullableMapper();
        modelMapper.getConfiguration().setPropertyCondition(isNotNull());
        return modelMapper;
    }
}
