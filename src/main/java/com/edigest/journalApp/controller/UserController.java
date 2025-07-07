// Declares the package where this class belongs
package com.edigest.journalApp.controller;

// Imports the User entity class
import com.edigest.journalApp.entity.User;
// Imports the UserService for handling user-related business logic
import com.edigest.journalApp.services.UserService;
// Imports Lombok's Getter annotation (though not used directly in this class)
import lombok.Getter;
// Imports MongoDB's ObjectId type (though not used directly in this class)
import org.bson.types.ObjectId;
// Imports Spring's Autowired annotation for dependency injection
import org.springframework.beans.factory.annotation.Autowired;
// Imports Spring's HttpStatus enum for HTTP response status codes
import org.springframework.http.HttpStatus;
// Imports Spring's ResponseEntity class for HTTP responses
import org.springframework.http.ResponseEntity;
// Imports Spring's Component annotation (though not used directly in this class)
import org.springframework.stereotype.Component;
// Imports Spring's web annotations for REST controllers
import org.springframework.web.bind.annotation.*;

// Imports Java's List interface for collections
import java.util.List;

/**
 * A RestController in Spring is responsible for handling web requests and returning data.
 * It maps HTTP requests to specific methods, processes the request data, and returns 
 * responses in formats like JSON. This controller specifically manages user-related operations.
 */

// Marks this class as a REST controller that handles HTTP requests
@RestController
// Maps this controller to handle requests to the "/user" path
@RequestMapping("/user")
// Defines the UserController class
public class UserController {

    // Injects the UserService dependency
    @Autowired
    // Declares a private field for the UserService
    private UserService userService;

    // Maps HTTP GET requests to this method
    @GetMapping
    // Defines a method that returns a list of all users
    public List<User> getAllUsers()
    {
      // Calls the service method to retrieve all user entries
      return userService.getAllEntries();
    }

    // Maps HTTP POST requests to this method
    @PostMapping
    // Defines a method to create a new user
    public void createUser(@RequestBody User user){
        // Calls the service method to save a new user
        userService.saveNewUser(user);
    }

    // Maps HTTP PUT requests to this method with a path variable for userName
    @PutMapping("/{userName}")
    // Defines a method to update an existing user
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName){
        // Finds the user in the database by username
        User userIndb = userService.findByUserName(userName);
        // Checks if the user exists
        if(userIndb != null){
            // Updates the username with the new value
            userIndb.setUserName(user.getUserName());
            // Updates and encodes the password with the new value
            userIndb.setPassword(userService.encodePassword(user.getPassword()));
            // Checks if the user has any roles
            if (userIndb.getRoles() == null || userIndb.getRoles().isEmpty()) {
                // Sets a default "USER" role if no roles are present
                userIndb.setRoles(List.of("USER"));
            }
            // Saves the updated user to the database
            userService.saveEntry(userIndb);
        }
        // Returns a response with NO_CONTENT status (204)
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
