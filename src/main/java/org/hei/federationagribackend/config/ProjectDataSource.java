package org.hei.federationagribackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class ProjectDataSource {

    private final Dotenv dotenv = Dotenv.load();

    @Bean
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    dotenv.get("JDBC_URL"),
                    dotenv.get("DB_USER"),
                    dotenv.get("DB_PASSWORD")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
