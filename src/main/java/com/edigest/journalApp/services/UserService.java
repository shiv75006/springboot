// Declares the package where this class belongs
package com.edigest.journalApp.services;

// Contains business logic for API calls

// Imports the JournalEntry entity class (though not used directly in this class)
import com.edigest.journalApp.entity.JournalEntry;
// Imports the User entity class
import com.edigest.journalApp.entity.User;
// Imports the JournalEntryRepository interface (though not used directly in this class)
import com.edigest.journalApp.repository.JournalEntryRepository;
// Imports the UserRepository interface for database operations
import com.edigest.journalApp.repository.UserRepository;
// Imports MongoDB's ObjectId type for document identifiers
import org.bson.types.ObjectId;
// Imports Spring's Autowired annotation for dependency injection
import org.springframework.beans.factory.annotation.Autowired;
// Imports Spring Security's BCryptPasswordEncoder for password hashing
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// Imports Spring Security's PasswordEncoder interface
import org.springframework.security.crypto.password.PasswordEncoder;
// Imports Spring's Component annotation to mark this class as a Spring component
import org.springframework.stereotype.Component;

// Imports Java's Arrays utility class (though not used directly in this class)
import java.util.Arrays;
// Imports Java's List interface for collections
import java.util.List;
// Imports Java's Optional class for null-safe operations
import java.util.Optional;

// Marks this class as a Spring component to be managed by the Spring container
@Component
// Defines the UserService class for user-related business logic
public class UserService {

    // Injects the UserRepository dependency
    @Autowired  // Dependency injection for UserRepository (used to perform database operations)
    // Declares a private field for the UserRepository
    private UserRepository userRep;

    // An instance of BCrypt password encoder for secure password hashing
    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    // Defines a method to save an existing user
    public void saveEntry(User user){ 
        // Calls the repository's save method to persist the user to the database
        userRep.save(user); // Repository interface called
    }

    // Defines a method to save a new user with encoded password and default role
    public void saveNewUser(User user){
      // Encodes the user's password using BCrypt before saving
      user.setPassword(encoder.encode((user.getPassword())));
      // Sets the default role for new users to "USER"
      user.setRoles(List.of("USER"));
      // Saves the user to the database
      userRep.save(user);
    }

    // Defines a method to get all users from the database
    public List<User> getAllEntries()
    {
        // Calls the repository's findAll method to retrieve all users
        return userRep.findAll();
    }

    // Defines a method to get a user by ID
    public Optional<User> getEntryById(ObjectId myid){
        // Calls the repository's findById method to retrieve a user by ID
        return userRep.findById(myid);
    }

    // Defines a method to delete a user by ID
    public void deleteEntryById(ObjectId myid){
        // Calls the repository's deleteById method to remove a user from the database
        userRep.deleteById(myid);
    }

    // Defines a method to find a user by username
    public User findByUserName(String userName){
        // Calls the repository's custom findByUserName method
        return userRep.findByUserName(userName);
    }

    // Defines a method to encode a password using BCrypt
    public String encodePassword(String password) {
        // Uses the BCryptPasswordEncoder to hash the password
        return encoder.encode(password);
    }
}
