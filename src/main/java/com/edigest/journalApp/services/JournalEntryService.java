// Declares the package where this class belongs
package com.edigest.journalApp.services;

// Contains business logic for API calls

// Imports the JournalEntry entity class
import com.edigest.journalApp.entity.JournalEntry;
// Imports the User entity class
import com.edigest.journalApp.entity.User;
// Imports the JournalEntryRepository interface for database operations
import com.edigest.journalApp.repository.JournalEntryRepository;
// Imports MongoDB's ObjectId type for document identifiers
import org.bson.types.ObjectId;
// Imports Spring's Autowired annotation for dependency injection
import org.springframework.beans.factory.annotation.Autowired;
// Imports Spring's Component annotation to mark this class as a Spring component
import org.springframework.stereotype.Component;
// Imports Spring's Transactional annotation for transaction management
import org.springframework.transaction.annotation.Transactional;

// Imports Java's LocalDateTime class for date and time operations
import java.time.LocalDateTime;
// Imports Java's List interface for collections
import java.util.List;
// Imports Java's Optional class for null-safe operations
import java.util.Optional;

// Marks this class as a Spring component to be managed by the Spring container
@Component
// Defines the JournalEntryService class for journal entry-related business logic
public class JournalEntryService {

    // Injects the JournalEntryRepository dependency
    @Autowired  // Dependency injection for JournalEntryRepository (used to perform database operations)
    // Declares a private field for the JournalEntryRepository
    private JournalEntryRepository journalEnRep;

    // Injects the UserService dependency
    @Autowired
    // Marks this dependency as lazy-initialized to avoid circular dependency issues
    @org.springframework.context.annotation.Lazy
    // Declares a private field for the UserService
    private UserService userService;


    // Method to get user, set date and save journal entry data to MongoDB database
    // Marks this method as transactional, ensuring all database operations succeed or fail together
    @Transactional
    // Defines a method to save a new journal entry for a specific user
    public void saveEntry(JournalEntry journalEntry, String userName){
      // Tries to save the entry and handle any exceptions
      try {
          // Finds the user in the database by username
          User user = userService.findByUserName(userName); 
          // Sets the current date and time for the journal entry
          journalEntry.setDate(LocalDateTime.now()); 
          // Saves the journal entry to the database and gets the saved entry
          JournalEntry save = journalEnRep.save(journalEntry);
          // Adds the saved journal entry to the user's list of journal entries
          user.getJournalEntries().add(save);
          // Saves the updated user to the database
          userService.saveEntry(user);
      } catch(Exception e){
          // Prints the exception message to the console
          System.out.println(e.getMessage());
          // Throws a runtime exception with a custom message
          throw new RuntimeException("Error saving entry");
      }
    }


    // Method for updating an existing journal entry (used for PUT requests)
    // Defines a method to save an existing journal entry
    public void saveEntry(JournalEntry journalEntry){
        // Calls the repository's save method to persist the journal entry to the database
        journalEnRep.save(journalEntry);
    }


    // Method to get all journal entries from the database
    // Defines a method to get all journal entries
    public List<JournalEntry> getAllEntries()
    {
        // Calls the repository's findAll method to retrieve all journal entries
        return journalEnRep.findAll();
    }

    // Defines a method to get a journal entry by ID
    public Optional<JournalEntry> getEntryById(ObjectId myid){
        // Calls the repository's findById method to retrieve a journal entry by ID
        return journalEnRep.findById(myid);
    }

    // Defines a method to delete a journal entry by ID for a specific user
    public void deleteEntryById(ObjectId myid, String userName){
        // Gets the user by username
        User user = userService.findByUserName(userName); 
        // Removes the journal entry with the specified ID from the user's list of journal entries
        user.getJournalEntries().removeIf(x -> x.getId().equals(myid)); 
        // Saves the updated user to the database
        userService.saveEntry(user); 
        // Deletes the journal entry from the database
        journalEnRep.deleteById(myid);
    }
}
