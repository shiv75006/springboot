package com.edigest.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
	}

	//platform transaction manager is required to enable transactional context in the project
	//MongoTransaction manager implements PlatformTransactionManager and provides functionalities to put it in use
	//dbFactory is a variable that helps in maintaining connection(sessions) with database during transactions(transactional context)
	@Bean
	public PlatformTransactionManager add(MongoDatabaseFactory dbFactory)
	{
		return new MongoTransactionManager(dbFactory);
	}

}
