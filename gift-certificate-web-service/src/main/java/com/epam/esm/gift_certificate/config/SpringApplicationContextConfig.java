package com.epam.esm.gift_certificate.config;

import com.epam.esm.gift_certificate.util.Validation;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
@EnableWebMvc
@EnableTransactionManagement
@PropertySource("classpath:appContext.properties")
public class SpringApplicationContextConfig {

    @Bean
    @Profile("dev")
    public DataSource hikariDataSource(@Value("${db.url}") String url
            , @Value("${db.user}") String user, @Value("${db.password}") String password) {

        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(user);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    @Profile("prod")
    public DataSource dataSource(@Value("${db.url}") String url
            , @Value("${db.user}") String user, @Value("${db.password}") String password) {

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setInitialSize(20);
        dataSource.setMaxActive(30);
        return dataSource;
    }


    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasenames("errorMessages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    @Bean
    public JdbcOperations jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public TransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public Validation validation(@Value("${availableSortTypes}") String availableSortTypes) {
        return new Validation(availableSortTypes);
    }

}
