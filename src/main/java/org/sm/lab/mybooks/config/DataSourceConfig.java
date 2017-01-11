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
     * postgres&#58;//&lt;username&gt;&#58;&lt;password&gt;@&lt;host&gt;&#58;&lt;port&gt;/&lt;dbname&gt;
     * 
     * to Postgres JDBC driver convention
     * 
     * jdbc&#58;postgresql&#58;//&lt;host&gt;&#58;&lt;port&gt;/&lt;dbname&gt;?user=&lt;username&gt;&amp;password=&lt;password&gt;
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

                if (credentials.length > 1) {

                    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath()
                            + "?" + dbUri.getQuery();

                    dataSource = new DriverManagerDataSource();
                    dataSource.setUrl(dbUrl);
                    dataSource.setUsername(credentials[0]);
                    dataSource.setPassword(credentials[1]);
                    dataSource.setDriverClassName("org.postgresql.Driver");
                } else {
                    logger.error("Credentials for database are not properly set.");
                }

            } catch (URISyntaxException e) {
                logger.error("", e);
            }

        }

        return dataSource;
    }

}
