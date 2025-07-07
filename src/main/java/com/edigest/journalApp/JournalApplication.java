// Declares the package where this class belongs
package com.edigest.journalApp;

// Imports Spring Boot's SpringApplication class which is used to bootstrap and launch a Spring application
import org.springframework.boot.SpringApplication;
// Imports Spring Boot's EnableAutoConfiguration annotation (though not used directly in this class)
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
// Imports Spring Boot's SpringBootApplication annotation which combines @Configuration, @EnableAutoConfiguration, and @ComponentScan
import org.springframework.boot.autoconfigure.SpringBootApplication;
// Imports Spring's Bean annotation for defining beans in the application context
import org.springframework.context.annotation.Bean;
// Imports Spring Data MongoDB's MongoDatabaseFactory interface for creating MongoDB database connections
import org.springframework.data.mongodb.MongoDatabaseFactory;
// Imports Spring Data MongoDB's MongoTransactionManager for managing MongoDB transactions
import org.springframework.data.mongodb.MongoTransactionManager;
// Imports Spring's PlatformTransactionManager interface for managing transactions
import org.springframework.transaction.PlatformTransactionManager;
// Imports Spring's EnableTransactionManagement annotation to enable Spring's annotation-driven transaction management
import org.springframework.transaction.annotation.EnableTransactionManagement;

// Marks this class as a Spring Boot application, combining @Configuration, @EnableAutoConfiguration, and @ComponentScan
@SpringBootApplication
// Enables Spring's annotation-driven transaction management capability
@EnableTransactionManagement
// Defines the main application class
public class JournalApplication {

	// The main method that serves as the entry point for the application
	public static void main(String[] args) {
		// Launches the Spring application by running the JournalApplication class
		SpringApplication.run(JournalApplication.class, args);
	}

	// Platform transaction manager is required to enable transactional context in the project
	// MongoTransaction manager implements PlatformTransactionManager and provides functionalities to put it in use
	// dbFactory is a variable that helps in maintaining connection(sessions) with database during transactions(transactional context)

	// Defines a bean that will be managed by the Spring container
	@Bean
	// Defines a method that returns a PlatformTransactionManager and takes a MongoDatabaseFactory as a parameter
	public PlatformTransactionManager add(MongoDatabaseFactory dbFactory)
	{
		// Creates and returns a new MongoTransactionManager using the provided database factory
		return new MongoTransactionManager(dbFactory);
	}

}
