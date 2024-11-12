package com.topkey.scheduler.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {"com.topkey.scheduler.erp"},
    entityManagerFactoryRef = "erpOracleEntityManagerFactory",
    transactionManagerRef = "erpOracleTransactionManager"
)
public class ErpOracleJpaConfig {
}