package com.cjstore.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource() throws URISyntaxException {
        String databaseUrl = System.getenv("DATABASE_URL");
        if (databaseUrl == null || databaseUrl.isEmpty()) {
            // Default for local development
            return DataSourceBuilder.create()
                    .url("jdbc:postgresql://localhost:5432/cjstore")
                    .username("postgres")
                    .password("postgres")
                    .build();
        }

        URI dbUri = new URI(databaseUrl);
        String userInfo = dbUri.getUserInfo();
        String username = userInfo != null ? userInfo.split(":")[0] : "";
        String password = userInfo != null ? userInfo.split(":")[1] : "";

        // Convert postgres:// to jdbc:postgresql://
        String host = dbUri.getHost();
        int port = dbUri.getPort();
        String path = dbUri.getPath();

        String jdbcUrl = String.format("jdbc:postgresql://%s:%d%s", host, port == -1 ? 5432 : port, path);

        return DataSourceBuilder.create()
                .url(jdbcUrl)
                .username(username)
                .password(password)
                .build();
    }
}
