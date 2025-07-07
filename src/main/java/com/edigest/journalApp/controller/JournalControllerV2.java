// Declares the package where this class belongs
package com.edigest.journalApp.controller;

// REST API for MongoDB database

// Imports the JournalEntry entity class
import com.edigest.journalApp.entity.JournalEntry;
// Imports the User entity class
import com.edigest.journalApp.entity.User;
// Imports the JournalEntryService for handling journal entry-related business logic
import com.edigest.journalApp.services.JournalEntryService;
// Imports the UserService for handling user-related business logic
import com.edigest.journalApp.services.UserService;
// Imports MongoDB's ObjectId type for document identifiers
import org.bson.types.ObjectId;
// Imports Spring's Autowired annotation for dependency injection
import org.springframework.beans.factory.annotation.Autowired;
// Imports Spring's HttpStatus enum for HTTP response status codes
import org.springframework.http.HttpStatus;
// Imports Spring's ResponseEntity class for HTTP responses
import org.springframework.http.ResponseEntity;
// Imports Spring's web annotations for REST controllers
import org.springframework.web.bind.annotation.*;

// Imports Java's LocalDateTime class for date and time (though not used directly in this class)
import java.time.LocalDateTime;
// Imports Java's List interface for collections
import java.util.List;


/**
 * A Controller is a specialized component in Spring MVC that handles HTTP requests.
 * Controllers receive and process user input, interact with services to perform business logic,
 * and return appropriate responses. They serve as the entry point for client requests in a web application.
 */

// Marks this class as a REST controller that handles HTTP requests
@RestController
// Maps this controller to handle requests to the "/journal" path
@RequestMapping("/journal")
// Defines the JournalControllerV2 class
public class JournalControllerV2 {

    // Injects the JournalEntryService dependency
    @Autowired
    // Declares a private field for the JournalEntryService
    private JournalEntryService journalEntryService;

    // Injects the UserService dependency
    @Autowired
    // Declares a private field for the UserService
    private UserService userService;

    // Maps HTTP GET requests to this method with a path variable for userName
    @GetMapping("{userName}")
    // Defines a method to get all journal entries for a specific user
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
        // Finds the user in the database by username
        User user = userService.findByUserName(userName);
        // Gets the list of journal entries associated with the user
        List<JournalEntry> all = user.getJournalEntries();
        // Checks if the list is not null and not empty
        if(all != null && !all.isEmpty()){
            // Returns the list of journal entries with OK status (200)
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        // Returns NO_CONTENT status (204) if no journal entries are found
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Maps HTTP POST requests to this method with a path variable for userName
    @PostMapping("{userName}")
    // Defines a method to create a new journal entry for a specific user
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName){
     // Tries to save the entry
     try{
         // Calls the service method to save the journal entry for the specified user
         journalEntryService.saveEntry(myEntry, userName);
         // Returns CREATED status (201) if successful
         return new ResponseEntity<>(HttpStatus.CREATED);
     } catch(Exception e){
         // Returns BAD_REQUEST status (400) if an error occurs
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
     }
    }

    // Maps HTTP GET requests to this method with a path variable for myId
    @GetMapping("/id/{myId}")
    // Defines a method to get a journal entry by its ID
    public JournalEntry getEntryById(@PathVariable ObjectId myId){
        // Calls the service method to get the entry by ID, returns null if not found
        return journalEntryService.getEntryById(myId).orElse(null);
    }

    // Maps HTTP DELETE requests to this method with path variables for userName and myId
    @DeleteMapping("/id/{userName}/{myId}")
    // Defines a method to delete a journal entry by its ID for a specific user
    public String deleteById(@PathVariable ObjectId myId, @PathVariable String userName){
         // Calls the service method to delete the entry by ID for the specified user
         journalEntryService.deleteEntryById(myId, userName);
        // Returns a success message
        return "record deleted";
    }

    // Maps HTTP PUT requests to this method with path variables for userName and id
    @PutMapping("/id/{userName}/{id}")
    // Defines a method to update a journal entry by its ID for a specific user
    public ResponseEntity<?> updateById(
            // Path variable for the journal entry ID
            @PathVariable ObjectId id,
            // Path variable for the username
            @PathVariable String userName,
            // Request body containing the updated journal entry
            @RequestBody JournalEntry newEntry)
    {
       // Gets the existing journal entry by ID
       JournalEntry old = journalEntryService.getEntryById(id).orElse(null);
       // Checks if the entry exists
       if(old != null){
           // Updates the title if the new title is not null and not empty, otherwise keeps the old title
           old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
           // Updates the content if the new content is not null and not empty, otherwise keeps the old content
           old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
           // Saves the updated journal entry
           journalEntryService.saveEntry(old);
           // Returns the updated entry with OK status (200)
           return new ResponseEntity<>(old, HttpStatus.OK);
       }

        // Returns NOT_FOUND status (404) if the entry doesn't exist
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
