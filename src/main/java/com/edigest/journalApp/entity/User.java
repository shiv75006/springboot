// Declares the package where this class belongs
package com.edigest.journalApp.entity;

// Imports Lombok's Data annotation which automatically generates getters, setters, equals, hashCode, and toString methods
import lombok.Data;
// Imports Lombok's NonNull annotation which generates null-check for annotated fields
import lombok.NonNull;
// Imports MongoDB's ObjectId type used for document identifiers
import org.bson.types.ObjectId;
// Imports Spring's Autowired annotation for dependency injection (though not used in this class)
import org.springframework.beans.factory.annotation.Autowired;
// Imports Spring Data's Id annotation to mark the primary key field
import org.springframework.data.annotation.Id;
// Imports Spring Data MongoDB's Indexed annotation for creating database indexes
import org.springframework.data.mongodb.core.index.Indexed;
// Imports Spring Data MongoDB's DBRef annotation for document references
import org.springframework.data.mongodb.core.mapping.DBRef;
// Imports Spring Data MongoDB's Document annotation to mark this class as a MongoDB document
import org.springframework.data.mongodb.core.mapping.Document;

// Imports Java's LocalDateTime class for date and time operations (though not used in this class)
import java.time.LocalDateTime;
// Imports Java's ArrayList class for implementing the List interface
import java.util.ArrayList;
// Imports Java's List interface for managing collections of elements
import java.util.List;

/**
 * A Document Entity in MongoDB represents a record in a NoSQL database collection.
 * Unlike traditional relational databases, MongoDB stores data in flexible, JSON-like documents.
 * This User entity contains authentication information, roles for authorization,
 * and references to associated journal entries through a document reference relationship.
 */

// Marks this class as a MongoDB document and specifies the collection name as "users"
@Document(collection = "users")
// Lombok annotation that generates all the boilerplate code typically associated with POJOs
@Data
// Defines the User class which represents a user in the application
public class User {

    // Marks this field as the document's primary key
    @Id
    // Declares an ObjectId field to store the unique identifier for each user document
    private ObjectId id;

    // Creates a unique index on this field in MongoDB to ensure usernames are unique
    @Indexed(unique = true)
    // Marks this field as non-null, Lombok will generate null checks in constructors and setters
    @NonNull
    // Declares a String field to store the user's username
    private String userName;

    // Marks this field as non-null, Lombok will generate null checks in constructors and setters
    @NonNull
    // Declares a String field to store the user's password
    private String password;

    // Declares a List of Strings to store the user's roles for authorization purposes
    private List<String> roles;

    // Creates a database reference relationship to JournalEntry documents
    @DBRef
    // Declares a List of JournalEntry objects and initializes it with an empty ArrayList
    private List<JournalEntry> journalEntries= new ArrayList<>();

}
