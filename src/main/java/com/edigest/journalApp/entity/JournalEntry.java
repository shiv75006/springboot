// Declares the package where this class belongs
package com.edigest.journalApp.entity;

// Imports Lombok's Data annotation which automatically generates getters, setters, equals, hashCode, and toString methods
import lombok.Data;
// Imports Lombok's Getter annotation (though not used directly in this class)
import lombok.Getter;
// Imports Lombok's Setter annotation (though not used directly in this class)
import lombok.Setter;
// Imports MongoDB's ObjectId type used for document identifiers
import org.bson.types.ObjectId;
// Imports Spring Data's Id annotation to mark the primary key field
import org.springframework.data.annotation.Id;
// Imports Spring Data MongoDB's Document annotation to mark this class as a MongoDB document
import org.springframework.data.mongodb.core.mapping.Document;

// Imports Java's LocalDateTime class for representing date and time
import java.time.LocalDateTime;
// Imports Java's Date class (though not used directly in this class)
import java.util.Date;

/**
 * An Entity in Spring Data MongoDB represents a document that is stored in a MongoDB collection.
 * This class maps Java objects to MongoDB documents, allowing the application to interact with 
 * the database using object-oriented programming. Each instance of this class represents a single journal entry.
 */

// Marks this class as a MongoDB document and specifies the collection name as "journal_entries"
@Document(collection = "journal_entries")
// Lombok annotation that generates all the boilerplate code typically associated with POJOs
@Data
// Defines the JournalEntry class which represents a journal entry in the application
public class JournalEntry {

    // Marks this field as the document's primary key
    @Id
    // Declares an ObjectId field to store the unique identifier for each journal entry document
    private ObjectId id;
    // Declares a String field to store the title of the journal entry
    private String title;
    // Declares a String field to store the content of the journal entry (note: variable name starts with uppercase 'C')
    private String Content;
    // Declares a LocalDateTime field to store the date and time when the journal entry was created
    private LocalDateTime date;

}
