package org.sm.lab.mybooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("org.sm.lab.mybooks.repository.data")
@EnableElasticsearchRepositories("org.sm.lab.mybooks.repository.index")
public class MyBooksApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBooksApplication.class, args);
	}


}
