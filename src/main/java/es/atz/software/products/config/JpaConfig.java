package es.atz.software.products.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"es.atz.software.products.repository"},
        entityManagerFactoryRef = "productsEntityManagerFactory",
        transactionManagerRef = "productsTransactionManager")

public class JpaConfig {

	@Autowired
    private Environment env;

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.products.jpa")
    public LocalContainerEntityManagerFactoryBean productsEntityManagerFactory(
            @Qualifier("productsDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(
                new String[] {"es.atz.software.products.domain"});

        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.datasource.products.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.datasource.products.jpa.properties.hibernate.dialect"));
        properties.put("hibernate.physical_naming_strategy", env.getProperty("spring.datasource.products.jpa.hibernate.naming.physical-strategy"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    @Primary
    public PlatformTransactionManager productsTransactionManager(
            @Qualifier("productsEntityManagerFactory") LocalContainerEntityManagerFactoryBean productsEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(productsEntityManagerFactory.getObject()));
    }

}
