package com.cjstore.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class ApiApplication {
	public static void main(String[] args) {
		String dbUrl = System.getenv("DATABASE_URL");
		if (dbUrl != null && dbUrl.startsWith("postgres://")) {
			try {
				URI uri = new URI(dbUrl);
				String jdbcUrl = "jdbc:postgresql://" + uri.getHost() + ":"
						+ (uri.getPort() != -1 ? uri.getPort() : 5432) + uri.getPath();

				// Handle potential query parameters manually if needed, but simple join is
				// usually enough for Render
				if (uri.getQuery() != null) {
					jdbcUrl += "?" + uri.getQuery();
				}

				System.setProperty("spring.datasource.url", jdbcUrl);

				if (uri.getUserInfo() != null) {
					String[] userInfo = uri.getUserInfo().split(":");
					System.setProperty("spring.datasource.username", userInfo[0]);
					if (userInfo.length > 1) {
						System.setProperty("spring.datasource.password", userInfo[1]);
					}
				}
			} catch (URISyntaxException e) {
				System.err.println("Failed to parse DATABASE_URL: " + e.getMessage());
			}
		}

		SpringApplication.run(ApiApplication.class, args);
	}
}
