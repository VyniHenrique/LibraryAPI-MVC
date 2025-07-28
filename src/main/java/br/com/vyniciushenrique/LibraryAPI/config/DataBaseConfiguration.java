package br.com.vyniciushenrique.LibraryAPI.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DataBaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    @Value("${spring.datasource.driver-class-name}")
    String driver;

    @Bean
    public DataSource hikariDataSource(){

        HikariConfig config = new HikariConfig();
        config.setUsername(username);
        config.setPassword(password);
        config.setJdbcUrl(url);
        config.setDriverClassName(driver);

        config.setMaximumPoolSize(10);
        config.setMaxLifetime(60000);
        config.setIdleTimeout(10000);
        config.setMinimumIdle(1);
        config.setPoolName("library-db-pool");

        log.trace("Data souce aberto com o username: {} e a senha: {}", config.getUsername(), config.getPassword());
        log.info("Url do banco atual: {}", url);

        return new HikariDataSource(config);
    }

}
