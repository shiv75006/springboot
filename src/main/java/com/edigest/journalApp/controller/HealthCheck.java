// Declares the package where this class belongs
package com.edigest.journalApp.controller;

// Imports Spring's GetMapping annotation for mapping HTTP GET requests to methods
import org.springframework.web.bind.annotation.GetMapping;
// Imports Spring's RestController annotation for creating RESTful web services
import org.springframework.web.bind.annotation.RestController;

/**
 * A RestController is a specialized controller used in RESTful web services.
 * It combines the @Controller and @ResponseBody annotations, meaning every method 
 * returns a domain object instead of a view, and the data is directly written to the HTTP response as JSON or XML.
 */

// Marks this class as a REST controller that handles HTTP requests
@RestController
// Defines the HealthCheck class for monitoring application health
public class HealthCheck {

    // Maps HTTP GET requests to "/health-check" path to this method
    @GetMapping("/health-check")
    // Defines a method that returns a simple health status
    public String healthCheck(){
        // Returns "OK" string to indicate the application is running properly
        return "OK";
    }

}
