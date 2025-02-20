package es.atz.software.products.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfig {

	@Bean
    @Primary
    @ConfigurationProperties("spring.datasource.products")
    public DataSourceProperties productsDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource productsDataSource() {
        return productsDataSourceProperties().initializeDataSourceBuilder().build();
    }

}
