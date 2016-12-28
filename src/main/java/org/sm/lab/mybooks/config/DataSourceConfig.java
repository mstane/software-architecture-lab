package org.sm.lab.mybooks.config;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Profile("prod")
@Configuration
public class DataSourceConfig {

	private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

	/**
	 * This is used to convert DATABASE_URL Heroku Postgres add-on convention
	 * 
	 * postgres://<username>:<password>@<host>:<port>/<dbname>
	 * 
	 * to Postgres JDBC driver convention
	 * 
	 * jdbc:postgresql://<host>:<port>/<dbname>?user=<username>&password=
	 * <password>
	 * 
	 * @return
	 */
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = null;
		String databaseUrl = System.getenv("DATABASE_URL");

		if (databaseUrl != null && !databaseUrl.isEmpty()) {
			try {
				URI dbUri = new URI(databaseUrl);

				String[] credentials = dbUri.getUserInfo().split(":");
				String username = credentials[0];
				String password;
				if (credentials.length > 1) {
					password = credentials[1];
				} else {
					password = "";
				}

				String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?"
						+ dbUri.getQuery();

				dataSource = new DriverManagerDataSource();
				dataSource.setUrl(dbUrl);
				dataSource.setUsername(username);
				dataSource.setPassword(password);
				dataSource.setDriverClassName("org.postgresql.Driver");

			} catch (URISyntaxException e) {
				logger.error("", e);
			}

		}

		return dataSource;
	}

}
