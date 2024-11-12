package com.topkey.scheduler.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class DataSourceConfig {

    // Oracle資料來源
    @Primary
    @Bean(name = "erpOracleDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    DataSource oracleDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    // MySQL資料來源
    @Bean(name = "oaMysqlDataSource")
    @ConfigurationProperties(prefix = "spring.mysql-datasource")
    DataSource mysqlDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Primary
    @Bean(name = "erpOracleEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean oracleEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("erpOracleDataSource") DataSource oracleDataSource) {
        return builder
                .dataSource(oracleDataSource)
                .packages("com.topkey.scheduler.erp")  // 替換成 Oracle 實體類別的 package
                .persistenceUnit("oracle")
                .build();
    }

    @Bean(name = "oaMysqlEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("oaMysqlDataSource") DataSource mysqlDataSource) {
        return builder
                .dataSource(mysqlDataSource)
                .packages("com.topkey.scheduler.oa")  // 替換成 MySQL 實體類別的 package
                .persistenceUnit("mysql")
                .build();
    }

    @Primary
    @Bean(name = "erpOracleTransactionManager")
    PlatformTransactionManager oracleTransactionManager(
            @Qualifier("erpOracleEntityManagerFactory") EntityManagerFactory oracleEntityManagerFactory) {
        return new JpaTransactionManager(oracleEntityManagerFactory);
    }

    @Bean(name = "oaMysqlTransactionManager")
    PlatformTransactionManager mysqlTransactionManager(
            @Qualifier("oaMysqlEntityManagerFactory") EntityManagerFactory mysqlEntityManagerFactory) {
        return new JpaTransactionManager(mysqlEntityManagerFactory);
    }
}
