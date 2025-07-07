// Declares the package where this class belongs
package com.edigest.journalApp.services;

// Imports the User entity class
import com.edigest.journalApp.entity.User;
// Imports the UserRepository interface for database operations
import com.edigest.journalApp.repository.UserRepository;
// Imports Spring's Autowired annotation for dependency injection
import org.springframework.beans.factory.annotation.Autowired;
// Imports Spring Security's UserDetails interface for representing user information
import org.springframework.security.core.userdetails.UserDetails;
// Imports Spring Security's UserDetailsService interface for loading user-specific data
import org.springframework.security.core.userdetails.UserDetailsService;
// Imports Spring Security's UsernameNotFoundException for authentication errors
import org.springframework.security.core.userdetails.UsernameNotFoundException;
// Imports Spring's Component annotation to mark this class as a Spring component
import org.springframework.stereotype.Component;

// Marks this class as a Spring component to be managed by the Spring container
@Component
// Defines the UserDetailsServiceimpl class that implements Spring Security's UserDetailsService interface
public class UserDetailsServiceimpl implements UserDetailsService {

    // Injects the UserRepository dependency
    @Autowired
    // Declares a private field for the UserRepository
    private UserRepository userRep;

    // Overrides the loadUserByUsername method from the UserDetailsService interface
    @Override
    // Defines a method to load user details by username for authentication
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Finds the user in the database by username
        User user = userRep.findByUserName(username);
        // Checks if the user exists
        if(user != null){
            // Creates and returns a Spring Security User object with the user's details
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())  // Sets the username from our database user
                    .password(user.getPassword()) // Sets the password from our database user
                    .roles(user.getRoles().toArray(new String[0])) // Sets the roles from our database user
                    .build();

        }
        // Throws an exception if the user is not found
        throw new UsernameNotFoundException("User not found");
    }
}
