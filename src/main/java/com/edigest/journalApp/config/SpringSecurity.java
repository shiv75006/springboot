// Declares the package where this class belongs
package com.edigest.journalApp.config;

// Imports the custom UserDetailsService implementation for authentication
import com.edigest.journalApp.services.UserDetailsServiceimpl;
// Imports Spring's Autowired annotation for dependency injection
import org.springframework.beans.factory.annotation.Autowired;

// Imports Spring's Bean annotation for defining beans in the application context
import org.springframework.context.annotation.Bean;
// Imports Spring's Configuration annotation to indicate that this class contains bean definitions
import org.springframework.context.annotation.Configuration;
// Imports Spring Security's Customizer interface for configuring security components
import org.springframework.security.config.Customizer;
// Imports Spring Security's AuthenticationManagerBuilder for building authentication mechanisms
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// Imports Spring Security's HttpSecurity for configuring web-based security
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// Imports Spring Security's EnableWebSecurity annotation to enable web security
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// Imports Spring Security's BCryptPasswordEncoder for password hashing
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// Imports Spring Security's PasswordEncoder interface for encoding passwords
import org.springframework.security.crypto.password.PasswordEncoder;
// Imports Spring Security's SecurityFilterChain for defining security filter chains
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security Configuration is responsible for setting up the application's security framework.
 * It defines authentication mechanisms, authorization rules, password encoding, and secures HTTP endpoints.
 * This class configures which URLs are accessible to which users based on their roles and authentication status.
 */

// Marks this class as a configuration class that defines beans
@Configuration
// Enables Spring Security's web security support
@EnableWebSecurity
// Defines the Spring Security configuration class
public class SpringSecurity {

    // Injects the custom UserDetailsService implementation for authentication
    @Autowired
    // Marks this dependency as lazy-initialized to avoid circular dependency issues
    @org.springframework.context.annotation.Lazy
    // Declares a private field for the UserDetailsService implementation
    private UserDetailsServiceimpl userDetailsService;

    // Defines a bean for the security filter chain
    @Bean
    // Defines a method that configures and returns a SecurityFilterChain
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Returns a configured security filter chain
        return http
                // Configures authorization rules
                .authorizeRequests()
                    // Allows all users (authenticated and unauthenticated) to access URLs starting with "/public/"
                    .antMatchers("/public/**").permitAll()
                    // Restricts access to URLs starting with "/journal/" to authenticated users only
                    .antMatchers("/journal/**").authenticated()
                    // Restricts access to URLs starting with "/admin/" to users with the "ADMIN" role
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    // Allows all users to access any other URL
                    .anyRequest().permitAll()
                // Combines the current configuration with additional configurations
                .and()
                // Configures HTTP Basic authentication with default settings
                .httpBasic(Customizer.withDefaults())
                // Disables CSRF protection
                .csrf().disable()
                // Builds the security filter chain
                .build();
    }

    // Marks this method for autowiring by Spring
    @Autowired
    // Defines a method to configure global authentication
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Configures authentication to use the custom UserDetailsService and BCrypt password encoder
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    // Defines a bean for password encoding
    @Bean
    // Defines a method that returns a PasswordEncoder
    public PasswordEncoder passwordEncoder() {
        // Creates and returns a new BCryptPasswordEncoder for secure password hashing
        return new BCryptPasswordEncoder();
    }
}
