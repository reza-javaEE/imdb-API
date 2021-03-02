package com.vodafone.imdbdemo.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

/**
 * Created By : rezaee_r
 * Date : 2/25/2021
 **/

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/mydb?autoReconnect=true&useSSL=false");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("test1234");
        return dataSourceBuilder.build();
    }

}
