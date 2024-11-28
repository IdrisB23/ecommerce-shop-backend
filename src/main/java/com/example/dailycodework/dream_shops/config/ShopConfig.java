package com.example.dailycodework.dream_shops.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.modelmapper.ModelMapper;

@Configuration
public class ShopConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
