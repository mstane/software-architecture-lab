package org.sm.lab.mybooks;


import org.springframework.cassandra.config.java.AbstractCqlTemplateConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories("org.sm.lab.mybooks.repository")
public class DatabaseConfiguration extends AbstractCqlTemplateConfiguration  {

	@Override
	protected String getKeyspaceName() {
		return "mybooks";
	}

}
