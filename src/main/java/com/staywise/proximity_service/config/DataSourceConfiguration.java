package com.staywise.proximity_service.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    @Value("${STAYWISE_PROXIMITY_SERVICE}")
    String jdbcUrl;


    @Value("${STAYWISE_USER_SERVICE_DATABASE_USERNAME}")
    String userName;

    @Value("${STAYWISE_USER_SERVICE_DATABASE_PASSWORD}")
    String password;

    @Bean
    @Profile("dev")
    public DataSource devDataSource()
    {
        HikariDataSource dataSource=new HikariDataSource();
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return dataSource;
    }

}

