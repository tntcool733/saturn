 package com.saturn.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wenziheng
 * @date 2019/06/23
 */
@Slf4j
@Configuration
public class DruidConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DruidDataSource druidDataSource() {
        log.info("[cmd=druidDataSource begin to init]");
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        log.info("[cmd=druidDataSource dataSource maxActive:{}]", dataSource.getMaxActive());
        return dataSource;
    }

}
