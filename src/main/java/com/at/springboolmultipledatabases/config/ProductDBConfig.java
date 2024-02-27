package com.at.springboolmultipledatabases.config;



import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "productEntityManagerFactory",
    transactionManagerRef = "productTransactionManager",
    basePackages = {
        "com.at.springboolmultipledatabases.product.repository"
    }
)
public class ProductDBConfig {

  @Bean(name = "productDataSource")
  @ConfigurationProperties(prefix = "spring.product.datasource")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "productEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean productEntityManagerFactory(EntityManagerFactoryBuilder builder,
      @Qualifier("productDataSource") DataSource dataSource) {

    HashMap<String, Object> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto", "update");

    return builder.dataSource(dataSource)
        .properties(properties)
        .packages("com.at.springboolmultipledatabases.product.model")
        .persistenceUnit("Product")
        .build();
  }

  @Bean(name = "productTransactionManager")
  public PlatformTransactionManager bookTransactionManager(@Qualifier("productEntityManagerFactory") EntityManagerFactory productEntityManagerFactory) {
    return new JpaTransactionManager(productEntityManagerFactory);
  }
}